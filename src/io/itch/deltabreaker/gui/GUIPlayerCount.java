package io.itch.deltabreaker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import io.itch.deltabreaker.main.Startup;

public class GUIPlayerCount extends JFrame {

	private static final long serialVersionUID = -6072075532490405358L;

	private JComboBox<Integer> playerCount;
	private JButton setPlayerCount;
	
	public GUIPlayerCount() {
		setTitle("Graphic Generator");
		setSize(220, 68);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		playerCount = new JComboBox<Integer>(Startup.PLAYER_COUNTS);
		playerCount.setBounds(10, 10, 40, 18);
		add(playerCount);
		
		setPlayerCount = new JButton("Set Player Count");
		setPlayerCount.setBounds(55, 10, 150, 18);
		setPlayerCount.setFocusable(false);
		setPlayerCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Startup.playerAmount = (int) playerCount.getSelectedItem();
				new GUIMain();
				dispose();
			}
		});
		add(setPlayerCount);
		
		setVisible(true);
	}
	
}
