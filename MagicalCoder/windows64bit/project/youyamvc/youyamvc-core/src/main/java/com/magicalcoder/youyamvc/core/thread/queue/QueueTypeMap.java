package com.magicalcoder.youyamvc.core.thread.queue;

import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by www.magicalcoder.com on 14-6-10.
 * 799374340@qq.com
 */
public class QueueTypeMap {
    //队列类型

    private static ConcurrentHashMap<String, BlockingQueue<SourceDto>> queueMap;

    public static void init(String []queues){
        if (queueMap == null) {
            synchronized (QueueTypeMap.class) {
                queueMap = new ConcurrentHashMap<String, BlockingQueue<SourceDto>>();
                for (String queue : queues) {
                    if (StringUtils.isNotBlank(queue)) {
                        queueMap.put(queue, new LinkedBlockingQueue<SourceDto>());
                    }
                }
            }
        }
    }
    public static ConcurrentHashMap<String, BlockingQueue<SourceDto>> getQueueMap() {
        return queueMap;
    }
}
