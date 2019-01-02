package com.magicalcoder.youyamvc.core.tag.paging.dto;

import java.io.Serializable;

//分页参数
public class PagingDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3972672600008191195L;
	private int pgIdx=1;//当前页码:默认
	private int pgCnt;//当前纪录的总条数
	private int pgSize=20;//每页多少条纪录：默认
	private boolean useCountMethod;//有没有countMethod方法 没有就默认自动使用第二种分页 无总数
	//暂时不用 使用？即可得到当前action
	private String action;//当前action的url
	private int pgCurrentCnt;//当前页返回记录条数
	public int getPgIdx() {
		return pgIdx;
	}
	public void setPgIdx(int pgIdx) {
		this.pgIdx = pgIdx;
	}
	public int getPgCnt() {
		return pgCnt;
	}
	public void setPgCnt(int pgCnt) {
		this.pgCnt = pgCnt;
	}
	public int getPgSize() {
		return pgSize;
	}
	public void setPgSize(int pgSize) {
		this.pgSize = pgSize;
	}
	public boolean isUseCountMethod() {
		return useCountMethod;
	}
	public void setUseCountMethod(boolean useCountMethod) {
		this.useCountMethod = useCountMethod;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getPgCurrentCnt() {
		return pgCurrentCnt;
	}
	public void setPgCurrentCnt(int pgCurrentCnt) {
		this.pgCurrentCnt = pgCurrentCnt;
	}
	
}
