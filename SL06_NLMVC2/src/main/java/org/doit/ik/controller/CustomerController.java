package org.doit.ik.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 공지사항 
@Controller
@RequestMapping("/customer/*")
public class CustomerController {

	@Autowired
	private NoticeDAO noticeDao;

	@GetMapping("noticeDel.htm")
	public String noticeDel(@RequestParam("seq") String seq) 
			throws ClassNotFoundException, SQLException {
		int rowCount = this.noticeDao.delete(seq);
		if (rowCount == 1) { 
			return "redirect:notice.htm";
		} else { 
			return "redirect:noticeDetail.htm?seq="+seq+"&error";
		} // if
	}

	// <!-- http://localhost/customer/noticeEdit.htm?seq=3 -->
	// <input type="submit" value="수정"  class="btn-save button"/>
	// <form action="" method="post">
	@PostMapping("noticeEdit.htm")
	public String noticeEdit(NoticeVO notice, Model model  ) 
			throws ClassNotFoundException, SQLException {
		int rowCount = this.noticeDao.update(notice);
		if (rowCount == 1) { 
			return "redirect:noticeDetail.htm?seq="+notice.getSeq();
		} else { 
			return "redirect:notice.htm";
		} // if    
	}

	//   <a class="btn-edit button" href="noticeEdit.htm?seq=${ notice.seq }">수정</a>
	@GetMapping("noticeEdit.htm")
	public String noticeEdit(@RequestParam("seq") String seq, Model model  ) 
			throws ClassNotFoundException, SQLException {
		NoticeVO notice = this.noticeDao.getNotice(seq);
		model.addAttribute("notice", notice);
		return "noticeEdit.jsp";
	}


	// <input class="btn-save button" type="submit" value="저장" />
	// <form action="" method="post">
	//          action = "http://localhost/customer/noticeReg.htm"
	@PostMapping( value =  "/noticeReg.htm" )
	public String noticeReg( NoticeVO notice ) 
			throws ClassNotFoundException, SQLException {   // 커맨드객체
		int rowCount = this.noticeDao.insert(notice);
		if (rowCount == 1) {
			// 글목록 페이지 이동  포워딩/리다이렉트
			return "redirect:notice.htm";
		} else {
			// 글쓰기 페이지 이동  포워딩
			return "noticeReg.htm?error";
		} // if      

	}
	/*
   @PostMapping( value =  "/noticeReg.htm" )
   public String noticeReg( @RequestParam("title") String title, 
         @RequestParam("content") String content, ) {
      NoticeVO notice = new NoticeVO();
      notice.setTitle(title);
      notice.setContent(content);

      return "noticeReg.jsp";
   }
	 */

	// <a class="btn-write button" href="noticeReg.htm">글쓰기</a>
	@GetMapping("/noticeReg.htm")
	public String noticeReg(HttpSession session) {
		// 스프링 시큐리티(보안) : 인증+권한 처리
		return "noticeReg.jsp";
	}

	// http://localhost/customer/noticeDetail.htm?seq=1
	@GetMapping("/noticeDetail.htm")
	public String noticeDetail(Model model
			, @RequestParam( value = "seq") String seq )
					throws ClassNotFoundException, SQLException {
		NoticeVO  notice  = this.noticeDao.getNotice(seq);      
		model.addAttribute("notice", notice);          
		return "noticeDetail.jsp";
	}

	/* [1]
   // http://localhost/customer/noticeDetail.htm?seq=1
   @Override
   public ModelAndView handleRequest(
         HttpServletRequest request
         , HttpServletResponse response) throws Exception {
      // 리턴자료형 : ModelAndView  p282
      ModelAndView mav = new ModelAndView("noticeDetail.jsp");      
      String seq = request.getParameter("seq");       
      NoticeVO  notice  = this.noticeDao.getNotice(seq);      
      mav.addObject("notice", notice);          
      return mav;
   }
	 */

	// 공지사항 목록 요청 URL 
	// http://localhost/customer/notice.htm?page=2&field=검색조건&query=검색어
	@GetMapping("/notice.htm")
	public String notices(Model model
			, @RequestParam( value = "page", defaultValue = "1") int page
			, @RequestParam( value = "field", defaultValue = "title") String field
			, @RequestParam( value = "query", defaultValue = "") String query
			) throws ClassNotFoundException, SQLException {
		List<NoticeVO> list = this.noticeDao.getNotices(page, field, query);
		model.addAttribute("list", list);
		model.addAttribute("message", "Hello World!");
		return "notice.jsp";
	}

	/* NoticeController.java 코딩.
   public ModelAndView handleRequest(
         HttpServletRequest request
         , HttpServletResponse response) throws Exception {
      // 리턴자료형 : ModelAndView  p282
      ModelAndView mav = new ModelAndView();

      String ppage = request.getParameter("page");
      String pfield = request.getParameter("field");
      String pquery = request.getParameter("query");

      int page = 1;
      String field = "title";
      String query = "";

      if( ppage != null && !ppage.equals("") ) page = Integer.parseInt(ppage);
      if( pfield != null && !pfield.equals("") ) field = pfield;
      if( pquery != null && !pquery.equals("") ) query = pquery;

      List<NoticeVO> list = this.noticeDao.getNotices(page, field, query);

      mav.addObject("list", list);
      mav.addObject("message", "Hello World!");

      mav.setViewName("notice.jsp");

      return mav;
   }
	 */
} // class






