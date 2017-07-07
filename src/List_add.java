import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class List_add {
	JFrame frame;
	JPanel panel1;
	JTextField text1, text2, text3, text4;
	JLabel label1, label2, label3, label4, label5, label6, label_y, label_m, label_d, labelh, labelm;
	JButton button1,button2;

	public List_add(String branchname_) {
		frame = new JFrame();

		frame.setTitle(branchname_);

		panel1 = new JPanel();
		panel1.setVisible(true);
		panel1.setSize(400, 800);
		panel1.setLayout(null);

		label6 = new JLabel("현황  추가");
		label6.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label6.setBounds(120, 20, 150, 40);
		panel1.add(label6);

		label1 = new JLabel("빵의 종류");
		label1.setBounds(50, 80, 80, 40);
		// label1.setFont(new Font("SansSerif", Font.PLAIN, 30));
		label1.setVisible(true);
		panel1.add(label1);

		text1 = new JTextField();
		text1.setBounds(140, 85, 120, 30);
		panel1.add(text1);

		label2 = new JLabel("가 격");
		label2.setBounds(50, 120, 80, 40);
		label2.setVisible(true);
		panel1.add(label2);

		text2 = new JTextField();
		text2.setBounds(140, 125, 120, 30);
		panel1.add(text2);

		label3 = new JLabel("재 고");
		label3.setBounds(50, 160, 80, 40);
		label3.setVisible(true);
		panel1.add(label3);

		text3 = new JTextField();
		text3.setBounds(140, 165, 120, 30);
		panel1.add(text3);

		label4 = new JLabel("유통기한");
		label4.setBounds(50, 200, 80, 40);
		label4.setVisible(true);
		panel1.add(label4);

		// 유통기한
		JSpinner openy_sp = new JSpinner();
		JSpinner openm_sp = new JSpinner();
		JSpinner opend_sp = new JSpinner();

		SpinnerNumberModel numModel1 = new SpinnerNumberModel(2016, 0, 2020, 1);
		SpinnerNumberModel numModel2 = new SpinnerNumberModel(11, 0, 12, 1);
		SpinnerNumberModel numModel3 = new SpinnerNumberModel(24, 0, 31, 1);

		openy_sp.setBounds(140, 210, 60, 20);
		openm_sp.setBounds(220, 210, 40, 20);
		opend_sp.setBounds(280, 210, 40, 20);

		openy_sp.setModel(numModel1);
		openm_sp.setModel(numModel2);
		opend_sp.setModel(numModel3);

		panel1.add(openy_sp);
		panel1.add(openm_sp);
		panel1.add(opend_sp);

		label_y = new JLabel("년"); // Open Hour
		label_y.setBounds(205, 210, 15, 20);
		panel1.add(label_y);
		label_m = new JLabel("월"); // Open Hour
		label_m.setBounds(265, 210, 15, 20);
		panel1.add(label_m);
		label_d = new JLabel("일"); // Open Minute
		label_d.setBounds(325, 210, 15, 20);
		panel1.add(label_d);

		label5 = new JLabel("제조 시간");
		label5.setBounds(50, 240, 80, 40);
		label5.setVisible(true);
		panel1.add(label5);

		// 제조 시간
		JSpinner openm = new JSpinner();

		SpinnerNumberModel numModel4 = new SpinnerNumberModel(0, 0, 100, 1);

		openm.setBounds(140, 250, 40, 20);

		openm.setModel(numModel4);

		panel1.add(openm);

		labelm = new JLabel("분");
		labelm.setBounds(185, 250, 15, 20);
		panel1.add(labelm);

		button1 = new JButton("추 가");
		button1.setBounds(90, 310, 80, 40);
		panel1.add(button1);
		// button1 ActionListener : 추가
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQL Connect
				SQLConnection a = new SQLConnection();
				Connection con = a.makeConnection();
				try {
					// text1 : 빵 종류, text2 : 가격, text3 : 재고, openy_sp, openm_sp,
					// opend_sp, openm
					if (text1.getText().equals("") || text2.getText().equals("") || text3.getText().equals("")
							|| openy_sp.getValue().toString().equals("") || openm_sp.getValue().toString().equals("")
							|| opend_sp.getValue().toString().equals("") || openm.getValue().toString().equals("")) { // 빈 텍스트박스 존재 할 때
						JOptionPane.showMessageDialog(null, "빈 정보가 존재합니다.", "리스트 수정 에러", JOptionPane.PLAIN_MESSAGE);
					} else { // 모든 조건을 만족 시킬 때
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT MAX(breadcode) FROM bread");
						rs.next();
						int breadCode_last = rs.getInt(1) + 1; // 빵종류 table 맨 마지막 index
						rs = stmt.executeQuery("SELECT * FROM bread WHERE breadname ='" + text1.getText() + "'");
						if (!rs.next()) { // 빵종류에 해당하는 빵이 존재하지 않는 경우
							String sql = "insert into bread values('" + breadCode_last + "','" + text1.getText() + "')";
							stmt.executeUpdate(sql); // select 문을 제외한 나머지를 실행할때 사용하는 메소드
						}
						rs = stmt.executeQuery("SELECT * FROM bread WHERE breadname ='" + text1.getText() + "'");
						rs.next();
						int breadCode = rs.getInt(1); // 빵 코드
						
						rs = stmt.executeQuery("SELECT * FROM User WHERE name ='" + branchname_ + "'");
						rs.next();
						int RegionCode = rs.getInt(6);
						int BranchCode = rs.getInt(7);
						
						String sql = "insert into branch_info values ('"+RegionCode+"','"+BranchCode+"','"+breadCode+"','"+text3.getText()+"','"+Integer.parseInt(openm.getValue().toString())+"','"+
								openy_sp.getValue().toString()+"-"+openm_sp.getValue().toString()+"-"+opend_sp.getValue().toString()+"','" +text2.getText()+"','N')";
						stmt.executeUpdate(sql); // select 문을 제외한 나머지를 실행할때 사용하는 메소드
						JOptionPane.showMessageDialog(null, "데이터가 추가되었습니다.", "추가 완료", JOptionPane.PLAIN_MESSAGE);
						frame.setVisible(false);
						frame.dispose();
						new Sell_Form(branchname_);
					}

				} catch (Exception ex) {
					ex.printStackTrace();

				}

			}
		});
		
		
		button2 = new JButton("취 소");
		button2.setBounds(190, 310, 80, 40);
		panel1.add(button2);
		
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
				new Sell_Form(branchname_);
			}
		});

		frame.add(panel1);

		frame.setVisible(true);
		frame.setBounds(840, 300, 380, 430);

		// frame.add();
	}
}
