package Servidor;

import java.net.*;
import java.io.*;

public class S_EcoD {
    public static void main(String[] args) {
        try{
            DatagramSocket s= new DatagramSocket(7000);
            s.setReuseAddress(true);
            System.out.println("Servicio iniciado ... esperando mensaje ...");
            for(;;){
                DatagramPacket p =new DatagramPacket(new byte[65535], 65535);
                s.receive(p);
                String msj = new String(p.getData(),0,p.getLength());
                System.out.println("Men");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
