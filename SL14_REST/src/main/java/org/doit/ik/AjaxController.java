package org.doit.ik;

import java.util.List;

import org.doit.ik.domain.EmpVO;
import org.doit.ik.mapper.AjaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/sample/*")
public class AjaxController {

    
   @Autowired
   private AjaxMapper ajaxMapper;
 
   @GetMapping( value = "/employees/{deptno}.ajax",   produces = {
         MediaType.APPLICATION_JSON_UTF8_VALUE 
   } )
   public ResponseEntity<List<EmpVO>> getDeptEmps(
           @PathVariable("deptno") int deptno
         , @RequestParam("deptno2") int deptno2
         , Authentication authentication) {      
      log.info("> MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
      log.info("> deptno2 : " + deptno2 );
      List<EmpVO> empList = this.ajaxMapper.selectByDeptEmps(deptno);
      
      // if (authentication != null && authentication.isAuthenticated() ) {}
      
      if ( empList == null) {
         return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(empList); 
      } else {
         return ResponseEntity.status(HttpStatus.OK).body(empList);
      }
   }
    
} // class
