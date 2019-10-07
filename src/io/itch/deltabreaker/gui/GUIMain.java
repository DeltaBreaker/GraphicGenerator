package io.itch.deltabreaker.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import io.itch.deltabreaker.main.ChallongeAPI;
import io.itch.deltabreaker.main.Player;
import io.itch.deltabreaker.main.ResourceLoader;
import io.itch.deltabreaker.main.Startup;

public class GUIMain extends JFrame implements Runnable {

	private static final long serialVersionUID = 579077519928688407L;

	private Player[] players;

	private JTextArea[] playerNames = new JTextArea[Startup.playerAmount];
	private JLabel[] playerLabel = new JLabel[Startup.playerAmount];

	@SuppressWarnings("unchecked")
	private JComboBox<String>[] playerCharacters = new JComboBox[Startup.playerAmount];
	@SuppressWarnings("unchecked")
	private JComboBox<String>[] playerSecondaries = new JComboBox[Startup.playerAmount];
	private JLabel[] characterLabel = new JLabel[Startup.playerAmount];

	@SuppressWarnings("unchecked")
	private JComboBox<Integer>[] characterAlt = new JComboBox[Startup.playerAmount];
	@SuppressWarnings("unchecked")
	private JComboBox<Integer>[] secondaryAlt = new JComboBox[Startup.playerAmount];
	private JLabel[] altLabel = new JLabel[Startup.playerAmount];

	private JPanel[] stockIcon = new JPanel[Startup.playerAmount];
	private JPanel[] secondaryIcon = new JPanel[Startup.playerAmount];

	private JButton changeAmount;
	private JTextArea challongeLink;
	private JButton challongePull;

	private JTextArea name, location, date;
	private JLabel nameLabel, locationLabel, dateLabel;

	private JTextArea filename;
	private JLabel filenameLabel;

	private JButton background, foreground;
	private Image backgroundImage, foregroundImage;

	private JButton create;

	private GUIPreview previewWindow;
	private BufferedImage livePreview;
	private Graphics g;
	private BufferedImage liveBuffer;
	private Graphics gb;

	private boolean changingBG = false;

	private JFormattedTextField portraitOffsetX, portraitOffsetY, portraitSpacingX, portraitSpacingY, portraitDensity,
			portraitWidth, portraitHeight;
	private JFormattedTextField secondaryOffsetX, secondaryOffsetY, secondarySpacingX, secondarySpacingY,
			secondaryDensity, secondaryWidth, secondaryHeight;
	private JFormattedTextField tagOffsetX, tagOffsetY, tagSpacingX, tagSpacingY, tagDensity;

	private JFormattedTextField metaPositionX, metaPositionY, metaColorR, metaColorG, metaColorB, tagColorR, tagColorG,
			tagColorB, tagFont, metaFont;

	private JLabel portraitOffsetsLabel, portraitSpacingsLabel, portraitDensityLabel, portraitSize;
	private JLabel secondaryOffsetsLabel, secondarySpacingsLabel, secondaryDensityLabel, secondarySize;
	private JLabel tagOffsetsLabel, tagSpacingsLabel, tagDensityLabel;
	private JLabel metaPosition, metaColor, tagColor, tagFontLabel, metaFontLabel;

	private JTextArea presetFilename;
	private JButton save, load;
	
