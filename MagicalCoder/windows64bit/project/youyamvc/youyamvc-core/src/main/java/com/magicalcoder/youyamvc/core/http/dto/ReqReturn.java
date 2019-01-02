package com.magicalcoder.youyamvc.core.http.dto;

import org.apache.http.Header;

import java.io.Serializable;

/**
 * Created by hzhedongyu on 2015/10/14.
 */
public class ReqReturn implements Serializable{
    private boolean success;
    private String status;
    private String body;
    private Header[] headers;
    private Object jsonParseBody;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getJsonParseBody() {
        return jsonParseBody;
    }

    public void setJsonParseBody(Object jsonParseBody) {
        this.jsonParseBody = jsonParseBody;
    }
}
