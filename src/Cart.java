import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cart extends JFrame 
{

	Vector<String> userColumn;
	Vector<String> userRow;

	String loginId;

	// table그리기
	JTable table;
	// 기본 테이블 모델
	DefaultTableModel model;

	public Cart(String loginId) 
	{
		// TODO Auto-generated constructor stub
		super("장바구니");

		this.loginId = loginId;

		// 상단
		JPanel toolBar = new JPanel();

		// toolBar에 추가
		JLabel label1 = new JLabel(loginId + "님 의 장바구니");
		toolBar.add(label1);

		// null님 반갑습니다. 로그인 하면 정보 전달받음
		JLabel label2 = new JLabel("가격합계 :        ");
		toolBar.add(label2);

		JButton button1 = new JButton("초기화");
		toolBar.add(button1);
		
		button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               userRow.clear(); // vector 초기화
               //테이블 초기화
              int rowCount = model.getRowCount();
              for(int k = rowCount - 1; k>=0;k--) {
                 model.removeRow(k);
              }
            }   
         });//button1 end
		
		JButton button2 = new JButton("구매");
		toolBar.add(button2);

		// 구매,판매내역에 추가
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// SQL Connect
				SQLConnection a = new SQLConnection();
				Connection con = a.makeConnection();
				String UID, BreadName, Expiretime, BranchName, IsCake = "N", price, Stock;
				boolean isAdd = false;
				int BreadCode = 0;
				int BranchCode = 0;

				int i = 0;
				int j = 0;

				try 
				{
					for (i = 0; i < model.getRowCount(); i++)
					{
						UID = loginId;
						BranchName = table.getValueAt(i, 0).toString();
						BreadName = table.getValueAt(i, 1).toString();
						price = table.getValueAt(i, 2).toString();
						Stock = table.getValueAt(i, 3).toString();
						Expiretime = table.getValueAt(i, 4).toString();
						
						// "원" 제거
						price = price.substring(0, price.length() - 1);
						
						// "개" 제거
						Stock = Stock.substring(0, Stock.length() - 1);
						Statement stmt = con.createStatement();
						
						// 유사 빵이름이 존재하는 경우
						ResultSet rs = stmt.executeQuery("SELECT * FROM bread where BreadName ='" + BreadName + "'");
						if (rs.next()) 
						{
							// 검색된 빵의 코드를 변수에 저장
							BreadCode = rs.getInt(1);
						}

						rs = stmt.executeQuery("SELECT * FROM Branch where BranchName ='" + BranchName + "'");
						if (rs.next()) 
						{ 
							// 검색된 지점의 코드를 변수에 저장
							BranchCode = rs.getInt(1);
						}
						rs = stmt.executeQuery("SELECT * FROM Branch_info where BranchCode ='" + BranchCode + "'");
						if (rs.next()) 
						{ 
							// 검색된 코드를 변수에 저장
							IsCake = rs.getString(8);
						}
						//현재 날짜
						long time = System.currentTimeMillis();
						SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
						String BuyDate = dayTime.format(new Date(time));
						
						String sql = "insert into history  values('" + UID + "', '" + BuyDate + "', '" + BreadCode
								+ "','" + price + "','" + Stock + "','" + Expiretime + "','" + BranchCode + "','"
								+ IsCake +"','"+ null +"')";
						stmt.executeUpdate(sql); // select 문을 제외한 나머지를 실행할때 사용하는 메소드
						
						sql = "update branch_info set stock = stock -'"+Stock+"' where Breadcode ='" +BreadCode+"'and BranchCode ='"+BranchCode+"'and ExpireTime='"+Expiretime+"'";
						stmt.executeUpdate(sql); // select 문을 제외한 나머지를 실행할때 사용하는 메소드
						isAdd = true;
					}
					// 장바구니 추가 되고난 후 테이블 초기화
					if (isAdd) 
					{
						int rowCount = model.getRowCount();
						for (int k = rowCount - 1; k >= 0; k--) 
						{
							model.removeRow(k);
						}
						userRow.clear(); // vector 초기화
						JOptionPane.showMessageDialog(null, "구매가 완료되었습니다.", "구매 완료", JOptionPane.PLAIN_MESSAGE);
					} 

				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}

			}
		}); // button2 end

		// 상태창
		add(toolBar, BorderLayout.NORTH);

		// 테이블의 행과 열을 구현하는 벡터
		userColumn = new Vector<String>();
		userRow = new Vector<String>();

		// Column 수정
		userColumn.addElement("지점이름");
		userColumn.addElement("빵이름");
		userColumn.addElement("가격");
		userColumn.addElement("수량");
		userColumn.addElement("유통기한");

		// 테이블에 열 넣기
		model = new DefaultTableModel(userColumn, 0);

		// 테이블에 모델 입력
		table = new JTable(model);

		// panel에 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		// 표
		add(scrollPane, BorderLayout.SOUTH);

	}// Constructor

	public void CreateCart() 
	{

		// 새로 추가 되는것을 방지하기위한 테이블 초기화
		int rowCount = model.getRowCount();
		for (int k = rowCount - 1; k >= 0; k--) 
		{
			model.removeRow(k);
		}

		// 행정보 입력
		for (int i = 0; i < userRow.size() / 5; i++) 
		{
			model.addRow(new Object[] { userRow.elementAt(5 * i + 0), userRow.elementAt(5 * i + 1),
					userRow.elementAt(5 * i + 2), userRow.elementAt(5 * i + 3) + "개", userRow.elementAt(5 * i + 4) });
		}

		setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void addCart(String CartRow[]) 
	{

		for (int i = 0; i < 5; i++)
		{
			userRow.addElement(CartRow[i]);
		}
	}
}
