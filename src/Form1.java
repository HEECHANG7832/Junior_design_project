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

	// 프레임 변수
	JFrame frame;

	// 왼쪽 프레임
	JInternalFrame Lframe;
	// 오른쪽 프레임
	JInternalFrame Rframe;
	// 프레임 열고 닫기
	boolean frameOpen = false;

	// 폼이 열릴 때 사용자로그인정보에서 가져온다
	// 사용자의 주소
	String Address = null;
	// 사용자의 위도 경도
	Float Lat;
	Float Lng;

	// 사용자의 아이디
	String LoginId;

	// 사용자 지역코드
	int regioncode = 0;

	// 클릭된 테이블의 줄 정보가 저장되는 변수
	int row;

	// 줌 기능 제한
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;

	// 기본 줌 상태
	private static int zoomValue = 17;

	// geocoding 을 통한 지도에 마커 표시하기
	GeoCoding CommonUtil = new GeoCoding();

	public Form1(String LoginId_) { // 임시로 Parameter 삽입
		//브라우저 객체 생성
		
		LoginId = LoginId_; // LoginID 삽입
		
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
						else {JOptionPane.showMessageDialog(null, "존재하는 데이터가 없습니다.", "데이터 존재 에러", JOptionPane.PLAIN_MESSAGE);}
						
						rs = stmt.executeQuery("SELECT * FROM region WHERE regioncode ='" +regioncode + "'");
						if(rs.next()) {
							Address = rs.getString(2) +" " + rs.getString(3) + " " + Address;
						}
						else {JOptionPane.showMessageDialog(null, "존재하는 데이터가 없습니다.", "데이터 존재 에러", JOptionPane.PLAIN_MESSAGE);}
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
		//Jxbrowser 객체 생성 웹패이지를 가져와서 script언어를 실행시킬수 있다.
		final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        
        //줌in버튼 줌out버튼
        JButton zoomInButton = new JButton("　　줌 인　　");
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                	//1. Using the Browser.executeJavaScript(String javaScript) method. This method executes the JavaScript code asynchronously without blocking the current thread execution. If you are not interested in receiving the result of JavaScript code, you can use this method because its return parameter is void.
                	browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });
        JButton zoomOutButton = new JButton(" 　 줌 아웃 　");
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                	browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });
        
        //상단 툴바
        JPanel toolBar = new JPanel();
        
        //toolBar에 추가
      	toolBar.add(new LoadImageApp("아이콘.png"));
        JLabel label1 = new JLabel("검색어 입력");
        Font f = new Font(null, Font.BOLD, 17);
        label1.setFont(f);
		toolBar.add(label1);
		JTextField Text1 = new JTextField(15); // 검색창
		toolBar.add(Text1);
		JButton button1 = new JButton("검색");
		toolBar.add(button1);
		//null님 반갑습니다. 로그인 하면 정보 전달받음
        JLabel label2 = new JLabel("   "+LoginId + "님 반갑습니다.    ");
        label2.setFont(f);
		toolBar.add(label2);
		
		//이미지로 바꾸기
		JButton button8 = new JButton("기능 모음");
        toolBar.add(button8);
        //기능모음 프레임 보여주기
        button8.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			//오른쪽에 프레임을 보여주게한다
        			if(frameOpen == false)
        			{
        				//프레임 사이즈 늘리고 Rframe보여준다
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
        
		
		//오른쪽 툴바
		JPanel toolBar2 = new JPanel();		
        
		
        //장바구니 넣기
        JButton button2 = new JButton("장바구니 넣기");
        
        //장바구니 보기
        JButton button3 = new JButton("장바구니 보기");
        
        //구매내역
        JButton button4 = new JButton("  케이크 주문  ");
        
        //구매내역 액션
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
        
        JButton button5 = new JButton("　구매 내역　");
        //케이크 주문 액션
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
        
        JButton button6 = new JButton("회원 정보 수정");
        //회원 정보 수정 액션
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
         
        JButton button7 = new JButton("　로그  아웃　");
        //회원탈퇴 액션
        button7.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent e)
        		{
        			//예버튼을 누르면
        			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, 
        																	"로그아웃 하시겠습니까?",
        																	"" ,
        																	JOptionPane.YES_NO_OPTION,
        																	JOptionPane.QUESTION_MESSAGE)){
        				//폼을 종료한다.
        				frame.dispose();
            			//다시 로그인창을 뛰운다.
        				new Login();
        			}
        			else{
        				return;
        			}

  				}
        	}
        ); 
        
        //오른쪽 프레임 배치 설정
        toolBar2.setLayout(new BoxLayout(toolBar2, BoxLayout.Y_AXIS));
        
        Box box = Box.createVerticalBox();
        
        //버튼 정렬하기
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
        JLabel label5 = new JLabel("권희창,강건,이용호.");
        JLabel label6 = new JLabel();
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label3);
        label4.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label4);
        label5.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolBar2.add(label5);
        
        Component com8 = Box.createVerticalStrut(20);
        toolBar2.add(com8);
        
        LoadImageApp Img = new LoadImageApp("잼아저씨.png");
        toolBar2.add(Img);
        
        
        ///////////////////////////////////////////////////////////////////////////////
        
		//table그리기
		JTable table;
		//기본 테이블 모델
		DefaultTableModel model;
		//MyTableModel model; //이전버전
		
		//행과 열
		Vector<String> userColumn = new Vector<String>();
		Vector<String> userRow = new Vector<String>();
		
		//테이블 정렬설정
		//DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		//celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		//DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		//celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
    	
		//Column 설정
		userColumn.addElement("지점이름");
		userColumn.addElement("빵이름");
		userColumn.addElement("주소");
		userColumn.addElement("전화번호");
		userColumn.addElement("가격");
		userColumn.addElement("재고");
		userColumn.addElement("Open/Close");
		userColumn.addElement("유통기한");
		userColumn.addElement("제조시간");

		//테이블 수정 불가
		model = new DefaultTableModel(userColumn, 0){
			public boolean isCellEditable(int row, int col) {
		     return false;
			}
			};
		
		//테이블에 모델 입력
		table = new JTable(model);
		
		//테이블 넓이와 정렬설정
		table.getColumn("지점이름").setPreferredWidth(150);
		table.getColumn("빵이름").setPreferredWidth(150);
		table.getColumn("주소").setPreferredWidth(200);
		//table.getColumn("가격").setCellRenderer(celAlignRight);
		//table.getColumn("재고").setCellRenderer(celAlignRight);
		//table.getColumn("Opne/Close").setCellRenderer(celAlignRight);
		//table.getColumn("유통기한").setCellRenderer(celAlignRight);
		//table.getColumn("제조시간").setCellRenderer(celAlignRight);
		
		//panel에 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));

        //버튼 검색 액션
      	//검색 버튼을 누르면 해당 빵을 판매하는 근처 지점을 지도에 표시하고
		//지점 정보들을 테이블에 그린다
		//데이터 받으면서 지도에 마크를 표시한다
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
					
						//새로 추가 되는것을 방지하기위한 테이블 초기화
						int rowCount = model.getRowCount();
						for(int k = rowCount - 1; k>=0;k--) {
							model.removeRow(k);
						}
						Vector<Integer> BreadCodeVector = new Vector<Integer>();
						Statement stmt = con.createStatement();
						//유사 빵이름이 존재하는 경우
						ResultSet rs = stmt.executeQuery("SELECT * FROM bread where BreadName like '%" +Text1.getText()+ "%'");
						if(rs.next()) {do{ // 검색된 빵의 코드들을 Vector에 저장
							BreadCode = rs.getInt(1);
							BreadCodeVector.addElement(BreadCode);
							}while(rs.next());	
						
						for(int l = 0; l <BreadCodeVector.size();l++){
							
							ResultSet rs2 = stmt.executeQuery("SELECT distinct * FROM branch_info where BreadCode ='" + BreadCodeVector.elementAt(l) + "'and date(ExpireTime)>= date(now()) ORDER BY price ASC");
							//지역별 rs 고려 //ResultSet rs2 = stmt.executeQuery("SELECT distinct * FROM branch_info where BreadCode ='" + BreadCode + "' and RegionCode ='"+regioncode+"'");
							// 지점이름 위치 거리 open/close 비고					
							if(rs2.next()) {
								do {//data를 db에서 가져와서 table에 삽입
									// BranchName, address, tel, position,open in Branch
									
									BranchName = rs2.getString(2);
									BreadCode = rs2.getInt(3);
									stock = rs2.getString(4);
									price = rs2.getString(7);
									exptime= rs2.getString(6);
									bakingtime = rs2.getInt(5);
									//지점이름 빵이름 주소 전화번호 가격 재고 오픈 유통기한 제조시간
				    				model.addRow(new Object[]{BranchName,BreadCode," "," ",price + "원",stock+"개"," ",exptime,bakingtime+"분"});
				    				j++; // 숫자코드를 문자로 바꾸기위한 변수
				    			
				    				
							} while(rs2.next());
							rs2.close(); // DB충돌을 없애기 위한 close
							
								for( i = 0;i<j;i++ ) {
									ResultSet rs3 = stmt.executeQuery("Select * FROM branch where branchCode = '" + table.getValueAt(i, 0)+"'");
									//지점이름 빵이름 주소 전화번호 가격 재고 오픈 유통기한 제조시간
									if(rs3.next()) {
									 table.setValueAt(rs3.getString(2),i,0); // 지점코드를 지점이름으로 변환
									 table.setValueAt(rs3.getString(6), i,2); // 주소
									 table.setValueAt(rs3.getString(3), i,3); // 전화번호
									 table.setValueAt(rs3.getString(4) + "~" + rs3.getString(5), i, 6); //Open ~ Close
									 }
									//rs3에 1개의 ResultSet이 들어가므로 else문으로 예외처리 할 필요없음.
								}
								//rs3.close(); // DB충돌을 없애기 위한 close, but for문에서 자동으로 사라지므로 필요없음.	
								for(i = 0; i<j;i++) {
								ResultSet rs4 = stmt.executeQuery("SELECT * FROM bread where BreadCode = '" +table.getValueAt(i,1)+ "'");
								if(rs4.next()) {
									table.setValueAt(rs4.getString(2),i,1);  // 빵코드를 빵이름으로 변환 
									}
								}

							} // if문 끝
							 
							else {} // DB변환이 필요없는 경우이므로 코드작성을 하지않음.
							
							} // for문 끝
							
							//검색된 지점명을 받을 string변수
							String location;
							Float[] coords;
							int k;
							int maxColumn = j;
							
							for(k = 0; k < maxColumn; k++){
								
							 //테이블에서 위치를 가져옵니다.
			                  location = table.getValueAt(k, 2).toString();
			                  
			                  //위치를 geocoding 객체에 넘겨줍니다.
			                  coords = CommonUtil.geoCoding(location);
			                  
			                   //확인용 콘솔표시
			                   //System.out.println(location + ": " + coords[0] + ", " + coords[1]);
			                   //지도에 출력
			                   browser.executeJavaScript("drawMarker("+coords[0]+","+coords[1]+");");
							}		
							
						} // 빵이름이 존재할 경우
						else {JOptionPane.showMessageDialog(null, "존재하는 데이터가 없습니다.", "데이터 존재 에러", JOptionPane.PLAIN_MESSAGE);} //빵이름이 존재하지 않는 경우
						//지역내 빵이 검색되지 않은 경우 : 지역정보를 선택할 수 있는 창을 데이터를 가져오기
						
					} catch (Exception ex) {
						ex.printStackTrace();
					} 
			}
      		}); //button1 end
      		
      		
      		//장바구니 저장하는 배열
      		String CartRow[] = new String[5];
      		//카트 객체 만들기
      		Cart cart = new Cart(LoginId);
      		
      		//button2 장바구니 넣기
            button2.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
                
                //지점이름 빵이름 가격 재고 유통기한
                String Count; String Stock;
                
                //테이블에서 재고를 가져온다
                Stock = table.getValueAt(row, 5).toString();
                //"개" 제거
                Stock = Stock.substring(0, Stock.length()-1);
                
                //재고가 없는경우
                if(Integer.parseInt(Stock) == 0)
                {
                   JOptionPane.showMessageDialog(null,"재고가 없습니다.");
                   return;
                }
                
                //구매 수량 물어보기
                Count = JOptionPane.showInputDialog("몇개를 구매하시겠습니까?");
                
                //숫자가 아닌경우 종료
                try{
                	Integer.parseInt(Count);
                }catch(NumberFormatException Ne){
                	JOptionPane.showMessageDialog(null,"숫자만 입력하세요");
                	return;
                }
                
                //재고와 입력된 값을 비교합니다.
                if(Integer.parseInt(Count) > Integer.parseInt(Stock))
                {
                   JOptionPane.showMessageDialog(null,"재고가 부족합니다.");
                   return;
                }
                
                //장바구니로 전달할 배열에 데이터 저장 // 지점이름/빵이름/가격/수량/유통기한
                CartRow[0] = table.getValueAt(row, 0).toString();
                CartRow[1] = table.getValueAt(row, 1).toString();
                CartRow[2] = table.getValueAt(row, 4).toString();
                CartRow[3] = Count;
                CartRow[4] = table.getValueAt(row, 7).toString();
                
                //카트 객체로 배열을 전달
                cart.addCart(CartRow);
                
                //배열 초기화
                for(int i = 0; i < 5; i++)
                {
                    CartRow[i] = null;
                }
                
                //확인문구표시
                JOptionPane.showMessageDialog(null, "장바구니에 추가되었습니다.", "장바구니 추가", JOptionPane.PLAIN_MESSAGE);
             }   
          });//button2 end
           
            
          //button3 장바구니 보기
            button3.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				//카트객체 보여주기
    				 cart.CreateCart();
    			}	
    		});//button3 end
            
            
    		//table의 행을 클릭했을때 이벤트 발생 
    		table.addMouseListener(new MouseAdapter() {
    			  public void mouseClicked(MouseEvent e) {
    				  
    				  //태이블의 행을 한번 클릭하는 경우
    	                 if (e.getClickCount() == 1) {
    	                    //현재 이벤트가 발생한 테이블의 정보를 가져옴
    	                   JTable table = (JTable)e.getSource();
    	                   
    	                   //현재 행정보를 저장
    	                   row = table.getSelectedRow();
    	                   
    	                   //int column = table.getSelectedColumn();
    	                   
    	                   //위치를 저장할 변수
    	                   String location;
    	                   //테이블에서 선택된 지점 위치를 가져옴
    	                   location = table.getValueAt(row, 2).toString();
    	                   
    	                   //위치를 geocoding 객체에 넘겨줍니다.
    	                   Float[] coords = CommonUtil.geoCoding(location);
    	                   //확인용 콘솔표시
    	                   //System.out.println(location + ": " + coords[0] + ", " + coords[1]);
    	                   
    	                   //지도에 출력
    	                   browser.executeJavaScript("drawMap("+Lat+","+Lng+","+coords[0]+","+coords[1]+");");
    	                 }
    			  }
    			});
 
    	////////////////////////////////////////////////////////////////////////////////////////////////////
    		
    	//프레임 만들기
    	frame = new JFrame();
    	
        //처음 지도가 로드 될때 사용자의 위치를 전달받아서 
        //해당 위치에 지도를 표시한다
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
				
	            //전달받은 초기 사용자 위치
	            //String Address 는 사용자가 회원가입할때 기입한 위치
	            Float[] coords = CommonUtil.geoCoding(Address);
	            
	            //test용 콘솔
	            //System.out.println(Address + ": " + coords[0] + ", " + coords[1]);
	            
	            //사용자의 위치로 지도 초기화
	            browser.executeJavaScript("initMap3("+coords[0]+","+coords[1]+");");
	            
	            //길찾기를 위해서 전역변수에 저장
	            Lat = coords[0];
	            Lng = coords[1];
			}
        });
        
        //왼쪽 프레임
        Lframe = new JInternalFrame();
        //오른쪽 프레임        
        Rframe = new JInternalFrame();
        
        //프레임에 틀을 없에기
        BasicInternalFrameTitlePane titlePane1 =(BasicInternalFrameTitlePane)((BasicInternalFrameUI)Lframe.getUI()).getNorthPane();
        Lframe.remove(titlePane1);
        BasicInternalFrameTitlePane titlePane2 =(BasicInternalFrameTitlePane)((BasicInternalFrameUI)Rframe.getUI()).getNorthPane();
        Rframe.remove(titlePane2);
        
        //검색창
        Lframe.add(toolBar, BorderLayout.NORTH);
        //브라우저
        Lframe.add(view, BorderLayout.CENTER);
        //표
        Lframe.add(scrollPane, BorderLayout.SOUTH);
        
        //기능창
        Rframe.add(toolBar2);
        
        //internalfram 설정하기
        //Lframe.setSize(900, 700);
        Lframe.setVisible(true);
        Lframe.setBorder(null);
        //Rframe.setSize(100, 700);
        Rframe.pack();
        Rframe.setBorder(null);
        
        //최종프레임에 추가
        frame.add(Lframe, BorderLayout.CENTER);
        frame.add(Rframe, BorderLayout.EAST);
        
        //최종프레임 설정
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setTitle("호빵맨 v1.0.0.1");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //브라우저에 표시할 웹페이지 경로가 중요!!!!
        //호스팅한 웹 브라우져
        browser.loadURL("http://kurokina.mireene.kr/index.html");
        //browser.loadURL("C:\\Users\\별파편\\Desktop\\Junior_design_project\\marker.html");

}// Form1의 끝

	// 메인
	public static void main(String[] args) {

		// 테스트
		//new Cake("dldydgh");
		//new Form1("rnjsgmlckd1");
		// new SearchResult();
		// new Cake();
		 new Login();
		// new Cart("권희창");
	}
}
