package M4.Ahorcado;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Ahorcado extends JFrame {

	private JPanel contentPane;
	private JPanel[] paneles_vidas;
	private JPanel panel_imagenes;

	private JLabel[] letras;
	private JLabel[] image_labels;
	private JLabel[] vidas;

	private JButton[] arrayTeclado;
	private JButton juegoButton;
	private JButton resButton;
	private JButton pisButton;

	private ArrayList<String> diccionario;

	private String palabraOculta;

	private int contadorPalabras = 10;
	private int intentos = 1;
	private int contadorVidas = 4;
	private int intentosGlobales = 0;
	
	private Utils utils = new Utils();

	public Ahorcado() {

		setTitle("Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 480);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		// ---------- CREACIÓN DE COMPONENTES (JPanels y JLabels) ------------------
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
		
		//Creo el panel_opciones
		JPanel panel_opciones = cc.getPanels("opciones");
		
		//Creo el panel que contiene las vidas y la palabra oculta
		JPanel panelContainer_vidas = cc.getPanels("container_vidas");
		
		//Creo el panel que contiene las vidas
		JPanel panel_vidas = cc.getPanels("vidas");
		panelContainer_vidas.add(panel_vidas);
		
		//Creo el panel que contiene la palabra oculta
		JPanel panel_palabra = cc.getPanels("palabra");
		panelContainer_vidas.add(panel_palabra);
		
		//Creo el panel que contiene el teclado
		JPanel panel_teclado = cc.getPanels("teclado");

		panel_imagenes = cc.getPanels("imagenes");

		// Agrego los paneles con vidas al panel_vidas
		for (int i = 0; i < paneles_vidas.length; i++) {
			panel_vidas.add(paneles_vidas[i]);
		}

		// Agrego las imágenes al panel de imágenes
		for (int i = 0; i < image_labels.length; i++) {
			panel_imagenes.add(image_labels[i]);
		}
		
		//Creo y agrego palabras al diccionario
		diccionario = utils.rellenarDiccionario("facil");
		
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
					eventoTeclado(e, panel_imagenes);
				}
			});
		}

		// Agrego los espacios de letras a adivinar en panel_palabra
		for (int i = 0; i < letras.length; i++) {
			panel_palabra.add(letras[i]);
		}
		/*----------BOTÓN PISTA ----------------------*/
		JButton pistaButton = new JButton("Pista");
		pistaButton.setEnabled(false);
		pistaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[] options = { "Si", "No" };
				int n = JOptionPane.showOptionDialog(null, "Quieres gastar una vida?", null, JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				//Si el jugador clickeó sí, muestro la letra
				if (n == 0) {
					// Desactivamos el botón (una vez por palabra)
					pistaButton.setEnabled(false);
					// Intento quitar una imagen de vida cada vez que se presiona el botón
					if (contadorVidas >= 0) {
						utils.nextImagen(paneles_vidas[contadorVidas]);
						contadorVidas--;
						for (int i = 0; i < palabraOculta.length(); i++) {
							if (letras[i].getText() == " _ ") {
								String letra = (palabraOculta.charAt(i) + "").toLowerCase();
								utils.desocultarLetra(letra, palabraOculta, letras);
								break;
							}
						}

					}

					if (utils.comprobarPalabra(letras)) {
						siguientePalabra(true);
					}
				}
				// Desactivo el botón de pista si el jugador se queda con 1 vida
				if (contadorVidas == 0) {
					pistaButton.setEnabled(false);
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
				// Me fijo si tiene las vidas suficientes
				if (contadorVidas > 1) {
					// Desoculto todas las letras
					for (int i = 0; i < palabraOculta.length(); i++) {
						if (letras[i].getText() == " _ ") {
							String letra = Character.toString(palabraOculta.charAt(i)).toLowerCase();
							utils.desocultarLetra(letra, palabraOculta, letras);
						}
					}
					// Le resto 2 vidas
					utils.nextImagen(paneles_vidas[contadorVidas]);
					contadorVidas--;
					utils.nextImagen(paneles_vidas[contadorVidas]);
					contadorVidas--;
					siguientePalabra(true);
				} else {
					// Necesita 3 vidas o más porque sino pierde
					JOptionPane.showMessageDialog(contentPane,
							"Necesitas tener mínimamente 3 vidas para poder resolver la palabra");
				}

			}
		});
		panel_opciones.add(resolverButton);
		resButton = resolverButton;

		/*---------------BOTÓN COMENZAR -----------------------*/
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
				reiniciarJuego();
			}
		});
		Archivo.add(Nuevo_juego);

		/* Agregar menú ayuda */
		JMenu Ayuda = new JMenu("Ayuda");
		menuBar.add(Ayuda);

		JMenuItem Como_jugar = new JMenuItem("Como jugar");
		Como_jugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane,
						"Bienvenido al juego del ahorcado!\nElige las letras necesarias para adivinar la palabra secreta\nTienes 5 vidas");
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
		
		/*---------------ADICIONES AL CONTENT PANE------------------*/
		contentPane.add(panel_opciones);
		contentPane.add(panelContainer_vidas);
		contentPane.add(panel_teclado);
		contentPane.add(panel_imagenes);
		
		setVisible(true);
	}

	private void asignarPalabra() {
		if (contadorPalabras > 0) {
			// palabra oculta es un atributo al que se le asigna una palabra random
			int r = utils.getRandom(contadorPalabras);
			palabraOculta = diccionario.get(r);
			diccionario.remove(r);
			contadorPalabras--;
			System.out.println(palabraOculta);
		} else {
			// Si contador es 0 el juego se termina
			JOptionPane.showMessageDialog(contentPane, "Juego terminado, ganaste!\n Solo requeriste " + intentosGlobales + " intentos!");
		}

	}

	// Comprueba si la letra presionada se encuentra en la palabra oculta, si está
	// la desoculta y sino cambia la imagen del ahorcado
	private void eventoTeclado(ActionEvent e, JPanel panel_imagen) {
		// obtengo el boton presionado y lo desactivo
		JButton botonPresionado = (JButton) e.getSource();
		botonPresionado.setEnabled(false);
		// compruebo si la letra está en la palabra oculta
		String letra = botonPresionado.getText().toLowerCase();
		boolean acerto = utils.comprobarAcierto(letra, palabraOculta);
		// Si acertó, desoculto la palabra
		if (acerto) {
			utils.desocultarLetra(letra, palabraOculta, letras);
			if (utils.comprobarPalabra(letras)) {
				siguientePalabra(true);
			}
		} else {
			if (intentos > 10) {
				utils.nextImagen(panel_imagen);
				utils.nextImagen(paneles_vidas[contadorVidas]);
				contadorVidas--;
				for (int i = 0; i < arrayTeclado.length; i++) {
					arrayTeclado[i].setEnabled(false);
				}
				resButton.setEnabled(false);
				siguientePalabra(false);
			} else {
				// Se pasa a la siguiente imagen en el momento que se falla una letra
				utils.nextImagen(panel_imagen);
				intentos++;
			}
			intentosGlobales++;
		}
	}

	private void reiniciarJuego() {
		// Vuevlo a poner todas las vidas
		contadorVidas = 4;
		for (int i = 0; i < paneles_vidas.length; i++) {
			utils.firstImage(paneles_vidas[i]);
		}
		// Reinicio el contador de intentos
		intentos = 1;
		utils.firstImage(panel_imagenes);

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

	private void siguientePalabra(boolean gano) {

		// Desactivamos los botones del teclado
		for (int i = 0; i < arrayTeclado.length; i++) {
			arrayTeclado[i].setEnabled(false);
		}
		// Desactivamos el botón de Pista
		pisButton.setEnabled(false);

		// Seleccionamos el Layout de las imagenes
		CardLayout cl = (CardLayout) panel_imagenes.getLayout();
		
		if (gano) {
			// Mostramos la imagen de victoria
			cl.last(panel_imagenes);
		}

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				// Reiniciamos los labels de la palabra a guiones
				for (int i = 0; i < letras.length; i++) {
					letras[i].setText(" _ ");
				}
				// Reactivo todo el teclado
				for (int i = 0; i < arrayTeclado.length; i++) {
					arrayTeclado[i].setEnabled(true);
				}
				// Se reactiva el botón de Pista en caso que fuera usado y tenga más de 1 vida
				if(contadorVidas>0) {
					pisButton.setEnabled(true);
				}
				if(contadorVidas>1) {
					resButton.setEnabled(true);
				}

				// Se asigna una nueva palabra
				asignarPalabra();
				// Reinicio los intentos
				intentos = 1;
				// Se reinicia el contador de imagenes
				cl.first(panel_imagenes);
			}
		}, 1600);
	}

}
