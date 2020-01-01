package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Excel.ExcelController;
import FileController.FileController;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JLabel;

public class Main_System extends JFrame {
	
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_System frame = new Main_System();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_System() {
//		ExcelController xlsxController = new ExcelController();
//		xlsxController.getXlsx(1);
		FileController file = new FileController();
		System.out.println(file.getFilePath());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 933, 639);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSystem = new JMenu("System");
		menuBar.add(mnSystem);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnSystem.add(mntmExit);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnSystem.add(mntmSave);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("C:/"));
				
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File f = chooser.getSelectedFile();
					try {
						file.setFilePath(f.getCanonicalPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mnSystem.add(mntmOpen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 917, 579);
		contentPane.add(tabbedPane);
		
		Panel panel = new Panel();
		tabbedPane.addTab("���� ����", null, panel, null);
		
		
		
		
		
		Panel panel_1 = new Panel();
		tabbedPane.addTab("��� ����", null, panel_1, null);
		
		
		String header [] = {"ǰ��","�԰���","��뷮","���Ƿ�","���"};
		String content[][]= {};
		panel_1.setLayout(null);
		JTable table = new JTable(content,header);
		table.setBounds(12, 20, 672, 495);
		
		
		panel_1.add(table);
		
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String []result = new String[3];
				String []message = {"ǰ��", "�ܰ�()","���"};
				Boolean suc = true;
				for(int i = 0 ; i < result.length; i++) {
					result[i] = JOptionPane.showInputDialog(message[i]);
					if(result[i] == null) {
						suc = false;
						break;
					}
				}
				if(suc) {
					
				}
					
			}
		});
		btnAddProduct.setBounds(749, 44, 121, 23);
		panel_1.add(btnAddProduct);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(749, 102, 121, 23);
		panel_1.add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(749, 161, 121, 23);
		panel_1.add(btnDelete);
		
		JLabel label = new JLabel("\uD488\uBA85");
		label.setBounds(30, 0, 116, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC785\uACE0\uB7C9");
		label_1.setBounds(125, 0, 57, 15);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uC0AC\uC6A9\uB7C9");
		label_2.setBounds(230, 0, 57, 15);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uB9DD\uC2E4\uB7C9");
		label_3.setBounds(347, 0, 57, 15);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("\uC7AC\uACE0");
		label_4.setBounds(458, 0, 57, 15);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("\uCD5C\uADFC\uC218\uC815\uC77C");
		label_5.setBounds(557, 0, 89, 15);
		panel_1.add(label_5);
	}
}