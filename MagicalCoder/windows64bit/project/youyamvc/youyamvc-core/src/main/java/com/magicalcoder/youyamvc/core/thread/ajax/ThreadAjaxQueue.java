package com.magicalcoder.youyamvc.core.thread.ajax;

import com.magicalcoder.youyamvc.core.spring.SpringContainer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 多久后开启线程执行任务 并非生产者消费者
 * qq:799374340
 * @author hdy
 * 2013-8-26下午2:38:23
 */
public class ThreadAjaxQueue {

	private static ScheduledExecutorService e = Executors.newScheduledThreadPool(5);
	public static ThreadAjaxQueue q = new ThreadAjaxQueue();
	public static ThreadAjaxQueue get(){
		return q;
	}
	public void delaySchedule(final String bean,final int seconds,final Object... args){
		e.schedule(new Runnable() {
			@Override
			public void run() {
				ThreadAjaxQueueService service = (ThreadAjaxQueueService) SpringContainer.getBean(bean);
				service.shedule(args);
			}
		},seconds,TimeUnit.SECONDS);
	}
}
