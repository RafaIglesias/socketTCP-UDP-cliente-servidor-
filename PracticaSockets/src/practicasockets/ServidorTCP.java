/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author rafa
 */
public class ServidorTCP {
    static String hostname = "Servidor de Rafa";
    static int puerto = 6789;
    
    public static void main(String[] args) throws IOException {
        int numeroserver = elegirNumero();
       
        System.out.println("Arrancando "+ hostname+"\n");
        ServerSocket socketServidor = new ServerSocket(puerto);
        System.out.println("Servidor TCP arrancado , escucha en puerto"+ puerto +"\n");
                

        while(true){
            System.out.println("Esperando conexion\n");
            System.out.println("----------------------");
           
            // Esperar a que llegue una conexión de un cliente
            Socket connectionSocket = socketServidor.accept();
            System.out.println("Conexion entrante");

            // Obtener el flujo de entrada del socket
            DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
          
            // Leer el número enviado por el cliente
            String mensaje = inFromClient.readUTF();
            String [] partes = mensaje.split(",");
            //parte 0 numero -- parte 1 hostname
            // cliente de rafa
            System.out.println(partes[1]);
            // numero recibido
            System.out.println("Numero del cliente ");
            System.out.println(partes[0]);
            // numeroserver
            System.out.println("Numero del servidor ");
            System.out.println(numeroserver);
            //suma
            System.out.println("Suma server+cliente");
            System.out.println(numeroserver+Integer.parseInt(partes[0]));
            
            //
            
            if(Integer.parseInt(partes[0])<1||Integer.parseInt(partes[0])>100){
                //si numero diferente de parametros
                System.out.println("----------------------");
                System.out.println("Numero no cumple los parametros de control");
                System.out.println("--Server crash --");
                //cierro todo
                outToClient.close();
                inFromClient.close();
                connectionSocket.close();
                socketServidor.close();
                break;
            }
          
             String mensajeServer = Integer.toString(numeroserver);
        
             mensajeServer = mensajeServer +","+ hostname;
             
             outToClient.writeUTF(mensajeServer);

            System.out.println("Conexion cerrada con "+ partes[1]);
            
            // Cerrar todo menos el socket tcp
            outToClient.close();
            inFromClient.close();
            connectionSocket.close();
            
        }
        
     
            //cerrar socket tcp
            
            socketServidor.close();
        
    }
    
    public static int elegirNumero(){
        
          int a=0;
        while (a==0){
        System.out.println("Introduce un entre  1 y  100 \n");
        
            Scanner s = new Scanner(System.in);
            int control;
            control= s.nextInt();
        if(control>=1&&control<=100){
            a=control;
            
        }
       
        }
        return a;    
    }
}
