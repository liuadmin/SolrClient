package com.fei.core.backup;

import com.fei.core.exception.SolrBackupException;


/**
 * solr��������
 * @author Administrator
 *
 */
public interface Replication {

	/**
	 * replication
	 * ��Ҫִ�е�URL
	 * @param urls
	 */
	void replication(String[] url)throws SolrBackupException;
}
