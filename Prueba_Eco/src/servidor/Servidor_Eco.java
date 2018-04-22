package servidor;

import java.net.*;
import java.io.*;

public class Servidor_Eco {
    public static void main(String[] args) {
        try{
            int pto=9000;
            ServerSocket s= new ServerSocket(pto);
            System.out.println("Servicio iniciado, esperando clientes..");
            for(;;){
                Socket cl= s.accept();
                System.out.println("Cliente conectado desde " + cl.getInetAddress() + " : " + cl.getPort());
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                while(true){
                    String msj = br.readLine();
                    if(msj.compareToIgnoreCase("salir")==0){
                        System.out.println("Cliente termino aplicación");
                        br.close();
                        pw.close();
                        cl.close();
                        break;
                    }else{
                        System.out.println("Mensaje recibido : " + msj);
                        String eco = "ECO " + msj;
                        pw.println(eco);
                        pw.flush();
                    }//else
                }//while
            }//for
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }      
}
