import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

//회원가입 창
public class SignUp extends JFrame 
{
	//구매자 판매자 구별 
	String isUser;

	SignUp(Boolean isUserBool) 
	{
		isUser = isUserBool ? "Y" : "N";

		//프레임 설정
		setTitle("Sign Up");
		setBounds(400, 20, 450, 400);
		setResizable(false);
		setLocation(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//패널 설정 메소드
		JPanel panel = new JPanel();
		placeSignUpPanel(panel);

		add(panel);
		setVisible(true);
	}

	public void placeSignUpPanel(JPanel panel) 
	{
		// SQL Connect
		SQLConnection a = new SQLConnection();
		Connection con = a.makeConnection();

		panel.setLayout(null);

		//아이디
		JLabel labelid = new JLabel("아이디");
		labelid.setBounds(20, 30, 100, 20);
		panel.add(labelid);

		//아이디 입력 칸
		JTextField Textid = new JTextField();
		Textid.setBounds(140, 30, 150, 20);
		panel.add(Textid);

		//중복확인
		JButton ButtonId = new JButton("중복확인");
		ButtonId.setBounds(310, 30, 90, 20);
		panel.add(ButtonId);
		
		//SQL문으로 DB Table UID의 값을 탐색
		ButtonId.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{

					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE UID ='" + Textid.getText()
							+ "'and Host ='" + isUser.toString() + "'");
					if (rs.next()) 
					{
						JOptionPane.showMessageDialog(null, "아이디가 이미 존재합니다", "중복확인", JOptionPane.PLAIN_MESSAGE);
					} 
					else 
					{
						JOptionPane.showMessageDialog(null, "존재하는 아이디가 없습니다.", "중복확인", JOptionPane.PLAIN_MESSAGE);
					}
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		});

		//패스워드
		JLabel labelpwd = new JLabel("패스워드");
		labelpwd.setBounds(20, 60, 150, 20);
		panel.add(labelpwd);

		//패스워드 입력 칸
		JPasswordField pw = new JPasswordField();
		pw.setBounds(140, 60, 150, 20);
		panel.add(pw);

		//패스워드 확인
		JLabel labelpwd2 = new JLabel("패스워드 재확인");
		labelpwd2.setBounds(20, 90, 100, 20);
		panel.add(labelpwd2);

		//패스워드 입력칸 
		JPasswordField pw2 = new JPasswordField();
		pw2.setBounds(140, 90, 150, 20);
		panel.add(pw2);
		
		//이름
		JLabel labelname;
		if (isUser.equals("Y")) 
		{
			labelname = new JLabel("매장 이름");
		} 
		else
		{
			labelname = new JLabel("이름");
		}
		labelname.setBounds(20, 120, 100, 20);
		panel.add(labelname);

		//이름 입력 칸
		JTextField Textname = new JTextField();
		Textname.setBounds(140, 120, 150, 20);
		panel.add(Textname);
		
		//주소
		JLabel labelname2;
		if (isUser.equals("Y")) {
			labelname2 = new JLabel("매장 위치");
		} else {
			labelname2 = new JLabel("주소");
		}
		labelname2.setBounds(20, 150, 100, 20);
		panel.add(labelname2);

		// region1
		Vector region1 = new Vector<>();
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT Region1 FROM region");

