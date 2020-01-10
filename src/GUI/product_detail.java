package GUI;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Excel.ExcelController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.WindowConstants;

public class product_detail extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ExcelController xlsxController = new ExcelController();

	
	
	public product_detail(Map<String , Object> map) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 725);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel((String) map.get("productName"));
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 13));
		lblNewLabel.setBounds(12, 10, 101, 29);
		
		contentPane.add(lblNewLabel);
		
		
		String [] Header = {"Date","ÀÔ°í·®","»ç¿ë·®","¸Á½Ç·®","Àç°í"};
		String [][] Content = (String[][]) map.get("content");
		table = new JTable(Content,Header);
		table.setBounds(12, 120, 357, 497);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int k= e.getKeyCode();
				if(k == 37 || k == 38 || k == 39 || k == 40 || k == 9 || k == 10) {
				int col = table.getSelectedColumn();
				int row = table.getSelectedRow();
				if(col != -1 && row != -1)
				{
					double lastDayStock = (row != 0) ? (double) table.getValueAt(row - 1, 4) : (double) map.get("lastMonthStock");
					System.out.println(table.getValueAt(row, col));
					
//					double input = ((double) table.getValueAt(row, 1) == 0 ) ? 0 : (double) table.getValueAt(row, 1);
//					double use = ((double) table.getValueAt(row, 2) == 0) ? 0 : (double) table.getValueAt(row, 2);
//					double lost = ((double) table.getValueAt(row, 3) == 0) ? 0 : (double) table.getValueAt(row, 3);
//					
//					double todayStock = lastDayStock + input - use - lost;
//					
//					table.setValueAt(todayStock +"", row, 4);
				}
				}
			}
		});

		
		contentPane.add(table);
		
		JLabel label = new JLabel("\uCD5C\uADFC\uC218\uC815\uC77C   :");
		label.setBounds(125, 35, 88, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel((String) map.get("lastUpdate"));
		label_1.setBounds(225, 35, 144, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\uC804\uC6D4 \uC7AC\uACE0     :");
		label_2.setBounds(125, 60, 88, 15);
		contentPane.add(label_2);
		
		JLabel lblNewLabel_1 = new JLabel(map.get("lastMonthStock") + "");
		lblNewLabel_1.setBounds(225, 58, 144, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 12));
		lblDate.setBounds(23, 95, 57, 15);
		contentPane.add(lblDate);
		
		JLabel label_3 = new JLabel("\uC785\uACE0\uB7C9");
		label_3.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 12));
		label_3.setBounds(87, 95, 57, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\uC0AC\uC6A9\uB7C9");
		label_4.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 12));
		label_4.setBounds(164, 95, 49, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\uB9DD\uC2E4\uB7C9");
		label_5.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 12));
		label_5.setBounds(233, 95, 57, 15);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("\uC7AC\uACE0");
		label_6.setFont(new Font("¸¼Àº °íµñ Semilight", Font.BOLD, 12));
		label_6.setBounds(302, 95, 57, 15);
		contentPane.add(label_6);
		
		JButton btnNewButton = new JButton("\uC800\uC7A5");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String[]> list = new ArrayList<String[]>();
				int rowMaximumCount = table.getRowCount();
				double Stock = (double) map.get("lastMonthStock");
				
				for(int rowIndex = 0; rowIndex < rowMaximumCount; rowIndex++) {
					String [] content = new String[4];
					int contentIndex = 0;
					
					for(int cellIndex = 1; cellIndex < 4; cellIndex++) {
						if(table.getValueAt(rowIndex, cellIndex) != null)
							content[contentIndex] = (String) table.getValueAt(rowIndex, cellIndex);
						else 
							content[contentIndex] = "";
						
						contentIndex++;
					}
					list.add(content);
				}
				Map<String,Object> detailMap = new HashMap<String, Object>();
				detailMap.put("startCellIndex", map.get("startCellIndex"));
				detailMap.put("content", list);
				try 
				{
					xlsxController.productDetailWriter(map);
					JOptionPane.showMessageDialog(null, "ÀúÀå ¿Ï·á");
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(65, 633, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uB2EB\uAE30");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(225, 633, 97, 23);
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
}

