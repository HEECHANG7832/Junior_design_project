import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//�����̸� �޾Ƽ� �̹��� �׷��ִ� Ŭ����
public class LoadImageApp extends JPanel 
{
	BufferedImage img;

	public void paint(Graphics g) 
	{
		g.drawImage(img, 0, 0, null);
	}

	// �����ڷ� �̹����� ������
	public LoadImageApp(String imgname) 
	{
		try 
		{
			img = ImageIO.read(new File(imgname));
		} 
		catch (IOException e) 
		{
			System.out.println("�̹��� ������ �����ϴ�.");
		}
	}

	// ������Ʈ�� ũ�⸦ �̹����� ũ�⿡ �����
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
