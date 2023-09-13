package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//page 관련 method 
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//Method	
	@RequestMapping(value="addProduct",method=RequestMethod.GET)
	public String addProduct() throws Exception {
		//작동잘됨 
		System.out.println("addprodut 작동유무 확인 -----------------------!");
		System.out.println("/addProduct : GET");
		System.out.println("수정page 입니까 ???");

		
		return "redirect:/product/addProductView.jsp";
	}

	//@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct",method = RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product, Model model ) throws Exception {
		
		
		System.out.println("============no가져오자 제"+product);
		System.out.println("/product/addProduct : POST");
		//Business Logic
		System.out.println("수정page 입니다 ???");
		String temDir=""
		productService.addProduct(product);
		model.addAttribute("product", product);
		return "forward:/product/getProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct",method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo")  int prodNo ,HttpServletRequest request, Model model ) throws Exception {
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		System.out.println("들어오긴함?");
		
		
	    
		return "forward:/product/getProduct.jsp";
	}

	//@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}

		//@RequestMapping("/updateProductView.do")
		@RequestMapping(value="updateProduct",method=RequestMethod.GET)
		public String updateProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProduct : GET");
		//Business ]]
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		System.out.println("updateView get 방식으로 들어왔는지 확인 ===================");
		System.out.println(product+"<===================");
		
		
		return "forward:/product/updateProductView.jsp";
	}
	
		//@RequestMapping("/updateProduct.do")
		@RequestMapping(value="updateProduct",method=RequestMethod.POST)		
		public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

			System.out.println("/product/updateProduct : POST");
			//Business Logic
			productService.updateProduct(product);
			 
			System.out.println("updateView post 방식으로 들어왔는지 확인 ==========");
			System.out.println(product+"prod 확인문 ::::::::");
			session.setAttribute("product", product);
			
			return "forward:/product/getProduct.jsp";
//			return "forward:/product/getProduct";
		}

}