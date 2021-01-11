/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LenguajesyAutomatas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


public class editor_texto extends javax.swing.JFrame {

    List<nodo_token> lista_tokens = new ArrayList<>(); // lista de objetos de token
    DefaultTableModel model; // variable utilizada para manipular la data table
    
    public editor_texto() 
    {
        initComponents();
        model = (DefaultTableModel) tabla_tokens.getModel();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_tokens = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txta_codigo_fuente = new javax.swing.JTextArea();
        btn_abrir_archivo = new javax.swing.JButton();
        txt_ruta_archivo = new javax.swing.JTextField();
        lbltokens = new javax.swing.JLabel();
        lblruta_archivo = new javax.swing.JLabel();
        btn_analizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador_SPARK - Julio Ambriz");
        setBackground(new java.awt.Color(153, 153, 153));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tabla_tokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Token", "Renglon"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla_tokens);
        if (tabla_tokens.getColumnModel().getColumnCount() > 0) {
            tabla_tokens.getColumnModel().getColumn(0).setResizable(false);
            tabla_tokens.getColumnModel().getColumn(1).setResizable(false);
            tabla_tokens.getColumnModel().getColumn(2).setResizable(false);
        }

        txta_codigo_fuente.setColumns(20);
        txta_codigo_fuente.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txta_codigo_fuente.setRows(5);
        txta_codigo_fuente.setEnabled(false);
        jScrollPane2.setViewportView(txta_codigo_fuente);

        btn_abrir_archivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_abrir_archivo.setText("....");
        btn_abrir_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abrir_archivoActionPerformed(evt);
            }
        });

        txt_ruta_archivo.setEnabled(false);

        lbltokens.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltokens.setText("Lista de tokens :");

        lblruta_archivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblruta_archivo.setText("Ruta del archivo :");

        btn_analizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_analizar.setText("Ejecutar analisis");
        btn_analizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_analizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblruta_archivo)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txt_ruta_archivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_abrir_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbltokens)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addComponent(btn_analizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblruta_archivo)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ruta_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_abrir_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addComponent(lbltokens)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_analizar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("compilador_spark");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    void llenar_tabla_tokens()
    {
        model.setRowCount(0);
        Object rowData[] = new Object[3];
        lista_tokens.stream().map((lista_token) -> {
            rowData[0] = lista_token.lexema;
            return lista_token;
        }).map((lista_token) -> {
            rowData[1] = lista_token.token;
            return lista_token;
        }).map((lista_token) -> {
            rowData[2] = lista_token.renglon;
            return lista_token;
        }).forEach((_item) -> {
            model.addRow(rowData);
        });
    }
    
    void vaciar_tabla_tokens() // vacia la tabla de tokens
    {
        model.setRowCount(0);
    }
    
    private void btn_analizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_analizarActionPerformed

        if(txta_codigo_fuente.getText().length() > 0)
        {
            lexico lex = new lexico(); // Inicializa un objeto de la clase lexico 
            lex.comprobar_lexico(txta_codigo_fuente.getText().concat("\0"));      // ejecuta el analisis lexico 
            lista_tokens = lex.obtener_lista_tokens();  // asigna la lista tokens la lista que se genero en la clase lexico    
            llenar_tabla_tokens();   
            
            if(lex.recuperar_error_lexico().length() > 0) // Si se encontro un error lexico , entonces devuelve un mensaje
            {
                JOptionPane.showMessageDialog(this, "Error lexico encontrado\nDetalles:\n"+ lex.recuperar_error_lexico());
            }
            else
            {              
                if(lista_tokens.size() > 0) // condición para ver si la lista de tokens contiene elementos
                {
                    String errores_sinta = "";
                    String errores_seman = "";
                    
                    sintactico sinta = new sintactico(lista_tokens);
                    sinta.comprobar_sintaxis(txta_codigo_fuente.getText());

                    errores_sinta = sinta.lista_errores_sintacticos.stream().map((lista_errores_sintactico) -> lista_errores_sintactico + "\n").reduce(errores_sinta, String::concat); 
                    errores_seman = sinta.lista_errores_semanticos.stream().map((lista_errores_semantico) -> lista_errores_semantico + "\n").reduce(errores_seman, String::concat); 
                    
                    
                    if(errores_sinta.length() > 0) JOptionPane.showMessageDialog(this, "Error(es) sintactico(s) encontrado(s) \nDetalles:\n"+errores_sinta);
                    if(errores_seman.length() > 0) JOptionPane.showMessageDialog(this, "Error(es) semantico(s) encontrado(s) \nDetalles:\n"+errores_seman);
                    
                    if(sinta.sintaxis_completada)
                    {
                        JOptionPane.showMessageDialog(this, "El analisis de sintaxis se completo sin errores");
                    }
                    
                }
            }
        }
    }//GEN-LAST:event_btn_analizarActionPerformed

    private void btn_abrir_archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abrir_archivoActionPerformed

        JFileChooser fileChooser = new JFileChooser(); // crea un objeto FileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("archivos de texto (.txt)", "txt", "text"); // Establecer extensión del archivo a abrir
        fileChooser.setFileFilter(filter); // Fija el filtro de la extensión de arhivos
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents")); // fija el destino predeterminado de la v entana filechooser
        
        int result = fileChooser.showOpenDialog(this); // abre una ventana para seleccionar un archivo
        if (result == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileChooser.getSelectedFile();
            txt_ruta_archivo.setText(selectedFile.getAbsolutePath());
            try 
            {
                String archivo;
                archivo = new String(Files.readAllBytes(selectedFile.toPath()));                
                txta_codigo_fuente.setText(archivo);
                vaciar_tabla_tokens();
            } 
            catch (FileNotFoundException ex) 
            {
                
            } 
            catch (IOException ex) {
               
            }
        }
    }//GEN-LAST:event_btn_abrir_archivoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try 
        {
            this.setIconImage(ImageIO.read(new File("res/images/icons/unnamed.png")));
        } catch (IOException ex) 
        {
            Logger.getLogger(editor_texto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(editor_texto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editor_texto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editor_texto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editor_texto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new editor_texto().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_abrir_archivo;
    private javax.swing.JButton btn_analizar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblruta_archivo;
    private javax.swing.JLabel lbltokens;
    private javax.swing.JTable tabla_tokens;
    private javax.swing.JTextField txt_ruta_archivo;
    private javax.swing.JTextArea txta_codigo_fuente;
    // End of variables declaration//GEN-END:variables
}
