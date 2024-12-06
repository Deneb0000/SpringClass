package org.doit.ik;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {
	
	@GetMapping(value="/index.htm")
	public String home(Locale locale, Model model) {
		return "home.index"; // viewName
	}
	
}