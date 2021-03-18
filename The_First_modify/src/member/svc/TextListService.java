package member.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import member.dao.TextDAO;
import member.vo.TextBean;

public class TextListService {

	public int getListCount(String receiver) {
		System.out.println("TextListService - getListCount");
		int listCount = 0; 
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		listCount = textDAO.selectListCount(receiver);
		
		close(con);
		
		return listCount;
	}

	public ArrayList<TextBean> getTextList(int page, int limit, String receiver) {
		System.out.println("TextListService - getTextList");
		ArrayList<TextBean> textList = null;
		
		Connection con = getConnection();
		TextDAO textDAO = TextDAO.getInstance();
		textDAO.setConnection(con);
		
		textList = textDAO.selectTextList(page, limit, receiver);
		
		close(con);
		
		return textList;
	}

}
