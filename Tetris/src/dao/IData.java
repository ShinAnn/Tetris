package dao;

import java.util.List;

import dto.Player;

/**
 * ���ݳ־ò�ӿ�
 * @author  user
 *
 */
public interface IData {
	/**
	 * ��ȡ����
	 * @return �������
	 */
	public List<Player> loadData();
	/**
	 * �洢����
	 */
	public void saveData(Player players);
}
