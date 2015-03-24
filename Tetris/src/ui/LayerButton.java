package ui;

import java.awt.Graphics;

public class LayerButton extends Layer {
	
	//private static Image IMG_DB = new ImageIcon("Graphics/string/db.png").getImage();
	
	public LayerButton(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		//g.drawImage(IMG_DB, this.x+32, this.y+32, null);
	}

}
