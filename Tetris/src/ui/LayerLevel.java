package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {
	
	/**
	 * ����ͼƬ�ĸ߶�
	 */
	private static final int IMG_LEVEL_W = Img.LEVEL.getWidth(null);
	

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// ���ڱ���
		int centerX = this.w-IMG_LEVEL_W>>1;
		g.drawImage(Img.LEVEL, this.x + centerX, this.y + PADDING, null);
		// ��ʾ�ȼ�
		this.drawNumberLeftPad(16, 64, this.dto.getNowLevel() ,5, g);
	}

}
