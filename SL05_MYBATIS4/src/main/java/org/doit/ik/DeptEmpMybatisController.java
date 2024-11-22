package org.doit.ik;

import java.util.Iterator;
import java.util.List;

import org.doit.ik.domain.DeptEmpSalgradeDTO;
import org.doit.ik.domain.EmpDTO;
import org.doit.ik.mapper.DeptEmpSalgradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
@AllArgsConstructor
public class DeptEmpMybatisController {
	
	//private static final Logger logger = LoggerFactory.getLogger(DeptEmpMybatisController.class);
	
	@Autowired
	private DeptEmpSalgradeMapper deptEmpSalgradeMapper;
	
	//컨트롤러 메서드 구현
	@GetMapping(value = "/dept/emp")
	public void getDeptEmpSalgrad(Model model) {
		log.info("> DeptEmpSalgradeMapper.getDeptEmpSalgrade()...");
		
		List<DeptEmpSalgradeDTO> desList = this.deptEmpSalgradeMapper.getDept();
		
		for (DeptEmpSalgradeDTO deptEmpSalgradeDTO : desList) {
		List<EmpDTO> empList	= this.deptEmpSalgradeMapper.getEmpOfDept(
					deptEmpSalgradeDTO.getDeptno());
		deptEmpSalgradeDTO.setEmpList(empList);		
		}//
		
		model.addAttribute("desList", desList);
	}
}
