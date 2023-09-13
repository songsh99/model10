package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao{
	
	//Field
	@Autowired
	@Qualifier("sqlSessionTemplate") 
	private SqlSession sqlSession; // 데이터베이스와의 연결, 쿼리 실행 등을 처리
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//Constructor
	public ProductDaoImpl() {
		System.out.println("product dao 임플 입니다 확인용 "+this.getClass());
	}
	
	//Method
	@Override
	public void addProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert("ProductMapper.addProduct", product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DAOimple========"+prodNo);
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("ProductMapper.updateProduct", product);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}
	
	

}
