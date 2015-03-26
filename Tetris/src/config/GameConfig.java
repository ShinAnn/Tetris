package config;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��Ϸ������
 * 
 * @author Anko
 *
 */

@SuppressWarnings({ "serial", "resource" })
public class GameConfig implements Serializable{
	private static FrameConfig FRAME_CONFIG = null;

	private static SystemConfig SYSTEM_CONFIG = null;

	private static DataConfig DATA_CONFIG = null;
	
	private static final boolean IS_DEBUG = false;

	static {

		try {
			if(IS_DEBUG){
				// ����XML�ļ���ȡ��
				SAXReader reader = new SAXReader();
				// ��ȡXML�ļ�
				Document doc;
				doc = reader.read("data/cfg.xml");
				// ���XML�ļ��ĸ��ڵ�
				Element game = doc.getRootElement();
				// �����������ö���
				FRAME_CONFIG = new FrameConfig(game.element("frame"));
				// �������ݷ������ö���
				DATA_CONFIG = new DataConfig(game.element("data"));
				// ����ϵͳ����
				SYSTEM_CONFIG = new SystemConfig(game.element("system"));
			}else{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/frameConfig.dat"));
				FRAME_CONFIG = (FrameConfig)ois.readObject();
				 ois = new ObjectInputStream(new FileInputStream("data/systemConfig.dat"));
				SYSTEM_CONFIG = (SystemConfig)ois.readObject();
				 ois = new ObjectInputStream(new FileInputStream("data/dataConfig.dat"));
				DATA_CONFIG = (DataConfig)ois.readObject();
				ois.close();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ������˽�л�
	 */
	private GameConfig() {
	}
	/**
	 * ��ô�������
	 */
	public static FrameConfig getFrameConfig() {
		return FRAME_CONFIG;
	}
	/**
	 * ���ϵͳ����
	 */
	public static SystemConfig getSystemConfig() {
		return SYSTEM_CONFIG;
	}
	/**
	 * ������ݷ�������
	 */
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
//	
//	public static void main(String[] args) throws Exception {
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/frameConfig.dat"));
//		oos.writeObject(FRAME_CONFIG);
//		 oos = new ObjectOutputStream(new FileOutputStream("data/systemConfig.dat"));
//		oos.writeObject(SYSTEM_CONFIG);
//		 oos = new ObjectOutputStream(new FileOutputStream("data/dataConfig.dat"));
//		oos.writeObject(DATA_CONFIG);
//		oos.close();
//	}

}
