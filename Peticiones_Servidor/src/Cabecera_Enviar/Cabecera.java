package Cabecera_Enviar;

import java.util.Date;

public class Cabecera {
    String cabecera;
    Date fechaActual = new Date();
    
    public Cabecera(String codigoRespuesta,int tam_archivo, String mimeType){
        this.cabecera="HTTP/1.0 "+ codigoRespuesta+ "\n"
                    + "Server: MyServer/1.0 \n"
                    + "Date: " + fechaActual + " \n"
            
                    + "Content-Type: "+mimeType+" \n" //modificar lor tipo MIME
            //  text/html
                    + "Content-Length: " + tam_archivo + " \n"
                    + "\n";
    }
    
    public Cabecera(String codigoRespuesta){
        this.cabecera="HTTP/1.0 "+ codigoRespuesta+ "\n"
                    + "Server: MyServer/1.0 \n"
                    + "Date: " + fechaActual + " \n"
            
                    + "Content-Type: text/html \n" //modificar lor tipo MIME
                    + "\n";
    }
    
    public String getCabecera(){
        return cabecera;
    }
}
