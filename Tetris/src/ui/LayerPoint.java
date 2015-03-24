package ui;

import java.awt.Color;
import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	/**
	 * ���������λ��
	 */
	private static final int POINT_BIT = 5;

	/**
	 * ��������
	 */
	private static final int LEVEL_UP =GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * ����y����
	 */
	private  final int emLineY;
	/**
	 * ����y����
	 */
	private  final int pointY;
	/**
	 * ����x����
	 */
	private final int comX;
	/**
	 * ����ֵy����
	 */
	private final int expY;

	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//��ʼ����ͬ��X����
		this.comX = this.w - ( IMG_NUMBER_W * POINT_BIT ) - PADDING;
		//��ʼ��������ʾ��Y����
		this.pointY= PADDING;
		//��ʼ��������ʾ��Y����
		this.emLineY= this.pointY+Img.POINT.getHeight(null)+ PADDING;
		//��ʼ������ֵ��ʾ��Y����
		this.expY = this.emLineY+Img.RMLINE.getHeight(null)+PADDING;
		
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		// ���ڱ���(����)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		// ���ڱ���(����)
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + emLineY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, emLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		//����ֵ�ۣ�����ֵ��
		int rmline = this.dto.getNowRemoveLine();
		this.drawRect(expY,"��һ��",null,(double)(rmline%LEVEL_UP)/(double)(LEVEL_UP),g);
		
	}
	
	//��ʵ��ͦϲ�����������_(:3����)_
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
