package com.fei.core.mlt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class testMorelikethis {

	private static HttpSolrServer solrServer;
	static {
		solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		solrServer.setConnectionTimeout(5000);
	}

	public static List getRelated(String id, int count) {
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/mlt");
		List articles = new ArrayList();
		try {
			query.setQuery("id:" + id)//.setParam("fl", "id,title,score")
					.setParam("mlt", "true").setParam("mlt.fl", "name")
					.setParam("mlt.mindf", "1").setParam("mlt.mintf", "1");
			//query.addFilterQuery("status:" + Article.STATUS_PUBLISHED);
			query.setRows(count);// mlt.count��Ч����Ҫ�˷������Ʒ�������

			QueryResponse response = solrServer.query(query);
			if (response == null)
				return articles;
			SolrDocumentList docs = response.getResults();
			Iterator<SolrDocument> iter = docs.iterator();
			while (iter.hasNext()) { // ��ؽ���в������Լ���ѭ���������ų�����

				SolrDocument doc = iter.next();
				/*String idStr = doc.getFieldValue("id").toString();
				Article article = new Article();
				article.setId(Integer.parseInt(idStr));
				article.setTitle(doc.getFieldValue("title").toString());*/
				System.out.println(doc.getFieldValue("title").toString());
				System.out.println(doc.getFieldValue("id"));
				articles.add(docs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("��solr��ȡ�������ʱ��������", e);
		}
		return articles;
	}

	public static void main(String[] args) {
		getRelated("18664473319",10);
	}
}
