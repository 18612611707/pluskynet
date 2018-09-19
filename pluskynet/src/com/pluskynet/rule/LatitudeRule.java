package com.pluskynet.rule;

import java.util.ArrayList;
import java.util.List;

import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.StatsDoc;

import net.sf.json.JSONObject;


public class LatitudeRule {
	/*
	 * 关键字匹配
	 */
	public List<StatsDoc> keyWord(List<Docsectionandrule> list,String str){
		List<StatsDoc> listsDocs = new ArrayList<StatsDoc>();
		String sectiontext = null;
		JSONObject jsonObject = JSONObject.fromObject(str);
		String[] contain = jsonObject.getString("contains").split(",");// 包含
		boolean a = false;
		for (int j = 0; j < list.size(); j++) {
			StatsDoc statsDoc = new StatsDoc();
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			sectiontext = list.get(j).getSectiontext();
			for (int x = 0; x < contain.length; x++) {
				if (sectiontext.contains(contain[x])) {
					sectiontext = sectiontext.replaceAll(contain[x], "<span style=\"color:red\">"+contain[x]+"</span>");
					a = true;
				}else {
					a = false;
					break;
				}
			}
			if(a){
				String[] notcon = jsonObject.getString("notcon").split(",");
				for (int k = 0; k < notcon.length; k++) {
					if (notcon[k]== null || notcon[k].equals("")){
						a=true;
						break;
					}else{
						if (!sectiontext.contains(notcon[k])) {
							a=true;
						}else {
							a=false;
							break;
						}
					}
				}
			}
			if (a) {
				statsDoc.setStats("符合");
				docidAndDoc.setDoc(sectiontext);
				docidAndDoc.setDocid(list.get(j).getDocumentsid());
				docidAndDoc.setTitle(list.get(j).getTitle());
				statsDoc.setDocidAndDoc(docidAndDoc);
				listsDocs.add(statsDoc);
				break;
			}else {
				statsDoc.setStats("不符合");
				docidAndDoc.setDoc(sectiontext);
				docidAndDoc.setDocid(list.get(j).getDocumentsid());
				docidAndDoc.setTitle(list.get(j).getTitle());
				statsDoc.setDocidAndDoc(docidAndDoc);
				listsDocs.add(statsDoc);
				break;
			}
		}
		return null;
	}
	

}
