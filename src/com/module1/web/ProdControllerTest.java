package com.module1.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ProdControllerTest implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest res, HttpServletResponse rsp) throws Exception {
		
		//res.getParameter("");
		System.out.println("oilkkkkkkkkkkkk");
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("message", "啊啊啊啊");
		return mv;
	}

}
