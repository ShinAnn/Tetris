package ui.window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextCtrl extends JTextField{
	
	private int keyCode;
	private final String methodName;
	
	public TextCtrl(int x,int y, int w, int h,String methodName){
		//设置文本框位置
		this.setBounds(x,y,w,h);

		//初始化方法名
		this.methodName = methodName;
		//增加事件监听
		this.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {	}
			/**
			 * 键盘松开
			 */
			public void keyReleased(KeyEvent e) {
				setKeyCode(e.getKeyCode());
			}
			public void keyPressed(KeyEvent e) {}
		});
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
		this.setText(KeyEvent.getKeyText(this.keyCode));
	}

	public String getMethodName() {
		return methodName;
	}
}
