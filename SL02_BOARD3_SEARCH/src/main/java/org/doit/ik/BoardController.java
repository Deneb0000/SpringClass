package org.doit.ik;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;
import org.doit.ik.domain.PageDTO;
import org.doit.ik.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
	
	//private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;
	
	
	/*@GetMapping("/board/list")
	public String list(Model model) {
		log.info("> BoardController.list()...");
		
		model.addAttribute("list", this.boardService.getList());
		
		
		return "/board/list";
	}
	*/

	/* [2] 페이징 처리가 안된
	@GetMapping("/list")
	public void list(Model model) {
		log.info("> BoardController.list()...");
		
		model.addAttribute("list", this.boardService.getList());
	}
	*/
	

	/*
	@GetMapping("/board/list")
	public ModelAndView list(ModelAndView mav) {
		log.info("> BoardController.list()...");
		
		mav.addObject("list", this.boardService.getList());
		mav.setViewName("/board/list");	
		
		return mav;		
		*/
	
	
	//페이징처리한 컨트롤러 메세지
	//http://localhost/board/list
	//http://localhost/board/list?pageNum=3&amount=10
	@GetMapping("/list")
	public void list(Model model, Criteria criteria) {
		log.info("> BoardController.list()...");
		model.addAttribute("list", this.boardService.getListWithPaging(criteria));
		int total = this.boardService.getTotal(criteria);
	      model.addAttribute("pageMaker", new PageDTO(criteria, total));
	}
	
	@GetMapping("/register")
	public void register() {
		log.info("> BoardController.register()...");
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("> BoardController.register()...Post");
		this.boardService.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		return "redirect:/board/list";
	}
	
	 @PostMapping(value = {"/modify"})
		public String modify(BoardVO boardVO, RedirectAttributes rttr, @ModelAttribute("criteria") Criteria criteria) {
			log.info("> BoardController.modify POST()...");
			
			if ( this.boardService.modify(boardVO)) {
				rttr.addFlashAttribute("result", "SUCCESS");
			}//if
			
			/* return String.format("redirect:/board/get?bno=%d", boardVO.getBno()); */
			
			/*
			rttr.addAttribute("pageNum", criteria.getPageNum());
			rttr.addAttribute("amount", criteria.getAmount());
			rttr.addAttribute("type", criteria.getType());
			rttr.addAttribute("keyword", criteria.getKeyword());
			*/
			//return String.format("redirect:/board/get?bno=%d", boardVO.getBno());

			
			//[2]
			String params = criteria.getListLink();
			//XXX :?pageNum=3&amount=10&type=TC&keyword=%EC%9E%90%EB%B0%9
			//System.out.println("XXX :" + params);
			
			return String.format("redirect:/board/get%s&bno=%d",params, boardVO.getBno());
			
	 }
	
	 
	
	//글상세보기 요청
	
	//[3] 
	 @GetMapping(value = {"/get","/modify"})
	 public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("criteria") Criteria criteria) {
		 log.info("> BoardController.get()...");
		 model.addAttribute("boardVO", this.boardService.get(bno));
		 //model.addAttribute("criteria", criteria);
	 }
	 
	 
/*	2번 
	 @GetMapping(value = {"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, Criteria criteria) {
		log.info("> BoardController.get()...");
		model.addAttribute("boardVO", this.boardService.get(bno));
		model.addAttribute("criteria", criteria);
	}
	*/
	
	/*
	@GetMapping(value = {"/get/{}bno"})
	public void get(@PathVariable("bno") Long bno, Model model) {
		log.info("> BoardController.get()...");
		model.addAttribute("boardVO", this.boardService.get(bno));
	}
	*/
	 
	@GetMapping(value = {"/remove"})
	public String remove(Model model, @RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("> BoardController.remove()...");
		if (this.boardService.remove(bno)) {
			rttr.addFlashAttribute("result", "SUCCESS");
		}//if
		return "redirect:/board/list";
	}
		
}
