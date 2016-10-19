package com.fei.base.util;

/**
 * JDBC���ݲ���������
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlJdbcUtil {
	
	//�����������ݿ�����Ҫ���ֶ�  
    private static String driveClassName = "oracle.jdbc.driver.OracleDriver";  
    private static String url = "jdbc:oracle:thin:@192.168.1.9:1521:ORCL";  
    private static String username = "SPIDER";  
    private static String password = "SPIDER";  
    //ͨ�������ļ�Ϊ�����ֶθ�ֵ  
    static{  
        try {  
            //����������  
            Class.forName(driveClassName); 
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    //
    

	/**
	 * 
	 * @Title getConnection
	 * @Description ������һ�仰�����������������
	 * @return �趨�ļ�
	 * @return �������� Connection
	 * @throws �׳����쳣����
	 */
	public static Connection getConnection() {
		Connection conn = null;
		// ������ͨjdbc����
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// �������ݳ�����
		/*
		 * Context ctx = null; DataSource ds = null; try { ctx = new
		 * InitialContext(); ds = (DataSource)
		 * ctx.lookup("java:/comp/env/lespool"); conn = ds.getConnection(); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
		return conn;
	}

	/**
	 * @param test
	 * @param rs
	 * @param conn
	 * @param closeConnection
	 * @return ��������HashMap
	 */
	public static ResultSet query(String sql, Connection conn) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 
	 * ִ��SQL��ѯ��䣨select������List<HashMap>����� @
	 * */
	@SuppressWarnings("unchecked")
	public static List<HashMap> queryList(String sql, Connection conn,
			boolean closeConnection) {
		return rsToList(query(sql, conn), conn, closeConnection);
	}

	/**
	 * ִ��SQL��ѯ��䣨select������һ��HashMap���
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static HashMap find(String sql, Connection conn,
			boolean closeConnection) {
		return rsToALine(query(sql, conn), conn, closeConnection);
	}

	/**
	 * ���ؽ�����еĵ�һ����¼��HashMap��ʽ���
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static HashMap find(ResultSet rs, Connection conn,
			boolean closeConnection) {
		return rsToALine(rs, conn, closeConnection);
	}

	/**
	 * ִ��û�н������SQL���(insert update �� delete)
	 * 
	 * */
	public static int execSQL(String sql, Connection conn,
			boolean closeConnection) {
		int affectNum = 0;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			affectNum = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (closeConnection) {
				closeAll(null, ps, conn);
			}
		}
		return affectNum;
	}
	
	/**
	 * ִ��û�н������SQL���(insert update �� delete)
	 * 
	 * */
	public static int execSQL(String sql, Connection conn,Object[] val
			) {
		int affectNum = 0;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<val.length;i++){
				ps.setString((i+1), val[i].toString());
			}
			
			affectNum = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return affectNum;
	}

	/**
	 * �������ת��ΪList<HashMap>
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static List<HashMap> rsToList(ResultSet rs, Connection conn,
			boolean closeConnection) {
		ArrayList ret = new ArrayList();
		ArrayList rsColNames = new ArrayList();
		if (rs != null) {
			try {
				while (rs.next()) {
					rsColNames = getRsColumns(rs, conn);
					// System.out.println(rs.getInt("replyId"));
					Map line = new HashMap();
					for (int i = 0; i < rsColNames.size(); i++) {
						line.put(rsColNames.get(i).toString(),
								rs.getObject(rsColNames.get(i).toString()));
					}
					ret.add(line);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (closeConnection) {
					closeAll(rs, null, conn);
				}
			}
			// System.out.println(ret);
			return ret;
		}
		return null;
	}

	/**
	 * ���ؽ����ResultSet�е�һ�е�HashMap��ʽ����
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static HashMap rsToALine(ResultSet rs, Connection conn,
			boolean closeConnection) {
		ArrayList rsColNames = new ArrayList();
		if (rs != null) {
			try {
				while (rs.next()) {
					rsColNames = getRsColumns(rs, conn);
					HashMap line = new HashMap();
					for (int i = 0; i < rsColNames.size(); i++) {
						line.put(rsColNames.get(i).toString(),
								rs.getObject(rsColNames.get(i).toString()));
					}
					closeAll(rs, null, null);
					return line;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (closeConnection) {
					closeAll(rs, null, conn);
				}
			}
		}
		return null;
	}

	/**
	 * ȡ�ý����ResultSet�б���ֶ���Ϣ
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static ArrayList getRsColumns(ResultSet rs, Connection conn) {
		ArrayList ret = new ArrayList();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int k;
			k = rsmd.getColumnCount();
			for (int i = 1; i <= k; i++) {
				ret.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ͷ���Դ
	 * 
	 * */
	public static void closeAll(ResultSet rs, PreparedStatement ps,
			Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Connection conn = MysqlJdbcUtil.getConnection();
		/*String sql = "insert into SOLR_COMMITINDEX "
				+ "values ('ccc','Thread-8',13,32,23,20140721,7)";*/
		String sql = "insert into SOLR_ERRORINDEX values ('"+UUIDGenerator.getUUID()+"','aaa','bbb',20141212020203,20141212020203)";
		MysqlJdbcUtil.execSQL(sql, conn, true);
	}
}