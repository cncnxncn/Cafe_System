package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Excel.ExcelController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class Today_Update extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private String [][] content = null;
	private String [][] contentModel = null;
	
	private Map<String,Object> todayMap = null;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Today_Update(Map<String,Object> map) {
		todayMap = map;
		Setting();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblToday = new JLabel("Today");
		lblToday.setHorizontalAlignment(SwingConstants.CENTER);
		lblToday.setBounds(12, 10, 99, 25);
		contentPane.add(lblToday);
		
		String [] header = {"품명","입고량","사용량","망실량","재고"};
		table = new JTable(content , header) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0 || column == 4)
					return false;
				else
					return true;
			}
		};

		table.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				int editCol = table.getEditingColumn();
				int editRow = table.getEditingRow();
				if(editCol != -1 && editRow != -1) 
				{
					JTextField EditCell = (JTextField) table.getEditorComponent();
					if(EditCell.getText() != null && !EditCell.getText().equals("")) 
					{
						String EditVal = EditCell.getText();
						double received = (editCol == 1) ? Double.parseDouble(EditVal) : 
							(table.getValueAt(editRow, 1) == null || table.getValueAt(editRow, 1).equals("")) ? 0 :
								Double.parseDouble((String)table.getValueAt(editRow, 1));
						double usage = (editCol == 2) ? Double.parseDouble(EditVal) : 
							(table.getValueAt(editRow, 2) == null || table.getValueAt(editRow, 2).equals("")) ? 0 :
								Double.parseDouble((String)table.getValueAt(editRow, 2));
						double Loss = (editCol == 3) ? Double.parseDouble(EditVal) : 
							(table.getValueAt(editRow, 3) == null || table.getValueAt(editRow, 3).equals("")) ? 0 :
								Double.parseDouble((String)table.getValueAt(editRow, 3));
						double Stock = Double.parseDouble(contentModel[editRow][4]);
						
						double originReceived= (contentModel[editRow][1] == null) ? 0 
								: Double.parseDouble(contentModel[editRow][1]);
						double originUsage= (contentModel[editRow][2] == null) ? 0 
								:Double.parseDouble(contentModel[editRow][2]);
						double originLoss= (contentModel[editRow][3] == null) ? 0 
								:Double.parseDouble(contentModel[editRow][3]);
						
						received = received - originReceived;
						usage = usage - originUsage;
						Loss = Loss - originLoss;
						
						
						double newStock = Stock + received - usage - Loss;
						
						table.setValueAt(String.valueOf(newStock), editRow, 4);		
					}			
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setLocation(32, 45);
		scroll.setSize(401,482);
		contentPane.add(scroll);
		
		
		JButton btnNewButton = new JButton("\uC800\uC7A5");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content[][] = new String[table.getRowCount()][table.getColumnCount() -1];
				String statisticsContent [][] = new String[table.getRowCount()][table.getColumnCount() - 1];
				for(int rowIndex = 0 ; rowIndex < table.getRowCount(); rowIndex ++) {
					for(int cellIndex = 1; cellIndex < 5; cellIndex++) {
						content[rowIndex][cellIndex - 1] = (String) table.getValueAt(rowIndex, cellIndex);
						
						double originVal = (contentModel[rowIndex][cellIndex] == null) ? 
								0 : Double.parseDouble(contentModel[rowIndex][cellIndex]);
						double newVal =(content[rowIndex][cellIndex - 1].equals("") || content[rowIndex][cellIndex - 1] == null) ?
								0 : Double.parseDouble(content[rowIndex][cellIndex - 1]);
						double changeVal = newVal - originVal;
						statisticsContent[rowIndex][cellIndex - 1] = changeVal + "";
					}
				}
				int todayRowIndex = (int)map.get("todayRowIndex");
				ExcelController xlsxController = new ExcelController();
				Map<String,Object> setMap = new HashMap<String, Object>();
				setMap.put("statisticsContent", statisticsContent);
				setMap.put("content", content);
				setMap.put("rowIndex", todayRowIndex);
				try {
				xlsxController.setTodayUpdate(setMap);
				JOptionPane.showMessageDialog(null, "저장 성공");
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "저장에 실패했습니다.");
				}
				
			}
		});
		btnNewButton.setBounds(102, 533, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uB2EB\uAE30");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(279, 533, 97, 23);
		contentPane.add(btnNewButton_1);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	private void Setting() {
		this.content = (String[][]) todayMap.get("content");
		this.contentModel = (String[][]) todayMap.get("contentModel");
	}
}
