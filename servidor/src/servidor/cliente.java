/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.math.BigInteger;

/**
 *
 * @author Anita
 */
public class cliente {
     JFrame ventana_chat=null;
     JButton btn_enviar=null;
     JButton btn_desencriptar=null;
     JTextField txt_mensaje=null;
     JTextArea area_chat=null;
     JPanel contenedor_areachat=null;
     JPanel contenedor_btntxt=null;
     JScrollPane scroll=null;
     ServerSocket servidor=null;
     Socket socket=null;
     BufferedReader lector=null;
     PrintWriter escritor =null;
     String cl2 = "";

public cliente(){
    hacerInterfaz();
    
}

public void hacerInterfaz(){
    ventana_chat=new JFrame("Cliente");
    btn_enviar=new JButton("Enviar");
    btn_desencriptar=new JButton("Desencriptar");
    txt_mensaje=new JTextField(4);
    area_chat =new JTextArea(10,12);
    scroll=new JScrollPane(area_chat);
    contenedor_areachat=new JPanel();
    contenedor_areachat.setLayout(new GridLayout(1,1));
    contenedor_areachat.add(scroll);
    contenedor_btntxt=new JPanel();
    contenedor_btntxt.setLayout(new GridLayout(1,2));
    contenedor_btntxt.add(txt_mensaje);
    contenedor_btntxt.add(btn_enviar);
    contenedor_btntxt.add(btn_desencriptar);
    ventana_chat.setLayout(new BorderLayout());
    ventana_chat.add(contenedor_areachat,BorderLayout.NORTH);
    ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
    ventana_chat.setSize(300,220);
    ventana_chat.setVisible(true);
    ventana_chat.setResizable(true);
    ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Thread principal= new Thread(new Runnable(){
        public void run(){
            try{
                socket=new Socket("localhost",9000);
                leer();
                escribir();
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
            area_chat.append("Servidor dice: "+ mensaje_recibido+"\n");
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
         btn_desencriptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //String enviar_mensaje=txt_mensaje.getText();
                //escritor.println(enviar_mensaje);
                //txt_mensaje.setText("");
                String cade = area_chat.getText();
                String cl = "";
                for(int i=cade.length()-3; i>=0; i-- ){
                    if(cade.charAt(i) != ' '){
                        cl += cade.charAt(i);
                    }
                    else{
                        i = -1;
                    }
                }
                cl2 = "";
                StringBuilder sb = new StringBuilder(cl);
                cl2 = sb.reverse().toString();
                System.out.println(cl2);
                keys enc = new keys();
                BigInteger b = BigInteger.valueOf(0);
                b=b.add(BigInteger.valueOf(Long.parseLong(cl2)));
                String s = enc.desencriptar(b);
                System.out.println(s + "" );
                     
            }
        });
    }catch(Exception ex){
        ex.printStackTrace();
    }
            
        }
    });
    escribir_hilo.start();
    
}
public static void main(String[]args){
    
    new cliente();
    
}
     
    
    
}
