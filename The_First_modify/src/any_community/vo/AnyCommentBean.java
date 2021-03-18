package any_community.vo;

import java.sql.Date;

public class AnyCommentBean {
	private int comment_num;
	private int post_num;
	private String nickname;
	private String comment;
	private Date date;
	private String time;

	public AnyCommentBean() {
		super();
	}

	public AnyCommentBean(int post_num, String nickname, String comment) {
		super();
		this.post_num = post_num;
		this.nickname = nickname;
		this.comment = comment;
	}

	public int getComment_num() {
		return comment_num;
	}

	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public int getPost_num() {
		return post_num;
	}

	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
