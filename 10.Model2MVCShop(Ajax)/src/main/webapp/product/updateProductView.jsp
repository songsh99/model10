<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%@ page import="com.model2.mvc.service.domain.Product" %>

<%
	Product vo=(Product)request.getAttribute("product");
%>
 --%>
 
<html>
<head>
<title>상품정보수정</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="../javascript/calendar.js"></script>
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<script type="text/javascript">

function fncUpdateProduct(){
	//Form 유효성 검증
//  	var name = document.detailForm.prodName.value;
// 	var detail = document.detailForm.prodDetail.value;
// 	var manuDate = document.detailForm.manuDate.value;
// 	var price = document.detailForm.price.value;

// 	if(name == null || name.length<1){
// 		alert("상품명은 반드시 입력하여야 합니다.");
// 		return;
// 	}
// 	if(detail == null || detail.length<1){
// 		alert("상품상세정보는 반드시 입력하여야 합니다.");
// 		return;
// 	}
// 	if(manuDate == null || manuDate.length<1){
// 		alert("제조일자는 반드시 입력하셔야 합니다.");
// 		return;
// 	}
// 	if(price == null || price.length<1){
// 		alert("가격은 반드시 입력하셔야 합니다.");
// 		return;
// 	}
	var name = $("input[name='prodName']").val();
 	var detail = $("input[name='prodDetail']").val();
 	var manuDate = $("input[name='manuDate']").val();
 	var price = $("input[name='price']").val();
 	var filename = $("input[name='fileName']").val();
 	
 	if(name == null || name.length<1){
		alert("상품명은 반드시 입력하여야 합니다.");
		return;
	}
	if(detail == null || detail.length<1){
		alert("상품상세정보는 반드시 입력하여야 합니다.");
		return;
	}
	if(manuDate == null || manuDate.length<1){
		alert("제조일자는 반드시 입력하셔야 합니다.");
		return;
	}
	if(price == null || price.length<1){
		alert("가격은 반드시 입력하셔야 합니다.");
		return;
	}
		
	//document.detailForm.action='/product/updateProduct';
	//document.detailForm.submit();
	$("form").attr("method" , "POST").attr("action" , "/product/updateProduct").submit();
}
$(function() {
	//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
	//==> 1 과 3 방법 조합 : $("tagName.className:filter함수") 사용함.	
	 $( "td.ct_btn01:contains('수정')" ).on("click" , function() {
		//Debug..
		//alert(  $( "td.ct_btn01:contains('수정')" ).html() );
		fncUpdateProduct();
	});
	
	 $( "td.ct_btn01:contains('이전')" ).on("click" , function() {
			//Debug..
			//alert(  $( "td.ct_btn01:contains('취소')" ).html() );
			history.go(-1);
	});
});	


</script>


</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post">
<%-- <input type="hidden" name="prodNo" value="<%= vo.getProdNo() %>"/>--%>
<input type="hidden" name="prodNo" value="${product.prodNo}"/>
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품수정</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">
					<%--<input 	type="text" name="prodName" class="ct_input_g" 
										style="width: 100px; height: 19px" maxLength="20" value="<%=vo.getProdName()%>">
					 --%>
					 <input 	type="text" name="prodName" class="ct_input_g" 
										style="width: 100px; height: 19px" maxLength="20" value="${product.prodName}">
					 </td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품상세정보 <img	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		<%--
		<input type="text" name="prodDetail" value="<%=vo.getProdDetail()%>" class="ct_input_g" 
						style="width: 100px; height: 19px" maxLength="10"	minLength="6">
		 --%>
		<input type="text" name="prodDetail" value="${product.prodDetail}" class="ct_input_g" 
						style="width: 100px; height: 19px" maxLength="10"	minLength="6">
		 
		  </td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			제조일자 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		<%-- <input type="text" readonly="readonly" name="manuDate" value="<%=vo.getManuDate()%>" 	
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="10" minLength="6">&nbsp;--%>
			<input type="text" readonly="readonly" name="manuDate" value="${product.manuDate}" 	
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="10" minLength="6">&nbsp;
						<img 	src="../images/ct_icon_date.gif" width="15" height="15" 
									onclick="show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value)" />
		<td>  </td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			가격 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		<%-- <input type="text" name="price" value="<%=vo.getPrice()%>"
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="50"/>&nbsp;원--%>
			<input type="text" name="price" value="${product.price}"
			class="ct_input_g" style="width: 100px; height: 19px" maxLength="50"/>&nbsp;원
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품이미지</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		<%-- 	<input	type="text" name="fileName" class="ct_input_g" 
						style="width: 200px; height: 19px" maxLength="13" value="<%=vo.getFileName()%>"/>
		--%>
				<input	type="text" name="fileName" class="ct_input_g" 
						style="width: 200px; height: 19px" maxLength="13" value="${product.fileName}"/>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
						<!--  <a href="javascript:fncAddProduct();">등록</a>-->
						수정
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					<td width="30"></td>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif"width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<!--<a href="javascript:history.go(-1)">취소</a>-->
						이전
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>

</body>
</html>