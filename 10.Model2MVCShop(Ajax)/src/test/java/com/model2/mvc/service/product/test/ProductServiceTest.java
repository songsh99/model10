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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data 를 다양하게 Wiring 하자...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	
	//Autowired => 아래 선언한 private bean을 찾아 field에 주입
	//Qualifier("") => 동일한 타입의 bean이 존재할때 어떤 bean을 주입할지 명시적으로 지정 
	//여러 빈 중에서 어떤 빈을 주입할지를 더 명확하게 지정
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
		

		System.out.println("숫자 출력문 : "+product.getProdNo());
		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		
		//ex) Assert.assertEquals("a",a); => a라는 문자와 a라는 data type속 값이 같은지 확인 
		//기대값과 실제값을 비교하고, 만약 이 값들이 같지 않으면 오류 발생
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();		//==> 필요하다면...
		
		System.out.println("!!!!!==========="+product.getProdNo());
		
		product = productService.getProduct(num);
		
		//==> console 확인
		System.out.println("getProduct start"+product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
	
		System.out.println("숫자확인=================="+product.getProdNo());
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		
		Product product = productService.getProduct(num);
		
		System.out.println("뭐가나오는데 update=============\n"+product);
		//Assert.assertNotNull(product);//선언한 객체의 null 유무 확인 하기 
		
		
		//변경할 객체의 정보를 가져옴
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testDate", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("testfileName", product.getFileName());
		
		//가져온 객체의 정보에 변경값을 정해줌 
		product.setProdName("changeName");
		product.setProdDetail("changeDetail");
		product.setManuDate("change");
		product.setPrice(999);
		product.setFileName("changefileName");
		
		System.out.println(product+"\n 밑에 대입해줄 준비 (완)");
		
		//update 안에 위에서 선언해준 product 값을 지정 
		productService.updateProduct(product);
		
		
		System.out.println("못들어옴???===================\n"+product);
		
		//
		product = productService.getProduct(num);
		
		System.out.println("update후 자료 /n"+product);
		
		Assert.assertEquals("changeName", product.getProdName());
		Assert.assertEquals("changeDetail", product.getProdDetail());
		Assert.assertEquals("change", product.getManuDate());
		Assert.assertEquals(999, product.getPrice());
		Assert.assertEquals("changefileName", product.getFileName());
		
	 }
	 
	 //==>  주석을 풀고 실행하면....
	//@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
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
	 	
	 	//==> console 확인
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
	 	search.setSearchKeyword("자전거");
	 	
	 	System.out.println("search/n"+search);
	 	
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	System.out.println("map/n"+map);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
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
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}