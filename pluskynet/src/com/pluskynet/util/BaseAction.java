package com.pluskynet.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pluskynet.domain.User;

/**
 * 
 * Action基类，实现将JSON格式结果的输出方法
 */

@SuppressWarnings(value="all")
public abstract class BaseAction extends ActionSupport implements Serializable,ModelDriven<Object> {
	/**  */
	private static final long serialVersionUID = 1L;	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public abstract Object getModel();

	private String retURL;

	/**
	 * 指示需要查询第几页的数据。
	 */
	private int page;
	/**
	 * 指示每页显示的记录条数。
	 */
	private int rows;
	
	/**
	 * 指示查询排序的条件，这是一个字符串，可能是数据库表字段或者是POJO对象的属性名
	 */
	private String sidx;
	/**
	 * 指示查询排序的方式，可能的值是ASC和DESC
	 */
	private String sord;

	/**
	 * 删除多个
	 */
	private String[] ids;
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	private String timeParam;


	protected String getBasePath() {
		return String.format("%s://%s:%d%s", this.getRequest().getScheme(), 
			this.getRequest().getServerName(), this.getRequest().getServerPort(), this.getRequest().getContextPath());
	}

	public void setTimeParam(String timeParam) {
		this.timeParam = timeParam;
	}

	/**
	 * 取得HttpServletRequest对象
	 * @return HttpServletRequest对象
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	             throws ServletException, IOException {
//	        System.out.println(request.getMethod());
	     }
	/**
	 * 取得Response对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		
			return ServletActionContext.getResponse();		
	}

	/**
	 * 取得ServletContext对象
	 * @return
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 转换字符串为json 格式
	 * @param str 普通字符串
	 */
	public void outJsonString(String str) {
		//getResponse().setContentType("text/javascript;charset=UTF-8");
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().addHeader("Access-Control-Allow-Origin", ServletActionContext.getRequest().getHeader("origin"));
		getResponse().addHeader("Access-Control-Max-Age", "10000");
		getResponse().addHeader("Access-Control-Allow-Credentials", "true");
		getResponse().addHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length,Authorization,Accept,X-Requested-With,accesstoken,orgcode");
		outString(str);
	}

	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 */
	public void outJson(Object obj) {
		outJsonString(JSONObject.fromObject(obj).toString());
	}
	
	
	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 */
	public void outJsonByMsg(Object obj) {
		this.outJsonByMsg(obj,0, null,null);
	}

	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 * @param message 提示信息
	 */
	public void outJsonByMsg(Object obj,String message) {
		this.outJsonByMsg(obj, 0,message,null);
	}
	
	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 * @param message 提示信息
	 * @param format 日期格式
	 */
	public void outJsonByMsg(Object obj,String message,String format) {
		this.outJsonByMsg(obj, 0,message,format);
	}
	
	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 * @param message 提示信息
	 */
	public void outJsonByMsg(String message) {
		this.outJsonByMsg(null, 0,message,null);
	}
	
	/**
	 *  转换ListRange对象为json 格式
	 * @param obj 任意对象
	 * @param totalSize 记录总数
	 */
	public void outJsonByMsg(List list,long totalSize) {
		this.outJsonByMsg(list,totalSize,null,null);
	}
	
	/**
	 *  转换任意对象为json 格式
	 * @param obj 任意对象
	 * @param message 提示信息
	 * @param format 日期格式
	 */
	@SuppressWarnings("unchecked")
	public void outJsonByMsg(Object obj,long totalSize,String message,String format) {
		int code = 0;
		if(message == null) {
			message = "";
		}else if (message == "成功") {
			code = 200;
		}
		JsonConfig config = this.getJsonConfig(format);
		// 类型为集合
		if(obj instanceof List) {
			if(obj != null && totalSize == 0) {
				totalSize = ((List)obj).size();
			}
			long totalPage = 0;
			if( totalSize >0 && rows>0) {
				totalPage=(totalSize + rows - 1)/rows;
			} else {
				totalPage = 0;
			}
		    if (page > totalPage) 
		    	page=(int)totalPage;

			ListRange formList = new ListRange();
			formList.setMsg(message);
			formList.setData((List)obj);
			formList.setCode(code);
//			formList.setTotalPage(totalPage);
//			formList.setPage(page);
//			formList.setRows(rows);
//			formList.setTotalSize(totalSize);
			JSONObject json = JSONObject.fromObject(formList,config);
			outJsonString(json.toString());
		} else {
			if(obj == null) {
				obj = new Object();
			}	
			JSONObject json = JSONObject.fromObject(obj,config);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", message);
			jsonObject.put("code", code);
			jsonObject.put("data", json);
			outJsonString(jsonObject.toString());
		}
	}


