package cn.ecomb.park.common.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author brian.zhou
 * @date 2021/4/22
 */
public class CyclicBarrierPark {

	class Solver {
		final int N;
		final float[][] data;
		final CyclicBarrier barrier;

		class Worker implements Runnable {
			int myRow;
			Worker(int row) {
				myRow = row;
			}
			public void run() {
				while (true) {
//				while (!done()) {
//					processRow(myRow);
					System.out.println("process");
					try {
						barrier.await();
					} catch (InterruptedException ex) {
						return;
					} catch (BrokenBarrierException ex) {
						return;
					}
				}
			}
		}

		public Solver(float[][] matrix) throws InterruptedException {
			data = matrix;
			N = matrix.length;
			Runnable barrierAction = new Runnable() {
						public void run() {
//							mergeRows();
							System.out.println("do merge");
						}
					};
			barrier = new CyclicBarrier(N, barrierAction);

			List<Thread> threads = new ArrayList<Thread>(N);
			for (int i = 0; i < N; i++) {
				Thread thread = new Thread(new Worker(i));
				threads.add(thread);
				thread.start();
			}

			// wait until done
			for (Thread thread : threads)
				thread.join();
		}
	}
}
