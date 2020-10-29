package cn.ecomb.park;

import static java.lang.Thread.sleep;

/**
 * @author brian.zhou
 * @date 2020/10/27
 */
public class highCPU {
	public static void main(String[] args) throws InterruptedException {
		for(;;){
			for(long i = 0; i < 620000000; i++){
			}
			sleep(1);
		}
	}
}
