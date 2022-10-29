package M4.Ahorcado;

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

import javax.swing.JLabel;

public class Ahorcado extends JFrame {

	private JPanel contentPane;
	private JButton[] arrayTeclado = new JButton[27];
	private String alfa = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	private ArrayList<String> diccionario = new ArrayList<>();
	private LectorImagenes lectorImg = new LectorImagenes();

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
		panelContainer_vidas.add(panel_vidas);
		
		JPanel panel_teclado = new JPanel();
		panel_teclado.setBounds(10, 234, 272, 176);
		panel_teclado.setLayout(new GridLayout(6, 6, 0, 0));
		
		JPanel panel_imagen = new JPanel();
		panel_imagen.setBounds(292, 11, 274, 399);
		panel_imagen.setLayout(null);
		
		/*-----------BOTONES-------------------------------*/
		JButton btnNewButton_1 = new JButton("Iniciar Juego");
		panel_opciones.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Resolver");
		panel_opciones.add(btnNewButton);


		for (int i = 0; i < arrayTeclado.length; i++) {
		
			arrayTeclado[i] = new JButton(String.valueOf(alfa.charAt(i))	);
			panel_teclado.add(arrayTeclado[i]);
			arrayTeclado[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

				}
			});
		}
		
		/*------------------JLABELS-----------------------------*/
		
		JLabel image_label = lectorImg.getImagenAhorcado(1);
		image_label.setBounds(0, 0, 274, 399);
		panel_imagen.add(image_label);
		
		/*----------RELLENAR DICCIONARIO -------------------*/
		
		diccionario = rellenarDiccionario("facil");

		/*---------------ADICIONES AL CONTENT PANE------------------*/
		contentPane.add(panel_opciones);
		contentPane.add(panelContainer_vidas);
		contentPane.add(panel_teclado);
		contentPane.add(panel_imagen);

		setVisible(true);
	}
	
	public void rellenarDiccionario (String dificultad){
		
		if(dificultad=="facil") {
			List<String> aux = Arrays.asList("audita", "silvan", "alojar", "bardos", "añejo", "Grecia", "jubilo", "Kosovo", "lanoso", "hundes");
			diccionario = new ArrayList<>(aux);
		}
	}
}