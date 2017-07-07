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
import javax.swing.plaf.synth.SynthSplitPaneUI;
import javax.swing.table.DefaultTableModel;

public class Sell_Form
{
	JFrame frame;
	JTable table;
	JTextField namef;
	JTextField scoref;
	JButton button1, button2, button3, button4;
	JPanel panel_t;
	JLabel label;
	int BranchCode = 0;

	// 클릭된 테이블의 줄 정보가 저장되는 변수
	int row;
	int column;

	// 지점 이름을 입력받음
	public Sell_Form(String branchname_) 
	{
		//프레임 설정
		frame = new JFrame();
		frame.setTitle(branchname_);
		frame.setBounds(400, 300, 560, 440);

		//패널 설정
		panel_t = new JPanel();
		panel_t.setLayout(null);
		frame.add(panel_t);
		// 클릭한 동이름 전달
		String ConturyName = branchname_;
		label = new JLabel("판매 목록");
		label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label.setBounds(200, 10, 400, 50);
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
		userColumn.addElement("빵종류");
		userColumn.addElement("가격");
		userColumn.addElement("개수");
		userColumn.addElement("유통기한");
		userColumn.addElement("제조시간");

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

		try 
		{

			// SQL Connect
			SQLConnection a = new SQLConnection();
			Connection con = a.makeConnection();

			int i = 0;
			int j = 0;
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch where branchname = '" + branchname_ + "'");
			if (rs.next())
			{
				BranchCode = rs.getInt(1);
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "존재하는 데이터가 없습니다.", "데이터 존재 에러", JOptionPane.PLAIN_MESSAGE);
			}
			rs = stmt.executeQuery(
					"Select * from branch_info where branchCode ='" + BranchCode + "'order by branchCode asc");
			
			if (rs.next()) 
			{
				do 
				{
					// data를 db에서 가져와서 삽입
					int BreadCode = rs.getInt(3);
					int stock = rs.getInt(4);
					int price = rs.getInt(7);
					String ExpireTime = rs.getString(6);
					int BakingTime = rs.getInt(5);
					model.addRow(new Object[] { BreadCode, price + "원", stock + "개", ExpireTime, BakingTime + "분" });
					j++; // 숫자코드를 문자로 바꾸기위한 변수
				} 
				while (rs.next());
				
				for (i = 0; i < j; i++)
				{
					rs = stmt.executeQuery("Select * from bread where breadcode ='" + table.getValueAt(i, 0) + "'");
					if (rs.next())
						table.setValueAt(rs.getString(2), i, 0);
				}

			}

		} // end try
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		// panel에 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBounds(20, 80, 500, 200);
		panel_t.add(scrollPane);

		frame.setVisible(true);

		//판매내역 버튼
		button1 = new JButton("판매 내역");
		button1.setBounds(30, 310, 100, 40);
		panel_t.add(button1);
		
		//판매내역 버튼 액션
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new Sell_list(branchname_);
			}
		});

		//케이크 주문 내역
		button2 = new JButton("케이크 주문 내역");
		button2.setBounds(140, 310, 140, 40);
		panel_t.add(button2);
		
		//케이크 주문 내역 버튼 액션
		button2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

				new Cake_list(branchname_);

			}
		});

		//현황 추가
		button3 = new JButton("현황 추가");
		button3.setBounds(290, 310, 100, 40);
		panel_t.add(button3);
		
		//현황 추가 버튼 액션
		button3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new List_add(branchname_);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		//현황 수정
		button4 = new JButton("현황 수정");
		button4.setBounds(400, 310, 100, 40);
		panel_t.add(button4);
		
		//현황 수정 버튼 액션
		button4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					
					Vector<String> data = new Vector<String>();
					row = table.getSelectedRow();

					for (int i = 0; i < userColumn.size(); i++)
					{
						data.addElement(table.getValueAt(row, i).toString());
					}
					new List_modify(branchname_, data);
					frame.setVisible(false);
					frame.dispose();
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "수정할 정보를 선택하여주십시오.", "리스트 선택 에러", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		frame.add(panel_t);
	}// Sell_Form
}
