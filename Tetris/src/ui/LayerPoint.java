package ui;

import java.awt.Color;
import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	/**
	 * 分数的最大位数
	 */
	private static final int POINT_BIT = 5;

	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP =GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * 消行y坐标
	 */
	private  final int emLineY;
	/**
	 * 分数y坐标
	 */
	private  final int pointY;
	/**
	 * 消行x坐标
	 */
	private final int comX;
	/**
	 * 经验值y坐标
	 */
	private final int expY;

	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//初始化共同的X坐标
		this.comX = this.w - ( IMG_NUMBER_W * POINT_BIT ) - PADDING;
		//初始化分数显示的Y坐标
		this.pointY= PADDING;
		//初始化消行显示的Y坐标
		this.emLineY= this.pointY+Img.POINT.getHeight(null)+ PADDING;
		//初始化经验值显示的Y坐标
		this.expY = this.emLineY+Img.RMLINE.getHeight(null)+PADDING;
		
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		// 窗口标题(分数)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		//显示分数
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		// 窗口标题(消行)
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + emLineY, null);
		//显示消行
		this.drawNumberLeftPad(comX, emLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		//绘制值槽（经验值）
		int rmline = this.dto.getNowRemoveLine();
		this.drawRect(expY,"下一级",null,(double)(rmline%LEVEL_UP)/(double)(LEVEL_UP),g);
		
	}
	
	//其实我挺喜欢这个方法的_(:3」∠)_
	@SuppressWarnings("unused")
	@Deprecated
	 private Color getNowColor(double hp,double maxUp){
		int colorR = 0;
		int colorG = 0;
		int colorB = 0;
		double hpHalf = maxUp/2;
		if(hp>hpHalf){
			colorR = 255-(int)((hp-hpHalf)/(maxUp/2)*255);
			colorG = 255;
		}else{
			colorR = 255;
			colorG = (int)(hp/(maxUp/2)*255);
		}
		return new Color(colorR, colorG, colorB);
	 }
	
	
	
	
}
