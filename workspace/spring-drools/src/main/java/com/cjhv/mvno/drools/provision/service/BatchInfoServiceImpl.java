package com.cjhv.mvno.drools.provision.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cjhv.mvno.framework.exception.ServiceException;
import com.cjhv.mvno.framework.jdbc.SqlIBatisClient;

public class BatchInfoServiceImpl implements BatchInfoService {

	private SqlIBatisClient generalDao;
	
	public void setGeneralDao(SqlIBatisClient generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public HashMap<String, Object> getBatchDate() throws ServiceException {
		// TODO Auto-generated method stub
		return (HashMap<String, Object>) generalDao.queryForObject("partner.partnerEvent.getBatchDate");
	}

}
