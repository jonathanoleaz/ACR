package Servidor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException {
        int pto = 7000;
        InetAddress dst = InetAddress.getByName("localhost");

        DatagramSocket s = new DatagramSocket();
        System.out.println("Servicio iniciado.");

        System.out.println("Mensaje: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String datos = br.readLine();
        byte[] msg = datos.getBytes();
        int tbuf = 10;
        int numDatagramas = 0;

        ByteArrayInputStream bais = new ByteArrayInputStream(msg);
        numDatagramas = (int) msg.length / tbuf;

        if (msg.length % tbuf > 0) {                                              // Se debe de enviar un datagrama más.
            numDatagramas++;
        }

        for (int i = 0; i < numDatagramas; i++) {
            byte[] tmp = new byte[tbuf];
            int n = bais.read(tmp);

            Objeto_U obj = new Objeto_U(i + 1, numDatagramas, tmp);

            /* Envío el objeto */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();

            byte[] buf = bos.toByteArray();

            DatagramPacket p1 = new DatagramPacket(buf, buf.length, dst, pto);
            System.out.println("p1: " + p1.getLength());
            s.send(p1);
            oos.close();

            /* Se recibe el objeto modificado */
            DatagramSocket s2 = new DatagramSocket(8000);
            s.setReuseAddress(true);
            DatagramPacket p = new DatagramPacket(new byte[200], 200);
            s2.receive(p);
            s2.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
            try {
                Objeto_U objeto = (Objeto_U) ois.readObject();
                System.out.println("Recibí del servidor: " + new String(objeto.getB()));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
