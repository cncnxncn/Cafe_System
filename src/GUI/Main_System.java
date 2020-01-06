package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import Excel.ExcelController;
import Excel.XlsxVO;
import FileController.FileController;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JLabel;

public class Main_System extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	ExcelController xlsxController = new ExcelController();

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
		tabbedPane.addTab("재고 관리", null, panel_1, null);
		
		
		
		
		String header [] = {"품목","입고량","사용량","망실량","재고","최근 수정일"};
		String content[][]= null;
		JTable table = null;
		
		
		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap = (Map<String, Object>) xlsxController.getXlsx();
		String result = (String) productMap.get("result");
		if(result.equals("성공")) {
			content = (String[][]) productMap.get("product");
			for(int i = 0 ; i < content[0].length; i++) {
				System.out.println(content[0][i]);
			}
			table = new JTable(content,header);
			table.setBounds(12, 20, 672, 495);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable t = (JTable)e.getSource();
					System.out.println(e.getClickCount());
					if(e.getClickCount() == 2) {
						TableModel m = t.getModel();
						Point pointer = e.getPoint();
						int index = t.rowAtPoint(pointer);
						System.out.println("table Index :" + index);
					}
				}
			});
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
				chooser.setCurrentDirectory(new File("C:/"));
				
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File f = chooser.getSelectedFile();
					try {
						file.setFilePath(f.getCanonicalPath());
						panel_1.revalidate();
						panel_1.repaint();
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
//				String []message = {"품명","단가(단위당 가격)","단가(단위)","재고"};
				String []message = {"품명","재고(숫자만 입력)"};
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
		label.setBounds(30, 0, 43, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\uC785\uACE0\uB7C9");
		label_1.setBounds(131, 0, 57, 15);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uC0AC\uC6A9\uB7C9");
		label_2.setBounds(244, 0, 57, 15);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\uB9DD\uC2E4\uB7C9");
		label_3.setBounds(362, 0, 57, 15);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("\uC7AC\uACE0");
		label_4.setBounds(476, 0, 57, 15);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("\uCD5C\uADFC\uC218\uC815\uC77C");
		label_5.setBounds(579, 0, 89, 15);
		panel_1.add(label_5);
	}
}
