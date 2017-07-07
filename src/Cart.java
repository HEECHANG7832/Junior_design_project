import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cart extends JFrame 
{

	Vector<String> userColumn;
	Vector<String> userRow;

	String loginId;

	// table�׸���
	JTable table;
	// �⺻ ���̺� ��
	DefaultTableModel model;

	public Cart(String loginId) 
	{
		// TODO Auto-generated constructor stub
		super("��ٱ���");

		this.loginId = loginId;

		// ���
		JPanel toolBar = new JPanel();

		// toolBar�� �߰�
		JLabel label1 = new JLabel(loginId + "�� �� ��ٱ���");
		toolBar.add(label1);

		// null�� �ݰ����ϴ�. �α��� �ϸ� ���� ���޹���
		JLabel label2 = new JLabel("�����հ� :        ");
		toolBar.add(label2);

		JButton button1 = new JButton("�ʱ�ȭ");
		toolBar.add(button1);
		
		button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               userRow.clear(); // vector �ʱ�ȭ
               //���̺� �ʱ�ȭ
              int rowCount = model.getRowCount();
              for(int k = rowCount - 1; k>=0;k--) {
                 model.removeRow(k);
              }
            }   
         });//button1 end
		
		JButton button2 = new JButton("����");
		toolBar.add(button2);

		// ����,�Ǹų����� �߰�
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// SQL Connect
				SQLConnection a = new SQLConnection();
				Connection con = a.makeConnection();
				String UID, BreadName, Expiretime, BranchName, IsCake = "N", price, Stock;
				boolean isAdd = false;
				int BreadCode = 0;
				int BranchCode = 0;

				int i = 0;
				int j = 0;

				try 
				{
					for (i = 0; i < model.getRowCount(); i++)
					{
						UID = loginId;
						BranchName = table.getValueAt(i, 0).toString();
						BreadName = table.getValueAt(i, 1).toString();
						price = table.getValueAt(i, 2).toString();
						Stock = table.getValueAt(i, 3).toString();
						Expiretime = table.getValueAt(i, 4).toString();
						
						// "��" ����
						price = price.substring(0, price.length() - 1);
						
						// "��" ����
						Stock = Stock.substring(0, Stock.length() - 1);
						Statement stmt = con.createStatement();
						
						// ���� ���̸��� �����ϴ� ���
						ResultSet rs = stmt.executeQuery("SELECT * FROM bread where BreadName ='" + BreadName + "'");
						if (rs.next()) 
						{
							// �˻��� ���� �ڵ带 ������ ����
							BreadCode = rs.getInt(1);
						}

						rs = stmt.executeQuery("SELECT * FROM Branch where BranchName ='" + BranchName + "'");
						if (rs.next()) 
						{ 
							// �˻��� ������ �ڵ带 ������ ����
							BranchCode = rs.getInt(1);
						}
						rs = stmt.executeQuery("SELECT * FROM Branch_info where BranchCode ='" + BranchCode + "'");
						if (rs.next()) 
						{ 
							// �˻��� �ڵ带 ������ ����
							IsCake = rs.getString(8);
						}
						//���� ��¥
						long time = System.currentTimeMillis();
						SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
						String BuyDate = dayTime.format(new Date(time));
						
						String sql = "insert into history  values('" + UID + "', '" + BuyDate + "', '" + BreadCode
								+ "','" + price + "','" + Stock + "','" + Expiretime + "','" + BranchCode + "','"
								+ IsCake +"','"+ null +"')";
						stmt.executeUpdate(sql); // select ���� ������ �������� �����Ҷ� ����ϴ� �޼ҵ�
						
						sql = "update branch_info set stock = stock -'"+Stock+"' where Breadcode ='" +BreadCode+"'and BranchCode ='"+BranchCode+"'and ExpireTime='"+Expiretime+"'";
						stmt.executeUpdate(sql); // select ���� ������ �������� �����Ҷ� ����ϴ� �޼ҵ�
						isAdd = true;
					}
					// ��ٱ��� �߰� �ǰ� �� ���̺� �ʱ�ȭ
					if (isAdd) 
					{
						int rowCount = model.getRowCount();
						for (int k = rowCount - 1; k >= 0; k--) 
						{
							model.removeRow(k);
						}
						userRow.clear(); // vector �ʱ�ȭ
						JOptionPane.showMessageDialog(null, "���Ű� �Ϸ�Ǿ����ϴ�.", "���� �Ϸ�", JOptionPane.PLAIN_MESSAGE);
					} 

				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}

			}
		}); // button2 end

		// ����â
		add(toolBar, BorderLayout.NORTH);

		// ���̺��� ��� ���� �����ϴ� ����
		userColumn = new Vector<String>();
		userRow = new Vector<String>();

		// Column ����
		userColumn.addElement("�����̸�");
		userColumn.addElement("���̸�");
		userColumn.addElement("����");
		userColumn.addElement("����");
		userColumn.addElement("�������");

		// ���̺� �� �ֱ�
		model = new DefaultTableModel(userColumn, 0);

		// ���̺� �� �Է�
		table = new JTable(model);

		// panel�� �߰�
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		// ǥ
		add(scrollPane, BorderLayout.SOUTH);

	}// Constructor

	public void CreateCart() 
	{

		// ���� �߰� �Ǵ°��� �����ϱ����� ���̺� �ʱ�ȭ
		int rowCount = model.getRowCount();
		for (int k = rowCount - 1; k >= 0; k--) 
		{
			model.removeRow(k);
		}

		// ������ �Է�
		for (int i = 0; i < userRow.size() / 5; i++) 
		{
			model.addRow(new Object[] { userRow.elementAt(5 * i + 0), userRow.elementAt(5 * i + 1),
					userRow.elementAt(5 * i + 2), userRow.elementAt(5 * i + 3) + "��", userRow.elementAt(5 * i + 4) });
		}

		setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void addCart(String CartRow[]) 
	{

		for (int i = 0; i < 5; i++)
		{
			userRow.addElement(CartRow[i]);
		}
	}
}
