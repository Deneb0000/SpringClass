package org.doit.ik.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

// 회원가입
@Controller
@RequestMapping("/common/*")
@Log4j
public class CommonController {


	@GetMapping("/accessError.htm")
	public String accessDenied(Model model
	         , Authentication auth) throws Exception{ 
	      log.info("> /common/accessError.htm... GET");
	      model.addAttribute("msg", "접근 금지됨!!!");
	      
	      // /WEB-INF/views/common/accessError.jsp
	      return "/common/accessError";
	   }
  
} // class






