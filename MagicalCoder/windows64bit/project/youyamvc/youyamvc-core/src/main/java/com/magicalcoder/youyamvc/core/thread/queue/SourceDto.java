package com.magicalcoder.youyamvc.core.thread.queue;

/**
 * Created by hedy on 14-5-11.
 */
public class SourceDto {

    private Class<?> executeServiceClazz;
    private Object[] params;
    private String queueType;//队列类型

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class<?> getExecuteServiceClazz() {
        return executeServiceClazz;
    }

    public void setExecuteServiceClazz(Class<?> executeServiceClazz) {
        this.executeServiceClazz = executeServiceClazz;
    }
}
