package ui;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.PlayerControl;
import dto.GameDto;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {

	private List<Layer> layers = null;
	private GameDto dto = new GameDto();
	public JPanelGame(GameDto dto) {
		//获得dto对象
		this.dto = dto;
		//初始化组件
		initComonent();
		//初始化层
		initLayer();
	}
	/**
	 * 安装玩家控制器
	 */
	public void setGameControl(PlayerControl playerControl){
		this.addKeyListener(playerControl);
	}
	/**
	 * 初始化组件
	 */
	private void initComonent(){
		
	}
	
	/**
	 * 初始化层
	 */
	private void initLayer(){
		try {
			// 获得游戏配置
	    	FrameConfig fCfg = GameConfig.getFrameConfig();
			// 获得层配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			// 创建游戏层数组
			layers = new ArrayList<Layer>(layersCfg.size());
			// 创建所有层对象
			for (LayerConfig layerCfg : layersCfg) {
				// 反射（创建对象）
				// 获得类对象
				Class<?> c = Class.forName(layerCfg.getClassName());
				// 获得构造函数
				Constructor<?> ctr = c.getConstructor(int.class, int.class,
						int.class, int.class);
				// 调用构造函数创建类对象
				Layer layer = (Layer) ctr.newInstance(layerCfg.getX(),
						layerCfg.getY(), layerCfg.getW(), layerCfg.getH()
						);
				//设置游戏数据对象
				layer.setDto(this.dto);
				// 把Layer对象放入集合
				layers.add(layer);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		//调用基类方法
		super.paintComponent(g);
		// 绘制游戏界面
		for (int i = 0; i < layers.size(); i++) {
			// 刷新层窗口
			layers.get(i).paint(g);
		}
		//返回焦点
		this.requestFocus();
	}
}
