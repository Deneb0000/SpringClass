package org.doit.ik.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
   
   private int pageNum; // 현재 페이지 번호
   private int amount; // 한 페이지에 출력할 게시글 수
   
   private String type; // 검색조건 T C W  TC TW TWC
   private String keyword; // 검색어
   
   public Criteria() {
      this(1,10);
   }
   
   public Criteria(int pageNum, int amount) {
      super();
      this.pageNum = pageNum;
      this.amount = amount;
   }
   
   // ?pageNum=2&amount=10&type=T&keyword=홍길동&...
   // org.springframework.web.util
   // UriComponentsBuilder
   public String getListLink() {
      UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
    		  .queryParam("pageNum", this.pageNum)
    		  .queryParam("amount", this.amount)
    		  .queryParam("type", this.type)
    		  .queryParam("keyword", this.keyword);
      return builder.toUriString();
   }
   
   
   //검색 조건이 다중항목일때"TWC" = ["T", "W", "C"] 문자열 배열.
   public String[] getTypeArr() {
	   return this.type == null? new String[]{} : this.type.split("");
   }
   
} // class
