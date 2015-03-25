package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
/**
 * 
 * @author  Anko
 *
 */
public class GameDto {
	/**
	 * 游戏宽度
	 */
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX()+1;
	/**
	 */
	public static final int GAMEZONE_H =  GameConfig.getSystemConfig().getMaxY()+1;
	  /**
	   * 构造函数
	   */
		public GameDto(){
			dtoInit();
		}
		/**
		 * dto初始化
		 */
		public void dtoInit(){
			this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
			//TODO初始化所有变量
		}
	   /**
	    * 数据库记录
	    */
		private List<Player> dbRecode;
		/**
		 * 本地记录
		 */
		private List<Player> diskRecode;
		/**
		 * 堆积方块
		 */
		private boolean[][] gameMap;
		/**
		 * 下落方块
		 */
		private GameAct gameAct;
		/**
		 * 下一个方块
		 */
		private int next;
		/**
		 * 等级
		 */
		private int nowLevel;
		/**
		 * 分数
		 */
		private int nowPoint;
		/**
		 * 消行
		 */
		private int nowRemoveLine;

		public List<Player> getDbRecode() {
			return dbRecode;
		}

		public void setDbRecode(List<Player> dbRecode) {
			this.dbRecode = this.setFillRecode(dbRecode);
		}
 
		public List<Player> getDiskRecode() {
			return diskRecode;
		}

		public void setDiskRecode(List<Player> diskRecode) {
			this.diskRecode = this.setFillRecode(diskRecode);
		}
		
	    private List<Player> setFillRecode(List<Player> players){
	    	//如果进来的为空，那么就创建
	    	if(players == null){
	    		players = new ArrayList<Player>();
			}
	    	//如果记录小于5，那么就加到5条为止
			while(players.size()<5){
				players.add(new Player("No Data",0));
			}
			Collections.sort(players);			
			return players;
	    }

		public boolean[][] getGameMap() {
			return gameMap;
		}

		public void setGameMap(boolean[][] gameMap) {
			this.gameMap = gameMap;
		}

		public GameAct getGameAct() {
			return gameAct;
		}

		public void setGameAct(GameAct gameAct) {
			this.gameAct = gameAct;
		}

		public int getNext() {
			return next;
		}

		public void setNext(int next) {
			this.next = next;
		}

		public int getNowLevel() {
			return nowLevel;
		}

		public void setNowLevel(int nowLevel) {
			this.nowLevel = nowLevel;
		}

		public int getNowPoint() {
			return nowPoint;
		}

		public void setNowPoint(int nowPoint) {
			this.nowPoint = nowPoint;
		}

		public int getNowRemoveLine() {
			return nowRemoveLine;
		}

		public void setNowRemoveLine(int nowRemoveLine) {
			this.nowRemoveLine = nowRemoveLine;
		}
}
