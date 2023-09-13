<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

function fncGetProductList(currentPage) {
    $("#currentPage").val(currentPage);

    // 현재 페이지의 "menu" 파라미터 값을 가져옵니다.
    var menu = "${param.menu}";

    // 조건에 따라서 "action" 값을 설정합니다.
    if (menu === "manage") {
        $("form").attr("method", "POST").attr("action", "/product/listProduct?menu=manage").submit();
    } else if (menu === "search") {
        $("form").attr("method", "POST").attr("action", "/product/listProduct?menu=search").submit();
    }
}


$(function() {
	 $( "td.ct_btn01:contains('검색')" ).on("click" , function() {
		fncGetProductList(1);
	});
	
	$( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
	    var prodNo = $(this).closest("tr").find(".prod-no").val();
	    
	    // 어떤 페이지로 이동할지를 조건에 따라 결정합니다.
	    var menu = "${param.menu}"; // "MANAGE" 또는 "SEARCH"를 담을 변수
	    
	    if (menu === "manage") {
	        // "MANAGE" 페이지로 이동하면서 "prodNo" 값을 넘깁니다.
	    	self.location = "/product/updateProduct?prodNo=" + prodNo;
	    } else if (menu === "search") {
	    	$.ajax({
	    		url : "/product/json/getProduct/"+prodNo ,
				method : "GET" ,
				dataType : "json" ,
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				},
				success : function(JSONData , status) {

					//Debug...
					//alert(status);
					//Debug...
					//alert("JSONData : \n"+JSONData);
					
					var displayValue = "<h3>"
												+"상품번호 : "+JSONData.prodNo+"<br/>"
												+"상품명 : "+JSONData.prodName+"<br/>"
												+"상세정보 : "+JSONData.prodDetail+"<br/>"
												+"가격 : "+JSONData.price+" 원<br/>"
												+"상품등록일 : "+JSONData.manuDate+"<br/>"
												+"파일 : "+JSONData.fileName+"<br/>"
												+"</h3>";
					//Debug...									
					//alert(displayValue);
					
					$("h3").remove();
					$( "#"+prodNo+"" ).html(displayValue);
				}
	    		
	    	});
	        // "SEARCH" 페이지로 이동하면서 "prodNo" 값을 넘깁니다.
	    	//self.location = "/product/getProduct?prodNo=" + prodNo;
	    }
	});
		

	$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
	$("h7").css("color" , "red");
					
	$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");;
});	
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					 <c:choose>
				        <c:when test="${'manage' eq param.menu}">
				          <td width="93%" class="ct_ttl01">판매 상품관리</td>
				        </c:when>
				        <c:when test="${'search' eq param.menu}">
				            <td width="93%" class="ct_ttl01">상품 목록조회</td>
				        </c:when>
				    </c:choose>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
		
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
					
			</select>
			
				<input 	type="text" name="searchKeyword"  
				value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
			 	class="ct_input_g" style="width:200px; height:19px" >	
		</td>

		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
			전체 ${resultPage.totalCount } 건수, 현제${resultPage.currentPage } 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		
		<td class="ct_list_b" width="150">상품명<br>
			<h7>Click[상세보기]</h7>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	 <c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="center">
			     <c:choose>
	                <c:when test="${'manage' eq param.menu}">
	                    ${product.prodName}
	                    <input type="hidden" class="prod-no" value="${product.prodNo}" />
	                </c:when>
	                <c:when test="${'search' eq param.menu}">
	                    ${product.prodName}
	                    <input type="hidden" class="prod-no" value="${product.prodNo}" />
	                </c:when>
            </c:choose>
			</td>    
			<td></td>
			<td align="center">${product.price} 원</td>
			<td></td>
			<td align="center">${product.regDate}</td>		
		</tr>
		<tr>
		<!--  <td colspan="11" bgcolor="D6D7D6" height="1"></td>-->
		<td id="${product.prodNo}" colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>

</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value="0"/>
			<jsp:include page="../common/pageNavigator.jsp">
			<jsp:param name="page" value="Product" />
			</jsp:include>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>