package cliente;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente_Eco {

    public static void main(String[] args) {

        try{
            BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
            InetAddress dir=null;
            String host="";
            int pto=9000;
            System.out.print("\nEscribe la dirección del servidor: ");
            host=br.readLine();
            try{
                dir=InetAddress.getByName(host);
            }catch (UnknownHostException u){
                System.out.println("Dirección no valida");
                main(args);
            }//catch
            Socket cl = new Socket(dir,pto);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            while(true){
                System.out.println("Escribe un mensaje <Enter> para enviar o\"salir\" para terminar: ");
                String msj = br.readLine();
		pw.println(msj);
		pw.flush();
		if(msj.compareToIgnoreCase("salir")==0){
                    System.out.println("Termina la aplicacion");
                    pw.close();
                    br2.close();
                    cl.close();
                    System.exit(0);
		}else{
                    String eco=br2.readLine();
                    System.out.println("Eco recibido: " + eco);
		}//else
            }//while
	}catch (Exception e){
            e.printStackTrace();
	}//catch	
    }
}
