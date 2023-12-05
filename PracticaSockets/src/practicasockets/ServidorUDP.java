/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasockets;


import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 *
 * @author rafa
 */
public class ServidorUDP {
    

    static String hostname = "Servidor de Rafa";
    static int puerto = 6789;

    public static void main(String[] args) throws IOException {
        
        int numeroserver = elegirNumero();
        
        System.out.println("Arrancando " + hostname + "\n");
        DatagramSocket socketServidor = new DatagramSocket(puerto);
        System.out.println("Servidor UDP arrancado, escucha en puerto " + puerto + "\n");

        while (true) {
            
            //para ver mejor las conexiones aqui voy a poner la linea antes , de forma que me divida las conexiones mejor
            System.out.println("----------------------");
            System.out.println("Esperando datagrama...\n");
          
            

            // Crear el paquete para recibir datos
            byte[] datosrecibidos = new byte[1024];
            byte[] datos = new byte[1024];
         
            DatagramPacket paquete = new DatagramPacket(datosrecibidos, datosrecibidos.length);

            // Recibir el datagrama del cliente
            socketServidor.receive(paquete);

            // Obtener los datos del paquete
            String mensaje = new String(paquete.getData(), 0, paquete.getLength());
            String[] partes = mensaje.split(",");
            
            // Parte 0: número, Parte 1: hostname
            // Cliente de Rafa
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

            System.out.println("Datagrama recibido de " + partes[1]);
            
            
             if(Integer.parseInt(partes[0])<1||Integer.parseInt(partes[0])>100){
                //si numero diferente de parametros
                System.out.println("----------------------");
                System.out.println("Numero no cumple los parametros de control");
                System.out.println("--Server crash --");
                
                socketServidor.close();
              break;
            }
            //guardo la ip y el puerto con referencia de lo recibido para hacer la respuesta .
            InetAddress direccionCliente = paquete.getAddress();
            int puertoCliente = paquete.getPort();
            
            String mensajeServer = Integer.toString(numeroserver);
        
             mensajeServer = mensajeServer +","+ hostname;
             datos = mensajeServer.getBytes();
             DatagramPacket outToClient = new DatagramPacket(datos, datos.length, direccionCliente, puertoCliente);
            
            socketServidor.send(outToClient);
            

            
            
        }
        
         // cierro el socket
        //los datagrampacket no se cierran , solo tengo q cerrar el socket, no es como las datainputstream
        
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
