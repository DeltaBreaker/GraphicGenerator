package io.itch.deltabreaker.main;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import io.itch.deltabreaker.gui.GUIPlayerCount;

public class Startup {

	public static int playerAmount;
	public static final Border border = BorderFactory.createLineBorder(new Color(104, 113, 122));
	public static final String[] CHARACTER_NAMES = { "None", "Mario", "Donkey Kong", "Link", "Samus", "Dark Samus",
			"Yoshi", "Kirby", "Fox", "Pikachu", "Luigi", "Ness", "Captain Falcon", "Jigglypuff", "Peach", "Daisy",
			"Bowser", "Ice Climbers", "Shiek", "Zelda", "Dr. Mario", "Pichu", "Falco", "Marth", "Lucina", "Young Link",
			"Ganondorf", "Mewtwo", "Roy", "Chrom", "Mr. Game & Watch", "Meta Knight", "Pit", "Dark Pit",
			"Zero Suit Samus", "Wario", "Snake", "Ike", "Pokemon Trainer", "Diddy Kong", "Lucas", "Sonic",
			"King Dedede", "Olimar", "Lucario", "R.O.B.", "Toon Link", "Wolf", "Villager", "Mega Man",
			"Wii Fit Trainer", "Rosalina & Luma", "Little Mac", "Greninja", "Mii Brawler", "Mii Swordfighter",
			"Mii Gunner", "Palutena", "Pac-Man", "Robin", "Shulk", "Bowser Jr.", "Duck Hunt", "Ryu", "Ken", "Cloud",
			"Corrin", "Bayonetta", "Inkling", "Ridley", "Simon", "Richter", "King K. Rool", "Isabelle", "Incineroar",
			"Piranha Plant", "Joker", "Hero", "Banjo & Kazooie" };
	public static final Integer[] ALT_NUMBERS = { 1, 2, 3, 4, 5, 6, 7, 8 };
	public static final Integer[] PLAYER_COUNTS = { 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	
	public static void main(String[] args) {
		try {
			ResourceLoader.loadImages("res");
			ResourceLoader.loadFont();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new GUIPlayerCount();
	}

}