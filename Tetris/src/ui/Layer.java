package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 *���ƴ��� 
 *
 * @author Anko learning from xiaoE
 */

public abstract class Layer {
	/**
	 * �ڱ߾�
	 */
	protected static final int PADDING;
	/**
	 * �߿���
	 */
	protected static final int BORDER;	
	
	private int WINDOW_W = Img.WINDOW.getWidth(null);
	
	private int WINDOW_H = Img.WINDOW.getHeight(null);
	
	/**
	 * ������Ƭ�Ŀ��
	 */
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
	/**
	 * ������Ƭ�ĸ߶�
	 */
	private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);
	/**
	 * ����ֵ�۸߶�
	 */
	protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
	/**
	 * ����ֵ��ͼƬ���
	 */
	private static final int IMG_RECT_W = Img.RECT.getWidth(null);
	/**
	 * ����ֵ�ۿ��
	 */
	private int RECT_W ;
	/**
	 * Ĭ������
	 */
	private static final Font DEF_FONT = new Font("����",Font.BOLD,20);
	static{
		// �����Ϸ����
    	FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	/**
	 * �������Ͻ�x����
	 */
	
	protected final int x;
   
   /**
	 * �������Ͻ�y����
	 */
   
   protected final int y;
   
   /**
    * ���ڿ��
    */
   
   protected final int w;
   
   /**
    * ���ڸ߶�
    */
   
   protected final int h;
   
   /**
    * ��Ϸ����
    */
   protected GameDto dto = null;
   
   protected Layer(int x,int y,int w,int h){
	   this.x = x;
	   this.y = y;
	   this.w = w;
	   this.h = h;
	   this.RECT_W = this.w - (PADDING<<1);
   }
   
   /**
    * ���ƴ���
    */
   
    protected void createWindow(Graphics g){
    	// ����
    	g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W	- BORDER, BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0,	WINDOW_W, BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER,WINDOW_H - BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x+BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER,	WINDOW_H - BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W ,WINDOW_H - BORDER, null);
    	// ����
    	g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER,WINDOW_H, null);
    	// ����
    	g.drawImage(Img.WINDOW, x+BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER,	WINDOW_H, null);
    	// ����
    	g.drawImage(Img.WINDOW, x + w - BORDER,  y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W,WINDOW_H, null);
    }
    /**
     * ������Ϸ��������
     * 
     * @author Anko
     * @param g  ����
     */

    abstract public void paint(Graphics g);

	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * ��ʾ����
	 * 
	 * @param x            ���Ͻ�x����
	 * @param y            ���Ͻ�y����
	 * @param maxCount            ����λ��
	 * @param num            Ҫ��ʾ������
	 * @param g            ���ʶ���
	 */

	protected void drawNumberLeftPad(int x, int y, int num, int maxCount,
			Graphics g) {
		// ������number�е�ÿһλȡ��
		String strNum = Integer.toString(num);
		//ѭ�����������Ҷ���
		for (int i = 0; i < maxCount; i++) {
			//�ж��Ƿ������������
			if (maxCount - i <= strNum.length()) {
				//����������ַ����е��±�
				int idx = i + strNum.length()-maxCount;
				//������number�е�ÿһλȡ��
				int bit = strNum.charAt(idx) - '0';
				//��������
				g.drawImage(Img.NUMBER, 
						this.x + x + IMG_NUMBER_W  * i,this.y + y,
						this.x + x + IMG_NUMBER_W  * (i + 1),	this.y + y + IMG_NUMBER_H ,
						bit * IMG_NUMBER_W, 0,
						(bit + 1) * IMG_NUMBER_W, IMG_NUMBER_H, null);
			}
		}
	}

	protected void drawNumberLeftPad(int rectX, int rectY, String number,
			int maxCount, Graphics g) {
		// ѭ�����������Ҷ���
		for (int i = 0; i < maxCount; i++) {
			// �ж��Ƿ������������
			if (maxCount - i <= number.length()) {
				// ����������ַ����е��±�
				int idx = i + number.length() - maxCount;
				// ������number�е�ÿһλȡ��
				String bitString = String.valueOf(number.charAt(idx));
				// ��������
				g.drawString(bitString, rectX + 240+i*10, rectY + 23);
			}
		}
	}
	/**
	 * ����ֵ��
	 */
	protected void drawRect(int y,String title,String number,double percent,Graphics g){
		//����ֵ��ʼ��
		int rectX = this.x+PADDING;
		int rectY = this.y +y;
		//���Ʊ���
		g.setColor(Color.BLACK);
		g.fillRect(rectX, rectY, RECT_W, IMG_RECT_H+2);
		g.setColor(Color.WHITE);
		g.fillRect(rectX+3, rectY+3,RECT_W-6, IMG_RECT_H-4);
		g.setColor(Color.BLACK);
		g.fillRect(rectX+5,rectY+5, RECT_W-10, IMG_RECT_H-8);
		//����ֵ��
		//������
		int w = (int)((RECT_W-10)*percent);
		//�����ɫ
		int subIdx = (int)(percent*(IMG_RECT_W-1));
		g.drawImage(Img.RECT,
				rectX+5,rectY+5, 
				rectX+5+w, rectY+IMG_RECT_H-3,
				subIdx, 0, subIdx+1, IMG_RECT_H, 
				null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rectX+6, rectY+23);
		if(number != null){
			drawNumberLeftPad(rectX, rectY, number, 5, g);
		}

	}
	/**
	 * ���л�ͼ
	 */
	protected void drawImageAtCenter(Image img,Graphics g){
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		int imgX = this.x+(this.w-imgW>>1);
		int imgY = this.y+(this.h-imgH>>1);
		g.drawImage(img, imgX, imgY, null);
		
	}
}
