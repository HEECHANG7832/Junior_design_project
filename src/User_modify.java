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

public class User_modify extends JFrame 
{
	// 사용자, 판매자 구별
	String isUser;

	User_modify(Boolean isUserBool, String LoginId) 
	{
		isUser = isUserBool ? "Y" : "N";

		// 프레임 설정
		setTitle("회원 정보 수정");
		setBounds(400, 20, 450, 400);
		setResizable(false);
		setLocation(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		placeSignUpPanel(panel, LoginId);

		add(panel);
		setVisible(true);
	}

	// 패널 설정 함수
	public void placeSignUpPanel(JPanel panel, String LoginId) 
	{
		// SQL Connect
		SQLConnection a = new SQLConnection();
		Connection con = a.makeConnection();

		// 패널 설정
		panel.setLayout(null);

		// 아이디
		JLabel labelid = new JLabel("아이디");
		labelid.setBounds(20, 30, 100, 20);
		panel.add(labelid);

		// 아이디 입력 칸
		JTextField Textid = new JTextField(LoginId);
		Textid.setBounds(160, 30, 150, 20);
		Textid.setEditable(false);
		panel.add(Textid);

		// 기존 패스워드
		JLabel labelpwd = new JLabel("기존 패스워드");
		labelpwd.setBounds(20, 60, 150, 20);
		panel.add(labelpwd);

		// 기존 패스워드 입력 칸
		JPasswordField pw = new JPasswordField();
		pw.setBounds(160, 60, 150, 20);
		panel.add(pw);

		// 새 패스워드
		JLabel labelpwd2 = new JLabel("새 패스워드");
		labelpwd2.setBounds(20, 90, 100, 20);
		panel.add(labelpwd2);

		// 새 패스워드 입력 칸
		JPasswordField pw2 = new JPasswordField();
		pw2.setBounds(160, 90, 150, 20);
		panel.add(pw2);

		// 패스워드 재확인
		JLabel labelpwd3 = new JLabel("새 패스워드 재확인");
		labelpwd3.setBounds(20, 120, 120, 20);
		panel.add(labelpwd3);

		// 패스워드 재확인 입력칸
		JPasswordField pw3 = new JPasswordField();
		pw3.setBounds(160, 120, 150, 20);
		panel.add(pw3);

		// 이름
		JLabel labelname;
		labelname = new JLabel("이름");
		labelname.setBounds(20, 150, 100, 20);
		panel.add(labelname);

		// 이름 입력 칸
		JTextField Textname = new JTextField();
		Textname.setBounds(160, 150, 150, 20);
		panel.add(Textname);

		// 주소
		JLabel labelname2;
		labelname2 = new JLabel("주소");
		labelname2.setBounds(20, 180, 100, 20);
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
		Region1.setBounds(160, 180, 90, 20);
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
		Region2.setBounds(270, 180, 90, 20);
		panel.add(Region2);

		// 세부 주소
		JLabel labelname_detail = new JLabel("세부 위치");
		labelname_detail.setBounds(20, 210, 100, 20);
		panel.add(labelname_detail);

		// 세부 주소 입력 칸
		JTextField Text_detail = new JTextField();
		Text_detail.setBounds(160, 210, 150, 20);
		panel.add(Text_detail);

		// 전화번호
		JLabel phone_id = new JLabel("전화번호");
		phone_id.setBounds(20, 240, 150, 20);
		panel.add(phone_id);
		Vector phone_v = new Vector<>();
		phone_v.add("010");
		phone_v.add("02");
		JComboBox phone = new JComboBox<>(phone_v);
		phone.setBounds(160, 240, 60, 20);
		panel.add(phone);

		// 전화번호 입력 칸
		JTextField phone_t = new JTextField();
		phone_t.setBounds(230, 240, 100, 20);
		panel.add(phone_t);

		// data set
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from User where UID ='" + LoginId + "'");
			int RegionCode = 0;
			while (rs.next()) 
			{
				RegionCode = rs.getInt(6) - 27;
				Region2.setSelectedIndex(RegionCode);
				Textname.setText(rs.getString(4));
				Text_detail.setText(rs.getString(8));
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		// 수정
		JButton join = new JButton("수정");
		join.setBounds(120, 280, 90, 40);
		panel.add(join);

		// Join Button ActionListener
		join.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select RegionCode From region where Region1 = '" + Region1.getSelectedItem().toString()
									+ "' && Region2 = '" + Region2.getSelectedItem().toString() + "'");
					rs.next();

					int RegionCode = rs.getInt(1);
					rs = stmt.executeQuery(
							"SELECT * FROM USER WHERE UID ='" + Textid.getText() + "'and Host='" + isUser + "'");
					rs.next();
					if (pw2.getText().equals("") && phone_t.getText().equals("")) 
					{
						// 새 패스워드와 전화번호를 적지 않은 경우
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// 기존패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "기존 패스워드가 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else 
						{
							// 이름, 주소, 세부위치
							String sql = "update user set Name='" + Textname.getText() + "', Region ='" + RegionCode
									+ "', others = '" + Text_detail.getText() + "' WHERE UID ='" + Textid.getText()
									+ "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "정보 수정 성공", JOptionPane.PLAIN_MESSAGE);
						} // 기존패스워드가 일치하는 경우
					} 
					else if (pw2.getText().equals("")) 
					{
						// 새 패스워드를 적지 않은 경우
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// 기존패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "기존 패스워드가 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else 
						{
							// 이름, 주소, 세부위치, 전화번호
							String sql = "update user set Name='" + Textname.getText() + "', Region ='" + RegionCode
									+ "', others = '" + Text_detail.getText() + "',Tel ='"
									+ phone.getSelectedItem().toString() + phone_t.getText() + "' WHERE UID ='"
									+ Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "정보 수정 성공", JOptionPane.PLAIN_MESSAGE);

						} // 기존패스워드가 일치하는 경우
					} 
					else if (phone_t.getText().equals("")) 
					{
						// 전화번호를 적지 않은 경우
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// 기존패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "기존 패스워드가 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);

						} 
						else if (!pw2.getText().equals(pw3.getText())) 
						{
							// 새 패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "새패스워드가 서로 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);
						}

						else 
						{
							// 이름, 주소, 새패스워드, 세부위치
							String sql = "update user set Name='" + Textname.getText() + "',Password='" + pw2.getText()
									+ "', Region ='" + RegionCode + "', others = '" + Text_detail.getText()
									+ "' WHERE UID ='" + Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "정보 수정 성공", JOptionPane.PLAIN_MESSAGE);
						} // 기존패스워드와 새 패스워드가 일치하는 경우
					} 
					else // 다 적은 경우
					{
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// 기존패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "기존 패스워드가 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else if (!pw2.getText().equals(pw3.getText()))
						{
							// 새 패스워드가 일치하지 않는 경우
							JOptionPane.showMessageDialog(null, "새패스워드가 서로 일치하지 않습니다", "정보 수정 실패",
									JOptionPane.PLAIN_MESSAGE);
						}
						else 
						{
							// 이름, 주소, 새패스워드, 세부위치, 전화번호
							String sql = "update user set Name='" + Textname.getText() + "',Password='" + pw2.getText()
									+ "', Region ='" + RegionCode + "', others = '" + Text_detail.getText() + "',Tel ='"
									+ phone.getSelectedItem().toString() + phone_t.getText() + "' WHERE UID ='"
									+ Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "정보 수정 성공", JOptionPane.PLAIN_MESSAGE);
						}
						// 기존패스워드와 새 패스워드가 일치하는 경우
					}
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});

		JButton cancel = new JButton("취소");
		cancel.setBounds(230, 280, 90, 40);
		panel.add(cancel);

		// Cancel Button ActionListener
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
