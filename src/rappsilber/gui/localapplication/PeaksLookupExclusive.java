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
package rappsilber.gui.localapplication;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import rappsilber.ms.ToleranceUnit;
import rappsilber.ms.dataAccess.filter.spectrafilter.ScanFilteredSpectrumAccess;
import rappsilber.ms.dataAccess.SpectraAccess;
import rappsilber.ms.dataAccess.msm.AbstractMSMAccess;
import rappsilber.gui.components.AutoAddTableModelListener;
import rappsilber.gui.components.GenericTextPopUpMenu;
import rappsilber.gui.logging.JMessageBoxHandle;

/**
 *
 * @author Lutz Fischer <l.fischer@ed.ac.uk>
 */
public class PeaksLookupExclusive extends javax.swing.JFrame {
//    private boolean m_filterChanging = false;

    /** Creates new form ConsistentPeaks */
    public PeaksLookupExclusive() {
        initComponents();
        JMessageBoxHandle errorLog = new JMessageBoxHandle(false);
        errorLog.setFilter(new Filter() {
            public boolean isLoggable(LogRecord record) {
                return true;
            }
        });

        errorLog.setLevel(Level.WARNING);

        Logger.getLogger("rappsilber").addHandler(errorLog);
        Logger.getLogger("rappsilber").setLevel(Level.ALL);
        Logger.getLogger("rappsilber").addHandler(errorLog);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Logger Connected");


        fbMSMFile.setDescription("MSM-File");
        fbMSMFile.setExtensions(new String[]{".msm",".msmlist"});
        fbCSVOut.setDescription("CSV-File");
        fbCSVOut.setExtensions(new String[]{".csv"});
        fbCSVOut.setSave();

        // replace the deault (nicly gui-generated) table model with a new one
        //tblScanFilter.setModel(new AutoAddTableModel(tblScanFilter.getModel()));

        tblMassFilter.getModel().addTableModelListener(new AutoAddTableModelListener());



//        tm.addTableModelListener(new TableModelListener() {
//
//            public void tableChanged(TableModelEvent e) {
//                if (m_filterChanging)
//                    return;
//                DefaultTableModel tm = ((DefaultTableModel)e.getSource());
//                int LastRow = tm.getRowCount() -1;
//                m_filterChanging = true;
//
//                if (tm.getValueAt(LastRow, 0) != null || tm.getValueAt(LastRow, 1) != null)
//                    tm.addRow(new Object[]{null,null});
//                if ((tm.getValueAt(e.getLastRow(), 0) == null || tm.getValueAt(e.getLastRow(), 0).toString().length() == 0)
//                        && tm.getValueAt(e.getLastRow(), 1) == null) {
//                    tm.removeRow(e.getLastRow());
//                }
//                m_filterChanging = false;
//            }
//
//        });
        GenericTextPopUpMenu copyPaste = new GenericTextPopUpMenu();
        copyPaste.installContextMenu(this);
        

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fbMSMFile = new rappsilber.gui.components.FileBrowser();
        jLabel3 = new javax.swing.JLabel();
        spToleranceValue = new javax.swing.JSpinner();
        cbToleranceUnit = new javax.swing.JComboBox();
        btnRun = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        fbCSVOut = new rappsilber.gui.components.FileBrowser();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        scanFilterComponentCsvCopyPaste1 = new rappsilber.gui.components.ScanFilterComponentCsvCopyPaste();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMassFilter = new javax.swing.JTable();
        fbMassFilterFile = new rappsilber.gui.components.FileBrowser();
        btnReadMassFilter = new javax.swing.JButton();
        spToleranceValue1 = new javax.swing.JSpinner();
        cbToleranceUnit1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Xlink Peaks List");

        jLabel2.setText("MSMFile");

        jLabel3.setText("Tolerance");

        spToleranceValue.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(30.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));

        cbToleranceUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ppm", "da" }));

