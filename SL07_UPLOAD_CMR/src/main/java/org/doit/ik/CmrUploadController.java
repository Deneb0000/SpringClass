package org.doit.ik;

import java.io.File;
import java.util.List;

import org.doit.ik.domain.Message;
import org.doit.ik.domain.MultiMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.extern.log4j.Log4j;


@Controller
@Log4j
@RequestMapping("/cmr/*")
public class CmrUploadController {

	@GetMapping("/multiupload")
	public void multiupload() {		
	}//multupload
	
	@PostMapping("/multiupload")
	public void multiupload(
			MultiMessage multiMessage
			) {
		log.info("> CmrUploadController.multimessageupload()... Post");
		log.info("-".repeat(30));
		log.info("1. output : " + multiMessage.getOutput());

		String uploadFolder = "C:\\upload";

		List<CommonsMultipartFile> attachList = multiMessage.getAttach();
		
		for (CommonsMultipartFile attach : attachList) {
			
			if(! attachList.isEmpty()) {
				
				// 1. 첨부된 파일의 정보 출력
				log.info("-".repeat(30));
				log.info("2. originalFilename : " + attach.getOriginalFilename());
				log.info("2. size : " + attach.getSize());          
				// 2. 첨부파일 저장
				File saveFile = new File(uploadFolder, attach.getOriginalFilename() );
				try {
					attach.transferTo(saveFile);
				} catch (Exception e) {
					log.error(e.getMessage());
				}			
			}//if
		}
		

	}//upload
	
	
	
	
	
	
	//private static final Logger logger = LoggerFactory.getLogger(CmrUploadController.class);
	@GetMapping("/upload")
	public void upload() {

	}//upload

	//2. 커맨드 객체 ...
	@PostMapping("/upload")
	public void upload(
			Message message
			) {
		log.info("> CmrUploadController.upload()... Post");
		log.info("-".repeat(30));
		log.info("1. output : " + message.getOutput());

		String uploadFolder = "C:\\upload";

		MultipartFile attach = message.getAttach();
		if(! attach.isEmpty()) {
			
			// 1. 첨부된 파일의 정보 출력
			log.info("-".repeat(30));
			log.info("2. originalFilename : " + attach.getOriginalFilename());
			log.info("2. size : " + attach.getSize());          
			// 2. 첨부파일 저장
			File saveFile = new File(uploadFolder, attach.getOriginalFilename() );
			try {
				attach.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}			
		}//if

	}//upload

	//<input type="text" name="output" value="hello world!">
	//<input type="file" name="attach">                     
	/*1.
	@PostMapping("/upload")
	public void upload(
			@RequestParam("output") String output
		  , @RequestParam("attach") MultipartFile attach	
			) {
		log.info("> CmrUploadController.upload()... Post");
		log.info("-".repeat(30));
		log.info("1. output : " + output);

		String uploadFolder = "C:\\upload";

		if(! attach.isEmpty()) {
			// 1. 첨부된 파일의 정보 출력
	         log.info("-".repeat(30));
	         log.info("2. originalFilename : " + attach.getOriginalFilename());
	         log.info("2. size : " + attach.getSize());          
	         // 2. 첨부파일 저장
	         File saveFile = new File(uploadFolder, attach.getOriginalFilename() );
	         try {
	            attach.transferTo(saveFile);
	         } catch (Exception e) {
	            log.error(e.getMessage());
	         }			
		}//if

	}//upload
	 */


}//class
