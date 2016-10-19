package com.fei.base.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;

/**
 * <p>
 * Title:HttpTookitEnhance
 * </p>
 * <p>
 * Description: httpclientģ��http���󣬽������������������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author libin
 * @version 1.0.0
 */
public class HttpTookitEnhance {
	/**
	 * ִ��һ��HTTP GET���󣬷���������Ӧ��HTML
	 * 
	 * @param url
	 *            �����URL��ַ
	 * @param queryString
	 *            ����Ĳ�ѯ����,����Ϊnull
	 * @param charset
	 *            �ַ���
	 * @param pretty
	 *            �Ƿ�����
	 * @return ����������Ӧ��HTML
	 */
	public static String doGet(String url, String queryString, String charset,
			boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		client.setConnectionTimeout(10000);
		client.setTimeout(10000);
		try {
			if (queryString != null && !queryString.equals(""))
				// ��get�����������http����Ĭ�ϱ��룬����û���κ����⣬���ֱ���󣬾ͳ�Ϊ%ʽ�����ַ���
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (URIException e) {
		} catch (IOException e) {
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * ִ��һ��HTTP POST���󣬷���������Ӧ��HTML
	 * 
	 * @param url
	 *            �����URL��ַ
	 * @param params
	 *            ����Ĳ�ѯ����,����Ϊnull
	 * @param charset
	 *            �ַ���
	 * @param pretty
	 *            �Ƿ�����
	 * @return ����������Ӧ��HTML
	 */
	public static String doPost(String url, Map<String, String> params,
			String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new PostMethod(url);
		// ����Http Post����
		if (params != null) {
			HttpMethodParams p = new HttpMethodParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				p.setParameter(entry.getKey(), entry.getValue());
			}
			method.setParams(p);
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public static void main(String[] args) {
		// http://9091.mpb.weduty.com/setting/custommenu?op=get&mpid=B0001
		// String y =
		// doPost("http://9091.mpb.weduty.com/setting/custommenu?op=get&mpid=B0001",
		// null, "GBK", true);
		String y = doGet(
				"http://weixin.sogou.com/weixin?type=1&query=a&ie=utf8&_ast=1402361344&_asf=null&w=01029901&p=40040100&dp=1&cid=null&repp=1",
				null, "GBK", true);
		System.out.println(y);
	}

}
