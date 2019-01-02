package com.magicalcoder.youyamvc.core.thread.queue;

/**
 * Created by hedy on 14-5-11.
 */
public class ProducerThread {

    public static void addToQueue(SourceDto source){
        Store.get().addSourceToStore(source);
    }
}
