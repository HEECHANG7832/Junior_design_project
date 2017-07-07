import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class Form1 {

	// ������ ����
	JFrame frame;

	// ���� ������
	JInternalFrame Lframe;
	// ������ ������
	JInternalFrame Rframe;
	// ������ ���� �ݱ�
	boolean frameOpen = false;

	// ���� ���� �� ����ڷα����������� �����´�
	// ������� �ּ�
	String Address = null;
	// ������� ���� �浵
	Float Lat;
	Float Lng;

	// ������� ���̵�
	String LoginId;

	// ����� �����ڵ�
	int regioncode = 0;

	// Ŭ���� ���̺��� �� ������ ����Ǵ� ����
	int row;

	// �� ��� ����
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;

	// �⺻ �� ����
	private static int zoomValue = 17;

	// geocoding �� ���� ������ ��Ŀ ǥ���ϱ�
	GeoCoding CommonUtil = new GeoCoding();

	public Form1(String LoginId_) { // �ӽ÷� Parameter ����
		//������ ��ü ����
		
		LoginId = LoginId_; // LoginID ����
		
		// SQL Connect
					SQLConnection a = new SQLConnection();
					Connection con = a.makeConnection();
					try{
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE UID ='" + LoginId + "' and host = 'N'");
						
						if(rs.next()) {
								regioncode = rs.getInt(6);
								Address = rs.getString(8);
							}
						else {JOptionPane.showMessageDialog(null, "�����ϴ� �����Ͱ� �����ϴ�.", "������ ���� ����", JOptionPane.PLAIN_MESSAGE);}
						
						rs = stmt.executeQuery("SELECT * FROM region WHERE regioncode ='" +regioncode + "'");
						if(rs.next()) {
							Address = rs.getString(2) +" " + rs.getString(3) + " " + Address;
						}
						else {JOptionPane.showMessageDialog(null, "�����ϴ� �����Ͱ� �����ϴ�.", "������ ���� ����", JOptionPane.PLAIN_MESSAGE);}
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
		//Jxbrowser ��ü ���� ���������� �����ͼ� script�� �����ų�� �ִ�.
		final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        
        //��in��ư ��out��ư
        JButton zoomInButton = new JButton("������ �Ρ���");
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                	//1. Using the Browser.executeJavaScript(String javaScript) method. This method executes the JavaScript code asynchronously without blocking the current thread execution. If you are not interested in receiving the result of JavaScript code, you can use this method because its return parameter is void.
                	browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });
        JButton zoomOutButton = new JButton(" �� �� �ƿ� ��");
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                	browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });
        
        //��� ����
        JPanel toolBar = new JPanel();
        
        //toolBar�� �߰�
      	toolBar.add(new LoadImageApp("������.png"));
        JLabel label1 = new JLabel("�˻��� �Է�");
        Font f = new Font(null, Font.BOLD, 17);
        label1.setFont(f);
		toolBar.add(label1);
		JTextField Text1 = new JTextField(15); // �˻�â
		toolBar.add(Text1);
		JButton button1 = new JButton("�˻�");
		toolBar.add(button1);
		//null�� �ݰ����ϴ�. �α��� �ϸ� ���� ���޹���
        JLabel label2 = new JLabel("   "+LoginId + "�� �ݰ����ϴ�.    ");
        label2.setFont(f);
		toolBar.add(label2);
		
		//�̹����� �ٲٱ�
		JButton button8 = new JButton("��� ����");
        toolBar.add(button8);
        //��ɸ��� ������ �����ֱ�
        button8.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			//�����ʿ� �������� �����ְ��Ѵ�
        			if(frameOpen == false)
        			{
        				//������ ������ �ø��� Rframe�����ش�
        				frame.setSize(1100, 700);
            			Rframe.setVisible(true);
            			frameOpen = true;
        			}
        			else
        			{
        				frame.setSize(1000, 700);
            			Rframe.setVisible(false);
            			frameOpen = false;
        			}
        			
  				}
        	}
        );
        Component com9 = Box.createVerticalStrut(60);
        toolBar.add(com9);
        
		
		//������ ����
		JPanel toolBar2 = new JPanel();		
        
		
        //��ٱ��� �ֱ�
        JButton button2 = new JButton("��ٱ��� �ֱ�");
        
        //��ٱ��� ����
        JButton button3 = new JButton("��ٱ��� ����");
        
        //���ų���
        JButton button4 = new JButton("  ����ũ �ֹ�  ");
        
        //���ų��� �׼�
        button4.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			new Cake(LoginId);
  				}
        	}
        ); //button4 end
        
        JButton button5 = new JButton("������ ������");
        //����ũ �ֹ� �׼�
        button5.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        		  new Buy_list(LoginId);
  				}
        	}
        );
        
        JButton button6 = new JButton("ȸ�� ���� ����");
        //ȸ�� ���� ���� �׼�
        button6.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			new User_modify(false,LoginId);
  				}
        	}
        ); 
         
        JButton button7 = new JButton("���α�  �ƿ���");
        //ȸ��Ż�� �׼�
        button7.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			//����ư�� ������
        			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, 
        																	"�α׾ƿ� �Ͻðڽ��ϱ�?",
        																	"" ,
        																	JOptionPane.YES_NO_OPTION,
        																	JOptionPane.QUESTION_MESSAGE)){
        				//���� �����Ѵ�.
        				frame.dispose();
            			//�ٽ� �α���â�� �ٿ��.
        				new Login();
        			}
        			else{
        				return;
        			}

  				}
        	}
        ); 
        
        //������ ������ ��ġ ����
        toolBar2.setLayout(new BoxLayout(toolBar2, BoxLayout.Y_AXIS));
        
        Box box = Box.createVerticalBox();
        
        //��ư �����ϱ�
        zoomInButton.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(zoomInButton);
		Component com = Box.createVerticalStrut(10);
		box.add(com);
		zoomOutButton.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(zoomOutButton);
        Component com1 = Box.createVerticalStrut(10);
        box.add(com1);
        button2.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(button2);
        Component com2 = Box.createVerticalStrut(10);
        box.add(com2);
        button3.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(button3);
        Component com3 = Box.createVerticalStrut(10);
        box.add(com3);
        button4.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(button4);
        Component com4 = Box.createVerticalStrut(10);
        box.add(com4);
        button5.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(button5);
        Component com5 = Box.createVerticalStrut(10);
        box.add(com5);
        button6.setAlignmentX((Component.CENTER_ALIGNMENT));
        box.add(button6);
        Component com6 = Box.createVerticalStrut(10);
        box.add(com6);
        button7.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(button7);
        Component com7 = Box.createVerticalStrut(20);
        box.add(com7);
        
        toolBar2.add(box);
        JLabel label3 = new JLabel("CopyRight(C)2016");
        JLabel label4 = new JLabel("All rights reserved by");
        JLabel label5 = new JLabel("����â,����,�̿�ȣ.");
        JLabel label6 = new JLabel();
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label3);
        label4.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label4);
        label5.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label5);
        
        Component com8 = Box.createVerticalStrut(20);
        toolBar2.add(com8);
        
        LoadImageApp Img = new LoadImageApp("�������.png");
        toolBar2.add(Img);
        
        
        ///////////////////////////////////////////////////////////////////////////////
        
		//table�׸���
		JTable table;
		//�⺻ ���̺� ��
		DefaultTableModel model;
		//MyTableModel model; //��������
		
		//��� ��
		Vector<String> userColumn = new Vector<String>();
		Vector<String> userRow = new Vector<String>();
		
		//���̺� ���ļ���
		//DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		//celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		//DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		//celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
    	
		//Column ����
		userColumn.addElement("�����̸�");
		userColumn.addElement("���̸�");
		userColumn.addElement("�ּ�");
		userColumn.addElement("��ȭ��ȣ");
		userColumn.addElement("����");
		userColumn.addElement("���");
		userColumn.addElement("Open/Close");
		userColumn.addElement("�������");
		userColumn.addElement("�����ð�");

		//���̺� ���� �Ұ�
		model = new DefaultTableModel(userColumn, 0){
			public boolean isCellEditable(int row, int col) {
		     return false;
			}
			};
		
		//���̺� �� �Է�
		table = new JTable(model);
		
		//���̺� ���̿� ���ļ���
		table.getColumn("�����̸�").setPreferredWidth(150);
		table.getColumn("���̸�").setPreferredWidth(150);
		table.getColumn("�ּ�").setPreferredWidth(200);
		//table.getColumn("����").setCellRenderer(celAlignRight);
		//table.getColumn("���").setCellRenderer(celAlignRight);
		//table.getColumn("Opne/Close").setCellRenderer(celAlignRight);
		//table.getColumn("�������").setCellRenderer(celAlignRight);
		//table.getColumn("�����ð�").setCellRenderer(celAlignRight);
		
		//panel�� �߰�
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));

        //��ư �˻� �׼�
      	//�˻� ��ư�� ������ �ش� ���� �Ǹ��ϴ� ��ó ������ ������ ǥ���ϰ�
		//���� �������� ���̺� �׸���
		//������ �����鼭 ������ ��ũ�� ǥ���Ѵ�
      		button1.addActionListener(new ActionListener(){
      			public void actionPerformed(ActionEvent e){
      				// SQL Connect
					SQLConnection a = new SQLConnection();
					Connection con = a.makeConnection();
					String BranchName, address, tel,price, stock,open,exptime;
					int bakingtime;
					int BreadCode = 0;

					int i = 0; 
					int j = 0;
					
					try{
					
						//���� �߰� �Ǵ°��� �����ϱ����� ���̺� �ʱ�ȭ
						int rowCount = model.getRowCount();
						for(int k = rowCount - 1; k>=0;k--) {
							model.removeRow(k);
						}
						Vector<Integer> BreadCodeVector = new Vector<Integer>();
						Statement stmt = con.createStatement();
						//���� ���̸��� �����ϴ� ���
						ResultSet rs = stmt.executeQuery("SELECT * FROM bread where BreadName like '%" +Text1.getText()+ "%'");
						if(rs.next()) {do{ // �˻��� ���� �ڵ���� Vector�� ����
							BreadCode = rs.getInt(1);
							BreadCodeVector.addElement(BreadCode);
							}while(rs.next());	
						
						for(int l = 0; l <BreadCodeVector.size();l++){
							
							ResultSet rs2 = stmt.executeQuery("SELECT distinct * FROM branch_info where BreadCode ='" + BreadCodeVector.elementAt(l) + "'and date(ExpireTime)>= date(now()) ORDER BY price ASC");
							//������ rs ��� //ResultSet rs2 = stmt.executeQuery("SELECT distinct * FROM branch_info where BreadCode ='" + BreadCode + "' and RegionCode ='"+regioncode+"'");
							// �����̸� ��ġ �Ÿ� open/close ���					
							if(rs2.next()) {
								do {//data�� db���� �����ͼ� table�� ����
									// BranchName, address, tel, position,open in Branch
									
									BranchName = rs2.getString(2);
									BreadCode = rs2.getInt(3);
									stock = rs2.getString(4);
									price = rs2.getString(7);
									exptime= rs2.getString(6);
									bakingtime = rs2.getInt(5);
									//�����̸� ���̸� �ּ� ��ȭ��ȣ ���� ��� ���� ������� �����ð�
				    				model.addRow(new Object[]{BranchName,BreadCode," "," ",price + "��",stock+"��"," ",exptime,bakingtime+"��"});
				    				j++; // �����ڵ带 ���ڷ� �ٲٱ����� ����
				    			
				    				
							} while(rs2.next());
							rs2.close(); // DB�浹�� ���ֱ� ���� close
							
								for( i = 0;i<j;i++ ) {
									ResultSet rs3 = stmt.executeQuery("Select * FROM branch where branchCode = '" + table.getValueAt(i, 0)+"'");
									//�����̸� ���̸� �ּ� ��ȭ��ȣ ���� ��� ���� ������� �����ð�
									if(rs3.next()) {
									 table.setValueAt(rs3.getString(2),i,0); // �����ڵ带 �����̸����� ��ȯ
									 table.setValueAt(rs3.getString(6), i,2); // �ּ�
									 table.setValueAt(rs3.getString(3), i,3); // ��ȭ��ȣ
									 table.setValueAt(rs3.getString(4) + "~" + rs3.getString(5), i, 6); //Open ~ Close
									 }
									//rs3�� 1���� ResultSet�� ���Ƿ� else������ ����ó�� �� �ʿ����.
								}
								//rs3.close(); // DB�浹�� ���ֱ� ���� close, but for������ �ڵ����� ������Ƿ� �ʿ����.	
								for(i = 0; i<j;i++) {
								ResultSet rs4 = stmt.executeQuery("SELECT * FROM bread where BreadCode = '" +table.getValueAt(i,1)+ "'");
								if(rs4.next()) {
									table.setValueAt(rs4.getString(2),i,1);  // ���ڵ带 ���̸����� ��ȯ 
									}
								}

							} // if�� ��
							 
							else {} // DB��ȯ�� �ʿ���� ����̹Ƿ� �ڵ��ۼ��� ��������.
							
							} // for�� ��
							
							//�˻��� �������� ���� string����
							String location;
							Float[] coords;
							int k;
							int maxColumn = j;
							
							for(k = 0; k < maxColumn; k++){
								
							 //���̺��� ��ġ�� �����ɴϴ�.
			                  location = table.getValueAt(k, 2).toString();
			                  
			                  //��ġ�� geocoding ��ü�� �Ѱ��ݴϴ�.
			                  coords = CommonUtil.geoCoding(location);
			                  
			                   //Ȯ�ο� �ܼ�ǥ��
			                   //System.out.println(location + ": " + coords[0] + ", " + coords[1]);
			                   //������ ���
			                   browser.executeJavaScript("drawMarker("+coords[0]+","+coords[1]+");");
							}		
							
						} // ���̸��� ������ ���
						else {JOptionPane.showMessageDialog(null, "�����ϴ� �����Ͱ� �����ϴ�.", "������ ���� ����", JOptionPane.PLAIN_MESSAGE);} //���̸��� �������� �ʴ� ���
						//������ ���� �˻����� ���� ��� : ���������� ������ �� �ִ� â�� �����͸� ��������
						
					} catch (Exception ex) {
						ex.printStackTrace();
					} 
			}
      		}); //button1 end
      		
      		
      		//��ٱ��� �����ϴ� �迭
      		String CartRow[] = new String[5];
      		//īƮ ��ü �����
      		Cart cart = new Cart(LoginId);
      		
      		//button2 ��ٱ��� �ֱ�
            button2.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
                
                //�����̸� ���̸� ���� ��� �������
                String Count; String Stock;
                
                //���̺��� ��� �����´�
                Stock = table.getValueAt(row, 5).toString();
                //"��" ����
                Stock = Stock.substring(0, Stock.length()-1);
                
                //��� ���°��
                if(Integer.parseInt(Stock) == 0)
                {
                   JOptionPane.showMessageDialog(null,"��� �����ϴ�.");
                   return;
                }
                
                //���� ���� �����
                Count = JOptionPane.showInputDialog("��� �����Ͻðڽ��ϱ�?");
                
                //���ڰ� �ƴѰ�� ����
                try{
                	Integer.parseInt(Count);
                }catch(NumberFormatException Ne){
                	JOptionPane.showMessageDialog(null,"���ڸ� �Է��ϼ���");
                	return;
                }
                
                //���� �Էµ� ���� ���մϴ�.
                if(Integer.parseInt(Count) > Integer.parseInt(Stock))
                {
                   JOptionPane.showMessageDialog(null,"��� �����մϴ�.");
                   return;
                }
                
                //��ٱ��Ϸ� ������ �迭�� ������ ���� // �����̸�/���̸�/����/����/�������
                CartRow[0] = table.getValueAt(row, 0).toString();
                CartRow[1] = table.getValueAt(row, 1).toString();
                CartRow[2] = table.getValueAt(row, 4).toString();
                CartRow[3] = Count;
                CartRow[4] = table.getValueAt(row, 7).toString();
                
                //īƮ ��ü�� �迭�� ����
                cart.addCart(CartRow);
                
                //�迭 �ʱ�ȭ
                for(int i = 0; i < 5; i++)
                {
                    CartRow[i] = null;
                }
                
                //Ȯ�ι���ǥ��
                JOptionPane.showMessageDialog(null, "��ٱ��Ͽ� �߰��Ǿ����ϴ�.", "��ٱ��� �߰�", JOptionPane.PLAIN_MESSAGE);
             }   
          });//button2 end
           
            
          //button3 ��ٱ��� ����
            button3.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				//īƮ��ü �����ֱ�
    				 cart.CreateCart();
    			}	
    		});//button3 end
            
            
    		//table�� ���� Ŭ�������� �̺�Ʈ �߻� 
    		table.addMouseListener(new MouseAdapter() {
    			  public void mouseClicked(MouseEvent e) {
    				  
    				  //���̺��� ���� �ѹ� Ŭ���ϴ� ���
    	                 if (e.getClickCount() == 1) {
    	                    //���� �̺�Ʈ�� �߻��� ���̺��� ������ ������
    	                   JTable table = (JTable)e.getSource();
    	                   
    	                   //���� �������� ����
    	                   row = table.getSelectedRow();
    	                   
    	                   //int column = table.getSelectedColumn();
    	                   
    	                   //��ġ�� ������ ����
    	                   String location;
    	                   //���̺��� ���õ� ���� ��ġ�� ������
    	                   location = table.getValueAt(row, 2).toString();
    	                   
    	                   //��ġ�� geocoding ��ü�� �Ѱ��ݴϴ�.
    	                   Float[] coords = CommonUtil.geoCoding(location);
    	                   //Ȯ�ο� �ܼ�ǥ��
    	                   //System.out.println(location + ": " + coords[0] + ", " + coords[1]);
    	                   
    	                   //������ ���
    	                   browser.executeJavaScript("drawMap("+Lat+","+Lng+","+coords[0]+","+coords[1]+");");
    	                 }
    			  }
    			});
 
    	////////////////////////////////////////////////////////////////////////////////////////////////////
    		
    	//������ �����
    	frame = new JFrame();
    	
        //ó�� ������ �ε� �ɶ� ������� ��ġ�� ���޹޾Ƽ� 
        //�ش� ��ġ�� ������ ǥ���Ѵ�
        frame.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
	            //���޹��� �ʱ� ����� ��ġ
	            //String Address �� ����ڰ� ȸ�������Ҷ� ������ ��ġ
	            Float[] coords = CommonUtil.geoCoding(Address);
	            
	            //test�� �ܼ�
	            //System.out.println(Address + ": " + coords[0] + ", " + coords[1]);
	            
	            //������� ��ġ�� ���� �ʱ�ȭ
	            browser.executeJavaScript("initMap3("+coords[0]+","+coords[1]+");");
	            
	            //��ã�⸦ ���ؼ� ���������� ����
	            Lat = coords[0];
	            Lng = coords[1];
			}
        });
        
        //���� ������
        Lframe = new JInternalFrame();
        //������ ������        
        Rframe = new JInternalFrame();
        
        //�����ӿ� Ʋ�� ������
        BasicInternalFrameTitlePane titlePane1 =(BasicInternalFrameTitlePane)((BasicInternalFrameUI)Lframe.getUI()).getNorthPane();
        Lframe.remove(titlePane1);
        BasicInternalFrameTitlePane titlePane2 =(BasicInternalFrameTitlePane)((BasicInternalFrameUI)Rframe.getUI()).getNorthPane();
        Rframe.remove(titlePane2);
        
        //�˻�â
        Lframe.add(toolBar, BorderLayout.NORTH);
        //������
        Lframe.add(view, BorderLayout.CENTER);
        //ǥ
        Lframe.add(scrollPane, BorderLayout.SOUTH);
        
        //���â
        Rframe.add(toolBar2);
        
        //internalfram �����ϱ�
        //Lframe.setSize(900, 700);
        Lframe.setVisible(true);
        Lframe.setBorder(null);
        //Rframe.setSize(100, 700);
        Rframe.pack();
        Rframe.setBorder(null);
        
        //���������ӿ� �߰�
        frame.add(Lframe, BorderLayout.CENTER);
        frame.add(Rframe, BorderLayout.EAST);
        
        //���������� ����
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setTitle("ȣ���� v1.0.0.1");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //�������� ǥ���� �������� ��ΰ� �߿�!!!!
        //ȣ������ �� ������
        browser.loadURL("http://kurokina.mireene.kr/index.html");
        //browser.loadURL("C:\\Users\\������\\Desktop\\Junior_design_project\\marker.html");

}// Form1�� ��

	// ����
	public static void main(String[] args) {

		// �׽�Ʈ
		//new Cake("dldydgh");
		//new Form1("rnjsgmlckd1");
		// new SearchResult();
		// new Cake();
		 new Login();
		// new Cart("����â");
	}
}
