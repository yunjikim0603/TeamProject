package academy_community.vo;

public class ActionForward {
	/*
	 * 서블릿에서 클라이언트로부터의 요청을 받아 처리한 후 View 페이지로 이동(포워딩)할 때
	 * 이동할 View 페이지의 URL(주소)과 포워딩할 방식(Dispatch 또는 Redirect) 을 다루기 위한 클래스
	 * => 주소 저장 변수와 포워딩 방식 저장 변수 및 Getter/Setter 로 구성
	 */
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