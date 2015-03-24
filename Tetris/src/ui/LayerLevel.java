package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {
	
	/**
	 * 标题图片的高度
	 */
	private static final int IMG_LEVEL_W = Img.LEVEL.getWidth(null);
	

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题
		int centerX = this.w-IMG_LEVEL_W>>1;
		g.drawImage(Img.LEVEL, this.x + centerX, this.y + PADDING, null);
		// 显示等级
		this.drawNumberLeftPad(16, 64, this.dto.getNowLevel() ,5, g);
	}

}
