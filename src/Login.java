import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//로그인 창
public class Login extends JFrame 
{
	JLabel labelid, labelpwd;
	JTextField Textid;
	JPasswordField Textpwd;
	JButton sign_in, sign_up;
	String isUser;
	ImageIcon icon;
	Font f;

	public Login() 
	{
		// 프레임설정
		icon = new ImageIcon("호빵맨.jpg");
		this.setTitle("login");
		this.setBounds(300, 300, 370, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 입력 성분들 생성
		JPanel panel = new JPanel() 
		{
			public void paintComponent(Graphics g) 
			{
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
				super.paintComponent(g);
			}
		};

		placeLoginPanel(panel);
		add(panel);
		this.setVisible(true);
	}

	// 각종 성분들 추가하는 메서드
	public void placeLoginPanel(JPanel panel) 
	{
		// 폰트 설정
		f = new Font(null, Font.BOLD, 20);

		// ID
		panel.setLayout(null);
		labelid = new JLabel("ID");
		labelid.setBounds(30, 50, 80, 25);
		labelid.setFont(f);
		panel.add(labelid);

		// PWD
		labelpwd = new JLabel("Pwd");
		labelpwd.setBounds(30, 90, 80, 25);
		labelpwd.setFont(f);
		panel.add(labelpwd);

		// ID_Textfield
		Textid = new JTextField(20);
		Textid.setBounds(120, 50, 160, 25);
		panel.add(Textid);

		// pw_field
		Textpwd = new JPasswordField(20);
		Textpwd.setBounds(120, 90, 160, 25);
		panel.add(Textpwd);

		// 로그인 구분
		// User
		JRadioButton rb1 = new JRadioButton("User");
		rb1.setBounds(70, 160, 100, 25);
		rb1.setOpaque(false);
		rb1.setForeground(Color.white);

		// Seller
		JRadioButton rb2 = new JRadioButton("Seller");
		rb2.setBounds(190, 160, 100, 25);
		rb2.setForeground(Color.white);
		rb2.setOpaque(false);

		// 라디오 묶기
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		panel.add(rb1);
		panel.add(rb2);

		// 회원가입 버튼
		sign_up = new JButton("회원 가입");
		sign_up.setBounds(190, 195, 100, 25);
		panel.add(sign_up);

		sign_up.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new SelectUserSignUp();
			}
		});

		// 로그인버튼
		sign_in = new JButton("로그인");
		sign_in.setBounds(70, 195, 100, 25);
		panel.add(sign_in);

		sign_in.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Textid 와 Textpwd의 정보를 DB와 비교
				// 사용자 구분 변수
				isUser = rb1.isSelected() ? "N" : (rb2.isSelected() ? "Y" : null);

				// SQL Connect
				if (rb1.isSelected() || rb2.isSelected()) 
				{
					SQLConnection a = new SQLConnection();
					Connection con = a.makeConnection();
					try 
					{
						Statement stmt = con.createStatement();
						System.out.println(isUser.toString());

						// 사용자 구분 로그인
						ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE UID ='" + Textid.getText()
								+ "'and Host='" + isUser.toString() + "'");
						if (rs.next()) 
						{
							if ((rs.getString(2)).equals(Textpwd.getText())) 
							{
								// pwd							
								setVisible(false);
								if (isUser.toString().equals("Y")) 
								{
									new Sell_Form(rs.getString(4));
								} 
								else 
								{
									new Form1(Textid.getText());
								}
								JOptionPane.showMessageDialog(null,
										"\"" + rs.getString(1) + "\"(" + rs.getString(4) + ") 님 환영합니다.", "로그인",
										JOptionPane.PLAIN_MESSAGE);
							} 
							else 
							{
								// pwd 일치
								JOptionPane.showMessageDialog(null, "비밀번호가 틀립니다", "로그인실패", JOptionPane.PLAIN_MESSAGE);
							}
						} 
						else 
						{
							JOptionPane.showMessageDialog(null, "존재하는 아이디가 없습니다.", "로그인실패", JOptionPane.PLAIN_MESSAGE);
						}
					} 
					catch (Exception ex) 
					{
						ex.printStackTrace();
					}
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "선택하여주십시오", "사용자선택", JOptionPane.PLAIN_MESSAGE);
				}
			}

		});

	}

}