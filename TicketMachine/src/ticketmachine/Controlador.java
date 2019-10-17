/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketmachine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Miguel
 */
public class Controlador extends JFrame{
    private JButton bottonAccounting,bottonPacking; 
    private JButton btnTema1, btnTema2,btnTema3;
    private JPanel titulo = new JPanel();
    private JPanel menu = new JPanel();
    private JLabel ayuda = new JLabel("TICKET OFFICE");
    private JLabel ayuda2 = new JLabel("SELECT REQUEST TYPE");
    private String textAccounting1,textAccounting2,textPacking1,textPacking2;
        
    private Color Color1= new Color(190,238,247);
    private Color Color2= new Color(111,194,208);
    private Color Color3= new Color(69,146,175);
    private Color Color4= new Color(255,130,70);
    private Color ColorJugar= new Color(249,248,235);
    private Font fuente1=new Font("Serif", Font.BOLD + Font.ITALIC, 25);
    private Font fuente2=new Font("Serif", Font.BOLD + Font.ITALIC, 60);
    private Font fuente3=new Font("Serif", Font.BOLD + Font.ITALIC, 80);
    private Font fuente4=new Font("Serif", Font.BOLD + Font.ITALIC, 45);
    
    private Color temaRojo= new Color(250,70,89);
    private Color temaAmarillo= new Color(254,255,228);
    private Color temaVerdeClaro= new Color(163,222,131);
    private Color temaVerdeOscuro= new Color(46,184,114);
    private Color temaVerde= new Color(37,147,91);
    
    private Color temaRosa= new Color(242,53,87);
    private Color temaAmarilloOscuro= new Color(240,212,58);
    private Color temaAzulClaro= new Color(34,178,218);
    private Color temaAzulOscuro= new Color(0,153,117);
    private Modelo modelo = new Modelo();
    
    private int counter=0;
    private int idiom=1;
    