	public GUIMain() {
		backgroundImage = ResourceLoader.images.get("background_default.png");

		livePreview = new BufferedImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		g = livePreview.getGraphics();
		liveBuffer = new BufferedImage(livePreview.getWidth(), livePreview.getHeight(), BufferedImage.TYPE_INT_ARGB);
		gb = liveBuffer.getGraphics();

		setTitle("Graphic Generator");
		setSize(1280, 720);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		for (int i = 0; i < Startup.playerAmount; i++) {
			int current = i;

			playerNames[i] = new JTextArea("Player " + (i + 1));
			playerNames[i].setBounds(10 + Math.floorDiv(i, 4) * 420, 10 + Math.floorMod(i, 4) * 88, 150, 18);
			playerNames[i].setBorder(Startup.border);
			add(playerNames[i]);

			playerLabel[i] = new JLabel("Player " + (i + 1));
			playerLabel[i].setBounds(165 + Math.floorDiv(i, 4) * 420, 10 + Math.floorMod(i, 4) * 88, 65, 18);
			add(playerLabel[i]);

			playerCharacters[i] = new JComboBox<String>(Startup.CHARACTER_NAMES);
			playerCharacters[i].setBounds(10 + Math.floorDiv(i, 4) * 420, 33 + Math.floorMod(i, 4) * 88, 150, 18);
			playerCharacters[i].setFocusable(false);
			playerCharacters[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playerCharacters[current].getParent().repaint();
				}
			});
			add(playerCharacters[i]);

