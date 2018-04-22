package Cabecera_Prueba;

import java.io.File;

public class PruebaObtenerArchivos {
    public static void main(String[] args) {
        File dir = new File(".");
        String[] ficheros = dir.list();
        if (ficheros == null)
            System.out.println("No hay ficheros en el directorio especificado");
        else { 
            for (int x=0;x<ficheros.length;x++)
                System.out.println(ficheros[x]);
        }
    }
}
