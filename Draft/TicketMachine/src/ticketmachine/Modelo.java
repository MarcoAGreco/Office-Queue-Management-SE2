
package ticketmachine;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Modelo extends Observable{
//    private int xPosicion = 0;
//    private int xDelta= 1;
//    private int idiom;
//    private int RequestType;
    
    public void ThrowTicket (int type,int idioma,int counter){
        Ticket ticket=new Ticket(type,idioma,counter);
    }
     
//    public int getType(){ return RequestType; }
//    public int getIdiom(){ return idiom; }
//    public int getXposicion(){ return xPosicion; }
    
    public static void agrega(Component c, GridBagConstraints gb, JFrame v, int x, int y){
        gb.gridx=x;
        gb.gridy=y;
        gb.weightx=0;
        gb.weighty=0;
        v.add(c,gb);
    }
    
//     public void LanzarJuego(int NumeroTema1,int v1,int v2,String Imagen,String ImagenOver){
//         Juego juego=new Juego(NumeroTema1,v1,v2, Imagen, ImagenOver);
//         setChanged();
//         notifyObservers();
//        
//     }
     
    public int getNumeroTema(int NumeroTema1){
         return NumeroTema1;     
    }
 
    
    
//    public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
//        
//        try{
//            FileInputStream FicheroTexto;
//            ObjectInputStream FicheroBinario;
//            FicheroTexto = new FileInputStream("puntuaciones.dat");
//            FicheroBinario = new ObjectInputStream(FicheroTexto);
//            ArrayList<Integer> puntuacionesF;
//            puntuacionesF = (ArrayList<Integer>) FicheroBinario.readObject();
//            puntuaciones = puntuacionesF;
//            setChanged();
//            notifyObservers();
//        }catch(Exception e){
//            System.out.println("Aun no hay ninguna puntuacion metida");
//            puntuaciones = new ArrayList();
//            puntuaciones.add(-1);
//            puntuaciones.add(-1);
//            puntuaciones.add(-1);
//        }
//    }
//    
//    public static void guardarDatos()throws FileNotFoundException, IOException{
//        
//        ObjectOutputStream ficheroBinario;
//        try (FileOutputStream ficheroTexto = new FileOutputStream("puntuaciones.dat")) {
//            ficheroBinario = new ObjectOutputStream(ficheroTexto);
//            ficheroBinario.writeObject(puntuaciones);
//        }
//        ficheroBinario.close();
//                
//    }  
    
}

