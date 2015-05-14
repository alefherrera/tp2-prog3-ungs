package com.turnos.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class JImagePanel extends JPanel {

	  private Image backgroundImage;

	  // Some code to initialize the background image.
	  // Here, we use the constructor to load the image. This
	  // can vary depending on the use case of the panel.
	  public JImagePanel(String fileName) throws IOException {
		  if (fileName.isEmpty()) return;
	    backgroundImage = ImageIO.read(new File(fileName));
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (backgroundImage == null) return;
	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	  }
	  
	  public void changeImage(String fileName) throws IOException{
		  if (fileName == null || fileName.isEmpty()){
			  this.setVisible(false);
			  return;
		  }
		  backgroundImage = ImageIO.read(new File(fileName));
		  this.setVisible(true);
		  revalidate();
		  repaint();
	  }
	  
	}