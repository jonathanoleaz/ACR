package peertopeer;

import java.net.*;
import java.io.*;

public class CAnuncioM {

    public static void main(String[] args) {
        try {
            int pto = 1234;
            String dir = "229.1.1.1";
            MulticastSocket s = new MulticastSocket(1234);
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
            System.out.println("Unido al grupo, comienza recibo de anuncios...");
            String msj = "Anuncio multicast";
            byte[] b = msj.getBytes();
            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[1500], 1500);
                s.receive(p);
                p.getAddress();
                DataInputStream ois = new DataInputStream(new ByteArrayInputStream(p.getData()));
                String recept = new String(p.getData(), 0, p.getLength());
                System.out.println("Recibí: " + recept);
                ois.close();

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
