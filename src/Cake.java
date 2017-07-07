import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cake extends JFrame {

	JLabel label_location, label_branch, label_choice, label_detail, label_login, label_userlocation,
			label_userlocation2, label_cake;
	JTextField Text_get_location, Text_get_branch, Text_detail;
	JButton button_order;

	String loginId;

	// �α��� ���̵��� ����
	String userlocation;

	Cake(String loginId) {
		this.loginId = loginId;

		setTitle("����ũ �ֹ�");
		setSize(600, 520);
		setResizable(false);
		setLocation(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		placeCakePanel(panel);

		add(panel);
		setVisible(true);

	}

	public void placeCakePanel(JPanel panel) {
		// SQL Connect
		SQLConnection a = new SQLConnection();
		Connection con = a.makeConnection();

		panel.setLayout(null);

		label_login = new JLabel(loginId + "�� ����ũ�ֹ��ϱ�");
		panel.add(label_login);
		label_login.setBounds(40, 30, 200, 20);

		label_userlocation = new JLabel("�˻����� ");
		panel.add(label_userlocation);
		label_userlocation.setBounds(40, 60, 150, 20);

		label_userlocation2 = new JLabel("");
		panel.add(label_userlocation2);
		label_userlocation2.setBounds(160, 60, 300, 20);

		label_branch = new JLabel("���� ����");
		panel.add(label_branch);
		label_branch.setBounds(40, 90, 150, 20);

		JComboBox branch = new JComboBox<>();
		// db�κ��� ���� �ش����� ���������� �����ִ� �κ�

		// Branch �޾ƿ���
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM user where UID='" + loginId + "'");
			rs.next();
			int Regioncode = rs.getInt(6);

			rs = stmt.executeQuery("Select * from user where Region='" + Regioncode + "'and Host ='Y'");
			if (rs.next()) { // ������ �����ϴ� ���
				do {
					branch.addItem(rs.getString(4));
				} while (rs.next());
			}
			// ������ ������������ ���
			else {
				branch.addItem("������ ���� ����");
			}
			rs.close();
			// ����� ���� ����
			rs = stmt.executeQuery("Select * from region where Regioncode = '" + Regioncode + "'");
			rs.next();
			userlocation = rs.getString(2) + " " + rs.getString(3);
			label_userlocation2.setText(userlocation);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		branch.setBounds(160, 90, 150, 20);
		panel.add(branch);

		label_cake = new JLabel("����ũ ����");
		panel.add(label_cake);

		JComboBox cake = new JComboBox<>();

		label_cake.setBounds(40, 120, 80, 20);
		branch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// Do Something
				// db�κ��� ���� �ش����� ����ũ�� �����ִ� �κ�
				cake.removeAllItems(); // �ʱ�ȭ
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select * from branch where BranchName='" + branch.getSelectedItem().toString() + "'");
					rs.next();
					int BranchCode = rs.getInt(1);
					int i = 0; // index of cake
					rs = stmt.executeQuery("select distinct Breadcode from branch_info where branchCode='" + BranchCode
							+ "'and  is_cake='Y'");

					Vector<Integer> cake_code = new Vector<Integer>();

					if (rs.next()) { // �˻��� �� ���
						do {
							i++;
							cake_code.addElement(rs.getInt(1)); // Vector�� �ʿ���
																// �������� �޴´�.

						} while (rs.next());
						for (int j = 0; j < i; j++) { // BreadCode�� BreadName����
														// ��ȯ�Ͽ�, ComboBox�� ����.
							rs = stmt.executeQuery(
									"select * from bread where Breadcode ='" + cake_code.elementAt(j) + "'");

							if (rs.next()) {
								cake.addItem(rs.getString(2));
							}

						}

					} // ����ũ�� ���������ʴ� ���
					else {
						cake.addItem("������ ����ũ�� ����");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		cake.setBounds(160, 120, 80, 20);
		panel.add(cake);

		label_detail = new JLabel("�߰� �䱸������ �����ּ���");
		panel.add(label_detail);
		label_detail.setBounds(40, 110, 300, 100);

		Text_detail = new JTextField();
		panel.add(Text_detail);
		Text_detail.setBounds(40, 180, 500, 200);

		button_order = new JButton("�� ��");
		panel.add(button_order);
		button_order.setBounds(250, 400, 100, 50);

		// �ֹ���ư �׼�
		button_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Statement stmt = con.createStatement();
					// ���� ��¥
					long time = System.currentTimeMillis();
					SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
					String BuyDate = dayTime.format(new Date(time));

					int price, stock, BreadCode, BranchCode;
					String ExpireTime;
					// set BreadCode
					ResultSet rs = stmt.executeQuery(
							"select * from bread where BreadName ='" + cake.getSelectedItem().toString() + "'");
					rs.next();
					BreadCode = rs.getInt(1);
					rs.close();

					// set BranchCode
					rs = stmt.executeQuery(
							"select * from branch where BranchName ='" + branch.getSelectedItem().toString() + "'");
					rs.next();
					BranchCode = rs.getInt(1);
					rs.close();

					// set BranchCode
					rs = stmt.executeQuery("select * from branch_info where is_cake = 'Y' and branchCode ='"
							+ BranchCode + "'and breadCode = '" + BreadCode + "'");
					rs.next();
					stock = rs.getInt(4);
					price = rs.getInt(7);
					ExpireTime = rs.getString(6);
					rs.close();

					// UID, buydate, BreadCode, price, Stock, Exprietime,
					// Branchcode, isCake, demand
					String sql = "insert into history values('" + loginId + "','" + BuyDate + "','" + BreadCode + "','"
							+ price + "','1','"+ExpireTime +"','" + BranchCode + "','Y','" + Text_detail.getText() + "')";
					stmt.executeUpdate(sql); // select ���� ������ �������� �����Ҷ� ����ϴ� �޼ҵ�
					
				
					dispose();
					new Form1(loginId);
					JOptionPane.showMessageDialog(null, "����ũ�� �ֹ��Ǿ����ϴ�.", "����ũ �ֹ��Ϸ�", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "����ũ�ֹ��� �����߽��ϴ�.", "����ũ �ֹ�����", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});// button_order end
	}

}
