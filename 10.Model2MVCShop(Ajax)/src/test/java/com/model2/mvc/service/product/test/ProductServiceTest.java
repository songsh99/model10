package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data �� �پ��ϰ� Wiring ����...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	
	//Autowired => �Ʒ� ������ private bean�� ã�� field�� ����
	//Qualifier("") => ������ Ÿ���� bean�� �����Ҷ� � bean�� �������� ��������� ���� 
	//���� �� �߿��� � ���� ���������� �� ��Ȯ�ϰ� ����
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	private int num = 10058;

	//@Test
	public void testAddPoduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProductName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("testDate");
		product.setPrice(111);
		product.setFileName("testfileName");
		
		System.out.println(product.toString());
		
		productService.addProduct(product);
		
		System.out.println(product);
		

		System.out.println("���� ��¹� : "+product.getProdNo());
		//==> console Ȯ��
		//System.out.println(user);
		
		//==> API Ȯ��
		
		//ex) Assert.assertEquals("a",a); => a��� ���ڿ� a��� data type�� ���� ������ Ȯ�� 
		//��밪�� �������� ���ϰ�, ���� �� ������ ���� ������ ���� �߻�
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();		//==> �ʿ��ϴٸ�...
		
		System.out.println("!!!!!==========="+product.getProdNo());
		
		product = productService.getProduct(num);
		
		//==> console Ȯ��
		System.out.println("getProduct start"+product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
	
		System.out.println("����Ȯ��=================="+product.getProdNo());
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		
		Product product = productService.getProduct(num);
		
		System.out.println("���������µ� update=============\n"+product);
		//Assert.assertNotNull(product);//������ ��ü�� null ���� Ȯ�� �ϱ� 
		
		
		//������ ��ü�� ������ ������
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
		
		//������ ��ü�� ������ ���氪�� ������ 
		product.setProdName("changeName");
		product.setProdDetail("changeDetail");
		product.setManuDate("change");
		product.setPrice(999);
		product.setFileName("changefileName");
		
		System.out.println(product+"\n �ؿ� �������� �غ� (��)");
		
		//update �ȿ� ������ �������� product ���� ���� 
		productService.updateProduct(product);
		
		
		System.out.println("������???===================\n"+product);
		
		//
		product = productService.getProduct(num);
		
		System.out.println("update�� �ڷ� /n"+product);
		
		Assert.assertEquals("changeName", product.getProdName());
		Assert.assertEquals("changeDetail", product.getProdDetail());
		Assert.assertEquals("change", product.getManuDate());
		Assert.assertEquals(999, product.getPrice());
		Assert.assertEquals("changefileName", product.getFileName());
		
	 }
	 
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	//@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetUserListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("������");
	 	
	 	System.out.println("search/n"+search);
	 	
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	System.out.println("map/n"+map);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	//@Test
	 public void testGetUserListBymanuday() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("testDate");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}