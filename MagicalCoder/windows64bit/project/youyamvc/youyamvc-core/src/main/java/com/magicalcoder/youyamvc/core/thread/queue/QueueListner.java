package com.magicalcoder.youyamvc.core.thread.queue;

import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.thread.util.QueuePropertiesUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedy on 14-5-11.
 */
public class QueueListner implements ServletContextListener {
    private List<ConsumerThread> consumerThreadList = new ArrayList<ConsumerThread>();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String queueList = QueuePropertiesUtil.getProperty("queueList");
        if(StringUtils.isBlank(queueList)){
            return;
        }
        String queueThreadRunCount = QueuePropertiesUtil.getProperty("queueThreadRunCount");
        String[] threadRunCount = queueThreadRunCount.split(",");
        String[] queues = queueList.split(",");
        //先初始化所有的队列类型
        QueueTypeMap.init(queues);
        //启动线程
        for(int i=0;i<queues.length;i++){
            String queueType = queues[i];
            int threadCount = Integer.valueOf(threadRunCount[i]);
            for(int j=0;j<threadCount;j++){
                ConsumerThread consumerThread = new ConsumerThread(queueType);
                consumerThread.start();
                consumerThreadList.add(consumerThread);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(ListUtils.isNotBlank(consumerThreadList))
        for(int i=0;i<consumerThreadList.size();i++){
            ConsumerThread consumerThread = consumerThreadList.get(i);
            consumerThread.stopRunning();
        }
    }
}
