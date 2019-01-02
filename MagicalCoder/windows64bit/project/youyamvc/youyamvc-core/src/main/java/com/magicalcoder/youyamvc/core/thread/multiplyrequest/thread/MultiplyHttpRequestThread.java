package com.magicalcoder.youyamvc.core.thread.multiplyrequest.thread;

import java.util.Vector;

/**
 * Created by www.magicalcoder.com on 2015/4/2.
 * 799374340@qq.com
 */
public class MultiplyHttpRequestThread<T> {

    private Vector<T> vector = new Vector<T>();
    private Thread[] threads;
    private int maxWaitTime;//最大等待时间 最大等3秒 30*100

    public MultiplyHttpRequestThread(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public void initThreads(MultiplyThreads multiplyThreads){
        this.threads = multiplyThreads.buildThreads();
    }
    public Vector<T> getVector() {
        return vector;
    }

    public void trigger(){
        if(threads!=null && threads.length>0){
            for(Thread thread:threads){
                if(thread!=null) {
                    thread.start();
                }
            }
        }
    }
    //如果所有线程都返回了结果 就返回Vector
    public Vector getVectorIfFinished(){
        if(threads==null || threads.length<0){
            return vector;
        }
        int sleep = 50;//毫秒
        int count = 0;
        int threadLen = threads.length;
        try {
            while(vector.size()<threadLen && (sleep*count)<maxWaitTime){//自动检测多线程请求是否完成
                Thread.sleep(sleep);
                count++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vector;
    }

}
