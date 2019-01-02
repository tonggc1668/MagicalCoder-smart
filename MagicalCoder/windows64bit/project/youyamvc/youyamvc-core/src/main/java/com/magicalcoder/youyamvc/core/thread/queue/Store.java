package com.magicalcoder.youyamvc.core.thread.queue;


import java.util.concurrent.BlockingQueue;

/**
 * Created by hedy on 14-5-11.
 */
public class Store {
    private static Store instance = new Store();
    public static Store get(){
        return instance;
    }

    SourceDto getSourceFromStore(String queueType){
        try {
            BlockingQueue<SourceDto> queue = QueueTypeMap.getQueueMap().get(queueType);
            if(queue!=null){
//                SourceDto sourceDto = queue.take();
//                return queue.poll(100, TimeUnit.MILLISECONDS);
                return queue.take();
            }else{
                System.out.println("Store：queueType="+queueType+"对应队列为空 请检查");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    void addSourceToStore(SourceDto source){
        String queryType = source.getQueueType();
        BlockingQueue<SourceDto> queue = QueueTypeMap.getQueueMap().get(queryType);
        if(queue!=null){
            queue.add(source);
        }else{
            throw new RuntimeException("未定义类型的队列"+queryType);
        }
    }
    public int queueLeftSize(String queryType){
//        BlockingQueue<SourceDto> queue = QueueTypeMap.getQueueMap().get(queryType);
//        int size = queue.size();
        return QueueTypeMap.getQueueMap().get(queryType).size();
    }

}
