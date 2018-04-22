/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagramasbasic;

import java.io.*;
import java.net.*;

public class Servidor_O_UDP {

    public static void main(String[] args) {
        int puerto = 8000;
        DatagramPacket dp = null;
        DatagramSocket s = null;
        //ObjectOutputStream oos=null;
        ObjectInputStream ois = null;
        //ByteArrayInputStream bis;
        Objeto_U objeto = null;
        for (;;) {
            try {
                s = new DatagramSocket(puerto);
                System.out.println("Servidor UDP iniciado en el puerto " + s.getLocalPort());
                System.out.println("Recibiendo datos...");
                for (int i = 0; i < 10; i++) {
                    dp = new DatagramPacket(new byte[1024], 1024);
                    s.receive(dp);
                    System.out.println("Datagrama recibido... extrayendo informaciĂłn");
                    System.out.println("Host remoto:" + dp.getAddress().getHostAddress() + ":" + dp.getPort());
                    System.out.println("Datos del paquete:");
                    ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData()));
                    objeto = (Objeto_U) ois.readObject();
                
                    
                    System.out.println("N: " + objeto.getN());
                    System.out.println("Total: " + objeto.getTotal());
                    System.out.println("Msj: " + new String(objeto.getB()));
                    ois.close();
                    if(objeto.getN()==objeto.getTotal())
                        break;
                }//for
                s.close();
            } catch (Exception e) {
                System.err.println(e);
            }
            System.out.println("Termina el contenido del datagrama...");
        }//for(ever)
    }//main
}//class
