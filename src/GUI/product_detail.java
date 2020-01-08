package GUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Excel.ExcelController;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.WindowConstants;

public class product_detail extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ExcelController xlsxCon = new ExcelController();

	
	public product_detail(Map<String , Object> map) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 601);
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
		table.setBounds(12, 120, 357, 432);
		
		
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

