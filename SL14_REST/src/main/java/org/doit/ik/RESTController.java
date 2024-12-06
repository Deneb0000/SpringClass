package org.doit.ik;

import java.util.List;

import org.doit.ik.domain.EmpVO;
import org.doit.ik.domain.Ticket;
import org.doit.ik.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@RestController
@Log4j
@RequestMapping("/scott/*")
public class RESTController {
	
	//	/scott/getText
	@GetMapping("/getText")
	public String getText() {
		log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);	//MIME TYPE :(스프링 http://의 마메타입)?
		return "헬러우 월드!";
	}
	
	
	//	/scott/getText2
	@GetMapping(value =  "/getText2",  produces = "text/plan; charset=UTF-8" )
	public String getText2() {
		log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);	//MIME TYPE :(스프링 http://의 마메타입)?
		return "헬러우 월드!";
	}
	
	@Autowired
	private EmpMapper empMapper;
	
	
//	@GetMapping(value =  "/employees")
	@GetMapping(value =  "/employees", produces = {
	         MediaType.APPLICATION_JSON_UTF8_VALUE
	         , MediaType.APPLICATION_XML_VALUE
	   })
	public List<EmpVO> getEmpList() {
		log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);	//MIME TYPE :(스프링 http://의 마메타입)?
		return this.empMapper.selectAll();
	}
	
	/*
//	@GetMapping(value =  "/employees")
	@GetMapping(value =  "/employee/{empno}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE
			, MediaType.APPLICATION_XML_VALUE
	})
	public EmpVO selectByEmpno(@PathVariable("empno")int empno) {
		log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);	//MIME TYPE :(스프링 http://의 마메타입)?
		return this.empMapper.selectByEmpno(empno);
	}
	*/
	
	
//	REST 방식 호출 -> 뷰 X, 데이터(String, xml, json) 응답
//								+ 정상적인 데이터 인지 비정상적인 데이터 인지
	//상대값을 같이 가져가는것 ReponseEntity 객체 = 응답 데이터 + 상태 메세지
	@GetMapping(value =  "/employee/{empno}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE
			, MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<EmpVO> selectByEmpno(@PathVariable("empno")int empno) {
		log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);	//MIME TYPE :(스프링 http://의 마메타입)?
		EmpVO vo = this.empMapper.selectByEmpno(empno);
		if (vo == null) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
	}
	
	
	@PostMapping(value = "/idCheck/{empno}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }) //제이슨형태로만 응답하겠다
	   public int idCheck(@PathVariable("empno") String empno) {    
	      return this.empMapper.idCheck(empno);
	   }
	
	
	@PostMapping(value = "/ticket", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	   public Ticket convert(@RequestBody Ticket ticket) {      
	      ticket.setOwner( ticket.getOwner() + "님" );
	      log.info(">> "+ ticket);
	      return ticket;
	   }
	
}//class
