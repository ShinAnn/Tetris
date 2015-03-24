package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import service.GameTetris;
import service.IGameService;
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
	 * ��Ϸ�߼���
	 */
	private IGameService gameService;
	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;
	
	private ui.cfg.FrameConfig frameConfig;

	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer,Method> actionList;
	public GameControl(JPanelGame panelGame,GameTetris gameService){
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
		//��ȡ�û���������
		this.setControlConfig();
		//
		this.frameConfig = new ui.cfg.FrameConfig(this);
	}
	
	/**
	 * ��ȡ�û���������
	 */
	private void setControlConfig(){
		//�����������뷽������ӳ������
		this.actionList = new HashMap<Integer, Method>();
		
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer,String> cfgSet= (HashMap<Integer, String>)ois.readObject();
			Set<Entry<Integer,String>> entrySet =  cfgSet.entrySet();
			for (Entry<Integer, String> entry : entrySet) {
				this.actionList.put(entry.getKey(), this.gameService.getClass().getDeclaredMethod(entry.getValue()));
			}
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
	/**
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * �Ӵ��ڹر��¼�
	 */
	public void serOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}
	
}
