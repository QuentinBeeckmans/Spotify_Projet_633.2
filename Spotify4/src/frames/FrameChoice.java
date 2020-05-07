package frames;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.*;

public class FrameChoice extends JFrame {
	
	public GridBagConstraints gbc = new GridBagConstraints();
	public JPanel jpanel = new JPanel ();
	public JLabel label0 = new JLabel("Que faire ?\n(entrez le chiffre de l'action souhaiter)");
	public JButton label1 = new JButton("1 : Choisir une musique MP3");
	public JLabel label2 = new JLabel("2 : Choisir une musique");
	public JLabel label3 = new JLabel(" : Choisir une musique");
	public JLabel label4 = new JLabel(" : Choisir une musique");
	public JLabel label5 = new JLabel(" : Choisir une musique");
	public JLabel label6 = new JLabel(" : Choisir une musique");
	public JLabel label7 = new JLabel("7 : Changer mon dossier de partage");
	public JLabel label8 = new JLabel("8 : Quitter");

	public FrameChoice () {
		
		this.setVisible(true);			
		this.setSize(500, 600);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		jpanel.setLayout(new GridLayout(10,1));
		
				
		
		jpanel.add(label0);
		jpanel.add(label1);
		jpanel.add(label2);
		jpanel.add(label3);
		jpanel.add(label7);
		jpanel.add(label8);
		
		add(jpanel);	
		
	}
}
