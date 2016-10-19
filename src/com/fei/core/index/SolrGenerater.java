package com.fei.core.index;

/**
 * 2014��5��12��10:28:10
 * @author kangfei
 *
 */
public interface SolrGenerater {

	
	/**
	 * ��ȡsolr������
	 * @param url
	 */
	public String getUrl();
	
	/**
	 * ��ȡ���ӳ�ʱ��Ϣ 
	 * @return
	 */
	public int getSoTimeOut();
	
	/**
	 * ��ȡ���ӳ�ʱ��Ϣ
	 * @return
	 */
	public int getConnectionTimeOut();
	
	/**
	 * �Ƿ��Ż�����
	 * @return
	 */
	public boolean optimize();
}