	/**
	 * 转换字符串为json 格式，并设置ContentType为text/html
	 * @param str 普通字符串
	 */
	public void outJsonStringOther(String str) {
		getResponse().setContentType("text/html;charset=UTF-8");
		outString(str);
	}

	/**
	 *  转换任意对象为json 格式，并设置ContentType为text/html
	 * @param obj 任意对象
	 */
	public void outJsonOther(Object obj) {
		outJsonStringOther(JSONObject.fromObject(obj).toString());
	}

	/**
	 * 输出集合对象为json 数组格式
	 * @param array 集合对象
	 */
	public void outJsonArray(Object array) {
		outJsonString(JSONArray.fromObject(array).toString());
	}

	/**
	 * 输出字符串到页面
	 * @param str 字符
	 */
	public void outString(String str) {
		try {
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().setHeader("Cache-Control", "no-store");
			getResponse().setDateHeader("Expires", 0L);
			getResponse().setHeader("Pragma", "no-cache");			
			PrintWriter out = getResponse().getWriter();
			out.write(str);
			getResponse().flushBuffer();
		} catch (IOException e) {
		}
	}

	/**
	 * 输出xml文本串到页面
	 * @param xmlStr xml串
	 */
	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	}

	/**
	 * 格式化默认日期
	 * @return
	 */
	public JsonConfig getJsonConfig() {
		return this.getJsonConfig(null);
	}
	
	/**
	 * 根据指定格式格式化日期
	 * @param format 根据format
	 * @return
	 */
	public JsonConfig getJsonConfig(String format) {
		if(format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		JsonConfig conf = new JsonConfig();
		conf.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
		// 转换日期格式
		return conf;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getRetURL() {
		return retURL;
	}

	public void setRetURL(String retURL) {
		this.retURL = retURL;
	}

	public void setRootRetURL(String retURL) {
		//if (!StringUtils.isEmpty(retURL)) {
		if(retURL!=null&&!"".equals(retURL)){
			this.setRetURL(this.getBasePath() + retURL);
		}
		//}
	}
	//分页显示
	public void outJsonByPage(Object obj,long totalSize,String message,String format) {
		int code = 0;
		if(message == null) {
			message = "";
		}else if (message == "成功") {
			code = 200;
		}
		JsonConfig config = this.getJsonConfig(format);
		// 类型为集合
		if(obj instanceof List) {
			if(obj != null && totalSize == 0) {
				totalSize = ((List)obj).size();
			}
			
			long totalPage = 0;
			if( totalSize >0 && rows>0) {
				totalPage=(totalSize + rows - 1)/rows;
			} else {
				totalPage = 0;
			}
		    if (page > totalPage) 
		    	page=(int)totalPage;

			ListRange formList = new ListRange();
			formList.setMsg(message);
			formList.setCode(code);
			formList.setData((List)obj);
			formList.setTotalPage(totalPage);
			formList.setPage(page);
//			formList.setRows(rows);
			formList.setTotalSize(totalSize);
			JSONObject json = JSONObject.fromObject(formList,config);
			outJsonString(json.toString());
		}
	}
	/*
	 * 验证是否登录
	 */
	public User isLogined(){
		User users = (User) ActionContext.getContext().getSession().get("user");
		if (users!= null) {
			return users;
		}
		return users;
	}
}
