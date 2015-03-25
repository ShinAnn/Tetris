package service;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
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
	/**
	 * ��������
	 */
	private static final int LEVEL_UP =GameConfig.getSystemConfig().getLevelUp();
	/**
	 * �������з�����
	 */
	private static final Map<Integer, Integer> PLUS_POINT =GameConfig.getSystemConfig().getPlusPoint();
	
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
		int plusExp = this.plusExp();
		//�����������
		if (plusExp > 0) {
			// ���Ӿ���ֵ
			this.plusPoint(plusExp);
		}
		// ������һ������
		this.dto.getGameAct().init(this.dto.getNext());
		//�����������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE)); 
	}
	/**
	 * �ӷ���������
	 */
	private void plusPoint(int plusExp) {
		int level = this.dto.getNowLevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		if(rmLine%LEVEL_UP+plusExp>=LEVEL_UP){
			level++;
			this.dto.setNowLevel(level);
		}
		this.dto.setNowRemoveLine(rmLine+plusExp);
		this.dto.setNowPoint(point+PLUS_POINT.get(plusExp));
	}

	/**
	 * ���в���
	 */
	private int plusExp() {
		//�����Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		// ɨ����Ϸ��ͼ���鿴�Ƿ������
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			//�ж��Ƿ������
			if(this.isCanRemoveLine(y,map)){
				//��������У��Ǹ�������
				this.removeLine(y,map);
				// ���Ӿ���ֵ
				exp++;
			}
		}
		return exp;
	}
	/**
	 * ���д���
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
	}
	/**
	 * �ж�ĳһ���Ƿ������
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		//�����ڶ�ÿһ����Ԫ�����ɨ��
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if(!map[x][y]){
				//�����һ������Ϊfalse��ֱ��������һ��
				return false;
			}
		}
		return true;
	}

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
		
	}

	@Override
	public void keyFunRight() {
		
	}
	
	public void setDbRecode(List<Player> players){
		this.dto.setDbRecode(players);
	}
	public void setDiskRecode(List<Player> players){
		this.dto.setDiskRecode(players);
	}

}
