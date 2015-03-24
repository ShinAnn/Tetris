package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame{
    public JFrameGame(JPanelGame panelGame){
    	// 获得游戏配置
    	FrameConfig fCfg = GameConfig.getFrameConfig();
    	//设置标题
    	this.setTitle(fCfg.getTitle());
    	//设置默认关闭属性（程序结束）
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小
    	this.setSize(fCfg.getWidth(), fCfg.getHeight());
    	//不允许用户改变窗口大小
    	this.setResizable(false);
    	//窗口居中
    	Toolkit toolkit =  Toolkit.getDefaultToolkit();
    	Dimension screen =  toolkit.getScreenSize();
    	int x = screen.width-this.getWidth()>>1;
    	int y = (screen.height-this.getHeight()>>1)-fCfg.getWindowUp();
    	this.setLocation(x,y);
    	//设置默认Panel
    	this.setContentPane(panelGame);
    	//默认窗口为显示
    	this.setVisible(true);
    }
}
