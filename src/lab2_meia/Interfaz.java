/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2_meia;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Pancho
 */
public class Interfaz extends javax.swing.JFrame {
ArrayList<Integer> numeros = new ArrayList<>();
String pathPuntuacion = Paths.get("C:/MEIA/puntuacion.txt").toString();
String pathResultado = Paths.get("C:/MEIA/resulta.txt").toString();
String especiales = "/¿?%$#";


    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        TxtContra = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nivel seguridad:");

        jButton1.setText("Validar");
        jButton1.setName("ContraB"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Contraseña:");
        jLabel2.setName("contraLabel"); // NOI18N

        TxtContra.setName("ContraTxb"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtContra, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jButton1)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     int puntuacion = 0;
     LecturaArchivo(pathPuntuacion);
     String contrasenia =  TxtContra.getText();
     if(contrasenia.length() >= numeros.get(0)){
         puntuacion = numeros.get(1) * contrasenia.length();
         ArrayList<String> contadorMayus = new ArrayList<>();
         for (int i = 0; i < contrasenia.length(); i++) {
             if((Integer.valueOf(contrasenia.charAt(i))>64) && (Integer.valueOf(contrasenia.charAt(i))<91))
             {
                 contadorMayus.add(String.valueOf(contrasenia.charAt(i)));
             }
         }
         puntuacion = puntuacion +   (numeros.get(2)*contadorMayus.size());  
         int counter = 0;
         for(int i =0;i<contrasenia.length();i++){
         if((!especiales.contains(String.valueOf(contrasenia.charAt(i))))||Isnumber(String.valueOf(contrasenia.charAt(i)))== false){
             counter++;
         }
     }
        
         puntuacion = puntuacion + (counter + numeros.get(3));
          counter = 0;
          for(int  i = 0; i<contrasenia.length();i++){
         if(Isnumber(String.valueOf(contrasenia.charAt(i)))){
             counter++;
         }
         
     }
         puntuacion = puntuacion+(counter+numeros.get(4));
         counter = 0;
         for(int i = 0;i<contrasenia.length();i++){
             if(especiales.contains(String.valueOf(contrasenia.charAt(i)))){
                 counter++;
             }
         }
          puntuacion = puntuacion+counter*(contrasenia.length()+numeros.get(5));
          if(counter == 0){
              if(onlyNumbers(contrasenia)== true){
                  puntuacion= puntuacion -numeros.get(7);
              } else if(onlyLetters(contrasenia)==true){
                  puntuacion = puntuacion - numeros.get(6);
              }
          }
          int code = SecurityCode(puntuacion);
          switch(code){
              case 1:
                   JOptionPane.showMessageDialog(null, "Contraseña insegura");
                  
                  break;
              case 2:
                   JOptionPane.showMessageDialog(null, "Contraseña poco segura");
                    jLabel1.setText("Contraseña poco segura");
                  break;
              case 3:
                   JOptionPane.showMessageDialog(null, "Contraseña segura");
                    jLabel1.setText("Contraseña segura");
                    
                    try{
                    FileWriter result = new FileWriter(pathResultado);
                    BufferedWriter writer = new BufferedWriter(result);
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(contrasenia.getBytes());
                    byte[] digest = md.digest();
                    String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
                    StringBuilder passstring = new StringBuilder();
                    passstring.append(contrasenia);
                    passstring.append(":");
                    passstring.append(myHash);
                    writer.write(passstring.toString());
                    writer.newLine();
                    writer.close();
                    result.close();
                    
                    }catch(Exception e){
                        
                    }
                    
                  break;
              case 4: 
                   JOptionPane.showMessageDialog(null, "Contraseña Muy Segura");
                  break;
              
          }
     } else {
          JOptionPane.showMessageDialog(null, "El largo de la con debe ser de al menos:" + numeros.get(0).toString() + " " + "caracteres");
     }      
    }//GEN-LAST:event_jButton1ActionPerformed
  private int CantMayusculas(String cadena){
      
      return 0;
  }
  private int SecurityCode(int puntuacion){
      if(puntuacion>0 && puntuacion<=25){
          return 1;
      } else if(puntuacion>25 && puntuacion<=35){
          return 2;
      } else if(puntuacion>35 && puntuacion<=50){
          return 3;
      } else {
          return 4;
      }
  }
  
  private boolean onlyNumbers(String cadena){
      int c = 0;
      for(int i = 0; i<cadena.length();i++){
          if(Isnumber(String.valueOf(cadena.charAt(i)))){
              c++;
          }
      }
      if(c==cadena.length()){
          return true;
      } else {
          return false;
      }
  }
  private boolean onlyLetters(String cadena){
      int c = 0;
      for(int i = 0; i<cadena.length();i++){
         if(Character.isLetter(cadena.charAt(i))==true){
             c++;
         }
      }
      if(c==cadena.length()){
          return true;
      } else {
          return false;
      }
  }
    private void LecturaArchivo(String path){
    StringBuilder text = new StringBuilder();
    try{
        FileReader lectura = new FileReader(path);
        BufferedReader buffer = new BufferedReader(lectura);
        String cadena;
        while((cadena = buffer.readLine())!= null){
            numeros.add(Integer.parseInt(cadena));
        }

        
    }catch(Exception e){
      
               JOptionPane.showMessageDialog(null, "Error al leer archivo");
    }
}
    private boolean Isnumber(String counter){
        try{
            int m8 = Integer.parseInt(counter);
        }catch(Exception e){
            return false;

    }
        return true;
    }
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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtContra;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
