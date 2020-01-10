package GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Excel.ExcelController;
import Excel.XlsxVO;
import FileController.FileController;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Main_System extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	
	private final String header [] = {"ǰ��","�԰�","��뷮","���Ƿ�","���","�ֱ� ������"};
	private String [][] content = null;
	private Map<String,Object> productMap = null;
	
	ExcelController xlsxController = new ExcelController();
	FileController filecon = new FileController();
	
	public static void main(String[] args) {
		Main_System main = new Main_System();
	}
	
	public Main_System() {
		FileController file = new FileController();
		
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
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 917, 579);
		contentPane.add(tabbedPane);
		
		Panel panel_1 = new Panel();
		tabbedPane.addTab("��� ����", null, panel_1, null);
		
		
		
		
		
		
		
		JScrollPane scrollpane = null;
		productMap = xlsxController.getXlsx();
		String result = (String) productMap.get("result");
		if(result.equals("����")) {
			table = tableSetting();
			scrollpane = new JScrollPane(table);
			panel_1.add(scrollpane);
			panel_1.add(table);
		}else {
			JOptionPane.showMessageDialog(null, result);
		}
		
		panel_1.setLayout(null);
		
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				String filePath = filecon.getFilePath();
				chooser.setCurrentDirectory(new File(filePath.substring(0,filePath.lastIndexOf("\\"))));
				
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File f = chooser.getSelectedFile();
					try {
						file.setFilePath(f.getCanonicalPath());
						productMap = xlsxController.getXlsx();
						panel_1.remove(table);
						table = tableSetting();
						panel_1.add(table);
						table.setBounds(12, 20, 672, 495);
						tabbedPane.repaint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		mnSystem.add(mntmOpen);
		
		
		
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String []result = new String[2];
//				String []message = {"ǰ��","�ܰ�(������ ����)","�ܰ�(����)","���"};
				String []message = {"ǰ��","���(���ڸ� �Է�)"};
				Boolean suc = true;
				for(int i = 0 ; i < message.length; i++) {
					result[i] = JOptionPane.showInputDialog(message[i]);
					if(result[i] == null) {
						suc = false;
						break;
					}
				}
				if(suc) {
					XlsxVO vo = new XlsxVO(result);
					xlsxController.addProduct(vo);
					productMap = xlsxController.getXlsx();
					panel_1.remove(table);
					table = tableSetting();
					panel_1.add(table);
					table.setBounds(12, 20, 672, 495);
					tabbedPane.repaint();
					
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
		label.setFont(new Font("���� ���", Font.BOLD, 12));
		label.setBounds(30, 0, 43, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC785\uACE0\uB7C9");
		label_1.setFont(new Font("���� ���", Font.BOLD, 12));
		label_1.setBounds(131, 0, 57, 15);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uC0AC\uC6A9\uB7C9");
		label_2.setFont(new Font("���� ���", Font.BOLD, 12));
		label_2.setBounds(244, 0, 57, 15);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uB9DD\uC2E4\uB7C9");
		label_3.setFont(new Font("���� ���", Font.BOLD, 12));
		label_3.setBounds(362, 0, 57, 15);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("\uC7AC\uACE0");
		label_4.setFont(new Font("���� ���", Font.BOLD, 12));
		label_4.setBounds(476, 0, 57, 15);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("\uCD5C\uADFC\uC218\uC815\uC77C");
		label_5.setFont(new Font("���� ���", Font.BOLD, 12));
		label_5.setBounds(579, 0, 89, 15);
		panel_1.add(label_5);
		
		setVisible(true);
	}
	
	private JTable tableSetting() {
		content = (String[][]) productMap.get("product");
		table = new JTable(content,header) {
			@Override
			public boolean isCellEditable(int row , int cell) {
				return false;
			}
		};
		table.setBounds(12, 20, 672, 495);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable t = (JTable)e.getSource();
				if(e.getClickCount() == 2) {
					Point pointer = e.getPoint();
					int index = t.rowAtPoint(pointer);
					
					//product_detail
					
					String [] header_detail = {"��/��","�԰�","��뷮","���Ƿ�","���"};
					Map<String,Object> DetailMap = new HashMap<String, Object>();
					DetailMap = xlsxController.productDetailInfo(index);
					product_detail detail = new product_detail(DetailMap);
					
				}
			}
		});
		table.setFont(new Font("�������", Font.BOLD,12));
		table.setRowHeight(35);
		return table;
	}
}

