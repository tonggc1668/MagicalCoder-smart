package com.magicalcoder.youyamvc.app.dto;

/**
 * Created by www.magicalcoder.com on 14-8-5.
 * 799374340@qq.com
 */
public class SysConfig {

    private String imageVersion;

    private String updateLogUrl;//首页更新日志url
    private String menuUrl;
    private String blueTieUrl;
    private String htmlFileRoot;
    private String localBasePath;//页面静态化要访问的项目url 这个必须是主机器的url

    private String domain;
    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String imageVersion) {
        this.imageVersion = imageVersion;
    }

    public String getHtmlFileRoot() {
        return htmlFileRoot;
    }

    public void setHtmlFileRoot(String htmlFileRoot) {
        this.htmlFileRoot = htmlFileRoot;
    }

    public String getLocalBasePath() {
        return localBasePath;
    }

    public void setLocalBasePath(String localBasePath) {
        this.localBasePath = localBasePath;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUpdateLogUrl() {
        return updateLogUrl;
    }

    public void setUpdateLogUrl(String updateLogUrl) {
        this.updateLogUrl = updateLogUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getBlueTieUrl() {
        return blueTieUrl;
    }

    public void setBlueTieUrl(String blueTieUrl) {
        this.blueTieUrl = blueTieUrl;
    }
}
