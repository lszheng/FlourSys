package com.module1.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.module1.service.SalesInitService;
import com.module1.service.impl.SalesPfmServiceimpl;

@Controller
@RequestMapping("/salesInit")
public class SalesInitController {

	@Autowired
	private SalesInitService salesInitService;
	
	@RequestMapping("/uploadFile.do")
	public ModelAndView uploadFile(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		
		ModelAndView mav = new ModelAndView();
		
		System.out.println("开始销售初始数据上传");
		//String uploadPath = request.getContextPath() + "\\uploadFile\\绩效";
		String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String uploadPath = request.getSession().getServletContext().getRealPath("/uploadFile/绩效");
		System.out.println("文件上传目录：" + uploadPath);
		File f = new File(uploadPath);
		if (!f.exists()) {
			// 递归创建目录
			f.mkdirs();
		}else{
			File file [] = f.listFiles();
			for (int i = 0; i < file.length; i++) {
				file[i].delete();
			}
		}

		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

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
					
					String path = uploadPath +"/"+ file.getOriginalFilename();
					File insertFile = new File(path);
					// 上传
					file.transferTo(insertFile);
					
					Boolean bool = salesInitService.uploadFile(insertFile);
					if(bool){
						mav.addObject("retcode", "0");
						mav.addObject("retMsg", "上传成功");
					}else{
						mav.addObject("retcode", "-1");
						mav.addObject("retMsg", "上传失败");
					}
					
				}

			}

		}
		mav.setViewName("/index");

		return mav;
	}
	
	@RequestMapping("/export.do")
	@ResponseBody
	 public ModelAndView exportExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		System.out.println("下载销售绩效表");
		ViewExcel viewExcel = new ViewExcel(); 
		Map map = null;
		
		//生成EXEC对象
		String path = request.getSession().getServletContext().getRealPath("/uploadFile/绩效");
		XSSFWorkbook wbook = null;//spservice.getDirExcel(path);
		XSSFWorkbook updwbook = null;//spservice.updateExcel(wbook);
		
		
		//HSSFWorkbook workbook = prodService.exportExcel();
		
		try {
			//将对象保存成文档到前端
			//viewExcel.buildExcelDocument(map, workbook, request, response);
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode("业务员绩效表.xlsx", "UTF-8"));
			updwbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView(viewExcel, model);
		
	}

}
