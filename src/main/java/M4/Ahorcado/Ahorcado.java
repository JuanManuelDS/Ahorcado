package M4.Ahorcado;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;

public class Ahorcado extends JFrame {

	private JPanel contentPane;
	private JButton[] arrayTeclado = new JButton[27];
	private String alfa = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	private ArrayList<String> diccionario = new ArrayList<>();
	private LectorImagenes lectorImg = new LectorImagenes();
	private String palabraOculta;
	private int contador = 10;
	private JLabel[] letras;
	private int indexImagenes = 1;
	private JLabel[] image_labels = new JLabel[7];

	public Ahorcado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*----------- JPANELS ------------------------*/

		JPanel panel_opciones = new JPanel();
		panel_opciones.setBounds(10, 11, 272, 89);
		panel_opciones.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelContainer_vidas = new JPanel();
		panelContainer_vidas.setBounds(10, 111, 272, 112);
		panelContainer_vidas.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_vidas = new JPanel();
		panelContainer_vidas.add(panel_vidas);

		JPanel panel_palabra = new JPanel();
		panelContainer_vidas.add(panel_palabra);
		panel_palabra.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_teclado = new JPanel();
		panel_teclado.setBounds(10, 234, 272, 176);
		panel_teclado.setLayout(new GridLayout(6, 6, 0, 0));

		JPanel panel_imagen = new JPanel();
		panel_imagen.setBounds(292, 11, 274, 399);
		panel_imagen.setLayout(new CardLayout());

		/*------------------JLABELS-----------------------------*/
		for (int i = 0; i < image_labels.length; i++) {
			image_labels[i] = lectorImg.getImagenAhorcado(i + 1);
			image_labels[i].setBounds(0, 0, 274, 399);
		}

		/*------------------IMAGENES-----------------------------*/

		for (int i = 0; i < image_labels.length; i++) {
			panel_imagen.add(image_labels[i]);
		}

		/*-----------BOTONES-------------------------------*/
		JButton comenzarButton = new JButton("Iniciar Juego");
		panel_opciones.add(comenzarButton);

		JButton resolverButton = new JButton("Resolver");
		panel_opciones.add(resolverButton);

		for (int i = 0; i < arrayTeclado.length; i++) {
			arrayTeclado[i] = new JButton(String.valueOf(alfa.charAt(i)));
			panel_teclado.add(arrayTeclado[i]);
			arrayTeclado[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// obtengo el boton presionado y lo desactivo
					JButton botonPresionado = (JButton) e.getSource();
					botonPresionado.setEnabled(false);
					// compruebo si la letra está en la palabra oculta
					String letra = botonPresionado.getText().toLowerCase();
					boolean acerto = comprobarAcierto(letra);
					// Si acertó, desoculto la palabra
					if (acerto) {
						desocultarLetra(letra);
					} else {
						if (indexImagenes > 6) {
							JOptionPane.showMessageDialog(contentPane, "Perdiste campeon! ");
						} else {
							// Se pasa a la siguiente imagen en el momento que se falla una letra
							nextImagen(panel_imagen);
							indexImagenes++;
						}

					}
				}
			});
		}

		/*----------RELLENAR DICCIONARIO -------------------*/
		rellenarDiccionario("facil");

		// Agrego los espacios de letras a adivinar en panel_palabra
		letras = new JLabel[6];
		for (int i = 0; i < letras.length; i++) {
			letras[i] = new JLabel(" _ ");
			letras[i].setVisible(false);
			panel_palabra.add(letras[i]);
		}

		/*---------------EVENTOS BOTONES -----------------------*/
		comenzarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cuando hago click en "comenzar juego" hago visibles todos los labels
				for (int i = 0; i < letras.length; i++) {
					letras[i].setVisible(true);
				}
				// Asigno la palabra oculta
				asignarPalabra();
				// Desactivo el botón "comenzar juego"
				JButton jb = (JButton) e.getSource();
				jb.setEnabled(false);
			}
		});

		/*---------------ADICIONES AL CONTENT PANE------------------*/
		contentPane.add(panel_opciones);
		contentPane.add(panelContainer_vidas);
		contentPane.add(panel_teclado);
		contentPane.add(panel_imagen);

		setVisible(true);
	}

	private void desocultarLetra(String letra) {
		// Comparo y busco en minúsculas el/los index de las coincidencias
		ArrayList<Integer> indices = new ArrayList<>();
		for (int i = 0; i < palabraOculta.length(); i++) {
			char letraOculta = palabraOculta.toLowerCase().charAt(i);
			if (letraOculta == letra.charAt(0)) {
				indices.add(i);
			}
		}
		// Ahora desoculto las letras
		indices.forEach(ind -> {
			letras[ind].setText(palabraOculta.substring(ind, ind + 1));
		});

	}

	private boolean comprobarAcierto(String letra) {
		if (palabraOculta.toLowerCase().contains(letra)) {
			return true;
		} else
			return false;
	}

	private void rellenarDiccionario(String dificultad) {

		if (dificultad == "facil") {
			List<String> aux = Arrays.asList("audita", "silvan", "alojar", "bardos", "añejo", "Grecia", "jubilo",
					"Kosovo", "lanoso", "hundes");
			diccionario = new ArrayList<>(aux);
		}
	}

	private void asignarPalabra() {
		if (contador > 0) {
			// palabra oculta es un atributo al que se le asigna una palabra random
			palabraOculta = diccionario.get(getRandom());
			contador--;
			System.out.println(palabraOculta);
		} else {
			// Si contador es 0 el juego se termina
			JOptionPane.showMessageDialog(contentPane, "Juego terminado");
		}

	}

	public int getRandom() {
		Random r = new Random();
		int random = r.nextInt(contador);
		return random;
	}
	
	// Declaramos el jpanel_imagen y usamos el metodo .next() del CardLayout
	private void nextImagen(JPanel container) {
		CardLayout cl = (CardLayout) container.getLayout();
		
		switch (indexImagenes) {
		case 1:
			cl.next(container);
			break;
		case 2:
			cl.next(container);
			break;
		case 3:
			cl.next(container);
			break;
		case 4:
			cl.next(container);
			break;
		case 5:
			cl.next(container);
			break;
		case 6:
			cl.next(container);
			break;

		default:
			break;
		}

	}

}