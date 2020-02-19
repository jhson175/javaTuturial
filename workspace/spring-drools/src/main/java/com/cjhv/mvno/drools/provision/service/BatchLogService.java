package com.cjhv.mvno.drools.provision.service;

import java.util.Map;

import com.cjhv.mvno.framework.exception.ServiceException;

public interface BatchLogService {

	public Map<String, Object> getBatchLogSeq() throws ServiceException;
	
	public void setStartBatchLog(Map<String, Object> m) throws ServiceException;
	
	public void setEndBatchLog(Map<String, Object> m) throws ServiceException;
	
	public void setErrorBatchLog(Map<String, Object> m) throws ServiceException;
	
	public void setBatchCount(String batchLogSeq, String str) throws ServiceException;
	
	public void setBatchCount(String batchLogSeq, String str, int cnt) throws ServiceException;
	
	public void setEndBatchAllLog(Map<String, Object> m) throws ServiceException;
	
	public void setEndBatchRemarkLog(Map<String, Object> m) throws ServiceException;
}