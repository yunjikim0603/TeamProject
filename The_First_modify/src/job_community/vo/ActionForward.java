package job_community.vo;

public class ActionForward {
	
	private String path; // 서블릿 요청 처리 후 포워딩 할 View 페이지의 URL(주소) 저장할 변수
	private boolean isRedirect; // 포워딩 할 방식을 저장할 변수(true : Redirect 방식, false : Dispatch 방식)
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
