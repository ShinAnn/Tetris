package util;

public class GameFunction {
	/**
	 * �����߳�˯��ʱ��
	 */
	public static long getSleepTimeByLevel(int level){
		long sleep = 400/(level+1)+300;
		return sleep;
	}
}
