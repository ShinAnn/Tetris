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
import ui.JFrameGame;
import ui.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.IData;
import dto.GameDto;

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
	/**
	 * ��Ϸ���ƴ���
	 */
	private ui.cfg.FrameConfig frameConfig;
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer,Method> actionList;
	/**
	 * ��Ϸ�߳�
	 */
	private Thread gameThread = null;
	/**
	 * ��Ϸ����Դ
	 */
	private GameDto dto = null;
	public GameControl(){
		//������Ϸ����Դ
		this.dto = new GameDto();
		//������Ϸ�߼��飨������Ϸ����Դ��
		this.gameService = new GameTetris(dto);
		//�������ݽӿ�A����
		this.dataA =createDataObject(GameConfig.getDataConfig().getDataA());
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());		
		//�����ݽӿ�B��ñ��ش��̼�¼
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		//���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		//������Ϸ���
		this.panelGame = new JPanelGame(this,dto);
		//��ȡ�û���������
		this.setControlConfig();
		//��ʼ���û����ô���
		this.frameConfig = new ui.cfg.FrameConfig(this);
		//��ʼ����Ϸ���ڣ���װ��Ϸ���
		new JFrameGame(this.panelGame);
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
	/**
	 * ��ʼ��ť�¼�
	 */
	public void start() {
		//��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		// �����̶߳���
		this.gameThread = new Thread() {
			@Override
			public void run() {
				// ˢ�»���
				panelGame.repaint();
				//��ѭ��
				while (true) {
					try {
						// �ȴ�0.5��
						Thread.sleep(500);
						// ��������
						gameService.mainAction();
						// ˢ�»���
						panelGame.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
     	//�����߳�
     	this.gameThread.start();
     	//ˢ�»���
     	this.panelGame.repaint();
	}

}
