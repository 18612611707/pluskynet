package com.pluskynet.action;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.service.LatitudeService;
import com.pluskynet.util.BaseAction;

import javassist.expr.NewArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LatitudeAction extends BaseAction {
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

	private String cause;
	private String spcx;
	private String sectionname;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getSpcx() {
		return spcx;
	}

	public void setSpcx(String spcx) {
		this.spcx = spcx;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	/*
	 * 新增纬度
	 */
	public void save() {
		User user = isLogined();
		if (user == null) {
			if (userid != null) {
				User getusers = new User();
				getusers.setUsername(username);
				getusers.setUserid(Integer.valueOf(userid));
				getusers.setName(name);
				getusers.setRolecode(rolecode);
				Map msg = latitudeService.save(latitude, getusers);
				outJsonByMsg(msg, "成功");
				return;
			}
			outJsonByMsg("未登录");
			return;
		}
		Map msg = latitudeService.save(latitude, user);
		outJsonByMsg(msg, "成功");
		// latitu.put("msg", msg);
		// return "save";
	}

	private String userid;
	private String username;
	private String name;
	private String rolecode;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}
	

	/*
	 * 修改纬度名称和规则
	 */
	public void update() {
		User user = isLogined();
		if (user == null) {
			if (userid != null) {
				User getusers = new User();
				getusers.setUsername(username);
				getusers.setUserid(Integer.valueOf(userid));
				getusers.setName(name);
				getusers.setRolecode(rolecode);
				String msg = latitudeService.update(latitude, getusers);
				outJsonByMsg(msg);
			}
			outJsonByMsg("未登录");
			return;
		} else {
			String msg = latitudeService.update(latitude, user);
			outJsonByMsg(msg);
		}
	}

	/*
	 * 获取纬度树
	 */
	public void getLatitudeList() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = latitudeService.getLatitudeList(user);
		outJsonByMsg(list, "成功");
	}

	/*
	 * 根据ID获取纬度详细信息
	 */
	public void getLatitude() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		Latitude latitudes = latitudeService.getLatitude(latitude);
		outJsonByMsg(latitudes, "成功");
	}

	/*
	 * 模糊查询纬度名称
	 */
	public void getLatitudeName() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<String> latitudes = latitudeService.getLatitudeName(latitude);
		outJsonByMsg(latitudes, "成功");

	}

	/*
	 * 规则预览
	 */
	public void getDocList() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<StatsDoc> list = latitudeService.getDocList(latitude, user);
		outJsonByMsg(list, "成功");
	}

	/*
	 * 获取筛选页左侧列表
	 */
	public void getScreeList() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		List<Map> list = latitudeService.getScreeList(latitudeName, latitudeId);
		outJsonByMsg(list, "成功");
	}

	/*
	 * 跑批根据ID获取规则
	 */
	public Latitude getLatitudes() {
		latitude.setLatitudeid(latitudeId);
		Latitude latitudes = latitudeService.getLatitude(latitude);
		return latitudes;
	}

	/*
	 * 按照名称查询
	 */
	public void getLatitudeShow() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = "失败";
		if (latitude.getLatitudename() == null) {
			outJsonByMsg(msg);
		} else {
			List<Map> list = latitudeService.getLatitudeShow(latitude.getLatitudename(), user);
			outJsonByMsg(list, "成功");
		}
	}

	/*
	 * 按照规则查询
	 */
	public void getRuleShow() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = "失败";
		if (latitude.getLatitudeid() == null) {
			outJsonByMsg(msg);
		} else {
			List<Latitude> list = latitudeService.getRuleShow(latitude.getLatitudeid(), cause, spcx, sectionname);
		}
	}

	/*
	 * 修改名称
	 */
	public void updateName() {
		User user = isLogined();
		if (user == null) {
			if (userid != null) {
				User getusers = new User();
				getusers.setUsername(username);
				getusers.setUserid(Integer.valueOf(userid));
				getusers.setName(name);
				getusers.setRolecode(rolecode);
				String msg = latitudeService.updateName(latitude, getusers);
				outJsonByMsg(msg);
				return;
			}
			outJsonByMsg("未登录");
			return;
		}
		String msg = null;
		if (latitude.getLatitudeid() == null) {
			msg = "失败";
		} else {
			msg = latitudeService.updateName(latitude, user);
		}
		outJsonByMsg(msg);
	}

	/*
	 * 送审
	 */
	public void approve() {
		User user = isLogined();
		if (user == null) {
			outJsonByMsg("未登录");
			return;
		}
		String msg = latitudeService.approve(latitude, user);
		outJsonByMsg(msg);
	}
}
