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


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {

	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
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
		//@pa~~~ 어노테이션을 사용함으로 써 URL 경로에서 변수를 추출할수 있다.
		System.out.println("/user/json/getUser : GET");
		System.out.println("찍어나보자"+userService.getUser(userId));
		System.out.println("찍어보자 찍히나?");
		//Business Logic
		return userService.getUser(userId);
	}
	
	 //update
	  @RequestMapping( value="json/updateUser", method=RequestMethod.POST ) public User
	  updateUser( @RequestBody User user,HttpSession session) throws Exception{
	  
	  System.out.println("====>user정보 :: "+user);
	  
	  System.out.println("확인/user/json/updateUser : POST"); //Business Logic
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
	1. @RequestMapping 어노테이션을 사용하여 "/json/listUser" 경로에 대한 GET 및 POST 요청을 처리하는 메서드를 정의합니다.
	2. @RequestBody 어노테이션을 사용하여 요청 바디에서 JSON 데이터를 Search 객체로 역직렬화합니다. 
	    클라이언트가 JSON 데이터를 POST로 보낼 때, 이 Search 객체에 데이터가 매핑됩니다.
	 */
	@RequestMapping(value = "json/listUser")
	public Map<String, Object> listUser(@RequestBody Search search) throws Exception {
		
		System.out.println("/user/listUser : GET / POST");
		
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
		Map<String , Object> map=userService.getUserList(search);
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











































