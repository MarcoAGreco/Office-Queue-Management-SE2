/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Counter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

class GUICounter extends JFrame implements ActionListener{
    private JTabbedPane tabbedPane;
    private JPanel panel1, panel2;
    private JButton bottonAvailable,bottonConferma;
    private JCheckBox bottonAccounting,bottonPacking;
    private JLabel label1;

    
    public GUICounter(){
        try {
            UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e) {}
        setLayout( new BorderLayout() );
        creaOpcion1();
        creaOpcion2();
//        creaOpcion3();
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("REQUEST", panel1 );
        tabbedPane.addTab("AVAILABLE", panel2 );
//        tabbedPane.addTab("Opción 3", panel3 );
        add( tabbedPane, BorderLayout.CENTER);
        setTitle( "COUNTER" );
        setBounds( 600,300,600, 400 );
        setBackground( Color.gray );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    

    public void creaOpcion1(){
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));
        
        bottonAccounting = new JCheckBox("Accounting");
        bottonAccounting.setSelected(true);
        bottonPacking = new JCheckBox("Packing");
        bottonPacking.setSelected(true);
        
        bottonConferma= new JButton("Conferma");
        bottonConferma.addActionListener(this);
        
        panel1.add(bottonAccounting);
        panel1.add(bottonPacking);
        panel1.add(bottonConferma);    
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (bottonAccounting.isSelected()){ System.out.println("Accounting = true"); }
        else { System.out.println("Accounting = false");}

        if (bottonPacking.isSelected()){ System.out.println("Packing = true"); }
        else { System.out.println("Packing = false");}
    }
    
    
    public void creaOpcion2(){
        panel2 = new JPanel();
        panel2.setLayout( new BorderLayout() );
        label1= new JLabel("Are you available?");
        label1.setFont(new Font("Times New Roman",Font.BOLD+ Font.ITALIC,30));
        panel2.add(label1,BorderLayout.NORTH);
        
        bottonAvailable = new JButton("YES");
        bottonAvailable.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getSource()==bottonAvailable) {
                    bottonAvailable.setText("Confermato");
                    bottonAvailable.setBackground(Color.GREEN);}
            }
        });
        panel2.add( bottonAvailable, BorderLayout.CENTER );
    }
      
}