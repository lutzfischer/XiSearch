/* 
 * Copyright 2016 Lutz Fischer <l.fischer@ed.ac.uk>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rappsilber.ms.dataAccess.calibration;

import rappsilber.ms.dataAccess.*;
import rappsilber.ms.ToleranceUnit;
import rappsilber.ms.spectra.Spectra;
import rappsilber.ms.spectra.SpectraPeak;
import rappsilber.utils.LinearRegression2D;

//TODO : expected peak 

/**
 *
 * @author Lutz Fischer <l.fischer@ed.ac.uk>
 */
public class CalibrateLinearRegression extends StreamingCalibrate {

    protected double[] m_targetMZ;
    protected ToleranceUnit m_initialTolerance;
    /** linear regression used to estimate the m/z error baesed on all found target peaks within the current spectra **/
    private LinearRegression2D m_InSpectraRegression = new LinearRegression2D();
    /**
     * In cases where there are not enough target peaks found to make a proper
     * linear regression, try to predict the result of the linear regression
     * baesed on the precursor mass.
     * This is used to predict the slope of the linear error function
     */
    private LinearRegression2D m_InSearchRegressionSlope = new LinearRegression2D();
    /**
     * In cases where there are not enough target peaks found to make a proper
     * linear regression, try to predict the result of the linear regression
     * baesed on the precursor mass.
     * This is used to predict the Y(error)-Intersection of the linear error function
     */
    private LinearRegression2D m_InSearchRegressionIntercept = new LinearRegression2D();



    public CalibrateLinearRegression(double targetMZ, ToleranceUnit initialTolerance) {
        this.m_targetMZ = new double[]{targetMZ};
        this.m_initialTolerance = initialTolerance;
    }

    public CalibrateLinearRegression(double[] targetMZ, ToleranceUnit initialTolerance) {
        this.m_targetMZ = targetMZ.clone();
        this.m_initialTolerance = initialTolerance;
    }

    public CalibrateLinearRegression(double targetMZ, ToleranceUnit initialTolerance, SpectraAccess reader) {
        this(targetMZ, initialTolerance);
        setReader(reader);
    }

    public CalibrateLinearRegression(double[] targetMZ, ToleranceUnit initialTolerance, SpectraAccess reader) {
        this(targetMZ, initialTolerance);
        setReader(reader);
    }






    @Override
    public void calibrate(Spectra s) {
        m_InSpectraRegression.clear();
        int count = 0;
        // collect the all found target peaks into the linear regression
        for (double targetMZ : m_targetMZ) {
            SpectraPeak tp = s.getPeakAt(targetMZ, m_initialTolerance);
            if (tp != null) {
                double expMZ = tp.getMZ();
                double error = targetMZ - expMZ;
                m_InSpectraRegression.addPair(expMZ, error);
                count++;
            }
        }

        // try to make an linear regression based on these
        if (m_InSpectraRegression.calc()) {

            double slope = m_InSpectraRegression.getSlope();
            double intercept = m_InSpectraRegression.getYInterxcept();
            recalcSpectra(s, intercept, slope);

            // seems to work so "remember" the result of the regression for further predictions
            m_InSearchRegressionIntercept.addPair(s.getPrecurserMZ(), intercept);
            m_InSearchRegressionSlope.addPair(s.getPrecurserMZ(), slope);

        } else if (m_InSearchRegressionIntercept.calc() ){
            // we could not make a proper linear regression within the spectra
            // so we try to predict the slope and intercept of the error linear
            // function, based on previous predicted error functions and
            // the precoursor
            double slope = m_InSearchRegressionSlope.predictY(s.getPrecurserMZ());
            double intercept = m_InSearchRegressionIntercept.predictY(s.getPrecurserMZ());
            recalcSpectra(s, intercept, slope);

        } else if (count>0) { // if we found at least one value try to add 0 as a value

            m_InSpectraRegression.addPair(0, 0);
            if (m_InSpectraRegression.calc()) {
                double slope = m_InSpectraRegression.getSlope();
                double intercept = m_InSpectraRegression.getYInterxcept();
                recalcSpectra(s, intercept, slope);
            }

        }
    }

    /**
     * shifts every peak in the spectra based on an linear error function
     * @param s
     * @param intercept
     * @param slope
     */
    private void recalcSpectra(Spectra s, double intercept, double slope) {
        for (SpectraPeak sp : s) {
            double expMZ = sp.getMZ();
            double error = intercept + slope * expMZ;
            double correctedMZ = expMZ + error;
            sp.setMZ(correctedMZ);
        }
    }


}
