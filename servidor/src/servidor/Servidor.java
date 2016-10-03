/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*; 
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.awt.event.KeyListener;
import java.math.BigInteger;



/**
 *
 * @author Anita
 */
public class Servidor extends JFrame implements ActionListener,KeyListener {
     JFrame ventana_chat=null;
     JButton btn_enviar=null;
     JButton btn_encriptar=null;
     JTextField txt_mensaje=null;
     JTextArea area_chat=null;
     JPanel contenedor_areachat=null;
     JPanel contenedor_btntxt=null;
     JScrollPane scroll=null;
     ServerSocket servidor=null;
     Socket socket=null;
     BufferedReader lector=null;
     PrintWriter escritor =null;
     

public Servidor(){
    hacerInterfaz();
    
}

public void hacerInterfaz(){
    ventana_chat=new JFrame("Servidor");
    btn_enviar=new JButton("Enviar");
    btn_encriptar=new JButton("Encriptar");
    txt_mensaje=new JTextField(10);
    area_chat =new JTextArea(10,12);
    scroll=new JScrollPane(area_chat);
    contenedor_areachat=new JPanel();
    contenedor_areachat.setLayout(new GridLayout(1,1));
    contenedor_areachat.add(scroll);
    contenedor_btntxt=new JPanel();
    contenedor_btntxt.setLayout(new GridLayout(1,2));
    contenedor_btntxt.add(txt_mensaje);
    contenedor_btntxt.add(btn_enviar);
    contenedor_btntxt.add(btn_encriptar);
    ventana_chat.setLayout(new BorderLayout());
    ventana_chat.add(contenedor_areachat,BorderLayout.NORTH);
    ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
    ventana_chat.setSize(300,220);
    //ventana_chat.setSize(350,240);
    ventana_chat.setVisible(true);
    ventana_chat.setResizable(true);
    ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Thread principal=new Thread(new Runnable(){
        public void run(){
            try{
            servidor=new ServerSocket(9000);
            while(true){
                socket=servidor.accept();
                leer();
                escribir();
                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
});
    principal.start();
}


public void leer(){
    Thread leer_hilo=new Thread(new Runnable(){
        public void run(){
             try{
        lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true){
            String mensaje_recibido=lector.readLine();
            area_chat.append("Cliente dice: "+ mensaje_recibido+ "\n");
        }
    }catch(Exception ex){
        ex.printStackTrace();
    }
            
        }
    });
    leer_hilo.start();
   
}
public void escribir(){
    Thread escribir_hilo=new Thread(new Runnable(){
        public void run(){
            try{
        escritor=new PrintWriter(socket.getOutputStream(),true);
        btn_enviar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String enviar_mensaje=txt_mensaje.getText();
                escritor.println(enviar_mensaje);
                txt_mensaje.setText("");
            }
        });
       btn_encriptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String enviar_mensaje=txt_mensaje.getText();
                keys enc = new keys();
                BigInteger clave = enc.calcular(enviar_mensaje);
                escritor.println(clave+"");
                txt_mensaje.setText("");
               //keys encriptar= new keys(); 
        //String s=JOptionPane.showInputDialog("ingrese palabra a encriptar");
        //encriptar.calcular(s);
            }
        });
    }catch(Exception ex){
        ex.printStackTrace();
    }
            
        }
    });
    escribir_hilo.start();
    
}


 @Override
public void actionPerformed(ActionEvent e)  {
    String var="";
    char array[]=var.toCharArray();
    System.out.print(String.valueOf(array));
    int ln=array.length;
    for(int i=0; i<ln;i++){
       array[i]=(char)(array[i]+(char)5);
}
    String encriptado=String.valueOf(array);
    System.out.println(encriptado);
    char arrayD [];
         arrayD = encriptado.toCharArray();
    int ln2=arrayD.length;
    
    for(int i=0; i<ln2;i++){
        arrayD[i]=(char)(array[i]-(char)5);
    }
    System.out.println(String.valueOf(arrayD));
}

public static void main(String[]args){
    //new Servidor();
    Servidor s=new Servidor();
    //cliente d= new cliente();
    
}

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   
     
    
    
}
