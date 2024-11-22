package org.doit.ik.domain;

import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVO {
	// 디비, jsp파일에도 컬럼명 동일하게 주는게 좋음
	private String seq;
	private String title;
	private String writer;
	private Date regdate;
	private String filesrc;	 // 파일명을 DB에 insert할때 사용
	private int hit;
	private String content;
	
	// 변수명은, input의 name속성으로 주기
	// <input type="file" id="txtFile" [ name="file" ] />
	private CommonsMultipartFile file; // 서버로 전송된 파일 받기 위해.
	
}
