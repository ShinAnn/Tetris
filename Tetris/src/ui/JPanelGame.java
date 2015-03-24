package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {
	private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	private List<Layer> layers = null;
	private GameDto dto = new GameDto();
	private JButton btnStart;
	private JButton btnConfig;
	private GameControl gameControl = null;
	
	public JPanelGame(GameDto dto) {
		//���dto����
		this.dto = dto;
		//���ò��ֹ�����Ϊ���ɲ���
		this.setLayout(null);
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
		// ��ʼ����ʼ��ť
		this.btnStart = new JButton(Img.BTN_START);
		// ���ÿ�ʼ��ťλ��
		this.btnStart.setBounds(GameConfig.getFrameConfig().getButtonConfig()
				.getStartX(), GameConfig.getFrameConfig().getButtonConfig()
				.getStartY(), BTN_SIZE_W, BTN_SIZE_H);
		// ��Ӱ�ť�����
		this.add(btnStart);
		// ��ʼ�����ð�ť
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		// ���ÿ�ʼ��ťλ��
		this.btnConfig.setBounds(GameConfig.getFrameConfig().getButtonConfig()
				.getUserConfigX(), GameConfig.getFrameConfig()
				.getButtonConfig().getUserConfigY(), BTN_SIZE_W, BTN_SIZE_H);
		this.btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
		// ��Ӱ�ť�����
		this.add(btnConfig);
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
	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}
}
