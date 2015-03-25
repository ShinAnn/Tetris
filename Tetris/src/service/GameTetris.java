package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
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
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size()-1;
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
	}

	
	/**
	 * ��Ϸʧ�ܺ�Ĵ���
	 */
	private void afterLose() {
		//������Ϸ��ʼ״̬Ϊfalse
		this.dto.setStart(false);
		// TODO �ر���Ϸ���߳�
		
	}

	/**
	 * �����Ϸ�Ƿ�ʧ��
	 */
	private boolean isLose() {
		//������ڵĻ����
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		//������ڵ���Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
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
	 * ���Ʒ�������ϣ�
	 */
	public boolean keyUp() {
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * ���Ʒ�������£�
	 */
	public boolean keyDown() {
		synchronized (this.dto) {
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// �����Ϸ��ͼ����
			boolean[][] map = this.dto.getGameMap();
			// ��÷������
			Point[] act = this.dto.getGameAct().getActPoints();
			// ������ѻ�����ͼ����
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// �ж����У��������õľ���ֵ
			int plusExp = this.plusExp();
			// �����������
			if (plusExp > 0) {
				// ���Ӿ���ֵ
				this.plusPoint(plusExp);
			}
			// ˢ���µķ���
			this.dto.getGameAct().init(this.dto.getNext());
			// �����������һ������
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// �����Ϸ�Ƿ�ʧ��
			if (this.isLose()) {
				this.afterLose();
			}
		}
		return true;
	}
	/**
	 * ���Ʒ��������
	 */
	public boolean keyLeft() {
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
			return true;
		}
	}

	/**
	 * ���Ʒ�������ң�
	 */
	public boolean keyRight() {
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
			return true;
		}
	}
	

	@Override
	public boolean keyFunUp() {
		// TODO ���׼�
		this.plusPoint(1);
		return true;
	}

	@Override
	public boolean keyFunDown() {
		//˲������
		while(!this.keyDown());
		return true;
	}

	@Override
	public boolean keyFunLeft() {
		//��Ӱ����
		this.dto.changeShowShadow();
		return true;
	}

	@Override
	public boolean keyFunRight() {
		//��ͣ
		return true;
	}
	
	@Override
	public void startGame() {
		//���������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//����������ڷ���
		this.dto.setGameAct( new GameAct(random.nextInt(MAX_TYPE)));
		//����Ϸ״̬��Ϊ��ʼ
		this.dto.setStart(true);
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}

}
