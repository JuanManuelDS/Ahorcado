package M4.Ahorcado;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
		JLabel[] image_labels = new JLabel[13];
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
	
	public JPanel getPanels(String panel) {
		JPanel aux = new JPanel();
		switch(panel) {
		case "opciones":
			aux.setBounds(10, 11, 272, 89);
			aux.setLayout(new GridLayout(3, 1, 0, 0));
			break;
		case "container_vidas":
			aux.setBounds(10, 111, 272, 112);
			aux.setLayout(new GridLayout(2, 0, 0, 0));
			break;
		case "vidas":
			aux.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			break;
		case "palabra":
			aux.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			break;
		case "teclado":
			aux.setBounds(10, 234, 272, 176);
			aux.setLayout(new GridLayout(6, 6, 0, 0));
			break;
		case "imagenes":
			aux.setBounds(292, 11, 274, 399);
			aux.setLayout(new CardLayout());
		}
		return aux;
	}

}
