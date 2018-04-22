package peertopeer;

import java.net.*;
import java.io.*;

public class ServAnuncioM {

    public static void main(String[] args) {
        try {
            int pto = 1234;
            String dir = "229.1.1.1";
            MulticastSocket s = new MulticastSocket(5678);

            InetAddress grupo = null;
            try {
                grupo = InetAddress.getByName(dir);
            } catch (UnknownHostException u) {
                System.out.println("Dirección no válida");
                System.exit(1);
            }
            s.setReuseAddress(true);
            s.setTimeToLive(255);
            s.joinGroup(grupo);
            System.out.println("Unido al grupo, comienza envio de anuncios...");
            String msj = "Anuncio multicast";
            byte[] b = msj.getBytes();
            for (;;) {
                DatagramPacket p = new DatagramPacket(b, b.length, grupo, pto);
                s.send(p);
                System.out.println("enviado");
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
