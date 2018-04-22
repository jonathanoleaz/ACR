package datagramasfiles;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonat
 */
public class Servidor {

    public static void main(String[] args) {
        FileOutputStream out = null;
        FileOutputStream stream = null;
        String data = "Test data";
        //out = new FileOutputStream("testFile2.txt");
        //out.write(data.getBytes());
        //out.close();

        try {
            DatagramSocket s = new DatagramSocket(7500);
            s.setReuseAddress(true);
            System.out.println("Servicio iniciado. Esperando mensajes");
            int tbuf = 700;

            InetAddress dst = InetAddress.getByName("localhost");
            int pto = 8000;
            Objeto_U objeto = null;

            for (;;) {
                try {
                    DatagramPacket p = new DatagramPacket(new byte[tbuf], tbuf);
                    
                    System.out.println("en espera");
                    s.receive(p);
                    System.out.println("recibido");
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                    objeto = (Objeto_U) ois.readObject();
                    ois.close();
                } catch (EOFException e) {
                    e.printStackTrace();
                }
                if (objeto.getN() == 1) {           //en el primer paquete viajará el nombre
                    
                    String msg = new String(objeto.getB(), 0, objeto.getB().length);
                    msg=msg.trim();                 //dejar la cadena limpia, sin caracteres no validos para nombre de un File
                    stream = new FileOutputStream(msg);
                   
                     
                } else {
                    System.out.println(objeto.getN());
                    System.out.println(objeto.getTotal());
                    //String msg = new String(objeto.getB(), 0, objeto.getB().length);
                    stream.write(objeto.getB(), 0, objeto.getB().length);
                    stream.flush();
                    if (objeto.getN() == objeto.getTotal()) {
                        stream.close();
                        System.out.println("cerrado");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
