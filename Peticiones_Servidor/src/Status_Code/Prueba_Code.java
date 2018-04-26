package Status_Code;

import Cabecera_Enviar.Cabecera;

public class Prueba_Code {
    public static void main(String[] args) {
        Code nuevo = new Code();
        System.out.println(nuevo.getCode("Not Found"));
        Cabecera nueva = new Cabecera(nuevo.getCode("Not Found"),0, "html");
        System.out.println("==Inicio de cabecera==");
        System.out.println(nueva.getCabecera());
        System.out.println("==Fin de cabecera==");
    }
}
