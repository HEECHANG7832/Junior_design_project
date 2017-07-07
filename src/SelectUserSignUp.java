import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//회원가입 종류 선택창
public class SelectUserSignUp extends JFrame 
{
	SelectUserSignUp()
	{
		//프레임 설정
		setLayout(null);
		setSize(210, 120);
		setLocation(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("선택");
		
		//패널 설정
		JPanel panel = new JPanel();
		panel.setBounds(0, 20, 200, 100);

		//구매자 판매자 구별
		JRadioButton rb1 = new JRadioButton("구매자");
		JRadioButton rb2 = new JRadioButton("판매자");
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		panel.add(rb1);
		panel.add(rb2);
		add(panel);
		
		//선택 버튼
		JButton button = new JButton("     선택     ");
		panel.add(button);

		//선택 버튼 액션
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (rb1.isSelected()) 
				{
					new SignUp(false);
					dispose();
				} else if (rb2.isSelected()) 
				{
					new SignUp(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "선택하여주십시오", "사용자선택", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		setVisible(true);
	}
}
