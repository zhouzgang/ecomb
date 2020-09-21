package cn.ecomb.common.utils.play;

import java.util.concurrent.*;

/**
 * 调试线程池
 * @author brian.zhou
 * @date 2020/7/23
 */
public class TryThreadPool {

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread Id :" + Thread.currentThread().getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 5, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        System.out.println("creat->" + t);
                        return t;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + "is discard");
                    }
                }
        ){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                super.terminated();
            }
        };

        for (int i = 0; i < 16; i++) {
            executor.submit(new MyTask());
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount() +
                    "活跃数：" + executor.getActiveCount());
            Thread.sleep(10);
        }
    }
}
