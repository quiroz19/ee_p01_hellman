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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.JScrollPane;
import java.io.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.net.*;


public class servidor {
    JFrame ventana=null;
    JButton botonEnviar=null;
    JTextField txt_mensaje=null;
    JTextArea area_chat=null;
    JPanel conte_area=null;
    JPanel conteBotonTexto=null;
    JScrollPane scroll=null;
    ServerSocket servidor=null;
    Socket socket=null;
    BufferedReader lee=null;
    PrintWriter escribe=null;
    
    public servidor(){
     hInterfaz();
    }
    public void hInterfaz(){
        ventana =new JFrame("Servidor");
        botonEnviar=new JButton("Enviar");
        txt_mensaje=new JTextField(4);
        area_chat=new JTextArea(10,12);
        area_chat.setEditable(false);
        area_chat.setBackground(Color.BLACK);
        area_chat.setForeground(Color.green);
        scroll=new JScrollPane(area_chat);
        conte_area=new JPanel();
        conte_area.setLayout(new GridLayout(1,1));
        conte_area.add(scroll);
        conteBotonTexto=new JPanel();
        conteBotonTexto.setLayout(new GridLayout(1,2));
        conteBotonTexto.add(txt_mensaje);
        conteBotonTexto.add(botonEnviar);
        ventana.setLayout(new BorderLayout());
        ventana.add(conte_area,BorderLayout.NORTH);
        ventana.add(conteBotonTexto,BorderLayout.SOUTH);
        ventana.setSize(300,220);
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Thread principal=new Thread(new Runnable(){
        public void run(){
            try{
            servidor=new ServerSocket(9000);
            while(true){
                socket=servidor.accept();
                MensajeRecibido();
                MensajeEnviado();
            }
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
            lee=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             while(true){
                 String mensajeRecibido=lee.readLine();
                 area_chat.append("[Usuario]: "+mensajeRecibido+"\n");
                 
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
            escribe = new PrintWriter(socket.getOutputStream(),true);
            botonEnviar.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                   String enviar_mensaje=txt_mensaje.getText();
                   escribe.println(enviar_mensaje);
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
        new servidor();
        
    }
  }
