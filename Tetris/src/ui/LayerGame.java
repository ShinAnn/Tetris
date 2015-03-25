package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;

public class LayerGame extends Layer {

	

	/**
	 * ��λ��ƫ����
	 */
	private static final int ACT_SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();

	private static final int LEFT_SIZE = GameConfig.getSystemConfig().getMinX();

	private static final int RIGHT_SIZE = GameConfig.getSystemConfig().getMaxX();

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// ��÷������鼯��
		Point[] points = this.dto.getGameAct().getActPoints();
		//������Ӱ 
		this.drawShadow(true,points,g);
		// ��÷������ͱ��(0-6)
		int typeCode = this.dto.getGameAct().getTypeCode();
		// ��ӡ����
		for (int i = 0; i < points.length; i++) {
			drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
		// ���Ƶ�ͼ
		boolean[][] map = this.dto.getGameMap();
		//���㵱ǰ�ѻ���ɫ
		int lv = this.dto.getNowLevel();
		//������ˣ�imgIdx = 8��
		int imgIdx = lv == 0 ? 0 : (lv-1) % 7+1;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					// ��ӡ����
					drawActByPoint(x, y, imgIdx, g);
				}
			}
		}
	}
	/**
	 * ������Ӱ
	 * @param g 
	 */
	private void drawShadow(boolean isShowShadow, Point[] points, Graphics g) {
		if(!isShowShadow){
			return;
		}
		int leftX = RIGHT_SIZE;
		int rightX = LEFT_SIZE;
		for (Point point : points) {
			leftX = point.x < leftX ? point.x : leftX;
			rightX = point.x > rightX ? point.x : rightX;
		}
		g.drawImage(Img.SHADOW, this.x+BORDER+(leftX<<ACT_SIZE_ROL), this.y+BORDER, 
				(rightX-leftX+1)<<ACT_SIZE_ROL, this.h-(BORDER<<1), null);
	}

	/**
	 * ���������ο�
	 */
	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		g.drawImage(Img.ACT, this.x + (x << ACT_SIZE_ROL) + 5, this.y
				+ (y << ACT_SIZE_ROL) + 5, this.x + (x << ACT_SIZE_ROL)
				+ (1 << ACT_SIZE_ROL) + 5, this.y + (y << ACT_SIZE_ROL)
				+ (1 << ACT_SIZE_ROL) + 5, imgIdx << ACT_SIZE_ROL, 0,
				(imgIdx + 1) << ACT_SIZE_ROL, 1 << ACT_SIZE_ROL, null);
	}
}
