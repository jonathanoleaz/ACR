package Cabecera_Prueba;

import java.io.File;

public class PruebaEliminarArch {
    public static void main(String[] args) {
        File archivoEliminar = new File("Test.txt");
        if(archivoEliminar.delete())
            System.out.println("Archivo Eliminado");
        else
            System.out.println("Archivo NO Eliminado");
    }
}
