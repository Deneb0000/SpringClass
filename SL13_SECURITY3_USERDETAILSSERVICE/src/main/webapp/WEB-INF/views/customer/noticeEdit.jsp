<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="content">
	<h2>공지사항</h2>
	<h3 class="hidden">방문페이지위치</h3>
	<ul id="breadscrumb" class="block_hlist">
		<li>HOME</li>
		<li>고객센터</li>
		<li>공지사항수정</li>
	</ul>

	<!-- enctype="multipart/form-data" 꼭 추가!!!!!! 폼태그 제출시 필수!! -->
	<form action="/customer/noticeEdit.htm?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
		
		<div id="notice-article-detail" class="article-detail margin-large">
			<dl class="article-detail-row">
				<dt class="article-detail-title">제목</dt>
				<dd class="article-detail-data">
					&nbsp;<input name="title" value="${ notice.title }" />
				</dd>
			</dl>
			
			<dl class="article-detail-row half-row">
				<dt class="article-detail-title">작성자</dt>
				<dd class="article-detail-data half-data">${ notice.writer }</dd>
			</dl>
			<dl class="article-detail-row half-row">
				<dt class="article-detail-title">조회수</dt>
				<dd class="article-detail-data half-data">${ notice.hit }</dd>
			</dl>
			<dl class="article-detail-row">
				<dt class="article-detail-title">첨부파일</dt>
				<dd class="article-detail-data">
					&nbsp;<input type="file" id="txtFile" name="file" />
					<!-- 해당 공지사항 글의 첨부파일명을 출력 -->
					<input type="text" name="o_filesrc" value="${ notice.filesrc }" />
				</dd>
			</dl>

			<div class="article-content">
				<textarea id="txtContent" class="txtContent" name="content">${ notice.content}</textarea>
			</div>
		</div>
		<p class="article-comment margin-small">
			<!-- <a class="btn-save button" href="noticeEditProc.jsp">수정</a> -->
			<!-- 현재페이지번호, 검색조건, 검색어 등을 주고받아야함. -->
			<input type="submit" value="수정" class="btn-save button" />
<a class="btn-cancel button" href="noticeDetail.htm?seq="noticeDetail.htm""></a> <!-- 취소 -->
<%-- <a class="btn-cancel button" href="noticeDetail.htm?seq=${notice.seq}"></a> --%> <!-- 취소 -->
		</p>

		<input type="hidden" name="${ _csrf.parameterName }"
			value="${ _csrf.token }" />
			<input type="hidden" name="seq" value="${notice.seq}">
	</form>

</div>

