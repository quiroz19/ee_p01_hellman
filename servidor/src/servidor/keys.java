/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import java.util.Random;
import java.math.BigInteger;

/**
 *
 * @author Anita
 */
public class keys{
    listaDoble<BigInteger> lista= new listaDoble<BigInteger>();    
    int bits=0;
    public BigInteger calcular(String text){
        //obtiene bits del mensaje
        keys mensaje= new keys();//Lista con valores de bits
        String binary = new BigInteger(text.getBytes()).toString(2);
        bits =binary.length();
        
        for(int x=0;x<binary.length();x++){
            //finarl
            char m=binary.charAt(x);
            String s="";
            s+=m;            
            mensaje.lista.insertarFinal( new BigInteger( s ) );                     
        }
        System.out.println("valor en bits "+binary);
        //-------------------------------------------------------
        keys keyPublic=new keys();//lista con llave publica

        BigInteger aleatorio=new BigInteger("10");
        BigInteger doble=new BigInteger("3");
        for(int x=0;x<bits;x++){       
            BigInteger valor= random(aleatorio);//Primer Numero
            lista.insertarFinal( valor  );
            aleatorio=new BigInteger(aleatorio.multiply(doble).toString());

        }
        System.out.println("w: " +lista);
        BigInteger sumatoria=sumatoria();
        System.out.println("sumatoria de w: "+sumatoria);
        BigInteger q= random(sumatoria);       
        System.out.println("q: "+q);
        BigInteger r=r(q);
        System.out.println("r: "+r);
        PublicKey(keyPublic,r,q);//secuenta llave publica 
        //multiplicar llave publica por el valor de la pocicion del bit del mensaje
        System.out.println("public key: "+keyPublic.lista);
        mensaje.multiplicar(keyPublic);
        System.out.println("private key * bits: "+mensaje.lista);
        BigInteger x =mensaje.mensajeX(binary);
        System.out.println("sumatoria de los valores w= 1: "+x);
        BigInteger clave=valorX(r,x,q);
        System.out.println("clave: "+clave);
return clave;
    }//clave
        public String desencriptar (BigInteger clave){
        keys binarioFinal=new keys();
        
        desencriptar(binarioFinal,clave);//desencriptar
        System.out.println(binarioFinal.lista);
        return binarioFinal.lista +"";
    }

    public BigInteger sumatoria(){
        nodoDoble<BigInteger> uno=lista.getInicio();    
        nodoDoble<BigInteger> dos=uno.getSiguiente();
        BigInteger total= uno.getDato();
        while(dos!=null){
            total=new BigInteger(total.add(dos.getDato()).toString());
            dos=dos.getSiguiente();
        }
        return total;
    }

    public BigInteger random(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) <= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;      
    }

    public BigInteger r(BigInteger q){
        BigInteger uno= new BigInteger("1");
        BigInteger random=random2(q);
        BigInteger r=q.gcd(random); //gcd
        while(r.compareTo(uno)!=0){
            random=random2(q);           
            r=q.gcd(random);//gcd
        }       
        return random;
    }

    public BigInteger random2(BigInteger n){//calcular modulo de r
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;      
    }

    public void PublicKey(keys k,BigInteger r,BigInteger q){
        nodoDoble<BigInteger> w=lista.getInicio();                    
        while(w!=null){
            nodoDoble<BigInteger> key2=new nodoDoble<BigInteger>(r);
            key2.setDato(new BigInteger(w.getDato().multiply(r).toString()) );           
            key2.setDato( key2.getDato().mod(q));
            k.lista.insertarFinal( key2.getDato());
            w=w.getSiguiente();            
        }        
    }

    public void multiplicar(keys keypublic){
        nodoDoble<BigInteger> resu=lista.getInicio();
        nodoDoble<BigInteger> pubkey=keypublic.lista.getInicio();
        while(resu!=null){          
            resu.setDato(new BigInteger(resu.getDato().multiply(pubkey.getDato() ).toString()));
            resu=resu.getSiguiente();
            pubkey=pubkey.getSiguiente();
        }
    }

    public BigInteger mensajeX(String bit){
        BigInteger sumatoria = new BigInteger("0");
        nodoDoble<BigInteger> w=lista.getInicio();
        int x=0;
        char m;
        while(w!=null){
            m=bit.charAt(x);           
            String s="";
            s+=m;
            if( s.compareTo("1") ==0){           
                sumatoria=new BigInteger(sumatoria.add(w.getDato()).toString());
            }
            w=w.getSiguiente();
            x++;            
        }

        return sumatoria;
    }

    public BigInteger valorX(BigInteger r,BigInteger x, BigInteger q ){
        BigInteger resu= new BigInteger((x).multiply(r.modInverse(q)).toString()).mod(q) ;
        return resu;
    }

    public void desencriptar(keys binario,BigInteger resu){
        BigInteger cero= new BigInteger("0");
        BigInteger uno= new BigInteger("1");
        nodoDoble<BigInteger> w=lista.ultimo();        
        while(w!=null){
            if(w.getDato().compareTo(resu)<=0){
                resu=new BigInteger(resu.subtract(w.getDato()).toString());                
                binario.lista.insertarInicio(uno);
            }else{
                binario.lista.insertarInicio(cero);
            }
            w=w.getAnterior();
        }
    }
}