    public Controlador(){
        setTitle("TicketMachine");
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.fill=GridBagConstraints.BOTH;
        setBounds(300, 100, 300, 600);


        //Configuracion titulo
        ayuda.setFont(fuente3);
        titulo.setLayout(new FlowLayout());
        titulo.add(ayuda);
        titulo.setBackground(Color4);
        titulo.setPreferredSize(new Dimension(1000,100));
        agrega(titulo,gb,this,0,0);


        //Configuracion menu
        menu.setBackground(Color1);
        menu.setPreferredSize(new Dimension(1000,500));
        menu.setLayout(null);

        ayuda2.setFont(fuente2);
        ayuda2.setLocation(150,10);
        ayuda2.setSize(800,200);
        menu.add(ayuda2);

        textAccounting1="Accounting";
        bottonAccounting= new JButton(textAccounting1);
        bottonAccounting.setFont(fuente1);
        bottonAccounting.setBackground(ColorJugar);
        bottonAccounting.setLocation(125,200);
        bottonAccounting.setSize(350, 70);
        bottonAccounting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter+=1;
                modelo.ThrowTicket(1,idiom,counter);
            }
        });
        menu.add(bottonAccounting);

        textPacking1="Packing";
        bottonPacking= new JButton(textPacking1);
        bottonPacking= new JButton("Packing");
        bottonPacking.setFont(fuente1);
        bottonPacking.setBackground(ColorJugar);
        bottonPacking.setLocation(500,200);
        bottonPacking.setSize(350, 70);
        bottonPacking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter+=1;
                modelo.ThrowTicket(2,idiom,counter);
            }
        });
        menu.add(bottonPacking);


        btnTema1= new JButton("ENGLISH");
        btnTema1.setBackground(Color4);
        btnTema1.setLocation(0,404);
        btnTema1.setSize(100,33);
        btnTema1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    textAccounting2="Accounting";
                    textAccounting1= textAccounting1.replace(textAccounting1,textAccounting2); 
                    textPacking2="Packing";
                    textPacking1= textPacking1.replace(textPacking1,textPacking2);
                    bottonAccounting.setText(textAccounting1);
                    bottonPacking.setText(textPacking1);
                    ayuda2.setFont(fuente2);
                    ayuda2.setLocation(150,10);
                    ayuda2.setSize(800,200);
                    ayuda2.setText("SELECT REQUEST TYPE");
                    idiom=1;
                }
            });  
        menu.add(btnTema1);


        btnTema2= new JButton("ITALIAN");
        btnTema2.setBackground(Color4);
        btnTema2.setLocation(0,437);
        btnTema2.setSize(100,33);
        btnTema2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    textAccounting2="Contabilità";
                    textAccounting1= textAccounting1.replace(textAccounting1,textAccounting2); 
                    textPacking2="Imballaggio";
                    textPacking1= textPacking1.replace(textPacking1,textPacking2);
                    bottonAccounting.setText(textAccounting1);
                    bottonPacking.setText(textPacking1);
                    ayuda2.setFont(fuente4);
                    ayuda2.setLocation(100,10);
                    ayuda2.setSize(900,200);
                    ayuda2.setText("SELEZIONARE IL TIPO DI RICHIESTA");
                    idiom=2;
                }
        });
        menu.add(btnTema2);


        btnTema3= new JButton("ESPAÑOL");
        btnTema3.setBackground(Color4);
        btnTema3.setLocation(0,470);
        btnTema3.setSize(100,33);
        btnTema3.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    textAccounting2="Contabilidad";
                    textAccounting1= textAccounting1.replace(textAccounting1,textAccounting2); 
                    textPacking2="Embalaje";
                    textPacking1= textPacking1.replace(textPacking1,textPacking2);
                    bottonAccounting.setText(textAccounting1);
                    bottonPacking.setText(textPacking1);
                    ayuda2.setFont(fuente4);
                    ayuda2.setLocation(100,10);
                    ayuda2.setSize(900,200);
                    ayuda2.setText("SELECCIONAR TIPO DE SOLICITUD");
                    idiom=3;
                }
        });
        menu.add(btnTema3);

        agrega(menu,gb,this,0,1);

        pack();
        setVisible(true); 
        setResizable(true);
    }
    
    private static void agrega(Component c, GridBagConstraints gb, JFrame v, int x, int y){
        gb.gridx=x;
        gb.gridy=y;
        gb.weightx=1;
        gb.weighty=1;
        v.add(c,gb);
    }
}
    
class Ticket extends JFrame{
    
    private JButton volverJuego; 
    private JPanel titulo = new JPanel();
    private JPanel menu = new JPanel();
    private JPanel info = new JPanel();
    private JLabel ayuda = new JLabel("TICKET");
    private JLabel number, time,type,date;
    private String letter,minutos,horas,segundos;
    
    private String num;
    
    private Modelo modelo = new Modelo();
    private Color Color1= new Color(190,238,247);
    private Color Color2= new Color(111,194,208);
    private Color Color3= new Color(69,146,175);
    private Color Color4= new Color(255,130,70);
    private Color ColorJugar= new Color(249,248,235);
    private Font fuente1=new Font("Serif", Font.BOLD + Font.ITALIC, 25);
    private Font fuente2=new Font("Serif", Font.BOLD + Font.ITALIC, 20);
    private Font fuente3=new Font("Serif", Font.BOLD + Font.ITALIC, 80);
    private Font fuente4=new Font("Serif", Font.BOLD + Font.ITALIC, 60);
    
    private Color temaRojo= new Color(250,70,89);
    private Color temaAmarillo= new Color(254,255,228);
    private Color temaVerdeClaro= new Color(163,222,131);
    private Color temaVerdeOscuro= new Color(46,184,114);
    private Color temaVerde= new Color(37,147,91);
    
