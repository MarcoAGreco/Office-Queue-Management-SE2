import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel implements ActionListener {
private JCheckBox bBarbilla;
private JCheckBox bGafas;
private JCheckBox bPelo;
private JCheckBox bDientes;
private JButton bEleccion = new JButton("Elige!");
public GUI() {
bBarbilla = new JCheckBox("Barbilla");
bBarbilla.setSelected(true);
bGafas = new JCheckBox("Gafas");
bGafas.setSelected(true);
bPelo = new JCheckBox("Pelo");
bPelo.setSelected(true);
bDientes = new JCheckBox("Dientes");
bDientes.setSelected(true);
bEleccion.addActionListener(this);
setLayout(new GridLayout(0, 1));
add(bBarbilla);
add(bGafas);
add(bPelo);
add(bDientes);
add(bEleccion); }


public void actionPerformed(ActionEvent e) {
if (bGafas.isSelected()){
System.out.println("Gafas = true"); }
else {
System.out.println("Gafas = false");}
System.exit(0);
}
}