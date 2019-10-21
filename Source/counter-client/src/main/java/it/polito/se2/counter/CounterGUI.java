package it.polito.se2.counter;

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

public class CounterGUI extends JFrame {
	private CounterClient client;
	private JPanel mainPane;
	private JTabbedPane tabbedPane;
	private JPanel tabSetting;
	private JPanel tabService;
	private JLabel lblSetting;
	private JCheckBox chckbxAccounting;
	private JCheckBox chckbxPackage;
	private JButton btnConfirm;
	private JButton btnServeNext;
	private JLabel lblCurrentTicket;

	public CounterGUI() {
		this.client = new CounterClient(this, "localhost", CounterClient.PORT_NUMBER);

		initComponents();
		createEvents(this);
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		setContentPane(mainPane);
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
		tabSetting = new JPanel();
		tabService = new JPanel();
		lblSetting = new JLabel("Wich request type you can manage today?");
		chckbxAccounting = new JCheckBox("Accounting");
		chckbxPackage = new JCheckBox("Package");
		btnConfirm = new JButton("Confirm");
		
		btnServeNext = new JButton("Serve next");
		lblCurrentTicket = new JLabel("Current ticket: ");
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add(tabSetting);
		
		GroupLayout gl_tabSetting = new GroupLayout(tabSetting);
		gl_tabSetting.setHorizontalGroup(
			gl_tabSetting.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_tabSetting.createSequentialGroup()
					.addGap(27)
					.addComponent(chckbxAccounting)
					.addPreferredGap(ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
					.addComponent(chckbxPackage)
					.addGap(35))
				.addGroup(gl_tabSetting.createSequentialGroup()
					.addGap(88)
					.addComponent(lblSetting)
					.addContainerGap(56, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_tabSetting.createSequentialGroup()
					.addContainerGap(179, Short.MAX_VALUE)
					.addComponent(btnConfirm)
					.addGap(168))
		);
		gl_tabSetting.setVerticalGroup(
			gl_tabSetting.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSetting.createSequentialGroup()
					.addGap(25)
					.addComponent(lblSetting)
					.addGap(62)
					.addGroup(gl_tabSetting.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxPackage)
						.addComponent(chckbxAccounting))
					.addGap(44)
					.addComponent(btnConfirm)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		tabSetting.setLayout(gl_tabSetting);
		tabbedPane.add(tabService);
		
		GroupLayout gl_tabService = new GroupLayout(tabService);
		gl_tabService.setHorizontalGroup(
			gl_tabService.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_tabService.createSequentialGroup()
					.addContainerGap(169, Short.MAX_VALUE)
					.addComponent(btnServeNext)
					.addGap(158))
				.addGroup(gl_tabService.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCurrentTicket)
					.addContainerGap(357, Short.MAX_VALUE))
		);
		gl_tabService.setVerticalGroup(
			gl_tabService.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabService.createSequentialGroup()
					.addGap(36)
					.addComponent(lblCurrentTicket)
					.addGap(47)
					.addComponent(btnServeNext)
					.addContainerGap(110, Short.MAX_VALUE))
		);
		tabService.setLayout(gl_tabService);
		tabbedPane.setTitleAt(0, "Setting");
		tabbedPane.setTitleAt(1, "Service");
		
		mainPane.add(tabbedPane);
	}
	
	private void createEvents(JFrame frame) {
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure you want to close this window?", "Close Window?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	client.shutdown();
		            System.exit(0);
		        }
		    }
		});
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] reqTypes = new String[2];
				boolean isAcc = chckbxAccounting.isSelected();
				boolean isPack = chckbxPackage.isSelected();
				if(isAcc && isPack) {
					reqTypes[0] = "Accounting";
					reqTypes[1] = "Package";
				} else if(isAcc) {
					reqTypes[0] = "Accounting";
					reqTypes[1] = "";
				} else if(isPack){
					reqTypes[0] = "";
					reqTypes[1] = "Package";
				} else {
					showPopUp("Please select at least one request type");
					return;
				}
				client.setReqTypeToCounter(reqTypes);
			}
		});		
		
		btnServeNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.serveNext();
			}
		});
	}
	
	public void showPopUp(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void servingTicket(String ticketID) {
		if(ticketID.equals("-")) {
			lblCurrentTicket.setText("Current ticket: No more pending tickets");
		} else {
			lblCurrentTicket.setText("Current ticket: "+ticketID);
		}
	}

	public static void main(String[] args) {
		CounterGUI frame = new CounterGUI();
		frame.setVisible(true);
	}

}
