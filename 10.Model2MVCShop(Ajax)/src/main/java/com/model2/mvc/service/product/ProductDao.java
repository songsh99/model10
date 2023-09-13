package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductDao {
	
//============================================================================================================			
	public void addProduct(Product product)throws Exception;
//============================================================================================================
	public Product getProduct(int prodNo)throws Exception;
//============================================================================================================
	public List<Product> getProductList(Search search) throws Exception;
//============================================================================================================	
	public void updateProduct(Product product)throws Exception;
//============================================================================================================	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
}



/*
PROD_NO: 제품 번호를 나타내는 열입니다.
PROD_NAME: 제품 이름을 나타내는 열입니다.
PROD_DETAIL: 제품에 대한 상세 정보를 나타내는 열입니다.
MANUFACTURE_DAY: 제품의 제조일자를 나타내는 열입니다.
PRICE: 제품 가격을 나타내는 열입니다.
IMAGE_FILE: 제품 이미지 파일 이름을 나타내는 열입니다.
REG_DATE: 레코드가 등록된 날짜를 나타내는 열입니다-
*/
