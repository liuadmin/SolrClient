package com.fei.base.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ڹ�����
 * 
 */
public class DateUtils {

	/** ȡ�÷�����ʱ�� */
	public static Date serverTime() {
		return new Date();
	}

	
	/**
	 * @param date
	 *            ��ʽ����Ϊyyyy-MM-dd HH:mm:ss
	 * @throws PubException
	 *             ��������������������׳����쳣
	 */
	public static Date parse(final String date) {
		try {
			return parse(date, "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date parseStartDate(final String date) throws Exception {
		return parse(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseEndDate(final String date) throws Exception {
		return parse(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	public static void main(String[] args) {
		String dt = getNowDateString("yyyy-MM-dd");
		try {
			System.out.println(parseEndDate(dt));
			System.out.println(parseStartDate(dt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param date
	 *            �����ַ���
	 * @param format
	 *            ���ڽ�����ʽ
	 * @throws PubException
	 *             ��������������������׳����쳣
	 * @return ת�����������
	 */
	public static Date parse(final String date, final String format)
			throws Exception {
		if (date == null) {
			throw new Exception("����date����Ϊ�գ�");
		}
		if (format == null) {
			throw new Exception("����format����Ϊ�գ�");
		}
		Map<String, SimpleDateFormat> map = cache.get();
		if (map == null) {
			map = new HashMap<String, SimpleDateFormat>();
			cache.set(map);
		}
		SimpleDateFormat dateFormat = map.get(format);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(format);
			map.put(format, dateFormat);
		}
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			throw new Exception("���ڸ�ʽ�������������ַ�����" + date + "�����ڽ�����ʽ��" + format);
		}
	}
	/**
	 *  @param int
	 *            ��������ʱ��
	 * @throws PubException
	 *             ��������������������׳����쳣
	 * @return ���ظ�ʽΪm��s���ʽ
	 */
	public static String IntToDate(final int usetime) throws Exception {
		String relUsetime=null;
		if((int)usetime/60000>=1){
			relUsetime = (int)usetime/60000+"��"+(int)(usetime/1000)%60+"��";
		}else{
			relUsetime = usetime/1000 +"��";
		}
		return relUsetime;
	}
	

	/**
	 * @throws PubException
	 *             ��������������������׳����쳣
	 * @return ���ظ�ʽΪyyyy-MM-dd HH:mm:ss�������ַ���
	 */
	public static String format(final Date date) {
		try {
			return format(date, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param format
	 *            "example:yyyy/MM/dd"
	 * @throws PubException
	 *             ��������������������׳����쳣
	 */
	public static String format(final Date date, final String format)
			throws Exception {
		if (date == null) {
			throw new Exception("����date����Ϊ�գ�");
		}
		if (format == null) {
			throw new Exception("����format����Ϊ�գ�");
		}
		Map<String, SimpleDateFormat> map = cache.get();
		if (map == null) {
			map = new HashMap<String, SimpleDateFormat>();
			cache.set(map);
		}
		SimpleDateFormat dateFormat = map.get(format);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(format);
			map.put(format, dateFormat);
		}
		return dateFormat.format(date);
	}

	/** Ϊ��С����SimpleDateFormat�Ŀ������������档 (Map��keyΪ����ת����ʽ) */
	private static final ThreadLocal<Map<String, SimpleDateFormat>> cache = new ThreadLocal<Map<String, SimpleDateFormat>>();

	/**
	 * ��ȡ����ʱ��
	 * 
	 * @return ����ʱ������ yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * ��ȡ����ʱ���ַ���
	 * 
	 * @return ����ʱ������ yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateString() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * ��ȡ����ʱ���ַ���
	 * 
	 * @return ����ʱ������ yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateString(String fmt) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(fmt);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static Date getTodayBeginDate()
	{
		Date now = new Date();
		Date rs=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			rs=sdf.parse(sdf.format(now));
		} catch (ParseException e)
		{
			
		}
		
		return rs;
	}
	
	public static String addMonth(String s, int n) {   
        try {   
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
  
                 Calendar cd = Calendar.getInstance();   
                 cd.setTime(sdf.parse(s));   
                 //cd.add(Calendar.DATE, n);//����һ��   
                 cd.add(Calendar.MONTH, n);//����һ����   
  
                 return sdf.format(cd.getTime());   
       
             } catch (Exception e) {   
                 return null;   
             }   
     }  

}
