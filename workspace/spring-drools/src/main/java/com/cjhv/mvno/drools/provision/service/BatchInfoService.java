package com.cjhv.mvno.drools.provision.service;

import java.util.HashMap;

import com.cjhv.mvno.framework.exception.ServiceException;

public interface BatchInfoService {

	public HashMap<String, Object> getBatchDate() throws ServiceException;
	
}