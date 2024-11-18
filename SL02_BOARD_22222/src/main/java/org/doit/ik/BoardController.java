package org.doit.ik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@GetMapping("/board/list")
	public String list() {
		log.info("> BoardController.list()..");
		
		return "/board/list";
	}
	
}
