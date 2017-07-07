import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Sell_list extends JFrame
{
	JTable table;
	JTextField namef;
	JTextField scoref;
	JButton button1, button2;
	JPanel panel_t;
	JLabel label;

	// 지점 이름을 입력받음
	public Sell_list(String branchname_) 
	{
		//프레임 설정
		this.setTitle("판매 내역");
		this.setBounds(1000, 300, 560, 440);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//패널 설정
		panel_t = new JPanel();
		this.add(panel_t);
		panel_t.setLayout(null);
		
		label = new JLabel("판매 내역");
		label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label.setBounds(190, 10, 400, 50);
		panel_t.add(label);

		// table그리기
		JTable table;
		// 기본 테이블 모델
		DefaultTableModel model;
		// MyTableModel model; //이전버전

		// 행과 열
		Vector<String> userColumn = new Vector<String>();
		Vector<String> userRow = new Vector<String>();

		// Column 입력
		userColumn.addElement("구매자ID");
		userColumn.addElement("구매일자");
		userColumn.addElement("빵종류");
		userColumn.addElement("가격");
		userColumn.addElement("개수");
		userColumn.addElement("유통기한");

		// 테이블에 열 넣기
		model = new DefaultTableModel(userColumn, 0);

		// 테이블에 모델 입력
		table = new JTable(model);

		// 테이블 초기화
		int rowCount = model.getRowCount();
		for (int k = rowCount - 1; k >= 0; k--)
		{
			model.removeRow(k);
		}
		
		// panel에 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBounds(20, 80, 500, 200);
		panel_t.add(scrollPane);

		setVisible(true);
		try 
		{
			// SQL Connect
			SQLConnection a = new SQLConnection();
			Connection con = a.makeConnection();
			int i = 0;
			int j = 0;
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from branch where branchName ='" + branchname_ + "'");
			rs.next();
			int BranchCode =rs.getInt(1); 
			rs = stmt.executeQuery("Select * from history where branchCode ='" + BranchCode + "'order by branchCode asc");
			if (rs.next()) 
			{
				// 구매자 ID 빵종류 개수 가격 유통기한
				do
				{
					// data를 db에서 가져와서 삽입
					String UID = rs.getString(1);
					int BreadCode = rs.getInt(3);
					int stock = rs.getInt(5);
					int price = rs.getInt(4);
					
					String BuyDate = rs.getString(2);
					String ExpireTime = rs.getString(6);
					model.addRow(new Object[] { UID, BuyDate, BreadCode, price + "원", stock + "개", ExpireTime });
					j++; // 숫자코드를 문자로 바꾸기위한 변수
				} 
				while (rs.next());
				
				for (i = 0; i < j; i++) 
				{
					rs = stmt.executeQuery("Select * from bread where breadcode ='" + table.getValueAt(i, 2) + "'");
					if (rs.next())
					{
						table.setValueAt(rs.getString(2), i, 2);
					}
				}

			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "존재하는 데이터가 없습니다.", "데이터 존재 에러", JOptionPane.PLAIN_MESSAGE);
			}

		} // end try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		add(panel_t);
	}// databoard
}
