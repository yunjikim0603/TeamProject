package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.vo.ActionForward;

public interface Action {
	/*
	 * XXXAction 클래스로부터 요청이 들어올 때
	 * 서로 다른 클래스들로부터의 요청이므로 각각의 타입으로 처리를 수행해야한다.
	 * => 이를 간단히 다형성 활용을 통해 하나의 타입으로 처리하기 위한 Action 인터페이스 설계
	 *    각 요청을 받아들일 execute() 메서드를 공통으로 정의하여 요청(request), 응답(response) 객체 전달받음
	 * => execute() 메서드 실행 후 포워딩 주소와 포워딩 방식을 저장한 ActionForward 객체를 리턴
	 */
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
