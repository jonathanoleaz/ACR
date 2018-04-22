/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envioobjetos;
import PaqueteDato.Dato;
import java.net.*;
import java.io.*;
/**
 *
 * @author jonat
 */
public class Servidor {
    public static void main(String[] args){
        try{
            int pto=8888;
            ServerSocket s=new ServerSocket(pto);
            s.setReuseAddress(true);
            System.out.println("Servicio iniciando... preparando para recibir cliente");
            for(;;){
                Socket cl=s.accept();
                
                System.out.println("Cliente conectado desde:"+cl.getInetAddress()+" :"+cl.getPort());
                
                ObjectOutputStream oos=new ObjectOutputStream(cl.getOutputStream());
                ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
                System.out.println("Recibiendo objeto...");
                Dato d1=(Dato)ois.readObject();
                System.out.println("Datos recibidos: ");
                System.out.println("Nombre: "+d1.getNombre()+" Edad:"+d1.getEdad());
                Dato d2=new Dato("Pepe", 18, "5557296000", 10.1f);
                oos.writeObject(d2);
                oos.flush();
                ois.close();
                cl.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
