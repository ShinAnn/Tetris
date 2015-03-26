package util;

public class GameFunction {
	/**
	 * 计算线程睡眠时间
	 */
	public static long getSleepTimeByLevel(int level){
		long sleep = 400/(level+1)+300;
		return sleep;
	}
}
