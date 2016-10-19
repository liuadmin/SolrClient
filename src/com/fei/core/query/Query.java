package com.fei.core.query;

import com.fei.base.util.QueryResult;
import com.fei.core.exception.QueryException;
import com.fei.core.index.SolrGenerater;

/**
 * ��ѯ
 * @author kangf
 *
 */
public interface Query extends SolrGenerater{

	/**
	 * ��ѯ���ݣ���ҳ��ѯ
	 * @param query ��ѯ����
	 * @param pageNo ��ǰҳ
	 * @param pagesize ÿҳ��ʾ����
	 * @return 
	 * @throws QueryException
	 */
	public QueryResult queryData(String query,int pageNo,int pagesize) throws QueryException;
	
	/**
	 * ��ѯ��Ĭ�ϲ�ѯ������ҳ
	 * @param query
	 * @return
	 * @throws QueryException
	 */
	public QueryResult queryData(String query) throws QueryException;
}
