package com.pluskynet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.dao.ParaDao;
import com.pluskynet.domain.TParaOne;

public class SettingImport {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		ParaDao paraDao = (ParaDao) resource.getBean("paraDao");
		File file = new File("C:/Users/Administrator/Desktop/法院表1.csv");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "";
		int a = 0;
		try {
			while ((line = br.readLine()) != null) {
				if (a == 0) {
					line = line.substring(1);
				}
				String[] clases = line.split(",");
				String fclass = clases[0];
				String zclass = clases[1];
				int pgid = Integer.valueOf(clases[2]);
				int rootid = Integer.valueOf(clases[3]);
				
				TParaOne tParaOne = paraDao.getTParaOne(zclass,pgid,rootid);
				if (tParaOne==null) {
					tParaOne = paraDao.getTParaOne(fclass,pgid,rootid);
				}else{
					a = a+1;
					continue;
				}
				
				String potype = tParaOne.getPoType();
				int order = paraDao.getMaxOrder(tParaOne);

				TParaOne tParaOne2 = new TParaOne();
				tParaOne2.setPoName(zclass);
				tParaOne2.setPoPid(tParaOne.getPoId());
				tParaOne2.setPoOrder(order + 1);
				tParaOne2.setPoType("branch");
				tParaOne2.setPgId(tParaOne.getPgId());
				if (tParaOne.getPoRootId() == 0) {
					tParaOne2.setPoRootId(tParaOne.getPoId());
				} else {
					tParaOne2.setPoRootId(tParaOne.getPoRootId());
				}
				tParaOne2.setPoTier(tParaOne.getPoTier()+1);
				tParaOne2.setPgId(tParaOne.getPgId());
				System.out.println(zclass.contains("法院"));
				System.out.println(!zclass.contains("管辖"));
				System.out.println(tParaOne.getPoType().equals("branch"));
				if (!zclass.contains("管辖")) {
					tParaOne2.setPoLink("prop");
					tParaOne2.setPoPropRelate("value");
					tParaOne2.setPoPropValue(zclass);
					tParaOne2.setPoPropKey("court");
					paraDao.saveInfoOne(tParaOne2);
					
				}else{
					paraDao.saveInfoOne(tParaOne2);
					fclass = zclass;
					zclass = zclass.substring(0,zclass.length()-2);
					tParaOne = paraDao.getTParaOne(zclass,pgid,rootid);
					if (tParaOne==null) {
						tParaOne = paraDao.getTParaOne(fclass,pgid,rootid);
					}else{
						a = a+1;
						continue;
					}
					
					potype = tParaOne.getPoType();
					order = paraDao.getMaxOrder(tParaOne);

					tParaOne2 = new TParaOne();
					tParaOne2.setPoName(zclass);
					tParaOne2.setPoPid(tParaOne.getPoId());
					tParaOne2.setPoOrder(order + 1);
					tParaOne2.setPoType("branch");
					tParaOne2.setPgId(tParaOne.getPgId());
					if (tParaOne.getPoRootId() == 0) {
						tParaOne2.setPoRootId(tParaOne.getPoId());
					} else {
						tParaOne2.setPoRootId(tParaOne.getPoRootId());
					}
					tParaOne2.setPoTier(tParaOne.getPoTier()+1);
					tParaOne2.setPgId(tParaOne.getPgId());
					System.out.println(zclass.contains("法院"));
					System.out.println(!zclass.contains("管辖"));
					System.out.println(tParaOne.getPoType().equals("branch"));
					if (!zclass.contains("管辖")) {
						tParaOne2.setPoLink("prop");
						tParaOne2.setPoPropRelate("value");
						tParaOne2.setPoPropValue(zclass);
						tParaOne2.setPoPropKey("court");
						paraDao.saveInfoOne(tParaOne2);
					}
					
				}
				
				a = a+1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
