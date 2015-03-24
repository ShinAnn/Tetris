package config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��Ϸ������
 * 
 * @author Anko
 *
 */

public class GameConfig {
	private static FrameConfig FRAME_CONFIG = null;

	private static SystemConfig SYSTEM_CONFIG = null;

	private static DataConfig DATA_CONFIG = null;

	static {

		try {
			// ����XML�ļ���ȡ��
			SAXReader reader = new SAXReader();
			// ��ȡXML�ļ�
			Document doc;
			doc = reader.read("config/cfg.xml");
			// ���XML�ļ��ĸ��ڵ�
			Element game = doc.getRootElement();
			// �����������ö���
			FRAME_CONFIG = new FrameConfig(game.element("frame"));
			// �������ݷ������ö���
			DATA_CONFIG = new DataConfig(game.element("data"));
			// ����ϵͳ����
			SYSTEM_CONFIG = new SystemConfig(game.element("system"));

		} catch (DocumentException e) {
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

}