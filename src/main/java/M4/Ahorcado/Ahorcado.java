package M4.Ahorcado;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridLayout;

public class Ahorcado extends JFrame {

	private JPanel contentPane;

	public Ahorcado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 272, 89);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JButton btnNewButton_1 = new JButton("Iniciar Juego");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Resolver");
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 111, 272, 112);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6);
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 234, 272, 176);
		contentPane.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(292, 11, 274, 399);
		contentPane.add(panel_3);
		
		setVisible(true);
	}
}
