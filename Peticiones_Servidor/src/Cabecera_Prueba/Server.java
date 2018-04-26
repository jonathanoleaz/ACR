package Cabecera_Prueba;

import ThreadPool.Pool;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception{
        int PUERTO=8000;
        ServerSocket ss;
        System.out.println("Iniciando Servidor de Peticiones GET");
	ss=new ServerSocket(PUERTO);
	System.out.println("Servidor iniciado -> OK");
        System.out.println("Esperando por un Cliente 25/4/18....");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        
	for(;;){
            Socket accept=ss.accept();
            //RecibirCabecera nuevo = new RecibirCabecera(accept);
            Runnable nuevo = new RecibirCabecera(accept);
            executor.execute(nuevo);
            //nuevo.start();
	}
    }
}
