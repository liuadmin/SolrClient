package com.fei.base.util;



import java.util.List;
import java.util.Map;

public class QueryResult<T> {
	private List<T> resultlist;
	private long totalrecord;
	
	private List clustering;
	
	private Map<String, Map<String, List<String>>> highlighting;
	
	//��һ����ʼλ��
	private int nextStart;
	
	
	public int getNextStart() {
		return nextStart;
	}

	public void setNextStart(int nextStart) {
		this.nextStart = nextStart;
	}

	/**
	 * ���ؾ������
	 * @return
	 */
	public List getClustering() {
		return clustering;
	}
	
	public void setClustering(List clustering) {
		this.clustering = clustering;
	}
	
	/**
	 * ���ظ������ֽ��
	 * @return
	 */
	public Map<String, Map<String, List<String>>> getHighlighting() {
		return highlighting;
	}
	public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
		this.highlighting = highlighting;
	}
	
	/**
	 * ���ز�ѯ���
	 * @return
	 */
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
}
