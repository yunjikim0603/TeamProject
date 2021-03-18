package job_community.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

import job_community.dao.JobBoardDAO;
import job_community.vo.JobBoardBean;

public class JobBoardListService {

	public int getListCount() {
		System.out.println("JobBoardListService - getListCount()");
		int listCount = 0; 
		
		Connection con = getConnection();
		JobBoardDAO boardDAO = JobBoardDAO.getInstance();
		boardDAO.setConnection(con);

		listCount = boardDAO.selectListCount();
		
		close(con);
		
		return listCount;
	}
	
	public ArrayList<JobBoardBean> getJobList(){
		System.out.println("JobBoardService - getJobList()");
		ArrayList<JobBoardBean> jobList = null;
		
		OpenApi http = new OpenApi();
		try {
			jobList = http.sendGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jobList;
	}

	public ArrayList<JobBoardBean> getArticleList() {
		System.out.println("BoardListService - getArticleList()");
		ArrayList<JobBoardBean> articleList = null;
		
		Connection con = getConnection();
		JobBoardDAO boardDAO = JobBoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		articleList = boardDAO.selectArticleList();
		
		close(con);
		
		return articleList;
	}
}
