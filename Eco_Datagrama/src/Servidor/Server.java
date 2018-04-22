package Servidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(7000);
            s.setReuseAddress(true);
            System.out.println("Servicio iniciado. Esperando mensajes");
            int tbuf = 200;

            InetAddress dst = InetAddress.getByName("localhost");
            int pto = 8000;
            Objeto_U objeto = null;

            for (;;) {
                try {
                    DatagramPacket p = new DatagramPacket(new byte[tbuf], tbuf);
                    s.receive(p);

                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
                    objeto = (Objeto_U) ois.readObject();
                    ois.close();
                } catch (EOFException e) {
                    e.printStackTrace();
                }

                String msg = new String(objeto.getB(), 0, objeto.getB().length);
                String msg2Send = msg + "<<Eco";

                Objeto_U obj2send = new Objeto_U(objeto.getN(), objeto.getTotal(), msg2Send.getBytes());

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj2send);
                oos.flush();

                byte[] buf = bos.toByteArray();

                DatagramPacket p1 = new DatagramPacket(buf, buf.length, dst, pto);
                s.send(p1);
                oos.close();
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
