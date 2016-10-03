/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import java.math.BigInteger;

/**
 *
 * @author Anita
 */
public class nodoDoble<T extends Comparable<T>>{
    private T dato;
    private nodoDoble<T> anterior,siguiente;

    public nodoDoble(T dato){
        this.dato=dato;
        siguiente=null;
        anterior=null;
    }

    public void setDato(T dato){
        this.dato=dato;
    }

    public T getDato(){
        return dato;
    }

    public void setSiguiente(nodoDoble<T> siguiente){
        this.siguiente=siguiente;
    }

    public nodoDoble<T> getSiguiente(){
        return siguiente;
    }

    public void setAnterior(nodoDoble<T> anterior){
        this.anterior=anterior;
    }

    public nodoDoble<T> getAnterior(){
        return anterior;
    }

    public String toString(){
        String s="";       
        s+=""+getDato()+"";
        return s;
    }
}