package PeticionesHttp;

import Cabecera_Enviar.Cabecera;
import Status_Code.Code;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manejador extends Thread {

    public Socket socket;
    public PrintWriter pw;
    public BufferedOutputStream bos;
    public DataInputStream br;
    public String FileName = "";
    public String extension = "";
    public Code codigoEnviar = new Code();
    public HashMap<String, String> hM_mimes = new HashMap<>();

    public Manejador(Socket _socket) throws Exception {
        this.socket = _socket;
    }

    @Override
    public void run() {
        hM_mimes.put("doc", "application/msword");
        hM_mimes.put("pdf", "application/pdf");
        hM_mimes.put("tex", "application/x-tex");
        hM_mimes.put("zip", "application/zip");

        hM_mimes.put("mp3", "audio/mpeg");
        hM_mimes.put("wav", "audio/x-wav");

        hM_mimes.put("bmp", "image/bmp");
        hM_mimes.put("gif", "image/gif");
        hM_mimes.put("jpe", "image/jpeg");
        hM_mimes.put("jpeg", "image/jpeg");
        hM_mimes.put("jfif", "image/pipeg");
        hM_mimes.put("svg", "image/svg+xml");
        hM_mimes.put("ico", "image/x-icon");

        hM_mimes.put("htm", "text/html");
        hM_mimes.put("html", "text/html");
        hM_mimes.put("stm", "text/html");
        hM_mimes.put("c", "text/plain");
        hM_mimes.put("h", "text/plain");
        hM_mimes.put("txt", "text/plain");
        hM_mimes.put("rtx", "text/richtext");

        hM_mimes.put("mp2", "video/mpeg");
        hM_mimes.put("mpa", "video/mpeg");
        hM_mimes.put("mpe", "video/mpeg");
        hM_mimes.put("mpg", "video/mpeg");
        hM_mimes.put("mpv2", "video/mpeg");
        hM_mimes.put("mov", "video/quicktime");
        hM_mimes.put("mp4", "video/mp4");

        hM_mimes.put("vrml", "x-world/x-vrml");

        try {
            br = new DataInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
            pw = new PrintWriter(new OutputStreamWriter(bos));
            byte[] b = new byte[1024];
            int g = br.read(b);
            if (g < 0) {
                g = 0;
            }
            System.out.println("\n\n\n==================RECIBIENDO DATOS==================");
            System.out.println("Tamaño de los datos enviados : " + g);
            String line = new String(b, 0, g);

            //======================================Proceso de análisis de la línea
            System.out.println("*********************CABECERA*********************");
            String[] splitline = line.split("\n");

            for (int i = 0; i < splitline.length; i++) {
                System.out.println(i + " " + splitline[i]);
            }
            line = splitline[0];
            String parametrospost = splitline[splitline.length - 1];
            //=======================================

            System.out.println("==========================================================");       //Conexión exitosa
            System.out.println("Cliente Conectado desde: " + socket.getInetAddress());
            System.out.println("Por el puerto: " + socket.getPort());
            System.out.println("Datos: " + line);
            System.out.println("==========================================================\n");

            if (line == null) {                                                                     //Si la linea (cabezera que se manda al servidor) es nula
                SendA("LineaVacia.html", "Not Found", "html");
                socket.close();
                return;
            } else if (line.toUpperCase().startsWith("GET")) {                                        //cuando tienes parámetros dentro de la petición
                if (!line.contains("?")) {                                                          //Cuando no tiene parámetros el html y se solicita un recurso
                    String ext = getArch(line); //Obtiene el recurso solicitado por el usuario
                    if (FileName.compareTo("") == 0) {                                              //Si es vacio manda el recurso por default
                        SendA("index.htm", "OK", "html");
                    } else {
                        int coincidenciaFile = 0;
                        File dir = new File(".");
                        String[] ficheros = dir.list();
                        if (ficheros == null) {
                            System.out.println("No hay ficheros en el directorio especificado");
                        } else {
                            for (int x = 0; x < ficheros.length; x++) {
                                if (FileName.equals(ficheros[x])) {
                                    coincidenciaFile = 1;
                                    break;
                                }
                            }
                        }
                        if (coincidenciaFile == 1) {
                            SendA(FileName, "OK", ext);
                        } else {
                            SendA("NoEncontrado.html", "Not Found", ext);
                        }
                    }
                } else {
                    //análisis de la linea para obtener los parámetros usando método GET
                    StringTokenizer tokens = new StringTokenizer(line, "?");
                    String req_a = tokens.nextToken();
                    String req = tokens.nextToken();
                    System.out.println("Token1: " + req_a + "\r\n\r\n");
                    System.out.println("Token2: " + req + "\r\n\r\n");
                    StringTokenizer tokens2 = new StringTokenizer(req, " ");

                    //Parámetros obtenidos
                    String parametros = tokens2.nextToken();
                    System.out.println("Los parámetros son: " + parametros);
                    String[] splitString = parametros.split("&");
                    System.out.println("Parámetros después del Split:");
                    for (String splitString1 : splitString) {
                        System.out.println(splitString1);
                    }
                    Code codigoEnvio = new Code();
                    Cabecera cabeceraEnvio = new Cabecera(codigoEnvio.getCode("OK"));
                    String sb = cabeceraEnvio.getCabecera();
                    bos.write(sb.getBytes());
                    bos.flush();
                    pw.print("<!DOCTYPE html>");
                    pw.flush();
                    pw.print("<html>"
                            + "<head>"
                            + "<title>SERVIDOR WEB</title>");
                    pw.flush();
                    pw.print("<meta charset=\"UTF-8\">"
                            + "<meta http-equiv=&quot;Content-Type&quot; content=&quot;text/html; charset=utf-8&quot; />"
                            + "<meta name=&quot;description&quot; content=&quot;Esta Tienda está desarrollada con PrestaShop&quot;/>");
                    pw.flush();
                    pw.print("<style>"
                            + "::-moz-selection {background: #b3d4fc; text-shadow: none;}"
                            + "::selection {background: #b3d4fc; text-shadow: none;}"
                            + "html {padding: 30px 10px; font-size: 16px; line-height: 1.4; color: #737373; background: #f0f0f0;"
                            + "-webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}"
                            + "html,"
                            + "input {font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;}"
                            + "body {max-width:700px; _width: 700px; padding: 30px 20px 50px; border: 1px solid #b3b3b3;"
                            + "border-radius: 4px;margin: 0 auto; box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;"
                            + "background: #fcfcfc;}"
                            + "h1 {margin: 0 10px; font-size: 50px; text-align: center;}"
                            + "h1 span {color: #bbb;}"
                            + "h2 {color:#5D6D7E;margin: 0 10px;font-size: 30px;text-align: center;}"
                            + "h2 span {color: #bbb;font-size: 80px;}"
                            + "h3 {margin: 1.5em 0 0.5em;}"
                            + "p {margin: 1em 0;}"
                            + "ul {padding: 0 0 0 40px;margin: 1em 0;}"
                            + ".container {max-width: 380px;_width: 480px;margin: 0 auto;}"
                            + "input::-moz-focus-inner {padding: 0;border: 0;}"
                            + "</style>");
                    pw.flush();
                    pw.print("</head>"
                            + "<body>");
                    pw.flush();
                    pw.print("<div class=&quot;container&quot;>"
                            + "<h2>LOS PARÁMETROS OBTENIDOS POR EL SERVIDOR SON:</h2>");
                    pw.flush();
                    for (int i = 0; i < splitString.length; i++) {
                        pw.print("<p align=\"center\">" + splitString[i] + "<br /><br /></p>");
                        pw.flush();
                    }
                    pw.print("</div>"
                            + "</body>"
                            + "</html>");
                    pw.flush();
                }
            } else if (line.toUpperCase().startsWith("POST")) {

                //Parámetros obtenidos
                String parametros = parametrospost;
                System.out.println("Los parámetros son: " + parametros);
                String[] splitString = parametros.split("&");
                System.out.println("Parámetros después del Split:");
                for (String splitString1 : splitString) {
                    System.out.println(splitString1);
                }
                Code codigoEnvio = new Code();
                Cabecera cabeceraEnvio = new Cabecera(codigoEnvio.getCode("OK"));
                String sb = cabeceraEnvio.getCabecera();
                bos.write(sb.getBytes());
                bos.flush();
                pw.print("<!DOCTYPE html>");
                pw.flush();
                pw.print("<html>"
                        + "<head>"
                        + "<title>SERVIDOR WEB</title>");
                pw.flush();
                pw.print("<meta charset=\"UTF-8\">"
                        + "<meta http-equiv=&quot;Content-Type&quot; content=&quot;text/html; charset=utf-8&quot; />"
                        + "<meta name=&quot;description&quot; content=&quot;Esta Tienda está desarrollada con PrestaShop&quot;/>");
                pw.flush();
                pw.print("<style>"
                        + "::-moz-selection {background: #b3d4fc; text-shadow: none;}"
                        + "::selection {background: #b3d4fc; text-shadow: none;}"
                        + "html {padding: 30px 10px; font-size: 16px; line-height: 1.4; color: #737373; background: #f0f0f0;"
                        + "-webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}"
                        + "html,"
                        + "input {font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;}"
                        + "body {max-width:700px; _width: 700px; padding: 30px 20px 50px; border: 1px solid #b3b3b3;"
                        + "border-radius: 4px;margin: 0 auto; box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;"
                        + "background: #fcfcfc;}"
                        + "h1 {margin: 0 10px; font-size: 50px; text-align: center;}"
                        + "h1 span {color: #bbb;}"
                        + "h2 {color:#5D6D7E;margin: 0 10px;font-size: 30px;text-align: center;}"
                        + "h2 span {color: #bbb;font-size: 80px;}"
                        + "h3 {margin: 1.5em 0 0.5em;}"
                        + "p {margin: 1em 0;}"
                        + "ul {padding: 0 0 0 40px;margin: 1em 0;}"
                        + ".container {max-width: 380px;_width: 480px;margin: 0 auto;}"
                        + "input::-moz-focus-inner {padding: 0;border: 0;}"
                        + "</style>");
                pw.flush();
                pw.print("</head>"
                        + "<body>");
                pw.flush();
                pw.print("<div class=&quot;container&quot;>"
                        + "<h2>LOS PARÁMETROS OBTENIDOS POR EL SERVIDOR SON:</h2>");
                pw.flush();
                for (int i = 0; i < splitString.length; i++) {
                    pw.print("<p align=\"center\">" + splitString[i] + "<br /><br /></p>");
                    pw.flush();
                }
                pw.print("</div>"
                        + "</body>"
                        + "</html>");
                pw.flush();

            } else if (line.toUpperCase().startsWith("HEAD")) {
                if (!line.contains("?")) {                                                          //Cuando no tiene parámetros el html y se solicita un recurso
                    String ext = getArch(line); //Obtiene el recurso solicitado por el usuario
                    if (FileName.compareTo("") == 0) {                                              //Si es vacio manda el recurso por default
                        SendA("NoEncontrado.html", "Not Found", "html");
                    } else {
                        int coincidenciaFile = 0;
                        File dir = new File(".");
                        String[] ficheros = dir.list();
                        if (ficheros == null) {
                            System.out.println("No hay ficheros en el directorio especificado");
                        } else {
                            for (int x = 0; x < ficheros.length; x++) {
                                if (FileName.equals(ficheros[x])) {
                                    coincidenciaFile = 1;
                                    break;
                                }
                            }
                        }
                        if (coincidenciaFile == 1) {
                            SendA(FileName, "Found", ext);
                        } else {
                            SendA("NoEncontrado.html", "Not Found", "html");
                        }
                    }
                } else {
                    Code codigoEnvio = new Code();
                    Cabecera cabeceraEnvio = new Cabecera(codigoEnvio.getCode("Not Implemented"));
                    String sb = cabeceraEnvio.getCabecera();
                    bos.write(sb.getBytes());
                    bos.flush();
                }
            } else if (line.toUpperCase().startsWith("DELETE")) {
                if (!line.contains("?")) {                                                          //Cuando no tiene parámetros el html y se solicita un recurso
                    String ext = getArch(line);                                                                  //Obtiene el recurso solicitado por el usuario
                    if (FileName.compareTo("") == 0) {                                              //Si es vacio manda el recurso por default
                        SendA("NoEncontrado.html", "Not Found", ext);
                    } else {
                        int coincidenciaFile = 0;
                        File dir = new File(".");
                        String[] ficheros = dir.list();
                        if (ficheros == null) {
                            System.out.println("No hay ficheros en el directorio especificado");
                        } else {
                            for (String fichero : ficheros) {
                                if (FileName.equals(fichero)) {
                                    coincidenciaFile = 1;
                                    break;
                                }
                            }
                        }
                        if (coincidenciaFile == 1) {
                            File archivoEliminar = new File(FileName);
                            if (archivoEliminar.delete()) {
                                System.out.println("Archivo Eliminado");
                                SendA("ArchEliminado.html", "OK", "html");
                            } else {
                                System.out.println("Archivo NO Eliminado");
                                SendA("ArchEliminado.html", "Unauthorized", "html");
                            }
                        } else {
                            SendA("NoEncontrado.html", "Not Found", "html");
                        }
                    }
                } else {
                    Code codigoEnvio = new Code();
                    Cabecera cabeceraEnvio = new Cabecera(codigoEnvio.getCode("Not Implemented"));
                    String sb = cabeceraEnvio.getCabecera();
                    bos.write(sb.getBytes());
                    bos.flush();
                }

            } else {
                SendA("NoImplementado.html", "Not Implemented", "html");
            }
            pw.flush();
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //socket.close();
        
            
        
    }

    public String getArch(String line) {
        int i = line.indexOf("/");
        int f = line.indexOf(" ", i);
        System.out.println("###########################################");
        System.out.println("Inicio del nombre del recurso: " + i);
        System.out.println("Fin del nombre del recurso: " + f);
        FileName = line.substring(i + 1, f);
        if (FileName.compareTo("") != 0) {
            String[] splitString = FileName.split("\\.");
            extension = splitString[1];
        }
        System.out.println("Nombre del recurso solicitado:" + FileName + " y su extención: " + extension);
        System.out.println("###########################################");
        return extension;
    }

    public void SendA(String arg, String code, String extensionF) {
        try {
            Code codigoEnvio = new Code();
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
            Cabecera cabeceraEnvio = new Cabecera(codigoEnvio.getCode(code), tam_archivo, hM_mimes.get(extensionF));
            String sb = cabeceraEnvio.getCabecera();
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
