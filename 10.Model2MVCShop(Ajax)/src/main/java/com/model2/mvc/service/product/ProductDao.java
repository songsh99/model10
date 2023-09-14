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
	// �Խ��� Page ó���� ���� ��üRow(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
}



/*
PROD_NO: ��ǰ ��ȣ�� ��Ÿ���� ���Դϴ�.
PROD_NAME: ��ǰ �̸��� ��Ÿ���� ���Դϴ�.
PROD_DETAIL: ��ǰ�� ���� �� ������ ��Ÿ���� ���Դϴ�.
MANUFACTURE_DAY: ��ǰ�� �������ڸ� ��Ÿ���� ���Դϴ�.
PRICE: ��ǰ ������ ��Ÿ���� ���Դϴ�.
IMAGE_FILE: ��ǰ �̹��� ���� �̸��� ��Ÿ���� ���Դϴ�.
REG_DATE: ���ڵ尡 ��ϵ� ��¥�� ��Ÿ���� ���Դϴ�-
*/
