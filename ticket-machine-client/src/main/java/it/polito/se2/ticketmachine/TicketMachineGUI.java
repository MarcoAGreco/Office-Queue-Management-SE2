package it.polito.se2.ticketmachine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TicketMachineGUI extends JFrame {
	private TicketMachineClient client;
	private JButton btnAccounting;
	private JButton btnPackage;
	private JLabel lblSelectReqType;
	private JLabel lblPostOffice;
	private JPanel contentPane;

	public TicketMachineGUI(TicketMachineClient client) {
		this.client = client;
		
		initComponents();
		createEvents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblPostOffice = new JLabel("PostOffice");	
		lblSelectReqType = new JLabel("Select Request Type");
		btnAccounting = new JButton("Accounting");
		btnPackage = new JButton("Package");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(69)
									.addComponent(btnAccounting, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnPackage, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(154)
									.addComponent(lblSelectReqType)))
							.addGap(70))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(180)
							.addComponent(lblPostOffice, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
							.addGap(163))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPostOffice, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(48)
					.addComponent(lblSelectReqType)
					.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAccounting, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPackage, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(70))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void createEvents() {
		btnAccounting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Button clicked");
			}
		});
		
		btnPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
	
	public static void main(String[] args) {
		TicketMachineClient client = new TicketMachineClient("localhost", TicketMachineClient.PORT_NUMBER);
		TicketMachineGUI frame = new TicketMachineGUI(null);
		frame.setVisible(true);
	}
}