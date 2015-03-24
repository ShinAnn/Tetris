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
 * 接收玩家键盘事件
 * 控制画面
 * 控制游戏逻辑
 * 
 * @author  Anko
 *
 */
public class GameControl {
	/**
	 * 数据访问接口A
	 */
	private IData dataA;
	/**
	 * 数据访问接口B
	 */
	private IData dataB;
	/**
	 * 游戏逻辑层
	 */
	private IGameService gameService;
	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;
	
	private ui.cfg.FrameConfig frameConfig;

	/**
	 * 游戏行为控制
	 */
	private Map<Integer,Method> actionList;
	public GameControl(JPanelGame panelGame,GameTetris gameService){
		this.panelGame = panelGame;
		this.gameService = gameService;
		//获得类对象
		this.dataA =createDataObject(GameConfig.getDataConfig().getDataA());
		//设置数据库记录到游戏
		this.gameService.setDbRecode(dataA.loadData());
		//从数据接口B获得本地磁盘记录
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		//设置本地磁盘记录到游戏
		this.gameService.setDiskRecode(dataB.loadData());
		//读取用户控制设置
		this.setControlConfig();
		//
		this.frameConfig = new ui.cfg.FrameConfig(this);
	}
	
	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig(){
		//创建键盘码与方法名的映射数组
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
	 * 创建数据对象
	 * 
	 * @param cfg
	 * @return
	 */
	private IData createDataObject(DataInterfaceConfig cfg){
		
		try {
			//获得类对象
			Class<?> cls= Class.forName(cfg.getClassName());
			//获得构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//创建对象
			return (IData)ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据玩家控制来决定行为
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
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void serOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}
	
}
