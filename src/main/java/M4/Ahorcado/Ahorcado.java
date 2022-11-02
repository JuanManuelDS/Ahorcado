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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;

public class Ahorcado extends JFrame {

	private JPanel contentPane;
	private JPanel[] paneles_vidas;
	
	private JLabel[] letras;
	private JLabel[] image_labels;
	private JLabel[] vidas;
	
	private JButton[] arrayTeclado;
	private JButton juegoButton;
	private JButton resButton;
	private JButton pisButton;
	
	private ArrayList<String> diccionario = new ArrayList<>();
	private LectorImagenes lectorImg = new LectorImagenes();
	
	private String palabraOculta;
	
	private int contador = 10;
	private int indexImagenes = 1;
	private int contadorVidas = 4;

	public Ahorcado() {

		setTitle("Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 480);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ---------- CREACIÓN DE COMPONENTES ------------------
		CreadorComponentes cc = new CreadorComponentes();
		// Creo teclado y lo inserto
		arrayTeclado = cc.getTeclado();
		// Labels con imágenes ahorcado
		image_labels = cc.getImagenesAhorcado();
		// Labels con imágenes vidas
		vidas = cc.getImagenesVidas();
		// Creo paneles con vidas que tendrán cardLayout para hacerlas desaparecer
		paneles_vidas = cc.getPanelesVidas(vidas);
		// Creo los labels con espacios vacíos
		letras = cc.getGuiones();

		/*----------- JPANELS ------------------------*/

		JPanel panel_opciones = new JPanel();
		panel_opciones.setBounds(10, 11, 272, 89);
		panel_opciones.setLayout(new GridLayout(3, 1, 0, 0));

		JPanel panelContainer_vidas = new JPanel();
		panelContainer_vidas.setBounds(10, 111, 272, 112);
		panelContainer_vidas.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_vidas = new JPanel();
		panelContainer_vidas.add(panel_vidas);
		panel_vidas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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
		// Agrego los paneles con vidas al panel_vidas
		for (int i = 0; i < paneles_vidas.length; i++) {
			panel_vidas.add(paneles_vidas[i]);
		}

		/*------------------IMAGENES-----------------------------*/
		// Agrego las imágenes al panel de imágenes
		for (int i = 0; i < image_labels.length; i++) {
			panel_imagen.add(image_labels[i]);
		}

		/*-----------BOTÓN INICIAR JUEGO------------------------------*/
		JButton comenzarButton = new JButton("Iniciar Juego");
		panel_opciones.add(comenzarButton);
		juegoButton = comenzarButton;

		// Agrego los botones que forman el teclado al panel_teclado y les agrego el
		// event listener
		for (int i = 0; i < arrayTeclado.length; i++) {
			panel_teclado.add(arrayTeclado[i]);
			arrayTeclado[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eventoTeclado(e, panel_imagen);
				}
			});
		}

		/*----------RELLENAR DICCIONARIO -------------------*/
		rellenarDiccionario("facil");

