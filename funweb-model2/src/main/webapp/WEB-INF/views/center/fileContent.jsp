<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%-- head 컨텐트 영역 --%>
<jsp:include page="/WEB-INF/views/include/headContent.jsp" />

<link href="/css/subpage.css" rel="stylesheet" type="text/css"
	media="all">
<link href="/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap-theme.css.map" rel="stylesheet"
	type="text/css">
<link href="/css/custom2.css" rel="stylesheet" type="text/css">
<style type="text/css">

</style>
</head>
<body>
	<div id="wrap">
		<%-- header 영역 --%>
		<jsp:include page="/WEB-INF/views/include/topHeader.jsp" />

		<div class="clear"></div>
		<div id="sub_img_center"></div>

		<div class="clear"></div>
		<%-- nav 영역 --%>
		<jsp:include page="/WEB-INF/views/include/submenuBoard.jsp" />

		<article>

			<h1>자료실 게시판 상세보기</h1>

			<table id="notice">
				<tr>
					<th scope="col" class="tno">글번호</th>
					<td class="left" width="500">${ noticeVo.num }</td>
				</tr>
				<tr>
					<th scope="col" class="tread">조회수</th>
					<td class="left">${ noticeVo.readcount }</td>
				</tr>
				<tr>
					<th scope="col" class="twrite">작성자</th>
					<td class="left">${ noticeVo.id }</td>
				</tr>
				<tr>
					<th scope="col" class="tdate">작성일자</th>
					<td class="left"><fmt:formatDate value="${ noticeVo.regDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th scope="col" class="ttitle">글제목</th>
					<td class="left">${ noticeVo.subject }</td>
				</tr>
				<tr>
					<th scope="col" class="ttitle">글내용</th>
					<td class="left">${ noticeVo.content }</td>
				</tr>
				<tr>
					<th scope="col" class="ttitle">첨부파일</th>
					<td class="left"><c:if test="${ not empty attachList }">

							<c:forEach var="attach" items="${ attachList }">

								<c:choose>
									<c:when test="${ attach.image eq 'I' }">
										<p>
											<a href="/upload/${ attach.uploadpath }/${ attach.filename }">
												<img
												src="/upload/${ attach.uploadpath }/${ attach.filename }"
												width="100" height="100">
											</a>
										</p>
									</c:when>
									<c:otherwise>
										<p>
											<a href="/upload/${ attach.uploadpath }/${ attach.filename }">
												${ attach.filename } </a>
										</p>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</c:if></td>
				</tr>
			</table>

			<div id="table_search">

				<c:if test="${ not empty id }">
					<%-- 로그인 했을때 --%>
					<c:if test="${ id eq noticeVo.id }">
						<%-- 로그인 아이디와 글작성자 아이디가 같을때 --%>
						<input type="button" value="글수정" class="btn">
						<input type="button" value="글삭제" class="btn" onclick="remove()">
					</c:if>
					<input type="button" value="답글쓰기" class="btn">
				</c:if>

				<input type="button" value="목록보기" class="btn"
					onclick="location.href = 'fileNotice.do?pageNum=${ pageNum }'">
			</div>

			<div class="clear"></div>
			<div id="page_control"></div>

			<div id="app" class="panel panel-default">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-comment"></span> 댓글 <span
						class="glyphicon glyphicon-refresh"></span>
				</div>
				<div class="panel-body">
					<!-- 댓글 리스트 -->
					<ul class="media-list">
						<li class="media" v-for="(comment, index) in commentList" v-bind:key="comment.cno" v-bind:style="{ marginLeft: comment.reLev * 50 + 'px' }">
							<span class="pull-left" v-if="comment.reLev > 0">└</span>
							<a class="pull-left" href="#">
								<img alt="Generic placeholder image" src="/images/center/pic.jpg">
							</a>
							<div class="media-body">
								<div class="media-heading">
									<strong class="text-primary">{{ comment.id }}</strong>
									<small class="pull-right text-muted">{{ getDate(comment.updateDate) }}</small>
								</div>
								<p>{{ comment.content }}</p>
								<span v-on:click="toggleReplyWriteArea(index)">{{ comment.writeOk ? '답글접기' : '답글쓰기' }}</span>
							</div>
							
							<div v-if="comment.writeOk">
								<textarea  rows="5" class="form-control" placeholder="댓글을 입력하세요"></textarea>
								<br>
								<button type="button" class="btn btn-primary">등록</button>
								<small class="text-muted" style="margin-left: 20px;">{{ remain }} / 600</small>
							</div>
							
						</li>
					</ul>
				</div>
				<div class="panel-footer">
					<textarea  rows="5" v-model="inputComment" class="form-control" placeholder="댓글을 입력하세요"></textarea>
					<br>
					<button type="button" v-on:click="addComment" class="btn btn-primary">등록</button>
					<small class="text-muted" style="margin-left: 20px;">{{ remain }} / 600</small>
				</div>
			</div>

		</article>

		<div class="clear"></div>
		<%-- footer 영역 --%>
		<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />
	</div>

	<script src="/script/jquery-3.5.1.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/ko.js"></script>
	<script>
		// 게시글 삭제 함수
		function remove() {
			let isDelete = confirm('${ noticeVo.num }번 글을 정말 삭제하시겠습니까?');
			if (isDelete) {
				location.href = 'fileDelete.jsp?num=${ noticeVo.num }&pageNum=${ pageNum }';
			}
		}
	</script>
	<script>
		new Vue({
			el: '#app',
			data: {
				commentList: [],
				inputComment: ''
			},
			computed: {
				// 댓글입력 남은 글자수
				remain: function () {
					return 600 - this.inputComment.length;
				}
			},
			watch:{
				inputComment: function () {
					if (this.inputComment.length > 600) {
						this.inputComment = this.inputComment.substring(0, 600);
					}
				}
			},
			methods: {
				toggleReplyWriteArea: function (index) {
					
					for (let i=0; i<this.commentList.length; i++) {
						let comment = this.commentList[i];

						// 클릭한 위치는 토글처리
						if (i == index) {
							if (comment.writeOk) {
								comment.writeOk = false;
							} else {
								comment.writeOk = true;
							}
						} else { // 클릭한 위치가 아니면 모두 댓글쓰기영역 접기
							comment.writeOk = false;
						}
					} // for

// 					for (let i=0; i<this.commentList.length; i++) {
// 						if (i == index) {
// 							this.commentList[i].writeOk = true;
// 						} else {
// 							this.commentList[i].writeOk = false;
// 						}
// 					}
				},
				
				getDate: function (str) {
					let result = moment(str).format('YY-MM-DD a hh:mm:ss');
					return result;
				},
				
				// 댓글목록 가져오기
				getList: function () {
					let nno = ${ noticeVo.num };  // jsp의 el언어로 게시판 글번호를 출력해서 저장
					// this -> 가장 가까운 function이 누구 것이냐 라는 기준으로
					// 그래서 function을 빼고 () => 이렇게 함수를 사용하면 this를 사용할 수 있다.
					let vm = this;  // ViewModel의 약칭. Vue객체 자기자신을 vm 변수로 저장
					
					$.ajax({
						url: 'http://localhost:8090/comment',
						data: 'category=list&nno=' + nno,
						method: 'GET',
						success: function (response) {
							console.log(typeof response);
							console.log(response);

							if (response.isSuccess) {

								for (let comment of response.commentList) {
									comment.writeOk = false;  // 오브젝트마다 writeOk 필드 추가
								}

								vm.commentList = response.commentList;
								
							}
						},
						error: function () {
							alert('댓글 리스트 가져오기 오류 발생...')
						}
					});
				},

				addComment: function () {
					
				}
				
			},
			mounted: function () {
				this.getList();
			}
		});
	</script>
</body>
</html>



