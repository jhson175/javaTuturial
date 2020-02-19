package com.cjhv.mvno.drools.batch;


import java.util.HashMap;

import com.cjhv.mvno.drools.provision.service.BatchInfoService;
import com.cjhv.mvno.framework.exception.ServiceException;

public class BatchInfo {
	
	public BatchInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	BatchInfoService batchInfoService;
	
	public HashMap<String, Object> getBatchDate() throws ServiceException {
		return batchInfoService.getBatchDate();
		
    }

}
