package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {

	

	/**
	 * 左位移偏移量
	 */
	private static final int ACT_SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();

	private static final int LEFT_SIZE = GameConfig.getSystemConfig().getMinX();

	private static final int RIGHT_SIZE = GameConfig.getSystemConfig().getMaxX();

	private static final int LOSE_IDX = GameConfig.getFrameConfig().getLoseIdx();
	
	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 获得方块数组集合
		GameAct act = this.dto.getGameAct();
		if (act != null) {
			Point[] points = act.getActPoints();
			// 绘制阴影
			this.drawShadow(points, g);
			// 绘制活动方块
			this.drawMainAct(points, g);
		}
		// 绘制游戏地图
		this.drawMap(g);
		//暂停
		if(this.dto.isPause()){
			this.drawImageAtCenter(Img.PAUSE, g);
		}
	}
	
	/**
	 * 绘制活动方块
	 */
	private void drawMainAct(Point[] points,Graphics g) {

		// 获得方块类型编号(0-6)
		int typeCode = this.dto.getGameAct().getTypeCode();

		// 打印方块
		for (int i = 0; i < points.length; i++) {
			this.drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
	}

	/**
	 * 绘制地图
	 */
	private void drawMap(Graphics g) {
		// 绘制地图
		boolean[][] map = this.dto.getGameMap();
		// 计算当前堆积颜色
		int lv = this.dto.getNowLevel();
		// 如果输了
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					// 打印方块
					this.drawActByPoint(x, y, imgIdx, g);
				}
			}
		}
	}


	/**
	 * 绘制阴影
	 * @param g 
	 */
	private void drawShadow(Point[] points, Graphics g) {
		
		if(!this.dto.isShowShadow()){
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
		imgIdx = this.dto.isStart()?imgIdx:LOSE_IDX;
		g.drawImage(Img.ACT, 
				this.x + (x << ACT_SIZE_ROL) + BORDER, 
				this.y+ (y << ACT_SIZE_ROL) + BORDER, 
				this.x + (x << ACT_SIZE_ROL)	+ (1 << ACT_SIZE_ROL) + BORDER, 
				this.y + (y << ACT_SIZE_ROL)	+ (1 << ACT_SIZE_ROL) + BORDER, 
				imgIdx << ACT_SIZE_ROL, 0,(imgIdx + 1) << ACT_SIZE_ROL, 1 << ACT_SIZE_ROL, null);
	}
}