			playerSecondaries[i] = new JComboBox<String>(Startup.CHARACTER_NAMES);
			playerSecondaries[i].setBounds(165 + Math.floorDiv(i, 4) * 420, 33 + Math.floorMod(i, 4) * 88, 150, 18);
			playerSecondaries[i].setFocusable(false);
			playerSecondaries[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playerSecondaries[current].getParent().repaint();
				}
			});
			add(playerSecondaries[i]);

			characterLabel[i] = new JLabel("Characters");
			characterLabel[i].setBounds(320 + Math.floorDiv(i, 4) * 420, 33 + Math.floorMod(i, 4) * 88, 65, 18);
			add(characterLabel[i]);

			characterAlt[i] = new JComboBox<Integer>(Startup.ALT_NUMBERS);
			characterAlt[i].setBounds(10 + Math.floorDiv(i, 4) * 420, 56 + Math.floorMod(i, 4) * 88, 35, 18);
			characterAlt[i].setFocusable(false);
			characterAlt[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					characterAlt[current].getParent().repaint();
				}
			});
			add(characterAlt[i]);

			secondaryAlt[i] = new JComboBox<Integer>(Startup.ALT_NUMBERS);
			secondaryAlt[i].setBounds(50 + Math.floorDiv(i, 4) * 420, 56 + Math.floorMod(i, 4) * 88, 35, 18);
			secondaryAlt[i].setFocusable(false);
			secondaryAlt[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					secondaryAlt[current].getParent().repaint();
				}
			});
			add(secondaryAlt[i]);

			altLabel[i] = new JLabel("Alternate");
			altLabel[i].setBounds(90 + Math.floorDiv(i, 4) * 420, 56 + Math.floorMod(i, 4) * 88, 65, 18);
			add(altLabel[i]);

			stockIcon[i] = new JPanel() {
				private static final long serialVersionUID = -6584480263329157535L;

				private int assigned = current;

				private int character;
				private int alt;

				public void paintComponent(Graphics g) {
					character = playerCharacters[assigned].getSelectedIndex();
					alt = characterAlt[assigned].getSelectedIndex() + 1;

					g.drawImage(ResourceLoader.images.get(character + " (" + alt + ").png"), 0, 0, 32, 32, null);
				}
			};
			stockIcon[i].setBounds(168 + Math.floorDiv(i, 4) * 420, 56 + Math.floorMod(i, 4) * 88, 32, 32);
			add(stockIcon[i]);

			secondaryIcon[i] = new JPanel() {
				private static final long serialVersionUID = -6584480263329157535L;

				private int assigned = current;

				private int character;
				private int alt;

				public void paintComponent(Graphics g) {
					character = playerSecondaries[assigned].getSelectedIndex();
					alt = secondaryAlt[assigned].getSelectedIndex() + 1;

					g.drawImage(ResourceLoader.images.get(character + " (" + alt + ").png"), 0, 0, 32, 32, null);
				}
			};
			secondaryIcon[i].setBounds(205 + Math.floorDiv(i, 4) * 420, 56 + Math.floorMod(i, 4) * 88, 32, 32);
			add(secondaryIcon[i]);
		}

		changeAmount = new JButton("Change Player Amount");
		changeAmount.setBounds(10, 357, 200, 18);
		changeAmount.setFocusable(false);
		changeAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GUIPlayerCount();
				setVisible(false);
				dispose();
				previewWindow.setVisible(false);
				previewWindow.dispose();
			}
		});
		add(changeAmount);

		challongeLink = new JTextArea("http://challonge.com/tournament_name");
		challongeLink.setBounds(10, 390, 300, 18);
		challongeLink.setBorder(Startup.border);
		add(challongeLink);

		challongePull = new JButton("Imprort from Challonge");
		challongePull.setBounds(10, 413, 300, 18);
		challongePull.setFocusable(false);
		challongePull.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					players = ChallongeAPI.getResults(challongeLink.getText());
					for (int i = 0; i < Startup.playerAmount; i++) {
						for (Player p : players) {
							if (i == p.placement - 1) {
								playerNames[i].setText(p.name);
							}
						}
					}
					JOptionPane.showMessageDialog(challongePull.getParent(), "Results imported successfully.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(challongePull.getParent(), "Something went wrong!\n"
							+ "Check the link and try again.\n"
							+ "Also make sure that the tournament has officially ended and does not say \"live\".");
				}
			}
		});
		add(challongePull);

		name = new JTextArea();
		name.setBounds(10, 446, 300, 18);
		name.setBorder(Startup.border);
		add(name);

		location = new JTextArea();
		location.setBounds(10, 469, 300, 18);
		location.setBorder(Startup.border);
		add(location);

		date = new JTextArea();
		date.setBounds(10, 492, 300, 18);
		date.setBorder(Startup.border);
		add(date);

		nameLabel = new JLabel("Event Name");
		nameLabel.setBounds(315, 446, 75, 18);
		add(nameLabel);

		locationLabel = new JLabel("Event Location");
		locationLabel.setBounds(315, 469, 100, 18);
		add(locationLabel);

		dateLabel = new JLabel("Event Date");
		dateLabel.setBounds(315, 492, 75, 18);
		add(dateLabel);

		filename = new JTextArea("results.png");
		filename.setBounds(10, 515, 300, 18);
		filename.setBorder(Startup.border);
		add(filename);

		filenameLabel = new JLabel("File Name");
		filenameLabel.setBounds(315, 515, 100, 18);
		add(filenameLabel);

		background = new JButton("Custom Background");
		background.setBounds(10, 548, 300, 18);
		background.setFocusable(false);
		background.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changingBG = true;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(background.getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						backgroundImage = ImageIO.read(chooser.getSelectedFile());
						livePreview = new BufferedImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null),
								BufferedImage.TYPE_INT_ARGB);
						g = livePreview.getGraphics();
						liveBuffer = new BufferedImage(livePreview.getWidth(), livePreview.getHeight(),
								BufferedImage.TYPE_INT_ARGB);
						gb = liveBuffer.getGraphics();

						previewWindow.dispose();
						previewWindow = new GUIPreview(liveBuffer);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					background.setText(chooser.getSelectedFile().getName());
				}
				changingBG = false;
			}
		});
		add(background);

		foreground = new JButton("Custom Foreground");
		foreground.setBounds(10, 571, 300, 18);
		foreground.setFocusable(false);
		foreground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(foreground.getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						foregroundImage = ImageIO.read(chooser.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					foreground.setText(chooser.getSelectedFile().getName());
				}
			}
		});
		add(foreground);

		create = new JButton("Create");
		create.setBounds(10, 604, 300, 18);
		create.setFocusable(false);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!filename.getText().endsWith(".png")) {
						filename.append(".png");
					}
					ImageIO.write((RenderedImage) generateImage(), "png", new File(filename.getText()));
					JOptionPane.showMessageDialog(challongePull.getParent(), "The image was created successfully.");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(challongePull.getParent(), "There was an error exporting the image.");
				}
			}
		});
		add(create);

		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setCommitsOnValidEdit(true);

		portraitOffsetX = new JFormattedTextField(formatter);
		portraitOffsetX.setText("232");
		portraitOffsetX.setBounds(450, 390, 50, 18);
		add(portraitOffsetX);

		portraitOffsetY = new JFormattedTextField(formatter);
		portraitOffsetY.setText("332");
		portraitOffsetY.setBounds(505, 390, 50, 18);
		add(portraitOffsetY);

		portraitSpacingX = new JFormattedTextField(formatter);
		portraitSpacingX.setText("575");
		portraitSpacingX.setBounds(450, 413, 50, 18);
		add(portraitSpacingX);

		portraitSpacingY = new JFormattedTextField(formatter);
		portraitSpacingY.setText("732");
		portraitSpacingY.setBounds(505, 413, 50, 18);
		add(portraitSpacingY);

		portraitDensity = new JFormattedTextField(formatter);
		portraitDensity.setText("4");
		portraitDensity.setBounds(450, 436, 50, 18);
		add(portraitDensity);

		portraitWidth = new JFormattedTextField(formatter);
		portraitWidth.setText("512");
		portraitWidth.setBounds(450, 459, 50, 18);
		add(portraitWidth);

		portraitHeight = new JFormattedTextField(formatter);
		portraitHeight.setText("512");
		portraitHeight.setBounds(505, 459, 50, 18);
		add(portraitHeight);

		portraitOffsetsLabel = new JLabel("Portrait Offsets (X/Y)");
		portraitOffsetsLabel.setBounds(560, 390, 150, 18);
		add(portraitOffsetsLabel);

		portraitSpacingsLabel = new JLabel("Portrait Spacing (X/Y)");
		portraitSpacingsLabel.setBounds(560, 413, 150, 18);
		add(portraitSpacingsLabel);

		portraitDensityLabel = new JLabel("Portrait Density (# per row)");
		portraitDensityLabel.setBounds(505, 436, 200, 18);
		add(portraitDensityLabel);

		portraitSize = new JLabel("Portrait Width/Height");
		portraitSize.setBounds(560, 459, 200, 18);
		add(portraitSize);

		metaPositionX = new JFormattedTextField(formatter);
		metaPositionX.setText("10");
		metaPositionX.setBounds(450, 492, 50, 18);
		add(metaPositionX);

		metaPositionY = new JFormattedTextField(formatter);
		metaPositionY.setText("60");
		metaPositionY.setBounds(505, 492, 50, 18);
		add(metaPositionY);

		metaPosition = new JLabel("Description Offsets (X/Y)");
		metaPosition.setBounds(560, 492, 250, 18);
		add(metaPosition);

		metaColorR = new JFormattedTextField(formatter);
		metaColorR.setText("0");
		metaColorR.setBounds(450, 515, 30, 18);
		add(metaColorR);

		metaColorG = new JFormattedTextField(formatter);
		metaColorG.setText("0");
		metaColorG.setBounds(485, 515, 30, 18);
		add(metaColorG);

		metaColorB = new JFormattedTextField(formatter);
		metaColorB.setText("0");
		metaColorB.setBounds(520, 515, 30, 18);
		add(metaColorB);

		metaColor = new JLabel("Description Color (R/G/B)");
		metaColor.setBounds(555, 515, 250, 18);
		add(metaColor);

		metaFont = new JFormattedTextField(formatter);
		metaFont.setText("48");
		metaFont.setBounds(450, 538, 50, 18);
		add(metaFont);

		metaFontLabel = new JLabel("Description Font Size");
		metaFontLabel.setBounds(505, 538, 250, 18);
		add(metaFontLabel);

		presetFilename = new JTextArea("preset.dat");
		presetFilename.setBounds(450, 561, 150, 18);
		presetFilename.setBorder(Startup.border);
		add(presetFilename);
		
		save = new JButton("Save Preset");
		save.setBounds(450, 584, 150, 18);
		save.setFocusable(false);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save(presetFilename.getText());
			}
		});
		add(save);
		
		load = new JButton("Load Preset");
		load.setBounds(450, 607, 150, 18);
		load.setFocusable(false);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				load(presetFilename.getText());
			}
		});
		add(load);
		
		secondaryOffsetX = new JFormattedTextField(formatter);
		secondaryOffsetX.setText("583");
		secondaryOffsetX.setBounds(715, 390, 50, 18);
		add(secondaryOffsetX);

		secondaryOffsetY = new JFormattedTextField(formatter);
		secondaryOffsetY.setText("682");
		secondaryOffsetY.setBounds(770, 390, 50, 18);
		add(secondaryOffsetY);

		secondarySpacingX = new JFormattedTextField(formatter);
		secondarySpacingX.setText("575");
		secondarySpacingX.setBounds(715, 413, 50, 18);
		add(secondarySpacingX);

		secondarySpacingY = new JFormattedTextField(formatter);
		secondarySpacingY.setText("732");
		secondarySpacingY.setBounds(770, 413, 50, 18);
		add(secondarySpacingY);

		secondaryDensity = new JFormattedTextField(formatter);
		secondaryDensity.setText("4");
		secondaryDensity.setBounds(715, 436, 50, 18);
		add(secondaryDensity);

		secondaryWidth = new JFormattedTextField(formatter);
		secondaryWidth.setText("162");
		secondaryWidth.setBounds(715, 459, 50, 18);
		add(secondaryWidth);

		secondaryHeight = new JFormattedTextField(formatter);
		secondaryHeight.setText("162");
		secondaryHeight.setBounds(770, 459, 50, 18);
		add(secondaryHeight);

		secondaryOffsetsLabel = new JLabel("Secondary Offsets (X/Y)");
		secondaryOffsetsLabel.setBounds(825, 390, 150, 18);
		add(secondaryOffsetsLabel);

		secondarySpacingsLabel = new JLabel("Secondary Spacing (X/Y)");
		secondarySpacingsLabel.setBounds(825, 413, 150, 18);
		add(secondarySpacingsLabel);

		secondaryDensityLabel = new JLabel("Secondary Density (# per row)");
		secondaryDensityLabel.setBounds(770, 436, 200, 18);
		add(secondaryDensityLabel);

		secondarySize = new JLabel("Secondary Width/Height");
		secondarySize.setBounds(825, 459, 200, 18);
		add(secondarySize);

		tagOffsetX = new JFormattedTextField(formatter);
		tagOffsetX.setText("238");
		tagOffsetX.setBounds(980, 390, 50, 18);
		add(tagOffsetX);

		tagOffsetY = new JFormattedTextField(formatter);
		tagOffsetY.setText("902");
		tagOffsetY.setBounds(1035, 390, 50, 18);
		add(tagOffsetY);

		tagSpacingX = new JFormattedTextField(formatter);
		tagSpacingX.setText("575");
		tagSpacingX.setBounds(980, 413, 50, 18);
		add(tagSpacingX);

		tagSpacingY = new JFormattedTextField(formatter);
		tagSpacingY.setText("732");
		tagSpacingY.setBounds(1035, 413, 50, 18);
		add(tagSpacingY);

		tagDensity = new JFormattedTextField(formatter);
		tagDensity.setText("4");
		tagDensity.setBounds(980, 436, 50, 18);
		add(tagDensity);

		tagOffsetsLabel = new JLabel("Tag Offsets (X/Y)");
		tagOffsetsLabel.setBounds(1090, 390, 100, 18);
		add(tagOffsetsLabel);

		tagSpacingsLabel = new JLabel("Tag Spacing (X/Y)");
		tagSpacingsLabel.setBounds(1090, 413, 100, 18);
		add(tagSpacingsLabel);

		tagDensityLabel = new JLabel("Tag Density (# per row)");
		tagDensityLabel.setBounds(1035, 436, 150, 18);
		add(tagDensityLabel);

		tagColorR = new JFormattedTextField(formatter);
		tagColorR.setText("255");
		tagColorR.setBounds(980, 459, 30, 18);
		add(tagColorR);

		tagColorG = new JFormattedTextField(formatter);
		tagColorG.setText("255");
		tagColorG.setBounds(1015, 459, 30, 18);
		add(tagColorG);

		tagColorB = new JFormattedTextField(formatter);
		tagColorB.setText("255");
		tagColorB.setBounds(1050, 459, 30, 18);
		add(tagColorB);

		tagColor = new JLabel("Tag Color (R/G/B)");
		tagColor.setBounds(1085, 459, 150, 18);
		add(tagColor);

		tagFont = new JFormattedTextField(formatter);
		tagFont.setText("72");
		tagFont.setBounds(980, 482, 50, 18);
		add(tagFont);

		tagFontLabel = new JLabel("Tag Font Size");
		tagFontLabel.setBounds(1035, 482, 150, 18);
		add(tagFontLabel);

		previewWindow = new GUIPreview(liveBuffer);
		setVisible(true);

		new Thread(this).start();
	}

	public Image generateImage() throws Exception {
		BufferedImage image = new BufferedImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();

		g.drawImage(backgroundImage, 0, 0, null);

		g.setColor(new Color(Integer.parseInt(tagColorR.getText()), Integer.parseInt(tagColorG.getText()),
				Integer.parseInt(tagColorB.getText())));
		g.setFont(ResourceLoader.font.deriveFont((float) Integer.parseInt(tagFont.getText())));
		for (int i = 0; i < Startup.playerAmount; i++) {
			g.drawImage(
					ResourceLoader.images.get("pp" + playerCharacters[i].getSelectedIndex() + " ("
							+ (characterAlt[i].getSelectedIndex() + 1) + ").png"),
					Integer.parseInt(portraitOffsetX.getText())
							+ Math.floorMod(i, Integer.parseInt(portraitDensity.getText()))
									* Integer.parseInt(portraitSpacingX.getText()),
					Integer.parseInt(portraitOffsetY.getText())
							+ Math.floorDiv(i, Integer.parseInt(portraitDensity.getText()))
									* Integer.parseInt(portraitSpacingY.getText()),
					Integer.parseInt(portraitWidth.getText()), Integer.parseInt(portraitHeight.getText()), null);
		}

		g.drawImage(foregroundImage, 0, 0, null);

		for (int i = 0; i < Startup.playerAmount; i++) {
			g.drawImage(
					ResourceLoader.images.get("ps" + playerSecondaries[i].getSelectedIndex() + " ("
							+ (secondaryAlt[i].getSelectedIndex() + 1) + ").png"),
					Integer.parseInt(secondaryOffsetX.getText())
							+ Math.floorMod(i, Integer.parseInt(secondaryDensity.getText()))
									* Integer.parseInt(secondarySpacingX.getText()),
					Integer.parseInt(secondaryOffsetY.getText())
							+ Math.floorDiv(i, Integer.parseInt(secondaryDensity.getText()))
									* Integer.parseInt(secondarySpacingY.getText()),
					Integer.parseInt(secondaryWidth.getText()), Integer.parseInt(secondaryHeight.getText()), null);
			g.drawString(playerNames[i].getText(),
					Integer.parseInt(tagOffsetX.getText()) + Math.floorMod(i, Integer.parseInt(tagDensity.getText()))
							* Integer.parseInt(tagSpacingX.getText()),
					Integer.parseInt(tagOffsetY.getText()) + Math.floorDiv(i, Integer.parseInt(tagDensity.getText()))
							* Integer.parseInt(tagSpacingY.getText()));
		}

		g.setFont(ResourceLoader.font.deriveFont((float) Integer.parseInt(metaFont.getText())));
		g.setColor(new Color(Integer.parseInt(metaColorR.getText()), Integer.parseInt(metaColorG.getText()),
				Integer.parseInt(metaColorB.getText())));
		g.drawString(name.getText(), Integer.parseInt(metaPositionX.getText()),
				Integer.parseInt(metaPositionY.getText()));
		g.drawString(location.getText(), Integer.parseInt(metaPositionX.getText()),
				Integer.parseInt(metaPositionY.getText()) + 63);
		g.drawString(date.getText(), Integer.parseInt(metaPositionX.getText()),
				Integer.parseInt(metaPositionY.getText()) + 125);

		return image;
	}

	@Override
	public void run() {
		while (isVisible()) {
			try {
				if (!changingBG) {
					g.drawImage(backgroundImage, 0, 0, null);

					g.setColor(new Color(Integer.parseInt(tagColorR.getText()), Integer.parseInt(tagColorG.getText()),
							Integer.parseInt(tagColorB.getText())));
					g.setFont(ResourceLoader.font.deriveFont((float) Integer.parseInt(tagFont.getText())));
					for (int i = 0; i < Startup.playerAmount; i++) {
						g.drawImage(
								ResourceLoader.images.get("pp" + playerCharacters[i].getSelectedIndex() + " ("
										+ (characterAlt[i].getSelectedIndex() + 1) + ").png"),
								Integer.parseInt(portraitOffsetX.getText())
										+ Math.floorMod(i, Integer.parseInt(portraitDensity.getText()))
												* Integer.parseInt(portraitSpacingX.getText()),
								Integer.parseInt(portraitOffsetY.getText())
										+ Math.floorDiv(i, Integer.parseInt(portraitDensity.getText()))
												* Integer.parseInt(portraitSpacingY.getText()),
								Integer.parseInt(portraitWidth.getText()), Integer.parseInt(portraitHeight.getText()),
								null);
					}

					g.drawImage(foregroundImage, 0, 0, null);

					for (int i = 0; i < Startup.playerAmount; i++) {
						g.drawImage(
								ResourceLoader.images.get("ps" + playerSecondaries[i].getSelectedIndex() + " ("
										+ (secondaryAlt[i].getSelectedIndex() + 1) + ").png"),
								Integer.parseInt(secondaryOffsetX.getText())
										+ Math.floorMod(i, Integer.parseInt(secondaryDensity.getText()))
												* Integer.parseInt(secondarySpacingX.getText()),
								Integer.parseInt(secondaryOffsetY.getText())
										+ Math.floorDiv(i, Integer.parseInt(secondaryDensity.getText()))
												* Integer.parseInt(secondarySpacingY.getText()),
								Integer.parseInt(secondaryWidth.getText()), Integer.parseInt(secondaryHeight.getText()),
								null);
						g.drawString(playerNames[i].getText(),
								Integer.parseInt(tagOffsetX.getText())
										+ Math.floorMod(i, Integer.parseInt(tagDensity.getText()))
												* Integer.parseInt(tagSpacingX.getText()),
								Integer.parseInt(tagOffsetY.getText())
										+ Math.floorDiv(i, Integer.parseInt(tagDensity.getText()))
												* Integer.parseInt(tagSpacingY.getText()));
					}

					g.setFont(ResourceLoader.font.deriveFont((float) Integer.parseInt(metaFont.getText())));
					g.setColor(new Color(Integer.parseInt(metaColorR.getText()), Integer.parseInt(metaColorG.getText()),
							Integer.parseInt(metaColorB.getText())));
					g.drawString(name.getText(), Integer.parseInt(metaPositionX.getText()),
							Integer.parseInt(metaPositionY.getText()));
					g.drawString(location.getText(), Integer.parseInt(metaPositionX.getText()),
							Integer.parseInt(metaPositionY.getText()) + 63);
					g.drawString(date.getText(), Integer.parseInt(metaPositionX.getText()),
							Integer.parseInt(metaPositionY.getText()) + 125);

					gb.drawImage(livePreview, 0, 0, null);
				}
				Thread.sleep(100L);
			} catch (Exception e) {
				System.out.println("[GUIMain] Rendering error, probably a parsing issue");
			}
		}
	}

	public void save(String filename) {
		File f = new File("saves");
		if(!f.exists()) {
			f.mkdir();
		}
		
		File output = new File(f + "/" + filename);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(output));
			
			out.writeUTF(portraitOffsetX.getText());
			out.writeUTF(portraitOffsetY.getText());
			out.writeUTF(portraitSpacingX.getText());
			out.writeUTF(portraitSpacingY.getText());
			out.writeUTF(portraitDensity.getText());
			out.writeUTF(portraitWidth.getText());
			out.writeUTF(portraitHeight.getText());
			
			out.writeUTF(secondaryOffsetX.getText());
			out.writeUTF(secondaryOffsetY.getText());
			out.writeUTF(secondarySpacingX.getText());
			out.writeUTF(secondarySpacingY.getText());
			out.writeUTF(secondaryDensity.getText());
			out.writeUTF(secondaryWidth.getText());
			out.writeUTF(secondaryHeight.getText());
			
			out.writeUTF(tagOffsetX.getText());
			out.writeUTF(tagOffsetY.getText());
			out.writeUTF(tagSpacingX.getText());
			out.writeUTF(tagSpacingY.getText());
			out.writeUTF(tagDensity.getText());
			out.writeUTF(tagColorR.getText());
			out.writeUTF(tagColorG.getText());
			out.writeUTF(tagColorB.getText());
			out.writeUTF(tagFont.getText());
			
			out.writeUTF(metaPositionX.getText());
			out.writeUTF(metaPositionY.getText());
			out.writeUTF(metaColorR.getText());
			out.writeUTF(metaColorG.getText());
			out.writeUTF(metaColorB.getText());
			out.writeUTF(metaFont.getText());
			
			out.flush();
			out.close();
			
			JOptionPane.showMessageDialog(this, "Presets saved successfully.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "There was an error loading this file.");
		}
	}

	public void load(String filename) {
		File f = new File("saves/" + filename);
		if(f.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
				
				portraitOffsetX.setText(in.readUTF());
				portraitOffsetY.setText(in.readUTF());
				portraitSpacingX.setText(in.readUTF());
				portraitSpacingY.setText(in.readUTF());
				portraitDensity.setText(in.readUTF());
				portraitWidth.setText(in.readUTF());
				portraitHeight.setText(in.readUTF());
				
				secondaryOffsetX.setText(in.readUTF());
				secondaryOffsetY.setText(in.readUTF());
				secondarySpacingX.setText(in.readUTF());
				secondarySpacingY.setText(in.readUTF());
				secondaryDensity.setText(in.readUTF());
				secondaryWidth.setText(in.readUTF());
				secondaryHeight.setText(in.readUTF());
				
				tagOffsetX.setText(in.readUTF());
				tagOffsetY.setText(in.readUTF());
				tagSpacingX.setText(in.readUTF());
				tagSpacingY.setText(in.readUTF());
				tagDensity.setText(in.readUTF());
				tagColorR.setText(in.readUTF());
				tagColorG.setText(in.readUTF());
				tagColorB.setText(in.readUTF());
				tagFont.setText(in.readUTF());
				
				metaPositionX.setText(in.readUTF());
				metaPositionY.setText(in.readUTF());
				metaColorR.setText(in.readUTF());
				metaColorG.setText(in.readUTF());
				metaColorB.setText(in.readUTF());
				metaFont.setText(in.readUTF());
				
				in.close();
				JOptionPane.showMessageDialog(this, "Presets loaded successfully.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "There was an error loading this file.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "That file does not exist.");
		}
	}
	
}
