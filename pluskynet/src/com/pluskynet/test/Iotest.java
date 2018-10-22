package com.pluskynet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Latitude;

public class Iotest {
	LatitudeDao latitudeDao;

	public void setLatitudeDao(LatitudeDao latitudeDao) {
		this.latitudeDao = latitudeDao;
	}
	public void save(Latitude latitude){
		latitudeDao.save(latitude);
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext("applicationContext.xml");
		CauseDao causeDao = (CauseDao) resource.getBean("causeDao");
		LatitudeDao latitudeDao = (LatitudeDao) resource.getBean("latitudeDao");
		File file = new File("C:/Users/Administrator/Desktop/民事案由（改）.csv");
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
				String causename = null;
				String fcausename = null;
				String causetable = null;
				String doctable = null;
				String[] words = line.split(",");
				for (int i = 0; i < words.length; i++) {
					if (i==0) {
						fcausename = words[0];
					}else if (i==1) {
						causename = words[1];
					}else if (i==2) {
						causetable = words[2];
					}else if (i==3) {
						doctable = words[3];
					}
				}
				System.out.println(fcausename.length());
				if (a==0) {
					fcausename = fcausename.substring(1, fcausename.length());
				}
				Latitude latitude = new Latitude();
				if (fcausename.length()!=1) {
					Integer latitudeid = latitudeDao.selectid(fcausename);
					if (latitudeid==null) {
						continue;
					}else{
					latitude.setLatitudename(causename);
					latitude.setLatitudefid(latitudeid);
					latitudeDao.save(latitude);
					}	
				}
				a = ++a;
			/*	Cause cause = new Cause();
				if (fcausename.length()!=1) {
					cause.setCausename(fcausename);
					Cause cause2= causeDao.selectCause(cause);
					cause.setCausename(causename);
					cause.setCausetable(causetable);
					cause.setDoctable(doctable);
					if(cause2==null){
						cause.setFid(0);
					}else{
					cause.setFid(cause2.getId());
					}
					causeDao.save(cause);
				}else{
					cause.setCausename(causename);
					cause.setFid(0);
					causeDao.save(cause);
				}*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
