
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String s[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame v = new JFrame("CheckBox Demo");
            v.setSize(200,250);
            v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            v.getContentPane().add(new GUI());
            v.setVisible(true);
        });
}


}
