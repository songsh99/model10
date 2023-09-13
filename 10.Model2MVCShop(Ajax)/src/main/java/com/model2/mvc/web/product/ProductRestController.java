package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> 상품관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//add
	 @RequestMapping( value="json/addProduct", method=RequestMethod.POST )
	 public Product addProduct(@RequestBody Product product) throws Exception{
		
		 System.out.println("/product/addProduct : POST");
		 productService.addProduct(product);
	  
		 return product;
	 }
	 
	//get  
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET )
	public Product getProduct( @PathVariable int prodNo ) throws Exception{
		//@pa~~~ 어노테이션을 사용함으로 써 URL 경로에서 변수를 추출할수 있다.
		System.out.println("/product/json/getProduct : GET");
		System.out.println("찍어나보자"+productService.getProduct(prodNo));
		System.out.println("찍어보자 찍히나?");
		//Business Logic
		return productService.getProduct(prodNo);
	}
	
	
	@RequestMapping(value="json/updateProduct",method=RequestMethod.POST)		
	public Product updateProduct( @RequestBody Product product , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		System.out.println(product+"prod 확인문 ::::::::");
		//Business Logic
		productService.updateProduct(product);
		 
		System.out.println("updateView post 방식으로 들어왔는지 확인 ==========");
		System.out.println(product+"prod 확인문 ::::::::");
		session.setAttribute("product", product);
		
		return product;
	}
	
	@RequestMapping(value = "json/listProduct")
	public Map<String, Object> listProduct(@RequestBody Search search) throws Exception {
		
		System.out.println("/product/listProduct : GET / POST");
		
		//간단한 로그 메시지를 출력합니다. 요청이 들어왔음을 로그로 남기는 용도로 사용됩니다.
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println(search);
		/*
		 Search 객체에서 현재 페이지(currentPage)가 0인 경우 1로 설정하고, 
		 페이지 크기(pageSize)를 설정합니다. 
		 이렇게 하는 것은 페이지 설정에 대한 기본값을 제공하는 것입니다.	
		 */
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		System.out.println("map확인좀 하자 뭐가 들어오냐 ㅅㅂ==>>"+map);
		/*
		 비즈니스 로직을 수행하기 위해 userService의 getUserList 메서드를 호출합니다. 
		 search 객체를 전달하여 검색 관련 정보를 서비스 레이어로 전달합니다.
		 */
		
		//Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		//System.out.println(resultPage);
		
		/*
		 결과 페이지(resultPage)를 생성합니다. 이 페이지 객체는 페이지 번호, 총 결과 수, 페이지 단위, 페이지 크기 등을 포함합니다.
		 */

	    return map;
	    //마지막으로 map 객체를 반환합니다. 이렇게 반환한 데이터는 JSON 형태로 클라이언트에게 응답됩니다.
	}
}