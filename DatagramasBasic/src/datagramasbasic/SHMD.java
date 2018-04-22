/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagramasbasic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author jonat
 */
public class SHMD {

    public static void main(String[] args) {
        try {
            int ndatagramas = 0;
            int tbuf = 10;
            int pto = 5678;
            ByteArrayInputStream bais = null;
            DatagramSocket s = new DatagramSocket(pto);
            System.out.println("Servicio iniciado, enviando mensaje");
            //String msj="Hola mundo con datagramas";

            System.out.println("Datos:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String datos = br.readLine();
            byte[] msj = datos.getBytes();
            if (msj.length >= tbuf) {
                bais = new ByteArrayInputStream(msj);
                ndatagramas = (int) msj.length / tbuf;
                if (msj.length % tbuf > 0) {
                    ndatagramas++;
                }
            }

            //byte []b=msj.getBytes();
            InetAddress dst = null;
            try {
                dst = InetAddress.getByName("localhost");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(1);
            }

            for (int i = 0; i < ndatagramas; i++) {
                byte[] temp = new byte[tbuf];
                int n = bais.read(temp);
                
                Objeto_U o=new Objeto_U(i+1, ndatagramas, temp);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                ObjectOutputStream oos=new ObjectOutputStream(baos);
                oos.writeObject(o);
                oos.flush();
                baos.flush();
                byte[]buf=baos.toByteArray();
                

                DatagramPacket p = new DatagramPacket(buf, buf.length, dst, 1234);
                s.send(p);
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e1) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
