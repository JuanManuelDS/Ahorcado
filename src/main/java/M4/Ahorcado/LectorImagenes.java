package M4.Ahorcado;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LectorImagenes {

	private BufferedImage imagenAhorcado;
	private BufferedImage imagenVidas;

	public JLabel getImagenAhorcado(int index) {

		JLabel imageLabel = null;

		try {
			imagenAhorcado = ImageIO.read(new File("imagenes\\imagen" + index + ".jpg"));
			ImageIcon imagenIcon = new ImageIcon("imagenes\\imagen" + index + ".jpg");
			Image aux = imagenIcon.getImage();
			Image aux2 = aux.getScaledInstance(274, 399, java.awt.Image.SCALE_SMOOTH);
			imagenIcon = new ImageIcon(aux2);
			imageLabel = new JLabel(imagenIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageLabel;
	}

	public JLabel getImagenVida() {

		JLabel imageLabel = null;

		try {
			imagenAhorcado = ImageIO.read(new File("imagenes\\vidas.png"));
			ImageIcon imagenIcon = new ImageIcon("imagenes\\vidas.png");
			Image aux = imagenIcon.getImage();
			Image aux2 = aux.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			imagenIcon = new ImageIcon(aux2);
			imageLabel = new JLabel(imagenIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageLabel;
	}

}
