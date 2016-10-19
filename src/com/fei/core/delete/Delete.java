package com.fei.core.delete;


import java.util.List;

import com.fei.core.exception.DeleteException;
import com.fei.core.index.SolrGenerater;

/**
 * ɾ������
 * 2014��5��13��10:11:56
 * @author kangfei
 *
 */
public interface Delete extends SolrGenerater{
	
	 /**
	  * ����id���Ͻ���ɾ������
	  * @param listIds
	  */
	 public void deleteIndexByIds(List<String> listIds)throws DeleteException;
	 
	 /**
	  * ����id����ɾ������
	  * @param id
	  */
	 public void deleteIndexById(String id)throws DeleteException;
	
	 /**
	  * ���ݲ�ѯ����ɾ��������:id:*����ɾ����������
	  * @param deleteQuery
	  */
	 public void deleteIndexByQuery(String deleteQuery)throws DeleteException;
}
