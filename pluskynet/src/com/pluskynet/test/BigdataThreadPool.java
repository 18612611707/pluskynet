package com.pluskynet.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BigdataThreadPool {

	public static void main(String[] args) {
		 ThreadPoolExecutor executor = new ThreadPoolExecutor(5, Integer.MAX_VALUE, 200, TimeUnit.MILLISECONDS,
                 new LinkedBlockingQueue<Runnable>());
          
         for(int i=0;i<15;i++){
//             MyTask myTask = new MyTask(i);
//             executor.execute(myTask);
             System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
         }
         executor.shutdown();
     }
}
