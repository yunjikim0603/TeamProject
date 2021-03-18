package academy_community.vo;

import java.util.Date;

/*
 	CREATE TABLE academy_community(
	num INT PRIMARY KEY AUTO_INCREMENT,
	nickname VARCHAR(20) NOT NULL UNIQUE,
	subject VARCHAR(50) NOT NULL,
	content VARCHAR(3000) NOT NULL,
	readcount INT NOT NULL,
	file VARCHAR(500) NOT NULL,
	date DATETIME NOT NULL
	);
 */
public class AcademyBean {
	private int num;
	private String nickname;
	private String subject;
	private String content;
	private int readcount;
	private String address;
	private Date date;
	private String academy_name;
	private String time;
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAcademy_name() {
		return academy_name;
	}
	public void setAcademy_name(String academy_name) {
		this.academy_name = academy_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
