package any_community.svc;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import any_community.dao.CommentDAO;
import any_community.vo.AnyCommentBean;
import coding_free.dao.CodingFreeDAO;
import coding_free.vo.CodingFreeCommentBean;

public class CommentListService {

	public int getCommentListCount(int post_num) {
		System.out.println("CommentListService - getCommentListCount");
		int cmmntListCount = 0;
		
		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);
		
		cmmntListCount = cdao.selectCommentListCount(post_num);
		
		close(con);
		
		return cmmntListCount;
	}

	public ArrayList<AnyCommentBean> getCommentList(int post_num, int cmmnt_page, int cmmnt_limit) {
		System.out.println("CommentListService - getCommentList");
		ArrayList<AnyCommentBean> list = new ArrayList<AnyCommentBean>();
		
		Connection con = getConnection();
		CommentDAO cdao = CommentDAO.getInstance();
		cdao.setConnection(con);
		
		list = cdao.getCommentList(post_num,cmmnt_page,cmmnt_limit);
		
		close(con);
		
		return list;
	}
	

}
