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
		
		this.setTitle("ȸ�� Ż��");
		this.setBounds(600,300,430,420);
		panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(null);
		
		
		label2=new JLabel("ȸ�� Ż��� ���ǻ���");
		label2.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label2.setBounds(20, 0, 300, 100);
		panel.add(label2);
		
		
		label3=new JLabel("ȸ�����Խ� �Է��Ͻ� ������ ��� �����Ǿ� �����Ͻ� �� �����ϴ�.");
		label3.setBounds(20, 80, 420, 40);
		panel.add(label3);
		
		label4=new JLabel("���α׷� ����� ���ؼ��� �α����� �ʿ��մϴ�.");
		label4.setBounds(20, 130, 420, 40);
		panel.add(label4);
		
		label5=new JLabel("��й�ȣ�� �Է��ϰ� '��'�� �����ø� Ż�� �Ϸ�˴ϴ�.");
		label5.setBounds(20, 180, 420, 40);
		panel.add(label5);
		
		label1 = new JLabel("��й�ȣ�� �Է��ϼ���");
		label1.setBounds(20,230,150,40);
		panel.add(label1);
		
		pw=new JPasswordField();
		pw.setBounds(180,230,150,40);
		panel.add(pw);
		
		
		
		button1=new JButton("��");
		button1.setBounds(80, 300, 100, 40);
		panel.add(button1);
		button1.addActionListener (new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(null,"ȸ�� Ż�� �Ǿ����ϴ�.");
				
				frame_.dispose();
				dispose();
				new Login();
			}
		});
		
		
		button2=new JButton("�ƴϿ�");
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
