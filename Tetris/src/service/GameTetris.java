package service;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import dto.GameDto;
import dto.Player;
import entity.GameAct;

public class GameTetris implements IGameService{
	/**
	 * 游戏数据
	 */
	private GameDto dto;
	/**
	 * 随机数生成器
	 */
	private Random random = new Random();
	/**
	 * 方块种类数
	 */
	private static final int MAX_TYPE = 6;
	
	public GameTetris(GameDto dto) {
		this.dto = dto;
		GameAct act = new GameAct(random.nextInt(MAX_TYPE));
		dto.setGameAct(act);
	}

	/**
	 * 控制方向键（上）
	 */
	public void keyUp() {
		this.dto.getGameAct().round(this.dto.getGameMap());
	}

	/**
	 * 控制方向键（下）
	 */
	public void keyDown() {
		if(this.dto.getGameAct().move(0, 1,this.dto.getGameMap())){
			return;
		}
		//获得游戏地图对象
		boolean[][] map = this.dto.getGameMap();
		//获得方块对象
		Point[] act = this.dto.getGameAct().getActPoints();
		//将方块堆积到地图数组
		for (int i = 0; i < act.length; i++) {
			map[act[i].x][act[i].y] = true;
		}
		//判断消行，并计算获得的经验值
		//TODO TODO TODO int exp = this.plusExp();
		//创建下一个方块
		this.dto.getGameAct().init(this.dto.getNext());
		//随机生成再下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE)); 
	}
	//TODO TODO TODO
	/*private int plusExp() {
		//
		boolean[][] map = this.dto.getGameMap();
		//
		for (int y = 0; y < 18; y++) {
			//
			if(this.isCanRemoveLine(y,map)){
				//
				this.removeLine(y,map);
			}
		}
		return 0;
	}*/
	//TODO TODO TODO
	/*private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < 18; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
	}
*/
	//TODO TODO TODO
	/*private boolean isCanRemoveLine(int y, boolean[][] map) {
		//
		for (int x = 0; x < 10; x++) {
			if(!map[x][y]){
				//
				return false;
			}
		}
		return true;
	}
*/
	/**
	 * 控制方向键（左）
	 */
	public void keyLeft() {
		this.dto.getGameAct().move(-1, 0,this.dto.getGameMap());
	}

	/**
	 * 控制方向键（右）
	 */
	public void keyRight() {
		this.dto.getGameAct().move(1, 0,this.dto.getGameMap());
	}
	

	@Override
	public void keyFunUp() {
		int point = this.dto.getNowPoint();
		int rmline = this.dto.getNowRemoveLine();
		int lv = this.dto.getNowLevel();
		point+=10;
		rmline+=1;
		if(rmline%20 == 0){
			lv+=1;
		}
		this.dto.setNowPoint(point);
		this.dto.setNowRemoveLine(rmline);
		this.dto.setNowLevel(lv);
		
	}

	@Override
	public void keyFunDown() {

	}

	@Override
	public void keyFunLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyFunRight() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDbRecode(List<Player> players){
		this.dto.setDbRecode(players);
	}
	public void setDiskRecode(List<Player> players){
		this.dto.setDiskRecode(players);
	}

}
