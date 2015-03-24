package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	
	private Img() {
	}

	/**
	 * 个人签名
	 */
	public static Image SIGN = new ImageIcon("graphics/string/sign.png").getImage();
	/**
	 * 窗口图片
	 */
	public static Image WINDOW = new ImageIcon("Graphics/window/window.png").getImage();
	/**
	 * 矩形值槽
	 */
	public static Image RECT = new ImageIcon("Graphics/window/rect.png").getImage();
	/**
	 * 数字图片 220 37
	 */
	public static Image NUMBER = new ImageIcon("Graphics/string/num.png").getImage();
	/**
	 * 等级标题图片
	 */
	public static Image LEVEL = new ImageIcon("Graphics/string/level.png").getImage();
	/**
	 * 数据库窗口标题
	 */
	public static Image DB = new ImageIcon("Graphics/string/db.png").getImage();
	/**
	 * 本地记录窗口标题
	 */
	public static Image DISK = new ImageIcon("Graphics/string/disk.png").getImage();
	/**
	 * 方块图片
	 */
	public static Image ACT = new ImageIcon("graphics/game/rect.png").getImage();
	/**
	 * 分数标题图片
	 */
	public static Image POINT = new ImageIcon("graphics/string/point.png").getImage();
	/**
	 * 消行标题图片
	 */
	public static Image RMLINE = new ImageIcon("graphics/string/rmline.png").getImage();
	/**
	 * 阴影
	 */
	public static Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	/**
	 * 开始按钮
	 */
	public static ImageIcon BTN_START = new ImageIcon("graphics/string/start.png");
	/**
	 * 设置按钮
	 */
	public static ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	/**
	 * 下一个的图片数组
	 */
	public static  Image[] NEXT_ACT;
	
	public static  List<Image> BG_LIST;

	static{
		//下一个方块图片
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/"+i+".png").getImage();
		}
		//背景图片数组
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			if(file.isDirectory()){
				continue;
			}
			BG_LIST.add(new ImageIcon(file.getPath()).getImage());
		}
	}
}
