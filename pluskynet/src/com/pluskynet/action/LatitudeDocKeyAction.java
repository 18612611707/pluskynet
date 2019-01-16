package com.pluskynet.action;

import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.service.LatitudeDocKeyService;
import com.pluskynet.util.BaseAction;

public class LatitudeDocKeyAction extends BaseAction{
	private LatitudedocKey latitudedockey;
	@Override
	public Object getModel() {
		latitudedockey = new LatitudedocKey();
		return latitudedockey;
	}

	private LatitudeDocKeyService latitudeKeyService;
	
	public void setLatitudeKeyService(LatitudeDocKeyService latitudeKeyService) {
		this.latitudeKeyService = latitudeKeyService;
	}
	public void save(){
		latitudeKeyService.save(latitudedockey);
		
	}
	public void delete(){
		latitudeKeyService.delete(latitudedockey);
		
	}

}
