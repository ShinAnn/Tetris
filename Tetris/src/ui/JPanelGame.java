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
		//���dto����
		this.dto = dto;
		//��ʼ�����
		initComonent();
		//��ʼ����
		initLayer();
	}
	/**
	 * ��װ��ҿ�����
	 */
	public void setGameControl(PlayerControl playerControl){
		this.addKeyListener(playerControl);
	}
	/**
	 * ��ʼ�����
	 */
	private void initComonent(){
		
	}
	
	/**
	 * ��ʼ����
	 */
	private void initLayer(){
		try {
			// �����Ϸ����
	    	FrameConfig fCfg = GameConfig.getFrameConfig();
			// ��ò�����
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			// ������Ϸ������
			layers = new ArrayList<Layer>(layersCfg.size());
			// �������в����
			for (LayerConfig layerCfg : layersCfg) {
				// ���䣨��������
				// ��������
				Class<?> c = Class.forName(layerCfg.getClassName());
				// ��ù��캯��
				Constructor<?> ctr = c.getConstructor(int.class, int.class,
						int.class, int.class);
				// ���ù��캯�����������
				Layer layer = (Layer) ctr.newInstance(layerCfg.getX(),
						layerCfg.getY(), layerCfg.getW(), layerCfg.getH()
						);
				//������Ϸ���ݶ���
				layer.setDto(this.dto);
				// ��Layer������뼯��
				layers.add(layer);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		//���û��෽��
		super.paintComponent(g);
		// ������Ϸ����
		for (int i = 0; i < layers.size(); i++) {
			// ˢ�²㴰��
			layers.get(i).paint(g);
		}
		//���ؽ���
		this.requestFocus();
	}
}
