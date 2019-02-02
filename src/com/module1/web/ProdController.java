package com.module1.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.module1.service.ProdService;

@Controller
@RequestMapping("/pro") // 给这个类映射一个url
public class ProdController {

	@Autowired
	private ProdService prodService;

	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping("/springUpload.do")
	public ModelAndView springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		ModelAndView mv = new ModelAndView();
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// 创建上传文件目录
		//String ctxPath = "E:/springUpload/";
		String ctxPath = request.getSession().getServletContext().getRealPath("/uploadFile/明细");
		File f = new File(ctxPath);

		if (!f.exists()) {
			// 递归创建目录
			f.mkdirs();
		}
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					String path = ctxPath+"/" + nowTime + file.getOriginalFilename();
					
					File insertFile = new File(path);

					// 上传
					file.transferTo(insertFile);

					// 入库
					prodService.importProd(insertFile);
					mv.addObject("retMsg1","上传成功!");
					mv.setViewName("/login");
				}

			}

		}
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return mv;
	}

	@RequestMapping("/export.do")
	@ResponseBody
	 public ModelAndView exportExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		System.out.println("下载销售员统计表");
		ViewExcel viewExcel = new ViewExcel(); 
		Map map = null;
		//生成EXEC对象
		HSSFWorkbook workbook = prodService.exportExcel();
		
		try {
			//将对象保存成文档到前端
			viewExcel.buildExcelDocument(map, workbook, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView(viewExcel, model);
		
	}
}
