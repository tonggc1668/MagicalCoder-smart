package com.magicalcoder.youyamvc.core.thread.ajax;


/**
 * qq:799374340
 * @author hdy
 * 2013-8-26下午2:38:50
 */
public interface ThreadAjaxQueueService {

	/**多线程队列调度任务 配合ThreadQueue.schedule使用 参考QueueThreadServiceImpl
	 * @param params 封装方法参数
	 */
	public void shedule(Object... params);
}
