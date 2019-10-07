package io.itch.deltabreaker.main;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ResourceLoader {

	public static TreeMap<String, Image> images = new TreeMap<>();
	
	public static Font font;
	
	public static void loadImages(String folder) throws Exception {
		for (File f : getFiles(folder)) {
			if (f.getAbsolutePath().endsWith(".png")) {
				images.put(f.getName(), ImageIO.read(f));
			}
		}
	}
	
	public static List<File> getFiles(String directoryName) {
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<File>();

		File[] fList = directory.listFiles();
		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			if (file.isDirectory()) {
				resultList.addAll(getFiles(file.getAbsolutePath()));
			}
		}

		return resultList;
	}
	
	public static void loadFont() throws Exception {
		font = Font.createFont(Font.TRUETYPE_FONT, new File("res/smash_font.ttf")).deriveFont(72f);
	}
	
}
