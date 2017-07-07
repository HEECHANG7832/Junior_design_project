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
import javax.swing.plaf.synth.SynthSplitPaneUI;
import javax.swing.table.DefaultTableModel;

public class Sell_Form
{
	JFrame frame;
	JTable table;
	JTextField namef;
	JTextField scoref;
	JButton button1, button2, button3, button4;
	JPanel panel_t;
	JLabel label;
	int BranchCode = 0;

	// Ŭ���� ���̺��� �� ������ ����Ǵ� ����
	int row;
	int column;

	// ���� �̸��� �Է¹���
	public Sell_Form(String branchname_) 
	{
		//������ ����
		frame = new JFrame();
		frame.setTitle(branchname_);
		frame.setBounds(400, 300, 560, 440);

		//�г� ����
		panel_t = new JPanel();
		panel_t.setLayout(null);
		frame.add(panel_t);
		// Ŭ���� ���̸� ����
		String ConturyName = branchname_;
		label = new JLabel("�Ǹ� ���");
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
		userColumn.addElement("������");
		userColumn.addElement("����");
		userColumn.addElement("����");
		userColumn.addElement("�������");
		userColumn.addElement("�����ð�");

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
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch where branchname = '" + branchname_ + "'");
			if (rs.next())
			{
				BranchCode = rs.getInt(1);
			} 
			else 
			{
				JOptionPane.showMessageDialog(null, "�����ϴ� �����Ͱ� �����ϴ�.", "������ ���� ����", JOptionPane.PLAIN_MESSAGE);
			}
			rs = stmt.executeQuery(
					"Select * from branch_info where branchCode ='" + BranchCode + "'order by branchCode asc");
			
			if (rs.next()) 
			{
				do 
				{
					// data�� db���� �����ͼ� ����
					int BreadCode = rs.getInt(3);
					int stock = rs.getInt(4);
					int price = rs.getInt(7);
					String ExpireTime = rs.getString(6);
					int BakingTime = rs.getInt(5);
					model.addRow(new Object[] { BreadCode, price + "��", stock + "��", ExpireTime, BakingTime + "��" });
					j++; // �����ڵ带 ���ڷ� �ٲٱ����� ����
				} 
				while (rs.next());
				
				for (i = 0; i < j; i++)
				{
					rs = stmt.executeQuery("Select * from bread where breadcode ='" + table.getValueAt(i, 0) + "'");
					if (rs.next())
						table.setValueAt(rs.getString(2), i, 0);
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

		frame.setVisible(true);

		//�Ǹų��� ��ư
		button1 = new JButton("�Ǹ� ����");
		button1.setBounds(30, 310, 100, 40);
		panel_t.add(button1);
		
		//�Ǹų��� ��ư �׼�
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new Sell_list(branchname_);
			}
		});

		//����ũ �ֹ� ����
		button2 = new JButton("����ũ �ֹ� ����");
		button2.setBounds(140, 310, 140, 40);
		panel_t.add(button2);
		
		//����ũ �ֹ� ���� ��ư �׼�
		button2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

				new Cake_list(branchname_);

			}
		});

		//��Ȳ �߰�
		button3 = new JButton("��Ȳ �߰�");
		button3.setBounds(290, 310, 100, 40);
		panel_t.add(button3);
		
		//��Ȳ �߰� ��ư �׼�
		button3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new List_add(branchname_);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		//��Ȳ ����
		button4 = new JButton("��Ȳ ����");
		button4.setBounds(400, 310, 100, 40);
		panel_t.add(button4);
		
		//��Ȳ ���� ��ư �׼�
		button4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					
					Vector<String> data = new Vector<String>();
					row = table.getSelectedRow();

					for (int i = 0; i < userColumn.size(); i++)
					{
						data.addElement(table.getValueAt(row, i).toString());
					}
					new List_modify(branchname_, data);
					frame.setVisible(false);
					frame.dispose();
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "������ ������ �����Ͽ��ֽʽÿ�.", "����Ʈ ���� ����", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		frame.add(panel_t);
	}// Sell_Form
}
