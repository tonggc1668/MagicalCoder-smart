package com.magicalcoder.youyamvc.core.thread.queue;

/**
 * Created by hedy on 14-5-11.
 */
public abstract class AbstractProducerConsumer {
    //不要把SourceDto 写成成员变量 因为是单例；写成就很多共享了
    public abstract void execute(SourceDto source);
    public void addToQueue(String queueType,Object... params){
        SourceDto sourceDto = new SourceDto();
        sourceDto.setParams(params);
        sourceDto.setQueueType(queueType);
        Class clazz = this.getClass();
        sourceDto.setExecuteServiceClazz(clazz);
        ProducerThread.addToQueue(sourceDto);
    }
}
