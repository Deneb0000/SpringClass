package org.doit.ik.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.mapper.NoticeMapper;
import org.doit.ik.service.MemberShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

// 공지사항 
@Controller
@RequestMapping("/customer/*")
public class CustomerController {

   @Autowired
   private NoticeMapper noticeDao;
   // private NoticeDaoImpl noticeDao;
   
   @Autowired
   private MemberShipService memberShipService; // 주입!! 서비스에 @Service 필요.
  

   // 컨트롤러 메서드 선언
   // <a href="download.htm?dir=customer/upload&file=${ notice.filesrc  }">${ notice.filesrc }</a>
   //  download.htm
   //  ?
   //  dir=customer/upload    경로
   //  &
   //  file=다운로드할 파일명
   @RequestMapping("download.htm")
   public void download (
         HttpServletResponse response
         , HttpServletRequest request
         , @RequestParam("dir") String p
         , @RequestParam("file") String f
         ) throws Exception{ 

      String fname =  f;  
      
      response.setHeader("Content-Disposition","attachment;filename="+ new String(fname.getBytes(), "ISO8859_1"));      

      String fullPath = request.getServletContext().getRealPath(   p + "/" + fname);

      FileInputStream fin = new FileInputStream(fullPath);
      ServletOutputStream sout = response.getOutputStream(); // 응답 스트림
      byte[] buf = new byte[1024];
      int size = 0;
      while((size = fin.read(buf, 0, 1024)) != -1) {
         sout.write(buf, 0, size); 
      }
      fin.close();
      sout.close();

   } // download

   // <a class="btn-del button" href="noticeDel.htm?seq=${ notice.seq }&filesrc=${ notice.filesrc }">삭제</a>
   @GetMapping("noticeDel.htm")
   public String noticeDel(@RequestParam("seq") String seq
         ,@RequestParam("filesrc") String filesrc 
         , HttpServletRequest request) 
         throws ClassNotFoundException, SQLException {
      // 1. 첨부파일이 있는 공지사항이라면 첨부파일 폴더 삭제.
      // if( filesrc != null )          X
      // if(! filesrc.equals(""))       O
      String uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
      File delFile = new File(uploadRealPath, filesrc);
      // System.out.println(">>> " + delFile.isDirectory() + " / " + delFile.isFile() );
      if (  delFile.exists()  && delFile.isFile()  ) {
         delFile.delete();
      } // if
      
      // 2. 
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
   public String noticeEdit(NoticeVO notice, Model model,
		   @RequestParam("o_filesrc") String oFilesrc, // 빈 문자열이 아니라면, 첨부파일이 있는것이다.(jsp에 추가한 태그)				
		   HttpServletRequest request 
		   ) 
         throws ClassNotFoundException, SQLException, IllegalStateException, IOException {
      
	   // 1.
	   CommonsMultipartFile multipartFile = notice.getFile();
	      String uploadRealPath = null;
	      if ( !multipartFile.isEmpty() ) {
	         uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
	         System.out.println("> uploadRealPath : " + uploadRealPath);
	         // 	  이전의 원래 첨부파일 삭제하는 코딩
	         File delFile = new File(uploadRealPath, oFilesrc);
	         // System.out.println(">>> " + delFile.isDirectory() + " / " + delFile.isFile() );
	         if (  delFile.exists()  && delFile.isFile()  ) {
	            delFile.delete();
	         } // if
	         
	         
	         //        새로운 첨부파일 저장.
	         String  originalFilename = multipartFile.getOriginalFilename();
	         String filesystemName = getFileNameCheck(uploadRealPath, originalFilename);

	         File dest = new File(uploadRealPath, filesystemName );
	         multipartFile.transferTo(dest); // 실제 파일 저장

	         notice.setFilesrc(filesystemName);
	      } else { // 첨부파일 없는경우
	    	  notice.setFilesrc(oFilesrc);
	    	  
	      }// if

	   
	   
	   // 2.
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
      return "customer.noticeEdit"; // 왜 이것만 바꿀까?
   }
   //                                                        /customer/upload           a.txt
   private String getFileNameCheck(String uploadRealPath, String originalFilename) {
      int index = 1;      
      while( true ) {         
         File f = new File(uploadRealPath, originalFilename);         
         if( !f.exists() ) return originalFilename;         
         // upload 폴더에 originalFilename 파일이 존재한다는 의미         a(2)                 .txt
         String fileName = originalFilename.substring(0, originalFilename.length() - 4 );  //   a
         String ext =  originalFilename.substring(originalFilename.length() - 4 );  // .txt
         // asdfasf-3.txt
         originalFilename = fileName+"-"+(index)+ext;

         index++;
      } // while 
   }

   // <input class="btn-save button" type="submit" value="저장" />
   // <form action="" method="post">
   //          action = "http://localhost/customer/noticeReg.htm"
   @PostMapping( value =  "/noticeReg.htm" )
   public String noticeReg( NoticeVO notice , HttpServletRequest request ) 
         throws ClassNotFoundException, SQLException, IllegalStateException, IOException {   // 커맨드객체
      // 1.
      CommonsMultipartFile multipartFile = notice.getFile();
      String uploadRealPath = null;
      if ( !multipartFile.isEmpty() ) {
         uploadRealPath = request.getServletContext().getRealPath("/customer/upload");
         System.out.println("> uploadRealPath : " + uploadRealPath);

         //        a_3.txt
         String  originalFilename = multipartFile.getOriginalFilename();
         String filesystemName = getFileNameCheck(uploadRealPath, originalFilename);

         File dest = new File(uploadRealPath, filesystemName );
         multipartFile.transferTo(dest); // 실제 파일 저장

         notice.setFilesrc(filesystemName);
      } // if

      notice.setWriter("deneb");

      // 2.
      // int rowCount = this.noticeDao.insert(notice);
      int rowCount = 1; // 일단 고정값 준다.
      //this.noticeDao.insertAndPointUpOfMember(notice, "deneb"); // 작성자에 포인트를 줌
      // this.memberShipService.insertAndPointUpOfMember(notice, "deneb"); // 작성자에 포인트를 줌
      rowCount = this.noticeDao.insert(notice); // 왜 원위치?
      
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
      return "customer.noticeReg"; // ViewName
   }

   // http://localhost/customer/noticeDetail.htm?seq=1
   @GetMapping("/noticeDetail.htm")
   public String noticeDetail(Model model
         , @RequestParam( value = "seq") String seq )
               throws ClassNotFoundException, SQLException {
	   
	  this.noticeDao.hitUp(seq); // 조회수 증가 코딩 추가(상세보기 클릭 시)
	  
      NoticeVO  notice  = this.noticeDao.getNotice(seq);      
      model.addAttribute("notice", notice);          
      return "customer.noticeDetail"; // ViewName
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
      return "customer.notice";
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






