package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * ֵ���⾶
	 */
	private static final int RECT_H = IMG_RECT_H + 2;
	/**
	 * ��ʼy����
	 */
	private static int START_Y;
	/**
	 * ���
	 */
	private static int SPA;
	// ���������
	private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		// �����¼���Ƽ��
		SPA = (this.h - RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null))/ MAX_ROW;
		// ������ʼy����
		START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}

	@Override
	public abstract void paint(Graphics g); 
	/**
	 * ���Ƹô�������ֵ��
	 * 
	 * @param imgTitle ����ͼƬ
	 * @param players ����Դ
	 * @param g ����
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		// ���Ʊ���
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		// ������ڷ���
		int nowPoint = this.dto.getNowPoint();
		// ѭ�����Ƽ�¼
		for (int i = 0; i < MAX_ROW; i++) {
			// ���һ����Ҽ�¼
			Player pla = players.get(i);
			// ��ø÷���
			int recodePoint = pla.getPoint();
			// �������ڷ����ͼ�¼������ֵ
			double percent = (double) nowPoint / pla.getPoint();
			// ������Ƽ�¼����ֵ��Ϊ100%
			percent = percent > 1 ? 1.0 : percent;
			//���ݿ����Ϊ0
			if(imgTitle == Img.DB){
				percent=0;
			}
			// ���Ƶ�����¼
			String strPoint = recodePoint == 0 ? null : Integer.toString(recodePoint);
			this.drawRect(START_Y + i * (RECT_H + SPA), pla.getName(),
					strPoint, percent, g);
		}


	}

}
