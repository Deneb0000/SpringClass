package org.doit.ik;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j;


@Controller
@Log4j
public class HomeController {


	//properties mapping과 동일
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@GetMapping(value = "/index.htm")
	public String home(Locale locale, Model model) {

		return "index.jsp";	//web-inf/index.jsp
	}

}