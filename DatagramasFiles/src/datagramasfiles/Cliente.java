package datagramasfiles;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author jonat
 */
public class Cliente {

    public static void enviaArchivoDat(int pto, InetAddress dst, File f) {
        try {
            byte[] bytesDelFile = null;
            FileInputStream fileInputStream = null;

            DatagramSocket s = new DatagramSocket();
            System.out.println("Socket creado.");

            bytesDelFile = new byte[(int) f.length()];

            //leer el archivo sobre el arreglo de bytes
            fileInputStream = new FileInputStream(f);
            fileInputStream.read(bytesDelFile);

            //byte[] msg = datos.getBytes();
            int tbuf = 500;
            int numDatagramas = 0;

            ByteArrayInputStream bais = new ByteArrayInputStream(bytesDelFile);
            numDatagramas = (int) bytesDelFile.length / tbuf;
            numDatagramas++;    //en el 1ro irà el nombre

            if (bytesDelFile.length % tbuf > 0) {                                              // Se debe de enviar un datagrama más.
                numDatagramas++;
            }
            Objeto_U obj;
            //
            
            
            for (int i = 0; i < numDatagramas; i++) {
                byte[] tmp = new byte[tbuf];

                if (i == 0) {
                    String nombre = f.getName();
                    ByteArrayInputStream bais2 = new ByteArrayInputStream(nombre.getBytes());
                    int n=bais2.read(tmp);
                    obj = new Objeto_U(i + 1, numDatagramas, tmp);
                    System.out.println(new String(obj.getB()));

                } else {
                    int n = bais.read(tmp);

                    obj = new Objeto_U(i + 1, numDatagramas, tmp);
                }
                /* Envío el objeto */
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();

                byte[] buf = bos.toByteArray();

                DatagramPacket p1 = new DatagramPacket(buf, buf.length, dst, pto);
                System.out.println("p1: " + p1.getLength());
                System.out.println(obj.getN());
                System.out.println(obj.getTotal());
                s.send(p1);
                oos.close();
            }

        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException {
        int pto = 7500;
        InetAddress dst = InetAddress.getByName("localhost");

        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        int r = jf.showOpenDialog(null);          //para ejercicio-->   jf.setMultiSelectionEnabled(true);
        jf.requestFocus(true);
        if (r == JFileChooser.APPROVE_OPTION) {
            File[] f = jf.getSelectedFiles();        //para varios File[] f=jf.getSelectedFiles();
            for (File actualFile : f) {
                enviaArchivoDat(pto, dst, actualFile);
            }
        }
    }

}
