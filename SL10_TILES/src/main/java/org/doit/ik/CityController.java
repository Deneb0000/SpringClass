package org.doit.ik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/city/")
public class CityController {

	@GetMapping("/london")
	public String london() {
		log.info(">> CityController.lodonong Get..");
		return "city/london.tiles";	
	}

	@GetMapping("/paris")
	public String paris() {
		log.info(">> CityController.paris Get..");
		return "city/paris.tiles";

	}
	@GetMapping("/seoul")
	public String seoul() {
		log.info(">> CityController.seoul Get..");
		return "city/seoul.tiles";

	}
}
