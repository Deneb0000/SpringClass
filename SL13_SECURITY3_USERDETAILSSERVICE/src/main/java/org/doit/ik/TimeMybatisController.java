package org.doit.ik;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.doit.ik.mapper.TimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TimeMybatisController {
	
	
	//root어찌구에서 생성된 timeMapper 객체 연결(주입) DI
	@Autowired
	private TimeMapper timeMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(TimeMybatisController.class);
	
	//properties mapping과 동일
	//@RequestMapping(value = "/time", method = RequestMethod.GET) 아래로 쓴거랑 똑같음
	@GetMapping(value = "/time")
	public String time(Locale locale, Model model
			, HttpServletRequest request) {
			
		// 이제 DAO랑 DAOImpl 을 했자나? 근데 그렇게 하지 말고 TimeMapper 인터페이스 만들어			
		logger.info(" > TimeMybatisController.time()... ");
		
		String currentTime = this.timeMapper.getTime();
		model.addAttribute("currentTime",currentTime);
		
		String currentNextTime = this.timeMapper.getNextTime();
		
	    request.setAttribute("currentNextTime", currentNextTime);
									
		return "time";
	}	
}