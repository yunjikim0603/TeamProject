package member.vo;

import java.sql.Timestamp;

public class TextBean {

	private int idx;
	private String sender;
	private String receiver;
	private String subject;
	private String contents;
	private boolean isChecked;
	private Timestamp send_date;

	public TextBean() {
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Timestamp getSend_date() {
		return send_date;
	}

	public void setSend_date(Timestamp send_date) {
		this.send_date = send_date;
	}

}
