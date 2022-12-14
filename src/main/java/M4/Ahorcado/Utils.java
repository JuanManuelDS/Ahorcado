package M4.Ahorcado;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Utils {
	
	//Cambia a la siguiente imagen del panel pasado por parámetro
	public void nextImagen(JPanel container) {
		CardLayout cl = (CardLayout) container.getLayout();
		cl.next(container);

	}
	
	//Desoculta la letra acertada
	public void desocultarLetra(String letra, String palabraOculta, JLabel[] letras) {
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

	//Devuelve un random dentro de un rango
	public int getRandom(int rango) {
		Random r = new Random();
		int random = r.nextInt(rango);
		return random;
	}
	
	//Devulve la primera imagen del container pasado por parámetro
	public void firstImage(JPanel container) {
		CardLayout cl = (CardLayout) container.getLayout();
		cl.first(container);
	}
	
	//Comprueba si se ha descubierto toda la palabra
	public boolean comprobarPalabra(JLabel[] letras) {
		boolean pc = true;
		// Se recorre la palabra oculta
		for (int i = 0; i < letras.length; i++) {
			// Si aún queda alguna letra por descubrir, devuelve false
			if (letras[i].getText() == " _ ") {
				pc = false;
			}
		}
		return pc;
	}
	
	//Comprueba si hay acierto o no
	public boolean comprobarAcierto(String letra, String palabraOculta) {
		if (palabraOculta.toLowerCase().contains(letra)) {
			return true;
		} else
			return false;
	}
	
	//Rellena el diccionario de palabras ocultas de Ahorcado con palabras de nuestro agrado
	public ArrayList<String> rellenarDiccionario(String dificultad) {
		ArrayList<String> diccionario = new ArrayList<>();
		if (dificultad == "facil") {
			List<String> aux = Arrays.asList("audita", "silvan", "alojar", "bardos", "añejos", "Grecia", "jubilo",
					"Kosovo", "lanoso", "hundes");
			diccionario = new ArrayList<>(aux);
		}
		return diccionario;
	}
}
