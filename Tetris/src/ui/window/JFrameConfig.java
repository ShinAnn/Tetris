package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import ui.Img;
import util.FrameUtil;
import control.GameControl;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {

	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnUse = new JButton("应用");
	private JLabel errorMsg = new JLabel();
	@SuppressWarnings("rawtypes")
	private JList skinList =null;
	private JPanel skinView = null;
	@SuppressWarnings("rawtypes")
	private DefaultListModel skinData =new DefaultListModel();
	private GameControl gameControl;
	
	private final static Image IMG_PSP = new ImageIcon("data/psp.jpg")	.getImage();
	
	private Image[] skinViewList = null;
	private TextCtrl[] keyTexts = new TextCtrl[8];

	private final static String[] METHOD_NAMES = {
		"keyRight", "keyUp",	"keyLeft", "keyDown", 
		"keyFunLeft", "keyFunUp", "keyFunRight",	"keyFunDown" };
	public final static String PATH = "data/control.dat";

	// = new TextCtrl(10,55,64,20);
	public JFrameConfig(GameControl gameControl) {
		//获得游戏控制器对象
		this.gameControl = gameControl;
		// 设置布局管理器为“边界布局“
		this.setLayout(new BorderLayout());
		this.setTitle("设置");
		// 初始化按键输入框
		this.initKeyText();
		// 添加主面板
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// 添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		// 设置窗口大小
		this.setSize(640, 350);
		// 设置不能变大小
		this.setResizable(false);
		// 居中
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * 初始化按键输入框
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
	 * 创建按钮面板
	 */
	private JPanel createButtonPanel() {
		// 创建按钮面板（流式布局）
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// 给确定按钮增加事件监听
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (writeConfig())
					setVisible(false);
				gameControl.setOver();
			}
		});
		errorMsg.setForeground(Color.RED);
		jp.add(errorMsg);
		jp.add(btnOk);
		// 给取消按钮增加事件监听
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		jp.add(btnCancel);
		// 给应用按钮增加事件监听
		this.btnUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
				gameControl.repaint();
				requestFocus();
			}
		});
		jp.add(btnUse);
		return jp;
	}

	/**
	 * 创建主面板(选项卡面板)
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}
	/**
	 * 玩家皮肤面板
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for (int i = 0; i < files.length; i++) {
			//增加选项
			this.skinData.addElement(files[i].getName());
			//增加预览图
			this.skinViewList[i] = new ImageIcon(files[i].getPath()+"\\logo.png").getImage();
		}
		//添加列表数据到列表
		this.skinList = new JList(this.skinData);
		//设置默认选中第二个
		this.skinList.setSelectedIndex(1);
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}
		});
		this.skinView = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(skinViewList[skinList.getSelectedIndex()], 0, 0 ,null);
			};
		};
		panel.add(new JScrollPane(skinList),BorderLayout.WEST);
		panel.add(this.skinView,BorderLayout.CENTER);
		return panel;
	}

	/**
	 * 玩家控制设计面板
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}

		};
		// 设置布局管理器
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}

	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < this.keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if (keyCode == 0) {
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyTexts[i].getMethodName());
		}
		if (keySet.size() != 8) {
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			//切换皮肤
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString()+"/");
			//写入控制配置
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
