package notice.vo;

import java.util.Date;

/*
 * notice 테이블 컬럼에 해당하는 멤버변수 생성 및 Getter/Setter 정의
 * -------------------------------------
	CREATE TABLE notice(
	num INT PRIMARY KEY AUTO_INCREMENT,
	nickname VARCHAR(20) NOT NULL UNIQUE,
	subject VARCHAR(50) NOT NULL,
	content VARCHAR(3000) NOT NULL,
	readcount INT NOT NULL,
	file VARCHAR(500) NOT NULL,
	date DATETIME NOT NULL
	);
 * ---------------------------------------
 */

public class NoticeBean {
	private int num;
	private String nickname;
	private String subject;
	private String content;
	private int readcount;
	private String file;
	private Date date;
	private String time;
	
	public NoticeBean() {}

	public NoticeBean(int num, String nickname, String subject, String content, int readcount, String file, Date date,
			String time) {
		super();
		this.num = num;
		this.nickname = nickname;
		this.subject = subject;
		this.content = content;
		this.readcount = readcount;
		this.file = file;
		this.date = date;
		this.time = time;
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
