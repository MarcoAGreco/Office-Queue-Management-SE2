package it.polito.se2.ticketboard;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;

public class TicketBoardGUI extends JFrame {
	private TicketBoardClient client;
	private JPanel mainPane;
	private JLabel lblCounter1;
	private JLabel lblCounter2;
	private JLabel lblCounter3;
	JLabel lblQueue1 = new JLabel("00A1");
	
	JLabel lblQueue2;
	JLabel lbQueue3;
	JLabel lblAccounting;
	JLabel lblPackage;
	JLabel lblQueueAccountingNO;	
	JLabel lblQueuePackageNO;

	
	private final Component horizontalGlue = Box.createHorizontalGlue();

	public TicketBoardGUI() {
		//this.client = new TicketBoardClient(this, "localhost", TicketBoardClient.PORT_NUMBER);

		initComponents();
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		setContentPane(mainPane);
		
		lblCounter1 = new JLabel("Counter 1");
		lblCounter1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCounter2 = new JLabel("Counter 2");
		lblCounter2.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCounter3 = new JLabel("Counter 3");
		lblCounter3.setFont(new Font("Dialog", Font.BOLD, 16));
		lblQueue1 = new JLabel("00A1");
		lblQueue1.setFont(new Font("Dialog", Font.BOLD, 21));
		lblQueue2 = new JLabel("00A1");
		lblQueue2.setFont(new Font("Dialog", Font.BOLD, 21));
		lbQueue3 = new JLabel("00A1");
		lbQueue3.setFont(new Font("Dialog", Font.BOLD, 21));
		lblAccounting = new JLabel("Accounting queue:");
		lblAccounting.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPackage = new JLabel("Package queue:");
		lblPackage.setFont(new Font("Dialog", Font.BOLD, 16));
		lblQueueAccountingNO = new JLabel("00");
		lblQueueAccountingNO.setFont(new Font("Dialog", Font.BOLD, 21));
		lblQueuePackageNO = new JLabel("00");
		lblQueuePackageNO.setFont(new Font("Dialog", Font.BOLD, 21));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_2 = new JSeparator();
		
		JSeparator separator_3 = new JSeparator();
		GroupLayout gl_mainPane = new GroupLayout(mainPane);
		gl_mainPane.setHorizontalGroup(
			gl_mainPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
						.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_mainPane.createSequentialGroup()
							.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCounter1, Alignment.TRAILING)
								.addComponent(lblQueue1, Alignment.TRAILING))
							.addGap(32)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_mainPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCounter2)
								.addComponent(lblQueue2, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
							.addGap(43)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCounter3, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
								.addComponent(lbQueue3, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_mainPane.createSequentialGroup()
							.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccounting, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPackage, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_mainPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblQueueAccountingNO, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_mainPane.createSequentialGroup()
									.addGap(8)
									.addComponent(lblQueuePackageNO, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
							.addGap(142))
						.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_mainPane.setVerticalGroup(
			gl_mainPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainPane.createSequentialGroup()
							.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_mainPane.createSequentialGroup()
									.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_mainPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblCounter1)
											.addComponent(lblCounter2))
										.addComponent(lblCounter3))
									.addGroup(gl_mainPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_mainPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
											.addGroup(gl_mainPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblQueue2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblQueue1))
											.addGap(44))
										.addGroup(gl_mainPane.createSequentialGroup()
											.addGap(28)
											.addComponent(lbQueue3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_mainPane.createSequentialGroup()
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(18)
							.addGroup(gl_mainPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccounting, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblQueueAccountingNO, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addGroup(gl_mainPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPackage, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblQueuePackageNO, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addGap(6))
		);
		mainPane.setLayout(gl_mainPane);
	}
	
	public void showPopUp(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static void main(String[] args) {
		TicketBoardGUI frame = new TicketBoardGUI();
		frame.setVisible(true);
	}
}
