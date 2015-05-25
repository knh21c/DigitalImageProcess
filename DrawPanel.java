import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class DrawPanel extends JPanel{
	int[][] imageR, imageG, imageB;
	int width, height;
	
	public DrawPanel(){
		super();
	}
	
	public DrawPanel(ImageClass image){
		this.imageR = image.getImageR();
		this.imageG = image.getImageG();
		this.imageB = image.getImageB();
		this.width = image.getImageWidth();
		this.height = image.getImageHeight();
		setSize(width, height);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int R, G, B, i, j;
		for (i = 0; i < width; i++) {
			for (j =0; j < height; j++) {
				R = imageR[i][j];
				G = imageG[i][j];
				B = imageB[i][j];
				g.setColor(new Color(R, G, B));
				g.drawRect(i, j, 1, 1);
			}
		}
	}
}