        btnRun.setText("run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane1.setViewportView(txtResult);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(spToleranceValue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbToleranceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
                                .addComponent(btnRun))
                            .addComponent(fbMSMFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(fbCSVOut, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fbMSMFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRun))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spToleranceValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbToleranceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addComponent(fbCSVOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("PeakList", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scanFilterComponentCsvCopyPaste1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scanFilterComponentCsvCopyPaste1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SpectraFilter", jPanel2);

        tblMassFilter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "MZ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMassFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblMassFiltertblFilterKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblMassFilter);

        btnReadMassFilter.setText("Read");
        btnReadMassFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadMassFilterActionPerformed(evt);
            }
        });

        cbToleranceUnit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ppm", "da" }));

        jLabel4.setText("Tolerance");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(fbMassFilterFile, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReadMassFilter))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spToleranceValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbToleranceUnit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spToleranceValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbToleranceUnit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReadMassFilter)
                    .addComponent(fbMassFilterFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("PeakFilter", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        try {
            ScanFilteredSpectrumAccess fsa = getFilter();
            ArrayList<Double> MZobjects = new ArrayList<Double>();
            for (int r =0; r< tblMassFilter.getRowCount(); r++) {
                Object v= tblMassFilter.getValueAt(r, 0);
                if (v!= null) {
                    MZobjects.add((Double) v);
                }
            }
            double[] MZ = new double[MZobjects.size()];
            for (int m = 0;m< MZobjects.size();m++ ) {
                MZ[m]= MZobjects.get(m);
            }


            ToleranceUnit t = new ToleranceUnit((Double) spToleranceValue.getModel().getValue(), cbToleranceUnit.getModel().getSelectedItem().toString());
            SpectraAccess sa = AbstractMSMAccess.getMSMIterator(fbMSMFile.getText(), t, 1, null);
            String result;
            StringBuffer sb = new StringBuffer();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bos);
            rappsilber.applications.PeaksLookupExclusive.run(fbMSMFile.getFile(), t, fsa, MZ, ps);
            txtResult.setText(bos.toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "File " + fbMSMFile.getText() + " not found", ex);
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "file not found", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error wile reading file " + fbMSMFile.getText() , ex);
            JOptionPane.showMessageDialog(this, "Error wile reading file " + fbMSMFile.getText() +"\n"+ex.getLocalizedMessage(), "Error wile reading file ", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error wile reading file " + fbMSMFile.getText() +"\n"+ex.getLocalizedMessage(), "Error wile reading file ", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnRunActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String result = txtResult.getText();
        if (fbCSVOut.getFile() == null) {
            Logger.getLogger(PeaksLookupExclusive.class.getName()).log(Level.WARNING, "No file selected");
            return;
        }
        if (result.length() == 0) {
            Logger.getLogger(PeaksLookupExclusive.class.getName()).log(Level.WARNING, "Nothing to be writen as - have you started the run?");
            return;
        }

        try {
            PrintStream ps = new PrintStream(fbCSVOut.getFile());
            ps.println(result);
            ps.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeaksLookupExclusive.class.getName()).log(Level.WARNING, "File " + fbCSVOut.getFile().getAbsolutePath() + " not found", ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblMassFiltertblFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMassFiltertblFilterKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DELETE) {
            JTable tbl = (JTable) evt.getSource();
            if (tbl.getSelectedRowCount() > 0) {
                DefaultTableModel tm = (DefaultTableModel) tbl.getModel();
                int LastRow = tm.getRowCount();
                int[] rows = tbl.getSelectedRows();
                for (int r = rows.length;r-->0;)
                    if (r<LastRow)
                        tm.removeRow(r);

            }
        }
}//GEN-LAST:event_tblMassFiltertblFilterKeyReleased

    private void btnReadMassFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadMassFilterActionPerformed
        DefaultTableModel tm = ((DefaultTableModel)tblMassFilter.getModel());

        String file  = fbMassFilterFile.getText();
        if (file != null || file.length() >0) {
            File in = new File(file);
            if (!in.exists()) {
                JOptionPane.showMessageDialog(this, "file " + file + " not found", "File Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(in));
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "file " + file + " not found", "File Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String line;
            try {
                int editRow = tm.getRowCount() - 1;
                while ((line = br.readLine()) != null) {
                    if (line.matches("^\\s*[0-9]*(\\.[0-9]*)?\\s*(,.*)?$")) {
                        String[] data = line.split(",",2);
                        tm.setValueAt(new Double(data[0]), editRow++, 0);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while reading file " + file + " !", "File Not Found", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_btnReadMassFilterActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PeaksLookupExclusive().setVisible(true);
            }
        });
    }


    protected ScanFilteredSpectrumAccess getFilter() {
        return scanFilterComponentCsvCopyPaste1.getScanFilter();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReadMassFilter;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cbToleranceUnit;
    private javax.swing.JComboBox cbToleranceUnit1;
    private rappsilber.gui.components.FileBrowser fbCSVOut;
    private rappsilber.gui.components.FileBrowser fbMSMFile;
    private rappsilber.gui.components.FileBrowser fbMassFilterFile;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private rappsilber.gui.components.ScanFilterComponentCsvCopyPaste scanFilterComponentCsvCopyPaste1;
    private javax.swing.JSpinner spToleranceValue;
    private javax.swing.JSpinner spToleranceValue1;
    private javax.swing.JTable tblMassFilter;
    private javax.swing.JTextArea txtResult;
    // End of variables declaration//GEN-END:variables

}
