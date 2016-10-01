/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatt;

/**
 *
 * @author Anita
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class cliente {
    
    JFrame ventana_chat=null;
    JButton bto_enviar=null;
    JTextField txt_mensaje=null;
    JTextArea area_chat=null;
    JPanel conte_area=null;
    JPanel conte_bto_txt=null;
    JScrollPane scroll=null;
    Socket socket=null;
    BufferedReader lector=null;
    PrintWriter escritor=null;
    
    public cliente(){
        hInterfaz();
    }
    public void hInterfaz(){
        ventana_chat =new JFrame("Cliente");
        bto_enviar=new JButton("Enviar");
        txt_mensaje=new JTextField(4);
        area_chat=new JTextArea(10,12);
        area_chat.setEditable(false);
        area_chat.setBackground(Color.BLACK);
        area_chat.setForeground(Color.green);
        scroll=new JScrollPane(area_chat);
        conte_area=new JPanel();
        conte_area.setLayout(new GridLayout(1,1));
        conte_area.add(scroll);
        conte_bto_txt=new JPanel();
        conte_bto_txt.setLayout(new GridLayout(1,2));
        conte_bto_txt.add(txt_mensaje);
        conte_bto_txt.add(bto_enviar);
        ventana_chat.setLayout(new BorderLayout());
        ventana_chat.add(conte_area,BorderLayout.NORTH);
        ventana_chat.add(conte_bto_txt,BorderLayout.SOUTH);
        ventana_chat.setSize(300,220);
        ventana_chat.setVisible(true);
        ventana_chat.setResizable(false);
        ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Thread principal=new Thread(new Runnable(){
            public void run(){
                try{ //10.127.127.1
                socket=new Socket("10.127.127.1",9000);
                MensajeRecibido();
                MensajeEnviado();
            }catch(Exception e){
                e.printStackTrace();
            }
            }
        });
        principal.start();
    }
    public void MensajeRecibido(){
        Thread MensajeRecibido_hilo=new Thread(new Runnable() {
            public void run(){
                try{
            lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             while(true){
                 String mensajeRecibido=lector.readLine();
                 area_chat.append("[Administrador]: "+mensajeRecibido+"\n");
             }
        }catch(Exception ex){
            ex.printStackTrace();
        }
            }
        });
        MensajeRecibido_hilo.start();
    }
    
    public void MensajeEnviado(){
        Thread MensajeEnviado_hilo = new Thread(new Runnable(){
         public void run(){
             try{
            escritor = new PrintWriter(socket.getOutputStream(),true);
            bto_enviar.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    area_chat.append("Bienvenido...\n\n");
                   String enviar_mensaje=txt_mensaje.getText();
                   escritor.println(enviar_mensaje);
                   area_chat.append("[Yo]: "+txt_mensaje.getText()+"\n");
                    txt_mensaje.setText(null);
                }
            });
        }catch(Exception e){
        e.printStackTrace();
        }
         }    
        });
        MensajeEnviado_hilo.start();
        
    }
    
    public static void main(String [] args){
        new cliente();
    }
    
}
