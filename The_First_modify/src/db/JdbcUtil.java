package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil {
	// DB 접속(커넥션풀), 자원반환, Commit, Rollback 작업 등을 수행하는 클래스
	// => 모든 메서드는 인스턴스 생성 없이 호출 가능하도록 static 메서드로 정의
	public static Connection getConnection(){
	// ==== DBCP 를 사용하여 커넥션 풀에서 Connection 객체 가져오기 ====
		Connection con = null;
		
		try {
			// 1. Context 객체 가져오기(=> META-INF 폴더 내의 context.xml 파일 내용 가져오기)
			Context initCtx = new InitialContext();
			// 2. context.xml 파일 내의 <Resouce> 태그 부분 가져오기(변하지 않음)
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			// 3. <Resource> 태그 내의 name 속성 부분 가져오기(name 속성 값에 따라 변함)
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			
			// 4. DataSource 객체(커넥션풀)로부터 Connection 객체 가져오기
			con = ds.getConnection();
			// => context.xml 에 DB 계정 및 패스워드를 하드코딩하지 않고 입력받아 사용 가능
//			con = ds.getConnection(username, password); 
			
			// 5. JDBC 의 기본 설정은 Auto Commit 이 적용되므로, Auto Commit 해제 시 설정할 내용(옵션)
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	// ==== Connection, PreparedStatement, ResultSet 객체에 대한 자원반환(close()) 작업 => 오버로딩 활용 ====
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// ==== Commit, Rollback 작업(옵션 -> Auto Commit 기능 해제 시 필요한 메서드) ====
	public static void commit(Connection con) {
		try {
			con.commit();
			System.out.println("Commit Success!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		try {
			con.rollback();
			System.out.println("Rollback Success!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}












