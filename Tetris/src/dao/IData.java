package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author  user
 *
 */
public interface IData {
	/**
	 * 读取数据
	 * @return 返回玩家
	 */
	public List<Player> loadData();
	/**
	 * 存储数据
	 */
	public void saveData(Player players);
}
