import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;



public class User_out extends JFrame{

	JPanel panel;
	JLabel label1,label2,label3,label4,label5;
	JButton button1,button2;
	JPasswordField pw;
	JFrame frame_;
	User_out(JFrame frame_)
	{
		this.frame_ = frame_;
		
		this.setTitle("회원 탈퇴");
		this.setBounds(600,300,430,420);
		panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(null);
		
		
		label2=new JLabel("회원 탈퇴시 주의사항");
		label2.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label2.setBounds(20, 0, 300, 100);
		panel.add(label2);
		
		
		label3=new JLabel("회원가입시 입력하신 정보는 모두 삭제되어 복구하실 수 없습니다.");
		label3.setBounds(20, 80, 420, 40);
		panel.add(label3);
		
		label4=new JLabel("프로그램 사용을 위해서는 로그인이 필요합니다.");
		label4.setBounds(20, 130, 420, 40);
		panel.add(label4);
		
		label5=new JLabel("비밀번호를 입력하고 '예'를 누르시면 탈퇴가 완료됩니다.");
		label5.setBounds(20, 180, 420, 40);
		panel.add(label5);
		
		label1 = new JLabel("비밀번호를 입력하세요");
		label1.setBounds(20,230,150,40);
		panel.add(label1);
		
		pw=new JPasswordField();
		pw.setBounds(180,230,150,40);
		panel.add(pw);
		
		
		
		button1=new JButton("예");
		button1.setBounds(80, 300, 100, 40);
		panel.add(button1);
		button1.addActionListener (new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(null,"회원 탈퇴 되었습니다.");
				
				frame_.dispose();
				dispose();
				new Login();
			}
		});
		
		
		button2=new JButton("아니오");
		button2.setBounds(200, 300, 100, 40);
		panel.add(button2);
		button2.addActionListener (new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				dispose();
			}
		});
		this.add(panel);
		this.setVisible(true);
		
	}
}
