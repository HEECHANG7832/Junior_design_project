import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Cake_list extends JFrame {
	JTable table;
	JTextField namef;
	JTextField scoref;
	JButton button1, button2;
	JPanel panel_t;
	JLabel label;

	// ���� �̸��� �Է¹���
	public Cake_list(String branchname_) {
		super(branchname_);
		setSize(550, 430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		panel_t = new JPanel();
		panel_t.setSize(630, 400);
		panel_t.setLayout(null);

		// Ŭ���� ���̸� ����
		String ConturyName = branchname_;
		// JLabel label = new JLabel(""+ConturyName ,JLabel.LEFT);
		label = new JLabel("����ũ �ֹ� ����");
		label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label.setBounds(150, 10, 400, 50);
		panel_t.add(label);

		// table�׸���
		JTable table;
		// �⺻ ���̺� ��
		DefaultTableModel model;
		// MyTableModel model; //��������

		// ��� ��
		Vector<String> userColumn = new Vector<String>();
		Vector<String> userRow = new Vector<String>();

		// Column �Է�
		userColumn.addElement("������ID");
		userColumn.addElement("��������");
		userColumn.addElement("����ũ����");
		userColumn.addElement("����");
		userColumn.addElement("����");
		userColumn.addElement("�䱸����");
		// ���̺� �� �ֱ�
		model = new DefaultTableModel(userColumn, 0);

		// ���̺� �� �Է�
		table = new JTable(model);

		// ���̺� �ʱ�ȭ
		int rowCount = model.getRowCount();
		for (int k = rowCount - 1; k >= 0; k--) {
			model.removeRow(k);
		}

		try {
			// SQL Connect
			SQLConnection a = new SQLConnection();
			Connection con = a.makeConnection();
			int i = 0;
			int j = 0;

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from branch where branchName ='" + branchname_ + "'");
			rs.next();
			int BranchCode = rs.getInt(1);
			rs = stmt.executeQuery("Select * from history where branchCode ='" + BranchCode
					+ "'and IsCake='Y' order by branchCode asc");
			if (rs.next()) {
				// ������ ID ������ ���� ���� �������
				do {
					// data�� db���� �����ͼ� ����
					String UID = rs.getString(1);
					int BreadCode = rs.getInt(3);
					int stock = rs.getInt(5);
					int price = rs.getInt(4);

					String BuyDate = rs.getString(2);
					String ExpireTime = rs.getString(6);
					String demand = rs.getString(9);
					model.addRow(
							new Object[] { UID, BuyDate, BreadCode, price + "��", stock + "��",  demand });
					j++; // �����ڵ带 ���ڷ� �ٲٱ����� ����
				} while (rs.next());

				for (i = 0; i < j; i++) {
					rs = stmt.executeQuery("Select * from bread where breadcode ='" + table.getValueAt(i, 2) + "'");
					if (rs.next()) {
						table.setValueAt(rs.getString(2), i, 2);
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "�����ϴ� �����Ͱ� �����ϴ�.", "������ ���� ����", JOptionPane.PLAIN_MESSAGE);
			}

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
		}
		// panel�� �߰�
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBounds(20, 80, 500, 200);
		panel_t.add(scrollPane);
		setVisible(true);
		add(panel_t);

	}// Cake_list
}
