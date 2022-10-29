package M4.Ahorcado;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LectorImagenes {
	
	public void algo()
	{
		try {
			BufferedImage myPicture = ImageIO.read(new File("imagenes\\pug.jpg"));
			ImageIcon ii = new ImageIcon("imagenes\\pug.jpg");
			Image i = ii.getImage();
			Image newI = i.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
			ii = new ImageIcon(newI);
			JLabel picLabel = new JLabel(ii);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
