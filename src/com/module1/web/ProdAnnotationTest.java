package com.module1.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.module1.service.ProdService;

//@Controller
//@RequestMapping("/protest")  //给这个类映射一个url
//@SessionAttributes({"user", "role"})//此注解可以把modelMap里的值生效范围改为session
public class ProdAnnotationTest {
	
	@Autowired
	private ProdService prodService;

	@RequestMapping("/login.do")//给方法指定一个请求路径
	public String login(@RequestParam("u")String name){//指定传过来的参数u 对应name
		System.out.println("kkkkkkkk:"+name);
		return "login";
	}
	
	/*@RequestMapping("/list.do")
	public String list(@RequestParam("u")String name, ModelMap map){
		
		map.put("user", name);//默认放置到modelMap里的值生效范围是request
		System.out.println("++++++++++");
		return "list";
	}*/
	
	/*@ModelAttribute  //在调用其他方法前，会调用添加了@ModelAttribute注解的方法
	public void doBefore(ModelMap map){
		map.put("role", "管理员");
		System.out.println("-----------");
	}*/
	
	@RequestMapping("/list.do")
	public String list(){
		
		System.out.println("++++++++++");
		return "redirect:list";  //重定向
	}
	
	@RequestMapping("/find.do")
	public void find(PrintWriter out){
		String json = "[{id:1,name:'xx'},{id:2,name:'yy'}]";
		out.write(json);
		out.flush();
	}
	
	/*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("/springUpload.do")
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
         long  startTime=System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        
        String ctxPath = "E:/springUpload/";
        File f = new File(ctxPath);
        
        if (!f.exists())
        {
            //递归创建目录
            f.mkdirs();
        }        
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    String path = ctxPath + nowTime + file.getOriginalFilename();
                    //String ctxPath = System.getProperty("user.dir");
                    System.out.println("-------"+path);
                    
                    File insertFile = new File(path);
                    
                    //上传
                    file.transferTo(insertFile);
                    
                    //入库
                    prodService.importProd(insertFile);
                }
                 
            }
           
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
    return "/success"; 
    }
	
}
