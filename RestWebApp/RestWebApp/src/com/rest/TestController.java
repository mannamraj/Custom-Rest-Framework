package com.rest;


import com.raja.rest.annotations.Path;
import com.raja.rest.annotations.RestInputBody;
import com.raja.rest.annotations.Request;
import com.raja.rest.annotations.RestService;
import com.raja.rest.annotations.RestServiceMapper;

@RestService(endPoint = "myController")
public class TestController {
	
	@RestServiceMapper(serviceName="test/{id}" , contentType = "application/json")
	public Student test(@Request(name="name") String name,@Path(name = "id") String id){
		
		Student student = new Student();
		
		student.setId("2323");
		
		student.setName("test");
		
		System.out.println(name+" #####  "+id);
		
		return student;
		
	}

	@RestServiceMapper(serviceName="test1")
	public void test1(@RestInputBody Student student ){
		
		System.out.println(student.getId() + "#####"+student.getName());
		
	}
	
	@RestServiceMapper(serviceName="test2")
	public void test2(@Request(name="name") String name,@Request(name="id") String id){
		
		System.out.println(name + "#####"+id);
		
	}
	
	@RestServiceMapper(serviceName="test3/{name}/test/{id}")
	public void test3(@Path(name="name") String name,@Path(name="id") String id){
		
		System.out.println(name + "#####"+id);
		
	}
	
	
}
