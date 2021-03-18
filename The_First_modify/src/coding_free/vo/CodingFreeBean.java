package coding_free.vo;

import java.sql.Date;

public class CodingFreeBean {
	private int num;
	private String nickname;
	private String subject;
	private String content;
	private int readcount;
	private String file;
	private Date date;
	private String time;
	
	public CodingFreeBean() {}

	public CodingFreeBean(String nickname, String subject, String content, String file) {
		super();
		this.nickname = nickname;
		this.subject = subject;
		this.content = content;
		this.file = file;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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
