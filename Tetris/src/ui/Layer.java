package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 *绘制窗口 
 *
 * @author Anko learning from xiaoE
 */

public abstract class Layer {
	/**
	 * 内边距
	 */
	protected static final int PADDING;
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;	
	
	private int WINDOW_W = Img.WINDOW.getWidth(null);
	
	private int WINDOW_H = Img.WINDOW.getHeight(null);
	
	/**
	 * 数字切片的宽度
	 */
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
	/**
	 * 数字切片的高度
	 */
	private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);
	/**
	 * 矩形值槽高度
	 */
	protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
	/**
	 * 矩形值槽图片宽度
	 */
	private static final int IMG_RECT_W = Img.RECT.getWidth(null);
	/**
	 * 矩形值槽宽度
	 */
	private int RECT_W ;
	/**
	 * 默认字体
	 */
	private static final Font DEF_FONT = new Font("黑体",Font.BOLD,20);
	static{
		// 获得游戏配置
    	FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	/**
	 * 窗口左上角x坐标
	 */
	
	protected final int x;
   
   /**
	 * 窗口左上角y坐标
	 */
   
   protected final int y;
   
   /**
    * 窗口宽度
    */
   
   protected final int w;
   
   /**
    * 窗口高度
    */
   
   protected final int h;
   
   /**
    * 游戏数据
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
    * 绘制窗口
    */
   
    protected void createWindow(Graphics g){
    	// 左上
    	g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
    	// 中上
    	g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W	- BORDER, BORDER, null);
    	// 右上
    	g.drawImage(Img.WINDOW, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0,	WINDOW_W, BORDER, null);
    	// 左中
    	g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER,WINDOW_H - BORDER, null);
    	// 中中
    	g.drawImage(Img.WINDOW, x+BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER,	WINDOW_H - BORDER, null);
    	// 右中
    	g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W ,WINDOW_H - BORDER, null);
    	// 下左
    	g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER,WINDOW_H, null);
    	// 下中
    	g.drawImage(Img.WINDOW, x+BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER,	WINDOW_H, null);
    	// 下右
    	g.drawImage(Img.WINDOW, x + w - BORDER,  y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W,WINDOW_H, null);
    }
    /**
     * 创新游戏具体内容
     * 
     * @author Anko
     * @param g  画笔
     */

    abstract public void paint(Graphics g);

	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * 显示数字
	 * 
	 * @param x            左上角x坐标
	 * @param y            左上角y坐标
	 * @param maxCount            数字位数
	 * @param num            要显示的数字
	 * @param g            画笔对象
	 */

	protected void drawNumberLeftPad(int x, int y, int num, int maxCount,
			Graphics g) {
		// 把数字number中的每一位取出
		String strNum = Integer.toString(num);
		//循环绘制数字右对齐
		for (int i = 0; i < maxCount; i++) {
			//判断是否满足绘制条件
			if (maxCount - i <= strNum.length()) {
				//获得数字在字符串中的下标
				int idx = i + strNum.length()-maxCount;
				//把数字number中的每一位取出
				int bit = strNum.charAt(idx) - '0';
				//绘制数字
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
		// 循环绘制数字右对齐
		for (int i = 0; i < maxCount; i++) {
			// 判断是否满足绘制条件
			if (maxCount - i <= number.length()) {
				// 获得数字在字符串中的下标
				int idx = i + number.length() - maxCount;
				// 把数字number中的每一位取出
				String bitString = String.valueOf(number.charAt(idx));
				// 绘制数字
				g.drawString(bitString, rectX + 240+i*10, rectY + 23);
			}
		}
	}
	/**
	 * 绘制值槽
	 */
	protected void drawRect(int y,String title,String number,double percent,Graphics g){
		//各种值初始化
		int rectX = this.x+PADDING;
		int rectY = this.y +y;
		//绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(rectX, rectY, RECT_W, IMG_RECT_H+2);
		g.setColor(Color.WHITE);
		g.fillRect(rectX+3, rectY+3,RECT_W-6, IMG_RECT_H-4);
		g.setColor(Color.BLACK);
		g.fillRect(rectX+5,rectY+5, RECT_W-10, IMG_RECT_H-8);
		//绘制值槽
		//求出宽度
		int w = (int)((RECT_W-10)*percent);
		//求出颜色
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
	 * 正中绘图
	 */
	protected void drawImageAtCenter(Image img,Graphics g){
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		int imgX = this.x+(this.w-imgW>>1);
		int imgY = this.y+(this.h-imgH>>1);
		g.drawImage(img, imgX, imgY, null);
		
	}
}
