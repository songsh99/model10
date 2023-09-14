package com.model2.mvc.web.user;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> ȸ������ RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {

	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method ���� ����
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//add
	 @RequestMapping( value="json/addUser", method=RequestMethod.POST )
	 public User addUser(@RequestBody User user) throws Exception{
		 System.out.println("/user/addUser : POST");
		 userService.addUser(user);
	  
		 return user;
	 }
	 
	//get  
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		//@pa~~~ ������̼��� ��������� �� URL ��ο��� ������ �����Ҽ� �ִ�.
		System.out.println("/user/json/getUser : GET");
		System.out.println("������"+userService.getUser(userId));
		System.out.println("���� ������?");
		//Business Logic
		return userService.getUser(userId);
	}
	
	 //update
	  @RequestMapping( value="json/updateUser", method=RequestMethod.POST ) public User
	  updateUser( @RequestBody User user,HttpSession session) throws Exception{
	  
	  System.out.println("====>user���� :: "+user);
	  
	  System.out.println("Ȯ��/user/json/updateUser : POST"); //Business Logic
	  userService.updateUser(user);
	  
	  //User dbUser=userService.getUser(user.getUserId());
	  //System.out.println("dbuser ====>>>>"+dbUser);
	  
	  //session.setAttribute("user", dbUser);
	  return user; 
	  }
	 
	//login  
	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		System.out.println("dbUser=====>"+dbUser);
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
	
	//list
	/*
	1. @RequestMapping ������̼��� ����Ͽ� "/json/listUser" ��ο� ���� GET �� POST ��û�� ó���ϴ� �޼��带 �����մϴ�.
	2. @RequestBody ������̼��� ����Ͽ� ��û �ٵ𿡼� JSON �����͸� Search ��ü�� ������ȭ�մϴ�. 
	    Ŭ���̾�Ʈ�� JSON �����͸� POST�� ���� ��, �� Search ��ü�� �����Ͱ� ���ε˴ϴ�.
	 */
	@RequestMapping(value = "json/listUser")
	public Map<String, Object> listUser(@RequestBody Search search) throws Exception {
		
		System.out.println("/user/listUser : GET / POST");
		
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
		Map<String , Object> map=userService.getUserList(search);
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

	
	//checkDuplication
	@RequestMapping( value="json/checkDuplication", method=RequestMethod.POST )
	public Map checkDuplication( @RequestBody String userId) throws Exception{
		
		System.out.println("/user/checkDuplication : POST");
		
		boolean result=userService.checkDuplication(userId);
		
		Map map = new HashMap<>();
		
		map.put("result", result);
		
		return map;
		
	}
}











































