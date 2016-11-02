package GUI;
import IO.IOOperators;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.helper.StringHelper;
import model.util.ImageUtils;
import model.util.sample.SamplePredictor;

public class MainFrame extends javax.swing.JFrame {
    public static final String NO_FILE = "No file";
    private File directory;
    private final ImageUtils imgProcObj;
    private final SamplePredictor predictor;
    public static String a = ""; 
    private static final int nProcess = 8;
    private IOOperators io;
    

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        this.io = new IOOperators();
        this.imgProcObj = new ImageUtils();
        this.predictor = new SamplePredictor();
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        browseButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();
        ImageNameList = new javax.swing.JScrollPane();
        imageNameList = new javax.swing.JList<String>();
        ImageContainer = new javax.swing.JScrollPane();
        imageContainer = new javax.swing.JLabel();
        predictButton = new javax.swing.JButton();
        PredictAllButton = new javax.swing.JButton();
        parallel = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sampling tool");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        imageLabel.setText("Image");

        imageNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                imageNameListValueChanged(evt);
            }
        });
        ImageNameList.setViewportView(imageNameList);

        imageContainer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageContainer.setViewportView(imageContainer);

        predictButton.setText("Predict");
        predictButton.setEnabled(false);
        predictButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predictButtonActionPerformed(evt);
            }
        });

        PredictAllButton.setText("Predict all");
        PredictAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PredictAllButtonActionPerformed(evt);
            }
        });

        parallel.setText("Parallel Running");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ImageNameList, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ImageContainer))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(imageLabel))
                                    .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(134, 134, 134)
                                .addComponent(PredictAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(parallel)))
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImageNameList, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(ImageContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PredictAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parallel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private File getDirectory(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile(); 
        }  
        
        return null;
    }
    
    private void predictButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predictButtonActionPerformed
        boolean isSick = this.predictor.isSick(this.directory);
        if(isSick)
        JOptionPane.showMessageDialog(null, "Benh nhan duoc chuan doan bi xuat huyet nao");
       else
        JOptionPane.showMessageDialog(null, "Benh nhan duoc chuan doan binh thuong");
    }//GEN-LAST:event_predictButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        // TODO add your handling code here:
        File directory = getDirectory(); 
        if(directory != null){
            this.directory = directory;
            File[] filesInDirectory = directory.listFiles();

            DefaultListModel<String> nameListData = new DefaultListModel<>();

            for (File file : filesInDirectory) {
                if(ImageUtils.isImage(file.getName())){
                    nameListData.addElement(file.getName());
                }
            }

            if(nameListData.isEmpty()){
                nameListData.addElement(NO_FILE);
            }

            this.imageNameList.setModel(nameListData);
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void imageNameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_imageNameListValueChanged
        // TODO add your handling code here:
        String chosenFile = this.imageNameList.getSelectedValue();

        if (chosenFile != null && !chosenFile.equalsIgnoreCase(NO_FILE)) {
            String directoryPath = StringHelper.getDirectoryPath(this.directory);
            this.imgProcObj.setFilePath(StringHelper.getAbsolutePath(directoryPath, chosenFile));

            if (ImageUtils.isImage(chosenFile)) {
                updateImage();
            }
        }
    }//GEN-LAST:event_imageNameListValueChanged

    private void PredictAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PredictAllButtonActionPerformed
        // TODO add your handling code here:
        File[] directories = this.directory.listFiles(new FileFilter(){
          public boolean accept(File file) {
                return file.isDirectory();
        }
       });
         
        /* Set pathfile */
        try {
            this.io.setPATH(StringHelper.getAbsolutePath(StringHelper.getDirectoryPath(this.directory), "result.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
         // Chay tuan tu
        if(!parallel.isSelected()){
            System.out.println("chay tuan tu");
             for (File subDirectory : directories){
             boolean isSick = this.predictor.isSick(subDirectory);
             if (isSick)
                this.io.WriteFile("Folder " + subDirectory.getName() + ": xuat huyet");
             else
                this.io.WriteFile("Folder " + subDirectory.getName() + ": binh thuong");
            } 
        }
        
        else{ //Chay song song
            System.out.println("chay song song");
            ExecutorService executor = Executors.newFixedThreadPool(nProcess);
            Set<Future<String>> set = new HashSet<Future<String>>();
            for (File subDirectory : directories){
                Callable<String> callable = new SamplePredictor(subDirectory);
                Future<String> future = executor.submit(callable);
                set.add(future);
            }
          
            for (Future<String> future : set) {
                try {
                    this.io.WriteFile(future.get());
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        }
       
         /*Close file*/
         this.io.close();
            
        
    }//GEN-LAST:event_PredictAllButtonActionPerformed
    
    private void updateImage(){
        ImageIcon imageIcon = new ImageIcon(this.imgProcObj.loadImage());
        this.imageContainer.setIcon(imageIcon);
        this.predictButton.setEnabled(true);
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
         
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ImageContainer;
    private javax.swing.JScrollPane ImageNameList;
    private javax.swing.JButton PredictAllButton;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel imageContainer;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JList<String> imageNameList;
    private javax.swing.JRadioButton parallel;
    private javax.swing.JButton predictButton;
    // End of variables declaration//GEN-END:variables
}
