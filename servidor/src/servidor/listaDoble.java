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
public class listaDoble<T extends Comparable<T>>{
    private nodoDoble<T> inicio;

    public nodoDoble<T> getInicio(){
        return inicio;       
    }

    public void setInicio(nodoDoble<T> inicio){
        this.inicio=inicio;
    }    

    public boolean vacia(){
        return inicio==null;
    }

    public void insertarInicio(T lista){
        nodoDoble<T> nodo= new nodoDoble<T>(lista);
        if(!vacia()){            
            nodo.setSiguiente(inicio);
            inicio.setAnterior(nodo);
            inicio=nodo;
        }else
            inicio=nodo;
    }

    public void insertarFinal(T lista){
        nodoDoble<T> nodo= new nodoDoble<T>(lista);
        if(!vacia()){
            nodoDoble<T> temporal = inicio;
            while(temporal.getSiguiente()!=null){
                temporal=temporal.getSiguiente();
            }
            temporal.setSiguiente(nodo);
            nodo.setAnterior(temporal);
        }else{
            inicio=nodo;
        }
    }

    public void insertarAntesDe(T lista,T referencia){        
        if(!vacia()){                        
            nodoDoble<T> temporal=inicio;
            boolean flag=true;
            while( temporal.getDato()!=referencia && flag ){
                if(temporal.getSiguiente()!=null){
                    temporal=temporal.getSiguiente();
                }else{
                    flag=false; System.out.println("referencia no encontrada");
                }
            }

            if(flag){
                nodoDoble<T> nodo= new nodoDoble<T>(lista);
                nodoDoble<T> encontrado= temporal.getAnterior();
                encontrado.setSiguiente(nodo);
                nodo.setSiguiente(temporal);
            }                            
        }else
            System.out.println("la lista esta vacia");
    }

    public nodoDoble<T> buscar(T lista){
        nodoDoble<T> temporal=null;
        if(!vacia()){
            temporal=inicio;
            boolean flag=true;
            while(lista.compareTo(temporal.getDato())!=0 && flag){
                if(temporal.getSiguiente()!=null){
                    temporal=temporal.getSiguiente();
                }else
                    flag=false; return null;
            }
        }
        return temporal;
    }   

    public nodoDoble<T> ultimo(){
        nodoDoble<T> nodo=inicio;
        while(nodo.getSiguiente()!=null){
            nodo=nodo.getSiguiente();            
        }
        return nodo;
    }

    public String toString (){
        String s="";
        nodoDoble<T> temporal=inicio;
        if(!vacia()){
            while(temporal!=null){
                
                if(temporal==inicio){
                    s+=temporal;
                }else{
                    s+=temporal;               
                }
                temporal=temporal.getSiguiente();
            }
        }else{            
            System.out.println("la lista esta vacia");
        }
        return s;
    }
}
