package M4.Ahorcado;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CreadorComponentes{

	public JButton[] getTeclado() {
		String alfa = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		JButton[] arrayTeclado = new JButton[27];
		for (int i = 0; i < arrayTeclado.length; i++) {
			arrayTeclado[i] = new JButton(String.valueOf(alfa.charAt(i)));
			arrayTeclado[i].setEnabled(false);
			
		}
		return arrayTeclado;
	}
	
	public JLabel[] getImagenesAhorcado() {
		LectorImagenes li = new LectorImagenes();
		JLabel[] image_labels = new JLabel[7];
		for (int i = 0; i < image_labels.length; i++) {
			image_labels[i] = li.getImagenAhorcado(i + 1);
			image_labels[i].setBounds(0, 0, 274, 399);
		}
		return image_labels;
	}
	
	public JLabel[] getImagenesVidas() {
		LectorImagenes li = new LectorImagenes();
		JLabel[] vidas = new JLabel[5];
		for (int i = 0; i < vidas.length; i++) {
			vidas[i] = li.getImagenVida();
		}
		return vidas;
	}
	
	public JPanel[] getPanelesVidas(JLabel[] vidas) {
		JPanel[] panelesVidas = new JPanel[5];
		for (int i = 0; i < panelesVidas.length; i++) {
			panelesVidas[i] = new JPanel();
			panelesVidas[i].add(vidas[i]);
			panelesVidas[i].add(new JLabel(""));
			panelesVidas[i].setLayout(new CardLayout());
		}
		return panelesVidas;
	}
	
	public JLabel[] getGuiones() {
		JLabel[] guiones = new JLabel[6];
		for(int i =0; i<guiones.length;i++) {
			guiones[i] = new JLabel(" _ ");
			guiones[i].setVisible(false);
		}
		return guiones;
	}

}
