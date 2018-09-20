package com.pluskynet.action;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.service.LatitudeService;
import com.pluskynet.util.BaseAction;

import javassist.expr.NewArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings("all")
public class LatitudeAction extends BaseAction{
	private Latitude latitude = new Latitude();
	@Override
	public Latitude getModel() {
		return latitude;
	}
	private LatitudeService latitudeService;
	public void setLatitudeService(LatitudeService latitudeService) {
		this.latitudeService = latitudeService;
	}
	private JSONObject latitu;
	public JSONObject getLatitu() {
		return latitu;
	}
	public void setLatitu(JSONObject latitu) {
		this.latitu = latitu;
	}
	public void setLatitudeList(JSONArray latitudeList) {
		this.latitudeList = latitudeList;
	}
	private JSONArray latitudeList;
	private String latitudeName;
	private Integer latitudeId;


	
	public Integer getLatitudeId() {
		return latitudeId;
	}
	public void setLatitudeId(Integer latitudeId) {
		this.latitudeId = latitudeId;
	}
	public void setLatitudeName(String latitudeName) {
		this.latitudeName = latitudeName;
	}
	/*
	 * 新增纬度
	 */
	public void save(){
		String msg = latitudeService.save(latitude);
		outJsonByMsg(msg);
//		latitu.put("msg", msg);
//		return "save";
		}
	/*
	 * 修改纬度名称和规则
	 */
	public void update(){
		String msg = latitudeService.update(latitude);
		outJsonByMsg(msg);
	}
	/*
	 * 获取纬度树
	 */
	public void getLatitudeList(){
		List<Map> list = latitudeService.getLatitudeList();
		outJsonByMsg(list, "成功");
	}
	/*
	 * 根据ID获取纬度详细信息
	 */
	public void getLatitude(){
		Latitude latitudes = latitudeService.getLatitude(latitude);
		outJsonByMsg(latitudes, "成功");
	}
	/*
	 * 模糊查询纬度名称
	 */
	public void getLatitudeName(){
		List<String> latitudes = latitudeService.getLatitudeName(latitude);
		outJsonByMsg(latitudes, "成功");

	}
	/*
	 *规则预览 
	 */
	public void getDocList(){
		List<StatsDoc> list = latitudeService.getDocList(latitude);
		outJsonByMsg(list, "成功");
	}
	/*
	 * 获取筛选页左侧列表
	 */
	public void getScreeList(){
		List<Map> list = latitudeService.getScreeList(latitudeName,latitudeId);
		outJsonByMsg(list, "成功");
	}
	/*
	 * 跑批根据ID获取规则
	 */
	public Latitude getLatitudes(){
		latitude.setLatitudeid(latitudeId);
		Latitude latitudes = latitudeService.getLatitude(latitude);
		return latitudes;
	}
}