		// Agrego los espacios de letras a adivinar en panel_palabra
		for (int i = 0; i < letras.length; i++) {
			panel_palabra.add(letras[i]);
		}
		/*----------BOTÓN PISTA ----------------------*/
		JButton pistaButton = new JButton("Pista");
		pistaButton.setEnabled(false);
		pistaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Desactivamos el botón (una vez por palabra)
				pistaButton.setEnabled(false);
				Object[] options = { "Si", "No" };
				int n = JOptionPane.showOptionDialog(null, "Quieres gastar una vida?", null, JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

				if (n == 0) {
					// Intento quitar una imagen de vida cada vez que se presiona el botón
					if (contadorVidas >= 0) {
						nextImagen(paneles_vidas[contadorVidas]);
						contadorVidas--;
						for (int i = 0; i < palabraOculta.length(); i++) {
							if (letras[i].getText() == " _ ") {
								desocultarLetra(palabraOculta.charAt(i) + "");
								break;
							}
						}

					}
					
					if(comprobarPalabra()) {
						siguientePalabra();
					}
					
					if (contadorVidas == 0) {
						// Desactivo el botón de ayuda si el jugador se queda sin vidas
						pistaButton.setEnabled(false);
					}

				}
			}
		});
		panel_opciones.add(pistaButton);
		pisButton = pistaButton;
		
		/*----------BOTÓN RESOLVER ----------------------*/
		JButton resolverButton = new JButton("Resolver");
		resolverButton.setEnabled(false);
		resolverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Desactivamos el botón (una vez por partida)
				resolverButton.setEnabled(false);
				//Me fijo si tiene las vidas suficientes
				if(contadorVidas>1) {
					//Desoculto todas las letras
					for(int i = 0; i<palabraOculta.length(); i++) {
						if(letras[i].getText() == " _ ") {
							desocultarLetra(Character.toString(palabraOculta.charAt(i)).toLowerCase());
						}
					}
					//Le resto 2 vidas
					nextImagen(paneles_vidas[contadorVidas]);
					contadorVidas--;
					nextImagen(paneles_vidas[contadorVidas]);
					contadorVidas--;
					siguientePalabra();
				} else {
					//Necesita 3 vidas o más porque sino pierde
					JOptionPane.showMessageDialog(contentPane, "Necesitas tener mínimamente 3 vidas para poder resolver la palabra");
				}
				
				
			}
		});
		panel_opciones.add(resolverButton);
		resButton = resolverButton;
		
		/*---------------EVENTOS BOTONES -----------------------*/
		comenzarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Activamos el boton para resolver la palabra
				pistaButton.setEnabled(true);
				resolverButton.setEnabled(true);
				// Cuando hago click en "comenzar juego" hago visibles todos los labels
				for (int i = 0; i < letras.length; i++) {
					letras[i].setVisible(true);
				}
				for (int i = 0; i < arrayTeclado.length; i++) {
					arrayTeclado[i].setEnabled(true);
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

		// Crear el menú horizontal 
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu Archivo = new JMenu("Archivo");
		menuBar.add(Archivo);

		JMenuItem Salir = new JMenuItem("Salir");
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Archivo.add(Salir);

		JMenuItem Nuevo_juego = new JMenuItem("Nuevo juego");
		Nuevo_juego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizarJuego(panel_imagen);
			}
		});
		Archivo.add(Nuevo_juego);

		/*Agregar menú ayuda*/
		JMenu Ayuda = new JMenu("Ayuda");
		menuBar.add(Ayuda);

		JMenuItem Como_jugar = new JMenuItem("Como jugar");
		Como_jugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Bienvenido al juego del ahorcado!\nElige las letras necesarias para adivinar la palabra secreta\nTienes 5 vidas");
			}
		});
		Ayuda.add(Como_jugar);

		JMenuItem Acerca_de = new JMenuItem("Acerca de");
		Acerca_de.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Juego creado por Team 2 ©\nJuan, Jaume y Karim");
			}
		});
		Ayuda.add(Acerca_de);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
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
			int r = getRandom(contador);
			palabraOculta = diccionario.get(r);
			diccionario.remove(r);
			contador--;
			System.out.println(palabraOculta);
		} else {
			// Si contador es 0 el juego se termina
			JOptionPane.showMessageDialog(contentPane, "Juego terminado, ganaste!");
		}

	}

	private int getRandom(int rango) {
		Random r = new Random();
		int random = r.nextInt(rango);
		return random;
	}

	// Comprueba si la letra presionada se encuentra en la palabra oculta, si está
	// la desoculta y sino cambia la imagen del ahorcado
	private void eventoTeclado(ActionEvent e, JPanel panel_imagen) {
		// obtengo el boton presionado y lo desactivo
		JButton botonPresionado = (JButton) e.getSource();
		botonPresionado.setEnabled(false);
		// compruebo si la letra está en la palabra oculta
		String letra = botonPresionado.getText().toLowerCase();
		boolean acerto = comprobarAcierto(letra);
		// Si acertó, desoculto la palabra
		if (acerto) {
			desocultarLetra(letra);
			if(comprobarPalabra()) {
				siguientePalabra();
			}
		} else {
			if (indexImagenes > 10) {
				nextImagen(panel_imagen);
				nextImagen(paneles_vidas[contadorVidas]);
				contadorVidas--;
				for (int i = 0; i < arrayTeclado.length; i++) {
					arrayTeclado[i].setEnabled(false);
				}
				JOptionPane.showMessageDialog(contentPane, "Perdiste campeon! ");
			} else {
				// Se pasa a la siguiente imagen en el momento que se falla una letra
				nextImagen(panel_imagen);
				indexImagenes++;
			}
		}
	}

	// Declaramos el jpanel_imagen y usamos el metodo .next() del CardLayout.
	private void nextImagen(JPanel container) {
		CardLayout cl = (CardLayout) container.getLayout();
		cl.next(container);

	}

	private void finalizarJuego(JPanel panel_imagenes) {
		// Vuevlo a poner todas las vidas
		contadorVidas = 4;
		for (int i = 0; i < paneles_vidas.length; i++) {
			firstImage(paneles_vidas[i]);
		}
		// Reinicio el contador de intentos
		indexImagenes = 1;
		firstImage(panel_imagenes);

		// Vuelvo a habilitar el botón comenzar juego
		juegoButton.setEnabled(true);

		// Desactivo todas las letras
		for (int i = 0; i < arrayTeclado.length; i++) {
			arrayTeclado[i].setEnabled(false);
		}
		for (int i = 0; i < letras.length; i++) {// Reiniciamos los labels de la palabra a guiones
			letras[i].setText(" _ ");
		}
		pisButton.setEnabled(false);
		resButton.setEnabled(false);
	}

	private void firstImage(JPanel container) {
		CardLayout cl = (CardLayout) container.getLayout();
		cl.first(container);
	}

	private boolean comprobarPalabra() {
		boolean pc = true;

		for (int i = 0; i < letras.length; i++) {
			if (letras[i].getText() == " _ ") {
				pc = false;
			}
		}
		return pc;
	}
	
	private void siguientePalabra() {
		for (int i = 0; i < letras.length; i++) {// Reiniciamos los labels de la palabra a guiones
			letras[i].setText(" _ ");
		}
		//reactivo todo el teclado
		for (int i = 0; i < arrayTeclado.length; i++) {
			arrayTeclado[i].setEnabled(true);
		}
		pisButton.setEnabled(true);
		resButton.setEnabled(true);
		asignarPalabra();
	}
	
	
}
