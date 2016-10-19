package com.fei.core.mlt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class testSpell {

	private static HttpSolrServer solrServer;
	static {
		solrServer = new HttpSolrServer("http://203.195.220.128:8080/solr/");
		solrServer.setConnectionTimeout(5000);

	}

	public static String spellCheck(String word) {
		SolrQuery query = new SolrQuery();
		// query.set("defType", "edismax");// ��Ȩ
		// query.set("qf", "name^20.0");
		query.set("suggest", "true");
		query.set("suggest.q", word);
		query.set("qt", "/suggest");
		//query.set("suggest.dictionary", "file");
		//query.set("suggest.build", "true");// �����µļ��ʣ����Զ���ӵ���������
		query.set("suggest.count", 5);
		System.out.println(query.toString());
		try {
			QueryResponse rsp = solrServer.query(query);
			System.out.println(rsp.getStatus());
			SpellCheckResponse re = rsp.getSpellCheckResponse();
			if (re != null) {
				if (!re.isCorrectlySpelled()) {
					String t = re.getFirstSuggestion(word);// ��ȡ��һ���Ƽ���
					System.out.println("�Ƽ��ʣ�" + t);
					return t;
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List getRelated(String id, int count) {
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/mlt");
		List articles = new ArrayList();
		try {
			query.setQuery("id:" + id)
					// .setParam("fl", "id,title,score")
					.setParam("mlt", "true").setParam("mlt.fl", "name")
					.setParam("mlt.mindf", "1").setParam("mlt.mintf", "1");
			// query.addFilterQuery("status:" + Article.STATUS_PUBLISHED);
			query.setRows(count);// mlt.count��Ч����Ҫ�˷������Ʒ�������

			QueryResponse response = solrServer.query(query);
			if (response == null)
				return articles;
			SolrDocumentList docs = response.getResults();
			Iterator<SolrDocument> iter = docs.iterator();
			while (iter.hasNext()) { // ��ؽ���в������Լ���ѭ���������ų�����

				SolrDocument doc = iter.next();
				/*
				 * String idStr = doc.getFieldValue("id").toString(); Article
				 * article = new Article();
				 * article.setId(Integer.parseInt(idStr));
				 * article.setTitle(doc.getFieldValue("title").toString());
				 */
				System.out.println(doc.getFieldValue("title").toString());
				System.out.println(doc.getFieldValue("id"));
				articles.add(docs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("��solr��ȡ�������ʱ��������", e);
		}
		return articles;
	}

	// TODO:����
	public static List<String> spellcheck(String word) {

		List<String> wordList = new ArrayList<String>();

		SolrQuery query = new SolrQuery();

		// query.set("q","name:ac");

		// query.set("qt", "/spell");

		// Ĭ����������
		query.set("q", "title:" + word + "");
		// query.set("q", "spell:" + word + "");

		query.set("qt", "/select");

		query.set("spellcheck.build", "true");// �����µļ��ʣ����Զ���ӵ���������

		// query.set("spellcheck.dictionary",
		// "file");//ʹ�ø�������checkSpellFile����Ľ���ʹ��

		query.set("spellcheck", "true");

		query.set("spellcheck.count", 1);
		query.setRows(1);
		// params.set("spellcheck.build", "true");

		try {

			QueryResponse rsp = solrServer.query(query);

			System.out.println("ֱ������:" + rsp.getResults().size());

			SolrDocumentList ss = rsp.getResults();

			for (SolrDocument doc : ss) {

				System.out.println(doc.get("title"));

			}

			// ������ȡ����Ĵ���

			SpellCheckResponse re = rsp.getSpellCheckResponse();

			if (re != null) {

				for (Suggestion s : re.getSuggestions()) {

					List<String> list = s.getAlternatives();

					for (String spellWord : list) {

						System.out.println(spellWord);

						// wordList.add(spellWord);

					}

				}

				// for(Collation s: spellCheckResponse.getCollatedResults()){

				// System.out.println(s.toString());

				// }

			}

			return wordList;

		} catch (SolrServerException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return null;

	}

	public static List<String> suggest(String word) throws Exception {

		List<String> wordList = new ArrayList<String>();
		SolrQuery query = new SolrQuery();
		query.set("q", "title:" + word);// ��ѯ�Ĵ�
		query.set("qt", "/suggest");// ����suggest��
		query.set("spellcheck.count", "10");// ��������
		QueryResponse rsp = solrServer.query(query);
		// System.out.println("ֱ������:"+rsp.getResults().size());
		// ������ȡ����Ĵ���
		SpellCheckResponse re = rsp.getSpellCheckResponse();// ��ȡƴд���Ľ����

		if (re != null) {
			for (Suggestion s : re.getSuggestions()) {
				List<String> list = s.getAlternatives();// ��ȡ���� �ļ�����
				for (String spellWord : list) {
					System.out.println(spellWord);
					wordList.add(spellWord);
				}

				return wordList;// ����ʻ�
			}

			// List<Collation> list=re.getCollatedResults();//
			String t = re.getFirstSuggestion(word);// ��ȡ��һ���Ƽ���
			System.out.println("�Ƽ��ʣ�" + t);
			// for(Collation c:list){
			//
			// System.out.println("�Ƽ���:"+c.getCollationQueryString());
			// }

		}

		return null;

	}

//	/http://kfcman.iteye.com/blog/1978709
	public static void main(String[] args) throws Exception {
		// getRelated("18664473319", 10);
	// spellCheck("title:����");
		//spellcheck("�п�㷺�Ĺ�ط� ");
		suggest("bf");
	}
	
	
}
