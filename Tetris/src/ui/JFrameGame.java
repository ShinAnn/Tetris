package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame{
    public JFrameGame(JPanelGame panelGame){
    	// �����Ϸ����
    	FrameConfig fCfg = GameConfig.getFrameConfig();
    	//���ñ���
    	this.setTitle(fCfg.getTitle());
    	//����Ĭ�Ϲر����ԣ����������
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //���ô��ڴ�С
    	this.setSize(fCfg.getWidth(), fCfg.getHeight());
    	//�������û��ı䴰�ڴ�С
    	this.setResizable(false);
    	//���ھ���
    	Toolkit toolkit =  Toolkit.getDefaultToolkit();
    	Dimension screen =  toolkit.getScreenSize();
    	int x = screen.width-this.getWidth()>>1;
    	int y = (screen.height-this.getHeight()>>1)-fCfg.getWindowUp();
    	this.setLocation(x,y);
    	//����Ĭ��Panel
    	this.setContentPane(panelGame);
    	//Ĭ�ϴ���Ϊ��ʾ
    	this.setVisible(true);
    }
}
