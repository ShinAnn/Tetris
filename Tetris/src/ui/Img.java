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
	 * ����ǩ��
	 */
	public static Image SIGN = new ImageIcon("graphics/string/sign.png").getImage();
	/**
	 * ����ͼƬ
	 */
	public static Image WINDOW = new ImageIcon("Graphics/window/window.png").getImage();
	/**
	 * ����ֵ��
	 */
	public static Image RECT = new ImageIcon("Graphics/window/rect.png").getImage();
	/**
	 * ����ͼƬ 220 37
	 */
	public static Image NUMBER = new ImageIcon("Graphics/string/num.png").getImage();
	/**
	 * �ȼ�����ͼƬ
	 */
	public static Image LEVEL = new ImageIcon("Graphics/string/level.png").getImage();
	/**
	 * ���ݿⴰ�ڱ���
	 */
	public static Image DB = new ImageIcon("Graphics/string/db.png").getImage();
	/**
	 * ���ؼ�¼���ڱ���
	 */
	public static Image DISK = new ImageIcon("Graphics/string/disk.png").getImage();
	/**
	 * ����ͼƬ
	 */
	public static Image ACT = new ImageIcon("graphics/game/rect.png").getImage();
	/**
	 * ��������ͼƬ
	 */
	public static Image POINT = new ImageIcon("graphics/string/point.png").getImage();
	/**
	 * ���б���ͼƬ
	 */
	public static Image RMLINE = new ImageIcon("graphics/string/rmline.png").getImage();
	/**
	 * ��Ӱ
	 */
	public static Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	/**
	 * ��ʼ��ť
	 */
	public static ImageIcon BTN_START = new ImageIcon("graphics/string/start.png");
	/**
	 * ���ð�ť
	 */
	public static ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	/**
	 * ��һ����ͼƬ����
	 */
	public static  Image[] NEXT_ACT;
	
	public static  List<Image> BG_LIST;

	static{
		//��һ������ͼƬ
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/"+i+".png").getImage();
		}
		//����ͼƬ����
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
