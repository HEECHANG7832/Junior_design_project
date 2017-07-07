import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//파일이름 받아서 이미지 그려주는 클래스
public class LoadImageApp extends JPanel 
{
	BufferedImage img;

	public void paint(Graphics g) 
	{
		g.drawImage(img, 0, 0, null);
	}

	// 생성자로 이미지를 입힌다
	public LoadImageApp(String imgname) 
	{
		try 
		{
			img = ImageIO.read(new File(imgname));
		} 
		catch (IOException e) 
		{
			System.out.println("이미지 파일이 없습니다.");
		}
	}

	// 컴포넌트의 크기를 이미지의 크기에 맞춘다
	public Dimension getPreferredSize() 
	{
		if (img == null) 
		{
			return new Dimension(100, 100);
		} 
		else 
		{
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}
}
