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
	// �����, �Ǹ��� ����
	String isUser;

	User_modify(Boolean isUserBool, String LoginId) 
	{
		isUser = isUserBool ? "Y" : "N";

		// ������ ����
		setTitle("ȸ�� ���� ����");
		setBounds(400, 20, 450, 400);
		setResizable(false);
		setLocation(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		placeSignUpPanel(panel, LoginId);

		add(panel);
		setVisible(true);
	}

	// �г� ���� �Լ�
	public void placeSignUpPanel(JPanel panel, String LoginId) 
	{
		// SQL Connect
		SQLConnection a = new SQLConnection();
		Connection con = a.makeConnection();

		// �г� ����
		panel.setLayout(null);

		// ���̵�
		JLabel labelid = new JLabel("���̵�");
		labelid.setBounds(20, 30, 100, 20);
		panel.add(labelid);

		// ���̵� �Է� ĭ
		JTextField Textid = new JTextField(LoginId);
		Textid.setBounds(160, 30, 150, 20);
		Textid.setEditable(false);
		panel.add(Textid);

		// ���� �н�����
		JLabel labelpwd = new JLabel("���� �н�����");
		labelpwd.setBounds(20, 60, 150, 20);
		panel.add(labelpwd);

		// ���� �н����� �Է� ĭ
		JPasswordField pw = new JPasswordField();
		pw.setBounds(160, 60, 150, 20);
		panel.add(pw);

		// �� �н�����
		JLabel labelpwd2 = new JLabel("�� �н�����");
		labelpwd2.setBounds(20, 90, 100, 20);
		panel.add(labelpwd2);

		// �� �н����� �Է� ĭ
		JPasswordField pw2 = new JPasswordField();
		pw2.setBounds(160, 90, 150, 20);
		panel.add(pw2);

		// �н����� ��Ȯ��
		JLabel labelpwd3 = new JLabel("�� �н����� ��Ȯ��");
		labelpwd3.setBounds(20, 120, 120, 20);
		panel.add(labelpwd3);

		// �н����� ��Ȯ�� �Է�ĭ
		JPasswordField pw3 = new JPasswordField();
		pw3.setBounds(160, 120, 150, 20);
		panel.add(pw3);

		// �̸�
		JLabel labelname;
		labelname = new JLabel("�̸�");
		labelname.setBounds(20, 150, 100, 20);
		panel.add(labelname);

		// �̸� �Է� ĭ
		JTextField Textname = new JTextField();
		Textname.setBounds(160, 150, 150, 20);
		panel.add(Textname);

		// �ּ�
		JLabel labelname2;
		labelname2 = new JLabel("�ּ�");
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

		// ���� �ּ�
		JLabel labelname_detail = new JLabel("���� ��ġ");
		labelname_detail.setBounds(20, 210, 100, 20);
		panel.add(labelname_detail);

		// ���� �ּ� �Է� ĭ
		JTextField Text_detail = new JTextField();
		Text_detail.setBounds(160, 210, 150, 20);
		panel.add(Text_detail);

		// ��ȭ��ȣ
		JLabel phone_id = new JLabel("��ȭ��ȣ");
		phone_id.setBounds(20, 240, 150, 20);
		panel.add(phone_id);
		Vector phone_v = new Vector<>();
		phone_v.add("010");
		phone_v.add("02");
		JComboBox phone = new JComboBox<>(phone_v);
		phone.setBounds(160, 240, 60, 20);
		panel.add(phone);

		// ��ȭ��ȣ �Է� ĭ
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

		// ����
		JButton join = new JButton("����");
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
						// �� �н������ ��ȭ��ȣ�� ���� ���� ���
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// �����н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���� �н����尡 ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else 
						{
							// �̸�, �ּ�, ������ġ
							String sql = "update user set Name='" + Textname.getText() + "', Region ='" + RegionCode
									+ "', others = '" + Text_detail.getText() + "' WHERE UID ='" + Textid.getText()
									+ "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���� ���� ����", JOptionPane.PLAIN_MESSAGE);
						} // �����н����尡 ��ġ�ϴ� ���
					} 
					else if (pw2.getText().equals("")) 
					{
						// �� �н����带 ���� ���� ���
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// �����н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���� �н����尡 ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else 
						{
							// �̸�, �ּ�, ������ġ, ��ȭ��ȣ
							String sql = "update user set Name='" + Textname.getText() + "', Region ='" + RegionCode
									+ "', others = '" + Text_detail.getText() + "',Tel ='"
									+ phone.getSelectedItem().toString() + phone_t.getText() + "' WHERE UID ='"
									+ Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���� ���� ����", JOptionPane.PLAIN_MESSAGE);

						} // �����н����尡 ��ġ�ϴ� ���
					} 
					else if (phone_t.getText().equals("")) 
					{
						// ��ȭ��ȣ�� ���� ���� ���
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// �����н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���� �н����尡 ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);

						} 
						else if (!pw2.getText().equals(pw3.getText())) 
						{
							// �� �н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���н����尡 ���� ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);
						}

						else 
						{
							// �̸�, �ּ�, ���н�����, ������ġ
							String sql = "update user set Name='" + Textname.getText() + "',Password='" + pw2.getText()
									+ "', Region ='" + RegionCode + "', others = '" + Text_detail.getText()
									+ "' WHERE UID ='" + Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���� ���� ����", JOptionPane.PLAIN_MESSAGE);
						} // �����н������ �� �н����尡 ��ġ�ϴ� ���
					} 
					else // �� ���� ���
					{
						if (!pw.getText().equals(rs.getString(2))) 
						{
							// �����н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���� �н����尡 ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);
						} 
						else if (!pw2.getText().equals(pw3.getText()))
						{
							// �� �н����尡 ��ġ���� �ʴ� ���
							JOptionPane.showMessageDialog(null, "���н����尡 ���� ��ġ���� �ʽ��ϴ�", "���� ���� ����",
									JOptionPane.PLAIN_MESSAGE);
						}
						else 
						{
							// �̸�, �ּ�, ���н�����, ������ġ, ��ȭ��ȣ
							String sql = "update user set Name='" + Textname.getText() + "',Password='" + pw2.getText()
									+ "', Region ='" + RegionCode + "', others = '" + Text_detail.getText() + "',Tel ='"
									+ phone.getSelectedItem().toString() + phone_t.getText() + "' WHERE UID ='"
									+ Textid.getText() + "'and Host='" + isUser + "'";
							stmt.executeUpdate(sql);
							setVisible(false);
							dispose();
							JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���� ���� ����", JOptionPane.PLAIN_MESSAGE);
						}
						// �����н������ �� �н����尡 ��ġ�ϴ� ���
					}
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});

		JButton cancel = new JButton("���");
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
