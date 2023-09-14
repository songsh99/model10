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



//==> ��ǰ���� Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
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
		//@pa~~~ ������̼��� ��������� �� URL ��ο��� ������ �����Ҽ� �ִ�.
		System.out.println("/product/json/getProduct : GET");
		System.out.println("������"+productService.getProduct(prodNo));
		System.out.println("���� ������?");
		//Business Logic
		return productService.getProduct(prodNo);
	}
	
	
	@RequestMapping(value="json/updateProduct",method=RequestMethod.POST)		
	public Product updateProduct( @RequestBody Product product , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		System.out.println(product+"prod Ȯ�ι� ::::::::");
		//Business Logic
		productService.updateProduct(product);
		 
		System.out.println("updateView post ������� ���Դ��� Ȯ�� ==========");
		System.out.println(product+"prod Ȯ�ι� ::::::::");
		session.setAttribute("product", product);
		
		return product;
	}
	
	@RequestMapping(value = "json/listProduct")
	public Map<String, Object> listProduct(@RequestBody Search search) throws Exception {
		
		System.out.println("/product/listProduct : GET / POST");
		
		//������ �α� �޽����� ����մϴ�. ��û�� �������� �α׷� ����� �뵵�� ���˴ϴ�.
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println(search);
		/*
		 Search ��ü���� ���� ������(currentPage)�� 0�� ��� 1�� �����ϰ�, 
		 ������ ũ��(pageSize)�� �����մϴ�. 
		 �̷��� �ϴ� ���� ������ ������ ���� �⺻���� �����ϴ� ���Դϴ�.	
		 */
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		System.out.println("mapȮ���� ���� ���� ������ ����==>>"+map);
		/*
		 ����Ͻ� ������ �����ϱ� ���� userService�� getUserList �޼��带 ȣ���մϴ�. 
		 search ��ü�� �����Ͽ� �˻� ���� ������ ���� ���̾�� �����մϴ�.
		 */
		
		//Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		//System.out.println(resultPage);
		
		/*
		 ��� ������(resultPage)�� �����մϴ�. �� ������ ��ü�� ������ ��ȣ, �� ��� ��, ������ ����, ������ ũ�� ���� �����մϴ�.
		 */

	    return map;
	    //���������� map ��ü�� ��ȯ�մϴ�. �̷��� ��ȯ�� �����ʹ� JSON ���·� Ŭ���̾�Ʈ���� ����˴ϴ�.
	}
}