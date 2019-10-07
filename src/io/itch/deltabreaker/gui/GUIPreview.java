package io.itch.deltabreaker.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIPreview extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 7846676306622813457L;
		
	private JPanel panel;
	private Image image;
	
	public GUIPreview(Image imageBase) {
		image = imageBase;
		
		setTitle("Graphic Generator");
		setSize(image.getWidth(null) / 2, image.getHeight(null) / 2);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new JPanel() {
			private static final long serialVersionUID = 13832447984131815L;
			
			public void paintComponent(Graphics g) {
				panel.setBounds(0, 0, panel.getParent().getWidth(), panel.getParent().getHeight());
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		panel.setBounds(0, 0, getWidth(), getHeight());
		add(panel);
		
		setVisible(true);
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(isVisible()) {
			try {
				repaint();
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
