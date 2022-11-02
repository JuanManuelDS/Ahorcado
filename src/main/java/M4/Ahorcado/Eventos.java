package M4.Ahorcado;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Eventos {
	
	
	
	public void eventoTeclado(ActionEvent e) {
		// obtengo el boton presionado y lo desactivo
				JButton botonPresionado = (JButton) e.getSource();
				botonPresionado.setEnabled(false);
				// compruebo si la letra está en la palabra oculta
				String letra = botonPresionado.getText().toLowerCase();
				boolean acerto = comprobarAcierto(letra);
				// Si acertó, desoculto la palabra
				if (acerto) {
					desocultarLetra(letra);
					if (comprobarPalabra()) {
						siguientePalabra(true);
					}
				} else {
					if (intentos > 10) {
						nextImagen(panel_imagen);
						nextImagen(paneles_vidas[contadorVidas]);
						contadorVidas--;
						for (int i = 0; i < arrayTeclado.length; i++) {
							arrayTeclado[i].setEnabled(false);
						}
						resButton.setEnabled(false);
						siguientePalabra(false);
					} else {
						// Se pasa a la siguiente imagen en el momento que se falla una letra
						nextImagen(panel_imagen);
						intentos++;
					}
					intentosGlobales++;
				}
	}
	
}
