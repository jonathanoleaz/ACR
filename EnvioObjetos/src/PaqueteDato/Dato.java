/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaqueteDato;
import java.io.Serializable;

/**
 *
 * @author jonat
 */
public class Dato implements Serializable{
    /*En la definición de clase que se va a enviar, se debe implementar la interfaz Serializable(java.io.Seriazable)*/
    String nombre;
    int edad;
    String telefono;
    float sueldo;       //al serializar el objeto, el valor de sueldo será el que asigne el constructor por omisión
                        // con transient float sueldo;
                            //primitivos=0;
                            //Objetos=null;
    
    public Dato(String n, int e, String t, float s){
        this.nombre=n;
        this.edad=e;
        this.telefono=t;
        this.sueldo=s;
        
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getEdad(){
        return this.edad;
    }
    public String getTelefono(){
        return this.telefono;
    }
    float getSueldo(){
        return this.sueldo;
    }
    
}
