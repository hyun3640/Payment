<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<BODY>

	<H1>1.결제정보</H1>
	<DIV class="detail_table">
		<c:choose>
			<c:when test="${resultMap.type eq 'NAVER_POINT'}">
				<table>
					<TBODY>
						<TR class="dot_line">
							<TD class="fixed_join">결제방법</TD>
							<TD>${resultMap.type}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">승인일시</TD>
							<TD>${resultMap.authDateTime}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">결제금액</TD>
							<TD>${resultMap.amount}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">현금영수증 발급대상금액</TD>
							<TD>${resultMap.cashReceipt}</TD>
						</TR>
					</TBODY>
				</table>
			</c:when>
			<c:otherwise>
				<table>
					<TBODY>
						<TR class="dot_line">
							<TD class="fixed_join">결제방법</TD>
							<TD>${resultMap.type}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">승인일시</TD>
							<TD>${resultMap.authDateTime}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">결제금액</TD>
							<TD>${resultMap.amount}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">승인번호</TD>
							<TD>${resultMap.authNumber}</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">카드명</TD>
							<TD>${resultMap.cardName }</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">카드번호</TD>
							<TD>${resultMap.cardNo }</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">할부개월</TD>
							<TD>${resultMap.quota }</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">카드코드</TD>
							<TD>${resultMap.cardCode }</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">카드구분정보 1</TD>
							<TD>${resultMap.binType01 }</TD>
						</TR>
						<TR class="dot_line">
							<TD class="fixed_join">카드구분정보 2</TD>
							<TD>${resultMap.binType02 }</TD>
						</TR>
					</TBODY>
				</table>
			</c:otherwise>
		</c:choose>
	</DIV>
	</form>
	<DIV class="clear"></DIV>
	<br>
	<br>
	<br>
	<center>
		<br> <br> <a href="${contextPath}/main/main.do"> <IMG
			width="75" alt=""
			src="${contextPath}/resources/image/btn_shoping_continue.jpg">
		</a>
		<DIV class="clear"></DIV>

		<%-- <h1>카카오 결제 성공</h1>

//머니일때
<h4>승인일시 : ${resultMap.authDateTime }</h4>
<h4>결제금액 : ${resultMap.amount }</h4>
<h4>현금영수증 발급대상금액: ${resultMap.cashReceipt }</h4>

//카드일때 

<h4>승인일시 : ${resultMap.authDateTime }</h4>
<h4>결제금액 : ${resultMap.amount }</h4>
<h4>승인번호 : ${resultMap.authNumber }</h4>
<h4>카드 명 : ${resultMap.cardName }</h4>
<h4>카드번호 : ${resultMap.cardNo }</h4>
<h4>할부개월 : ${resultMap.quota }</h4> --%>