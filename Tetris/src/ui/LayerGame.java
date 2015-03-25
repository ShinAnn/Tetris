package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;

public class LayerGame extends Layer {

	

	/**
	 * 左位移偏移量
	 */
	private static final int ACT_SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();

	private static final int LEFT_SIZE = GameConfig.getSystemConfig().getMinX();

	private static final int RIGHT_SIZE = GameConfig.getSystemConfig().getMaxX();

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 获得方块数组集合
		Point[] points = this.dto.getGameAct().getActPoints();
		//绘制阴影 
		this.drawShadow(true,points,g);
		// 获得方块类型编号(0-6)
		int typeCode = this.dto.getGameAct().getTypeCode();
		// 打印方块
		for (int i = 0; i < points.length; i++) {
			drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
		// 绘制地图
		boolean[][] map = this.dto.getGameMap();
		//计算当前堆积颜色
		int lv = this.dto.getNowLevel();
		//如果输了，imgIdx = 8；
		int imgIdx = lv == 0 ? 0 : (lv-1) % 7+1;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					// 打印方块
					drawActByPoint(x, y, imgIdx, g);
				}
			}
		}
	}
	/**
	 * 绘制阴影
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
	 * 绘制正方形块
	 */
	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		g.drawImage(Img.ACT, this.x + (x << ACT_SIZE_ROL) + 5, this.y
				+ (y << ACT_SIZE_ROL) + 5, this.x + (x << ACT_SIZE_ROL)
				+ (1 << ACT_SIZE_ROL) + 5, this.y + (y << ACT_SIZE_ROL)
				+ (1 << ACT_SIZE_ROL) + 5, imgIdx << ACT_SIZE_ROL, 0,
				(imgIdx + 1) << ACT_SIZE_ROL, 1 << ACT_SIZE_ROL, null);
	}
}
