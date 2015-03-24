package control;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import service.GameService;
import ui.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.IData;

/**
 * ������Ҽ����¼�
 * ���ƻ���
 * ������Ϸ�߼�
 * 
 * @author  Anko
 *
 */
public class GameControl {
	/**
	 * ���ݷ��ʽӿ�A
	 */
	private IData dataA;
	/**
	 * ���ݷ��ʽӿ�B
	 */
	private IData dataB;
	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;
	
	/**
	 * ��Ϸ�߼���
	 */
	private GameService gameService;
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer,Method> actionList;
	public GameControl(JPanelGame panelGame,GameService gameService){
		this.panelGame = panelGame;
		this.gameService = gameService;
		//��������
		this.dataA =createDataObject(GameConfig.getDataConfig().getDataA());
		//�������ݿ��¼����Ϸ
		this.gameService.setDbRecode(dataA.loadData());
		//�����ݽӿ�B��ñ��ش��̼�¼
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		//���ñ��ش��̼�¼����Ϸ
		this.gameService.setDiskRecode(dataB.loadData());
		//��ʼ����Ϸ��Ϊ
		this.actionList = new HashMap<Integer, Method>();
		
		try {
			actionList.put(37, this.gameService.getClass().getMethod("keyLeft"));
			actionList.put(38, this.gameService.getClass().getMethod("keyUp"));
			actionList.put(39, this.gameService.getClass().getMethod("keyRight"));
			actionList.put(40, this.gameService.getClass().getMethod("keyDown"));
			actionList.put(70, this.gameService.getClass().getMethod("testLevelUp"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * �������ݶ���
	 * 
	 * @param cfg
	 * @return
	 */
	private IData createDataObject(DataInterfaceConfig cfg){
		
		try {
			//��������
			Class<?> cls= Class.forName(cfg.getClassName());
			//��ù�����
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//��������
			return (IData)ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * ������ҿ�����������Ϊ
	 */
	public void actionByKeyCode(int keyCode) {
		
		try {
			if(this.actionList.containsKey(keyCode)){
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.panelGame.repaint();
	}
	
}
