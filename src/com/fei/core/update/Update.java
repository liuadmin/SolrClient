package com.fei.core.update;

import java.util.Map;

import com.fei.core.index.SolrGenerater;

/**
 * ��������
 * 2014��5��13��11:00:22
 * @author kangfei
 *
 */
public interface Update extends SolrGenerater{

	/**
	 * �����ĵ�
	 * @param map ��Ҫ���µ��ֶ�
	 * @param query ��ѯ�������������һ����¼��ʹ��ID:XXX������������£��봫���ѯ������
	 */
	public void update(Map map,String query);
}
