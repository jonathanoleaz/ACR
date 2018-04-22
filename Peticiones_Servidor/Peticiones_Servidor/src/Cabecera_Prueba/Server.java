package Cabecera_Prueba;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        int PUERTO=8000;
        ServerSocket ss;
        System.out.println("Iniciando Servidor de Peticiones GET");
	ss=new ServerSocket(PUERTO);
	System.out.println("Servidor iniciado -> OK");
        System.out.println("Esperando por un Cliente....");
	for(;;){
            Socket accept=ss.accept();
            RecibirCabecera nuevo = new RecibirCabecera(accept);
            nuevo.start();
	}
    }
}