    private Color temaRosa= new Color(242,53,87);
    private Color temaAmarilloOscuro= new Color(240,212,58);
    private Color temaAzulClaro= new Color(34,178,218);
    private Color temaAzulOscuro= new Color(0,153,117);
    
    
    public Ticket(int RequestType,int idiom,int counter){
        //Configuración ventana
        setTitle("BuscaT");
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.fill=GridBagConstraints.BOTH;
        setBounds(300, 100, 300, 300);
        
   
        //Configuracion titulo
        ayuda.setFont(fuente3);
        titulo.setLayout(new FlowLayout());
        titulo.add(ayuda);
        titulo.setBackground(Color4);
        titulo.setPreferredSize(new Dimension(900,100));
        modelo.agrega(titulo,gb,this,0,0);
        
        
        //Configuracion cuerpo
        info.setBackground(Color1);
        info.setLayout(new GridLayout(4,1,50,50));
        info.setPreferredSize(new Dimension(900,500));
        
        if (RequestType==1){
            letter="A";
            if (idiom==1){type=new JLabel("TYPE: Accounting");}
            if (idiom==2){type=new JLabel("TIPO: Contabilità");}
            if (idiom==3){type=new JLabel("TIPO: Contabilidad");}
        }
        else{
            letter="B";
            if (idiom==1){type=new JLabel("TYPE: Packing");}
            if (idiom==2){type=new JLabel("TIPO: Imballaggio");}
            if (idiom==3){type=new JLabel("TIPO: Embalaje");}
        }
               
        type.setFont(fuente4);
        
          
        if (counter<10) num="00"+counter;
        if (counter>=10 & counter<100) num="0"+counter;
                
        if (idiom==1){number=new JLabel("NUMBER: "+letter+num);}
        if (idiom==2){number=new JLabel("NUMERO: "+letter+num);}
        if (idiom==3){number=new JLabel("NÚMERO: "+letter+num);}
        number.setFont(fuente4);
        
        long millis=System.currentTimeMillis();  
        java.sql.Date date1=new java.sql.Date(millis);
        if (idiom==1){date=new JLabel("DATE: " +date1.toString());}
        if (idiom==2){date=new JLabel("DATA: " +date1.toString());}
        if (idiom==3){date=new JLabel("FECHA: " +date1.toString());}
        date.setFont(fuente4);
        
        millis = System.currentTimeMillis();
        int hours   = (int) ((millis / (1000*60*60)) % 24);
        int minutes = (int) ((millis / (1000*60)) % 60);
        int seconds = (int) (millis / 1000) % 60 ;
        
        if (hours<10) horas="0"+hours;
        else horas=""+hours;
        if (minutes<10) minutos="0"+minutes;
        else minutos=""+minutes;
        if (seconds<10) segundos="0"+seconds;
        else segundos=""+seconds;
        
        if (idiom==1){time=new JLabel("CURRENT TIME: " + horas + ":"+ minutos+":"+segundos);}
        if (idiom==2){time=new JLabel("ORA CORRENTE: " + horas + ":"+ minutos+":"+segundos);}
        if (idiom==3){time=new JLabel("HORA ACTUAL: " + horas + ":"+ minutos+":"+segundos);}
        time.setFont(fuente4);
        
        info.add(number);
        info.add(type);
        info.add(date);
        info.add(time);
        
        modelo.agrega(info,gb,this,0,1);
        
        //Configuracion menu
        menu.setBackground(Color4);
        menu.setPreferredSize(new Dimension(100,100));
        menu.setLayout(null);
        if (idiom==1){volverJuego= new JButton("Confirm");}
        if (idiom==2){volverJuego= new JButton("Confirmare");}
        if (idiom==3){volverJuego= new JButton("Confirmar");}
        volverJuego.setFont(fuente1);
        volverJuego.setBackground(ColorJugar);
        volverJuego.setLocation(130,15);
        volverJuego.setSize(500, 70);
        volverJuego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        menu.add(volverJuego);
        modelo.agrega(menu,gb,this,0,2);
        
        
       
        pack();
        setVisible(true); 
        setResizable(false);
    } 
}
