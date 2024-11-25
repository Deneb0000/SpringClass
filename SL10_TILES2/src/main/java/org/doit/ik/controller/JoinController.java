package org.doit.ik.controller;

import org.doit.ik.domain.MemberVO;
import org.doit.ik.persistence.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 회원가입
@Controller
@RequestMapping("/joinus/*")
public class JoinController {

	@Autowired
	private MemberDao memberDao;
	
	// 로그인     /joinus/login.htm  ->   /joinus/login.jsp
	@GetMapping("/login.htm")
	public String login() throws Exception { // join함수 생성!!
		return "joinus.login";
	}
	
	// 로그인 후 처리는 스프링 시큐리티로 나중에 하자..
	
	// 회원가입    /joinus/join.htm ->    /joinus/join.jsp 이동하게
	@GetMapping("/join.htm")
	public String join() throws Exception { // join함수 생성!!
		return "joinus.join";
	}
	
	// 회원가입    /joinus/join.htm + POST   ->  join.htm에 정보를 저장 후, 메인페이지 이동하게
	@PostMapping("/join.htm")
	public String join(MemberVO member) throws Exception { // join함수 생성!!
		this.memberDao.insert(member);
		return "redirect:../index.htm"; // 상위로 올라간다고 해줘야, 위의 경로로 잡음..  
	}
  
} // class






