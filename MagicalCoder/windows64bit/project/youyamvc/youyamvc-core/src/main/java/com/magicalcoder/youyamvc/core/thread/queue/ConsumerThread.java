package com.magicalcoder.youyamvc.core.thread.queue;

import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.spring.SpringContainer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by hedy on 14-5-11.
 */
public class ConsumerThread extends Thread {

    private String queueType;
    private AtomicBoolean running = new AtomicBoolean(true);

    public void stopRunning() {
        running.set(false);
    }
    public ConsumerThread(String queueType){
        this.queueType=queueType;
    }
    @Override
    public void run() {
            while(running.get()) {
                try {
                    processFromQueue(queueType);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
    private void processFromQueue(String queryType) throws InterruptedException {
        SourceDto source = Store.get().getSourceFromStore(queryType);
        if(source==null){
//            if("queueHeroRanking".equals(queryType)){
//                System.out.println("ConsumerThread：queueType="+queueType+"对应队列为空 请检查");
//            }
            return;
        }
//        System.out.println("ConsumerThread:queryType="+queryType+"队列长度："+Store.get().queueLeftSize(queryType));
        AbstractProducerConsumer consumer = (AbstractProducerConsumer) SpringContainer.getBean(source.getExecuteServiceClazz());
        try{
            consumer.execute(source);
        }catch (Exception e){//不加try 会出现死锁
            System.out.println(JSON.toJSONString(source)+"ConsumerThread：queueType="+queueType+"出队列执行任务报错："+e.getMessage());
        }

    }
}
