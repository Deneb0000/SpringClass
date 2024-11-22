package org.doit.ik.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//공지사항의 목록을 처리하는 컨트롤러
public class NoticeController  implements Controller{
	
	private NoticeDAO noticeDAO;
	

	public NoticeController() {
		
	}

	public NoticeController(NoticeDAO noticeDAO) {
		super();
		this.noticeDAO = noticeDAO;
	}

	// 공지사항 목록 요청 URL
	//http://localhost/customer/notice.htm?page=2&field=검색조건&query=검색어
	//요렇게 올것이다
	@Override
	public ModelAndView handleRequest(
			HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		//리턴 자료형 : modelandview p282
		ModelAndView mav =new ModelAndView();
		
		String ppage = request.getParameter("page");
	      String pfield = request.getParameter("field");
	      String pquery = request.getParameter("query");
	      
	      int page = 1;
	      String field = "title";
	      String query = "";
	      
	      if( ppage != null && !ppage.equals("") ) page = Integer.parseInt(ppage);
	      if( pfield != null && !pfield.equals("") ) field = pfield;
	      if( pquery != null && !pquery.equals("") ) query = pquery;
	      
	      List<NoticeVO> list = this.noticeDAO.getNotices(page, field, query);
	      
	      mav.addObject("list", list);
	      mav.addObject("message", "Hello World!");
	      
	      mav.setViewName("notice.jsp");
	      
		
		return mav;
	}//ModelAndView

}//class
