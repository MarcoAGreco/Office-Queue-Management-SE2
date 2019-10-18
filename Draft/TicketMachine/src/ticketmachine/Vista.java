///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ticketmachine;
//import java.awt.Font;
//import java.util.Observable;
//import java.util.Observer;
//import javax.swing.JLabel;
//import javax.swing.*;
//
//public class Vista extends JPanel implements Observer{ 
//    Modelo model;
//    int etiqueta; 
//    int numeroPaso= 0; 
//    private JLabel type=new JLabel(),number=new JLabel();
//    String letter;
//    private Font fuente4=new Font("Serif", Font.BOLD + Font.ITALIC, 60);
//    
//    public Vista(Modelo modelo){ 
//        model=modelo;
//        int etiqueta = 0; 
//        letter="C";
//        
//        add(number);
//        add(type);
//    }
//    
//    public void update (Observable obs, Object arg){ 
//        System.out.println("esto es lo primero"+model.getXposicion());
//        System.out.println(model.getIdiom());
//        System.out.println(model.getType());
//        if (model.getType()==1){
//            letter="A";
//            if (model.getIdiom()==1){type=new JLabel("TYPE: Accounting");}
//            if (model.getIdiom()==2){type=new JLabel("TIPO: Contabilità");}
//            if (model.getIdiom()==3){type=new JLabel("TIPO: Contabilidad");}
//        }
//        else{
//            letter="B";
//            if (model.getIdiom()==1){type=new JLabel("TYPE: Packing");}
//            if (model.getIdiom()==2){type=new JLabel("TIPO: Imballaggio");}
//            if (model.getIdiom()==3){type=new JLabel("TIPO: Embalaje");}
//        }
//               
//        type.setFont(fuente4);
//        etiqueta=model.getXposicion();
//        number=new JLabel(letter+etiqueta);
//        System.out.println(type);
//        System.out.println(number);
//        
//    } 
//}
//
