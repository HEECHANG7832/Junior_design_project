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

public class Buy_list extends JFrame 
{
	JTable table;
	JTextField namef;
	JTextField scoref;
	JButton button1, button2;
	JPanel panel_t;
	JLabel label;

	// ���� �̸��� �Է¹���
	public Buy_list(String Username_) 
	{
		// ������ ����
		this.setTitle(Username_);
		this.setBounds(300, 300, 560, 430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// �г� ����
		panel_t = new JPanel();
		panel_t.setSize(630, 400);
		panel_t.setLayout(null);

		// Ŭ���� ���̸� ����
		String ConturyName = Username_;
		// JLabel label = new JLabel(""+ConturyName ,JLabel.LEFT);
		label = new JLabel("���� ����");
		label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label.setBounds(200, 10, 400, 50);
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
		userColumn.addElement("��������");
		userColumn.addElement("�����̸�");
		userColumn.addElement("������");
		userColumn.addElement("����");
		userColumn.addElement("����");
		userColumn.addElement("�������");

		// ���̺� �� �ֱ�
		model = new DefaultTableModel(userColumn, 0);

		// ���̺� �� �Է�
		table = new JTable(model);

		// ���̺� �ʱ�ȭ
		int rowCount = model.getRowCount();
		for (int k = rowCount - 1; k >= 0; k--) 
		{
			model.removeRow(k);
		}

		try 
		{
			// SQL Connect
			SQLConnection a = new SQLConnection();
			Connection con = a.makeConnection();
			int i = 0;
			int j = 0;

			// ��������,�����̸�, ������, ����, ����, �������
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM history where UID = '" + Username_ + "'");
			if (rs.next()) 
			{
				do 
				{
					// data�� db���� �����ͼ� ����
					String BuyDate = rs.getString(2);
					int BranchCode = rs.getInt(7);
					int BreadCode = rs.getInt(3);
					int stock = rs.getInt(5);
					int price = rs.getInt(4);

					String ExpireTime = rs.getString(6);
					model.addRow(new Object[] { BuyDate, BranchCode, BreadCode, stock + "��", price + "��", ExpireTime });
					j++; // �����ڵ带 ���ڷ� �ٲٱ����� ����
				} 
				while (rs.next());
				for (i = 0; i < j; i++)
				{
					rs = stmt.executeQuery("Select * from Branch where Branchcode ='" + table.getValueAt(i, 1) + "'");
					if (rs.next())
					{
						table.setValueAt(rs.getString(2), i, 1);
					}
					rs.close();
					rs = stmt.executeQuery("Select * from bread where breadcode ='" + table.getValueAt(i, 2) + "'");
					if (rs.next())
					{
						table.setValueAt(rs.getString(2), i, 2);
					}
					rs.close();
				}

			}
		} // end try
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		// panel�� �߰�
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBounds(20, 80, 500, 200);
		panel_t.add(scrollPane);
		setVisible(true);
		add(panel_t);

	}// buy_list
}
