package ui.cfg;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextCtrl extends JTextField{
	
	private int keyCode;
	private final String methodName;
	
	public TextCtrl(int x,int y, int w, int h,String methodName){
		//�����ı���λ��
		this.setBounds(x,y,w,h);

		//��ʼ��������
		this.methodName = methodName;
		//�����¼�����
		this.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {	}
			/**
			 * �����ɿ�
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