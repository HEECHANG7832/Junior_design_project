import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//ȸ������ ���� ����â
public class SelectUserSignUp extends JFrame 
{
	SelectUserSignUp()
	{
		//������ ����
		setLayout(null);
		setSize(210, 120);
		setLocation(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("����");
		
		//�г� ����
		JPanel panel = new JPanel();
		panel.setBounds(0, 20, 200, 100);

		//������ �Ǹ��� ����
		JRadioButton rb1 = new JRadioButton("������");
		JRadioButton rb2 = new JRadioButton("�Ǹ���");
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		panel.add(rb1);
		panel.add(rb2);
		add(panel);
		
		//���� ��ư
		JButton button = new JButton("     ����     ");
		panel.add(button);

		//���� ��ư �׼�
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
					JOptionPane.showMessageDialog(null, "�����Ͽ��ֽʽÿ�", "����ڼ���", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		setVisible(true);
	}
}
