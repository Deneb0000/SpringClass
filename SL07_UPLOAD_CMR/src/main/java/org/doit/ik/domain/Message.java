package org.doit.ik.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data

public class Message { //Notice.VO

	private String output;
	private CommonsMultipartFile attach;
}
