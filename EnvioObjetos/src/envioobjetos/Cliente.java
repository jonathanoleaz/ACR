/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envioobjetos;
import java.net.*;
import java.io.*;
import PaqueteDato.Dato;
/**
 *
 * @author jonat
 */
public class Cliente {
    public static void main(String[] args){
        try{
            
            String dst="localhost";
            int pto=8888;
            Socket cl=new Socket(dst,pto);
            System.out.println("Conexión establecida, creando objeto...");
            Dato d=new Dato("Juan", 20, "5557164590", 20.5f);
            System.out.println("Objeto creado... enviando los sigs. datos: ");
            System.out.println("Nombre"+d.getNombre()+" Edad:"+d.getEdad());
            ObjectOutputStream oos=new ObjectOutputStream(cl.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
            oos.writeObject(d);//se entepone encabezado, especificando cuantos campos tiene, y que clase es
            oos.flush();
            System.out.println("Preparando para recibir objetos");
            //sino se sabe que objeto se recibirá, puede hacerse
            // Object o=ois.readObject();
            //     if(o isInstanceOf Dato){ Dato d1=(Dato) o}
            
            Dato d1=(Dato)ois.readObject();
            System.out.println("Objeto recibido con los datos ");
            System.out.println("Nombre "+d1.getNombre()+" "+d1.getTelefono());
            ois.close();
            oos.close();
            cl.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
