package org.doit.ik.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//공지사항의 목록을 처리하는 컨트롤러
public class NoticeDetailController  implements Controller{
	
	private NoticeDAO noticeDAO;
	

	public NoticeDetailController() {
		
	}
//생성자 DI
	public NoticeDetailController(NoticeDAO noticeDAO) {
		super();
		this.noticeDAO = noticeDAO;
	}

	// 공지사항 목록 요청 URL
	//http://localhost/customer/noticeDetail.htm?seq=1
	//요렇게 올것이다
	@Override
	public ModelAndView handleRequest(
			HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		//리턴 자료형 : modelandview p282
		ModelAndView mav =new ModelAndView("noticeDetail.jsp");
		
		String seq = request.getParameter("seq");
	   	        
	      NoticeVO notice = this.noticeDAO.getNotice(seq);
	      
	      mav.addObject("notice", notice);
	     		
		return mav;
	}//ModelAndView

}//class
