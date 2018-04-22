package Cabecera_Prueba;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class RecibirCabecera extends Thread{
    public Socket socket;
    public PrintWriter pw;
    public BufferedOutputStream bos;
    public DataInputStream br;
    public String FileName="";
    public String extencion="html";
    
    public RecibirCabecera(Socket _socket) throws Exception{
	this.socket=_socket;
    }
    
    @Override
    public void run(){
        try {
            br = new DataInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
            pw = new PrintWriter(new OutputStreamWriter(bos));
            byte [] b = new byte[1024];
            int g = br.read(b);
            if(g<0)
                g=0;
            System.out.println("\n\n\n==================RECIBIENDO DATOS==================");   
            System.out.println("Tamaño de los datos enviados : " + g);
            String line = new String (b,0,g);
            
            //======================================Proceso de análisis de la línea
            System.out.println("*********************CABECERA*********************");
            String[] splitString = line.split("\n");
            for (int i=0;i<splitString.length;i++) {
                System.out.println(i+" "+splitString[i]);
            }
            line=splitString[0];
            //=======================================
              
            System.out.println("==========================================================");//Conexión exitosa
            System.out.println("Cliente Conectado desde: " + socket.getInetAddress());
            System.out.println("Por el puerto: " + socket.getPort());
            System.out.println("Datos: " + line);
            System.out.println("==========================================================\n");
            
            if (line.indexOf("?") == -1) {              //Cuando no tiene parámetros el html
                getArch(line);                          //Obtiene el recurso solicitado por el usuario
                if (FileName.compareTo("") == 0) {      //Si es vacio manda el recurso por default
                    SendA("index.htm");
                } else {
                    SendA(FileName);                    //mandas el recurso al browser
                }
            }else {
                pw.println("HTTP/1.0 501 Not Implemented");
                pw.println();
            }
            pw.flush();
            bos.flush();
            bos.close();
            br.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getArch(String line) {
        int i;
        int f;
        
        if (line.toUpperCase().startsWith("GET")) {
            i = line.indexOf("/");
            f = line.indexOf(" ", i);
            System.out.println("###########################################");
            System.out.println("Inicio del nombre del recurso: "+i);
            System.out.println("Fin del nombre del recurso: "+f);
            FileName = line.substring(i + 1, f);
            if(FileName.compareTo("")!=0){
                String [] splitString=FileName.split("\\.");
                extencion=splitString[1];
            }
            System.out.println("Nombre del recurso solicitado:" + FileName + " y su extención: " + extencion);
            System.out.println("###########################################");
        }
    }
    
    public void SendA(String arg) {
        try {
            int b_leidos = 0;
            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(arg));
            byte[] buf = new byte[1024];
            int tam_bloque = 0;
            if (bis2.available() >= 1024) {
                tam_bloque = 1024;
            } else {
                bis2.available();
            }
            int tam_archivo = bis2.available();
            String sb = "";
            sb = sb + "HTTP/1.0 200 ok\n";
            sb = sb + "Server: Axel Server/1.0 \n";
            sb = sb + "Date: " + new Date() + " \n";
            
            sb = sb + "Content-Type: text/html \n";//modificar lor tipo MIME
            
            sb = sb + "Content-Length: " + tam_archivo + " \n";
            sb = sb + "\n";
            bos.write(sb.getBytes());
            bos.flush();
            while ((b_leidos = bis2.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, b_leidos);
            }
            bos.flush();
            bis2.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
