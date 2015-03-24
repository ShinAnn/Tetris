package service;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import dto.GameDto;
import dto.Player;
import entity.GameAct;

public class GameTetris implements IGameService{
	/**
	 * ��Ϸ����
	 */
	private GameDto dto;
	/**
	 * �����������
	 */
	private Random random = new Random();
	/**
	 * ����������
	 */
	private static final int MAX_TYPE = 6;
	
	public GameTetris(GameDto dto) {
		this.dto = dto;
		GameAct act = new GameAct(random.nextInt(MAX_TYPE));
		dto.setGameAct(act);
	}

	/**
	 * ���Ʒ�������ϣ�
	 */
	public void keyUp() {
		this.dto.getGameAct().round(this.dto.getGameMap());
	}

	/**
	 * ���Ʒ�������£�
	 */
	public void keyDown() {
		if(this.dto.getGameAct().move(0, 1,this.dto.getGameMap())){
			return;
		}
		//�����Ϸ��ͼ����
		boolean[][] map = this.dto.getGameMap();
		//��÷������
		Point[] act = this.dto.getGameAct().getActPoints();
		//������ѻ�����ͼ����
		for (int i = 0; i < act.length; i++) {
			map[act[i].x][act[i].y] = true;
		}
		//�ж����У��������õľ���ֵ
		//TODO TODO TODO int exp = this.plusExp();
		//������һ������
		this.dto.getGameAct().init(this.dto.getNext());
		//�����������һ������
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
	 * ���Ʒ��������
	 */
	public void keyLeft() {
		this.dto.getGameAct().move(-1, 0,this.dto.getGameMap());
	}

	/**
	 * ���Ʒ�������ң�
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