			while (rs.next()) 
			{
				region1.add(rs.getString(1));

			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		JComboBox Region1 = new JComboBox<>(region1);
		Region1.setBounds(140, 150, 90, 20);
		panel.add(Region1);

		// region2
		Vector region2 = new Vector<>();
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT Region2 FROM region");
			while (rs.next())
			{
				region2.add(rs.getString(1));
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
		JComboBox Region2 = new JComboBox<>(region2);
		Region2.setBounds(250, 150, 90, 20);
		panel.add(Region2);

		//세부위치
		JLabel labelname_detail = new JLabel("세부 위치");
		labelname_detail.setBounds(20, 180, 100, 20);
		panel.add(labelname_detail);

		//세부위치 입력칸 
		JTextField Text_detail = new JTextField();
		Text_detail.setBounds(140, 180, 150, 20);
		panel.add(Text_detail);

		//전화번호
		JLabel phone_id = new JLabel("전화번호");
		phone_id.setBounds(20, 210, 150, 20);
		panel.add(phone_id);
		Vector phone_v = new Vector<>();
		phone_v.add("010");
		phone_v.add("02");
		JComboBox phone = new JComboBox<>(phone_v);
		phone.setBounds(140, 210, 60, 20);
		panel.add(phone);

		//전화번호 입력 칸
		JTextField phone_t = new JTextField();
		phone_t.setBounds(210, 210, 100, 20);
		panel.add(phone_t);

		JLabel labelopen = new JLabel("Open/Close");
		labelopen.setBounds(20, 240, 100, 20);
		panel.add(labelopen);

		// open time
		JSpinner openh_sp = new JSpinner();
		JSpinner openm_sp = new JSpinner();
		SpinnerNumberModel numModel1 = new SpinnerNumberModel(0, 0, 23, 1);
		SpinnerNumberModel numModel2 = new SpinnerNumberModel(0, 0, 59, 1);
		openh_sp.setBounds(120, 240, 40, 20);
		openm_sp.setBounds(180, 240, 40, 20);
		openh_sp.setModel(numModel1);
		openm_sp.setModel(numModel2);
		panel.add(openh_sp);
		panel.add(openm_sp);

		JLabel labelOh = new JLabel("시"); // Open Hour
		labelOh.setBounds(160, 240, 15, 20);
		panel.add(labelOh);
		JLabel labelOm = new JLabel("분       ~"); // Open Minute
		labelOm.setBounds(220, 240, 80, 20);
		panel.add(labelOm);

		// close time
		JSpinner closeh_sp = new JSpinner();
		JSpinner closem_sp = new JSpinner();
		SpinnerNumberModel numModel3 = new SpinnerNumberModel(0, 0, 23, 1);
		SpinnerNumberModel numModel4 = new SpinnerNumberModel(0, 0, 59, 1);
		closeh_sp.setBounds(280, 240, 40, 20);
		closem_sp.setBounds(340, 240, 40, 20);
		closeh_sp.setModel(numModel3);
		closem_sp.setModel(numModel4);
		panel.add(closeh_sp);
		panel.add(closem_sp);

		// Close Hour
		JLabel labelCh = new JLabel("시");
		labelCh.setBounds(320, 240, 15, 20);
		panel.add(labelCh);
		
		// Close Minute
		JLabel labelCm = new JLabel("분");
		labelCm.setBounds(380, 240, 15, 20);
		panel.add(labelCm);
		if (isUser.equals("N")) 
		{
			labelopen.setVisible(false);
			openh_sp.setVisible(false);
			openm_sp.setVisible(false);
			labelOh.setVisible(false);
			labelOm.setVisible(false);
			closeh_sp.setVisible(false);
			closem_sp.setVisible(false);
			labelCh.setVisible(false);
			labelCm.setVisible(false);
		}

		//가입하기
		JButton join = new JButton("가입하기");
		join.setBounds(120, 300, 90, 40);
		panel.add(join);

		//가입하기 버튼 액션
		join.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// UID Textid, pw password Textname name region1 region1 region2
				// region2 phone + phone_t tel
				try 
				{
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(
							"SELECT * FROM USER WHERE UID ='" + Textid.getText() + "'and Host='" + isUser + "'");
					if (Textid.getText().equals("") || pw.getText().equals("") || pw2.getText().equals("")
							|| Textname.getText().equals("") || phone_t.getText().equals("")
							|| Text_detail.getText().equals("")) 
					{
						// 비어있는 공간이 존재
						JOptionPane.showMessageDialog(null, "비어있는 정보가 존재합니다.", "회원가입 실패", JOptionPane.PLAIN_MESSAGE);
					} 
					else if (!pw.getText().equals(pw2.getText())) 
					{
						// pw가 일치하지않습니다.
						JOptionPane.showMessageDialog(null, "Password가 일치하지않습니다.", "회원가입 실패",
								JOptionPane.PLAIN_MESSAGE);
					}
					else if (rs.next()) 
					{ 
						// ID 중복
						JOptionPane.showMessageDialog(null, "아이디가 이미 존재합니다", "회원가입 실패", JOptionPane.PLAIN_MESSAGE);
					} 
					else 
					{ 
						// 회원가입 data를 DB의 user Table 에 삽입
						rs = stmt.executeQuery(
								"select RegionCode From region where Region1 = '" + Region1.getSelectedItem().toString()
										+ "' && Region2 = '" + Region2.getSelectedItem().toString() + "'");
						rs.next();
						int RegionCode = rs.getInt(1);
						String sql = "insert into user (UID, Password, Host, Name,Tel, Region,others)  values('"
								+ Textid.getText() + "', '" + pw.getText() + "', '" + isUser + "','"
								+ Textname.getText() + "','" + phone.getSelectedItem().toString() + phone_t.getText()
								+ "','" + RegionCode + "','" + Text_detail.getText() + "')";
						stmt.executeUpdate(sql); // select 문을 제외한 나머지를 실행할때 사용하는
													// 메소드
						rs = stmt.executeQuery(
								"select * from user where UID ='" + Textid.getText() + "'and Host='" + isUser + "'");
						rs.next();
						sql = "insert into branch values ('" + rs.getInt(7) + "','" + rs.getString(4) + "','"
								+ rs.getString(5) + "','" + openh_sp.getValue() + ":" + openm_sp.getValue() + "','"
								+ closeh_sp.getValue() + ":" + closem_sp.getValue() + "','"
								+ Region1.getSelectedItem().toString() + " " + Region2.getSelectedItem().toString()
								+ " " + Text_detail.getText() + "')";
						stmt.executeUpdate(sql);
						setVisible(false);
						dispose();
						JOptionPane.showMessageDialog(null, "회원가입 완료하였습니다. 로그인하여주세요.", "회원가입 성공",
								JOptionPane.PLAIN_MESSAGE);
					}
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();

				}

			}
		});

		//취소 버튼
		JButton cancel = new JButton("취소");
		cancel.setBounds(230, 300, 90, 40);
		panel.add(cancel);

		// 취소 버튼 액션
		cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				dispose();
			}
		});

	}
}
