package ui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import util.FrameUtil;
import control.GameControl;

@SuppressWarnings("serial")
public class FrameConfig extends JFrame {

	private JButton btnOk = new JButton("ȷ��");
	private JButton btnCancel = new JButton("ȡ��");
	private JButton btnUse = new JButton("Ӧ��");
	private JLabel errorMsg = new JLabel();
	private GameControl gameControl;
	
	private final static Image IMG_PSP = new ImageIcon("data/psp.jpg")
			.getImage();
	private TextCtrl[] keyTexts = new TextCtrl[8];

	private final static String[] METHOD_NAMES = {
		"keyRight", "keyUp",	"keyLeft", "keyDown", 
		"keyFunLeft", "keyFunUp", "keyFunRight",	"keyFunDown" };
	public final static String PATH = "data/control.dat";

	// = new TextCtrl(10,55,64,20);
	public FrameConfig(GameControl gameControl) {
		//�����Ϸ����������
		this.gameControl = gameControl;
		// ���ò��ֹ�����Ϊ���߽粼�֡�
		this.setLayout(new BorderLayout());
		this.setTitle("����");
		// ��ʼ�����������
		this.initKeyText();
		// ��������
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// ��Ӱ�ť���
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		// ���ô��ڴ�С
		this.setSize(640, 350);
		// ���ò��ܱ��С
		this.setResizable(false);
		// ����
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * ��ʼ�����������
	 */
	private void initKeyText() {
		int x = 10;
		int y = 55;
		int w = 64;
		int h = 20;
		for (int i = 0; i < 4; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 26;
		}
		x = 565;
		y = 55;
		for (int i = 4; i < 8; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 27;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois
					.readObject();
			ois.close();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for (Entry<Integer, String> entry : entrySet) {
				for (TextCtrl tc : keyTexts) {
					if (tc.getMethodName().equals(entry.getValue()))
						tc.setKeyCode(entry.getKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ť���
	 */
	private JPanel createButtonPanel() {
		// ������ť��壨��ʽ���֣�
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// ��ȷ����ť�����¼�����
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (writeConfig())
					setVisible(false);
				gameControl.serOver();
			}
		});
		errorMsg.setForeground(Color.RED);
		jp.add(errorMsg);
		jp.add(btnOk);
		// ��ȡ����ť�����¼�����
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.serOver();
			}
		});
		jp.add(btnCancel);
		// ��Ӧ�ð�ť�����¼�����
		this.btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		jp.add(btnUse);
		return jp;
	}

	/**
	 * ���������(ѡ����)
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", new JLabel("Ƥ��"));
		return jtp;
	}

	/**
	 * ��ҿ���������
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}

		};
		// ���ò��ֹ�����
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}

	/**
	 * д����Ϸ����
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < this.keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if (keyCode == 0) {
				this.errorMsg.setText("���󰴼�");
				return false;
			}
			keySet.put(keyCode, this.keyTexts[i].getMethodName());
		}
		if (keySet.size() != 8) {
			this.errorMsg.setText("�ظ�����");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			errorMsg.setText(e.getMessage());
			return false;
		}
		errorMsg.setText(null);
		return true;
	}
	
}
