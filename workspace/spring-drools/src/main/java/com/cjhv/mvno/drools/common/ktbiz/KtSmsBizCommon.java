package com.cjhv.mvno.drools.common.ktbiz;
/**================================================================================
 * @Project      : SMS 이중화 PJT
 * @Source       : KtSmsBizCommon
 * @Description  : SMS KT API CALL 
 * @Version      : v1.0
 * 
 * Copyright(c) 2014 GM Solution All rights reserved
 * =================================================================================
 *  No    CSR ID   Req. No.         Req. Date     Author  Description
 * =================================================================================
 *  1.0                            2015/09/14     hsy  1.0 최초작성
 * =================================================================================
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cjhv.mvno.framework.exception.ServiceException;
import com.cjhv.mvno.framework.jdbc.SqlIBatisClient;
import com.cjhv.mvno.drools.common.sgi.excp.BatchException;
import com.cjhv.mvno.drools.util.Util;


public class KtSmsBizCommon {

	private SqlIBatisClient generalDao;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
	public SqlIBatisClient getGeneralDao() {
		return generalDao;
	}
	
	public void setGeneralDao(SqlIBatisClient generalDao) {
		this.generalDao = generalDao;
	}
	
	protected Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    protected Date getDateFromString(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.parse(dateString);
    }

    protected Date modifyDate(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);

        return calendar.getTime();
    }

    protected String createFormattedDateWithDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }
	
	protected final static String NL = "\n"; 
	
	public int smsSend(Map<String, Object> map){
		int nReslt = 0;
		/*
		 *
		 */
		try{
			Map<String, Object> smsMap = new HashMap<String, Object>();
			//Map smsMap = new HashMap()  ;
			
			Date sendDate = getCurrentDate();
	        String orgSendDate = "";
	        sendDate = modifyDate(sendDate, Calendar.SECOND, 3);
	        
	        /** jhseo2015 CHANGE
	         * 발송예약일자에 값이 "" 인 경우, null 인 경우, KEY(SEND_DATE) 구성이 않된 경우를 고려해서 수정
	         * Start
	         * */
	        if(!map.containsKey("SEND_DATE")){
	        	orgSendDate = "";
	        }else{
	        	orgSendDate =  Util.nullToString((String)map.get("SEND_DATE"));
	        }
	        /** jhseo2015 CHANGE
	         * 발송예약일자에 값이 "" 인 경우, null 인 경우, KEY(SEND_DATE) 구성이 않된 경우를 고려해서 수정
	         * End
	         * */ 	        

	        if ((!"".equals(orgSendDate)) && (orgSendDate.length() > 0)) {
	            try {
	                sendDate = getDateFromString((String) map.get("SEND_DATE"));
	            } catch (ParseException e) {
	                Object[] arguments = { "유효하지 않은 일자가 입력되었습니다." + " - " + map.get("SEND_DATE") };
	                //throw new ServiceException("commoninfra.customer.agent.customMessage", arguments);
	                logger.debug("ParseException : "+arguments);
	                sendDate = getCurrentDate();
	    	        sendDate = modifyDate(sendDate, Calendar.SECOND, 3);
	            }
	        }

	        if( !(map.containsKey("PHONE"))){
	        	String arguments = "INPUT값에 PHONE 데이타가 없습니다.(받는사람 전화번호)";
	        	throw new ServiceException(arguments); 
	        }else if( !(map.containsKey("CALLBACK"))){
	        	String arguments = "INPUT값에 CALLBACK 데이타가 없습니다.(보내는사람 전화번호)";
	        	throw new ServiceException(arguments); 
		    }
	        
	        boolean forceMms = (Boolean) (map.containsKey("FORCE_MMS") == true ? map.get("FORCE_MMS") : false);
	        boolean ktCustYn = (Boolean) (map.containsKey("KTCUST_YN") == true ? map.get("KTCUST_YN") : false);
	         
	        if (map.containsKey("REGR_ID") == false || map.get("REGR_ID") == null) {
	        	if (map.containsKey("BATCH_ID") == true && map.get("BATCH_ID") != null) {
	        		map.put("REGR_ID"  , map.get("BATCH_ID"));
	        	}else{
	        		map.put("REGR_ID"  , "BATCH");
	        	}
	        }
			
			smsMap.put("PHONE"    , map.get("PHONE").toString().replace("-", ""));
			smsMap.put("CALLBACK" , map.get("CALLBACK").toString().replace("-", ""));
			smsMap.put("SEND_DTM" , createFormattedDateWithDate(sendDate));
			smsMap.put("MSG"      , map.get("MSG"));
			smsMap.put("REGR_ID"  , map.get("REGR_ID"));
			if (forceMms == false) {
				smsMap.put("FORCE_MMS", 0);
			}else{
				smsMap.put("FORCE_MMS", 1);
			}
			smsMap.put("ETC1"     , map.get("ETC"));
			smsMap.put("ETC2"     , map.get("ETC2"));
			smsMap.put("ETC3"     , map.get("ETC3"));
			smsMap.put("ETC4"     , map.get("ETC4"));
			smsMap.put("ETC5"     , map.get("ETC5"));
			smsMap.put("ETC6"     , map.get("ETC6"));
			smsMap.put("ETC7"     , map.get("ETC7"));
			smsMap.put("SUBJECT"  , map.get("SUBJECT"));
			smsMap.put("CTRT_ID"  , map.get("CTRT_ID"));
			//2015.11.06 add
			if (ktCustYn == false) {
				smsMap.put("KTCUST_YN", "N");
			}else{
				smsMap.put("KTCUST_YN", "Y");
			}
			smsMap.put("MSGCODE"  , " " );
			smsMap.put("MESSAGE"  , " " );
			
			logger.debug("insertTsycoSmsSend Call...");
			
			nReslt = insertTsycoSmsSend(smsMap);
			
			logger.debug("insertTsycoSmsSend nReslt :"+nReslt);
    	}catch(Exception e){
			logger.debug(e.toString());
			e.printStackTrace();
		}    	
		return nReslt;
	}
	
	public String getSMSDisruptionYn() throws BatchException
	{
		String smsRefCode = (String)generalDao.queryForObject("appr.getDisruptionYn", "01");
		return smsRefCode;
	}
	
	public String getMMSDisruptionYn() throws BatchException
	{
		String mmsRefCode = (String)generalDao.queryForObject("appr.getDisruptionYn", "02");
		return mmsRefCode;
	}
	
	/**
     * sms - IF_SMS_SEND_HIST
     */
    public int insertIfSmsSendHist(Map<String, Object> inMap){
    	
    	logger.debug( "insertIfSmsSendHist ▶" + inMap );
        int updated = 0;        
    	try{
    		updated = generalDao.update("kt.sms.insertIfSmsSendHist",inMap);
    	}catch(Exception e){
			logger.debug(e.toString());
		}    	
    	logger.debug( "insertIfSmsSendHist ◀" + inMap ); 
    	return updated;
    }
    
    /**
     * mms - IF_MMS_SEND_HIST
     */
    public int insertIfMmsSendHist(Map<String, Object> inMap){
    	
    	logger.debug( "insertIfMmsSendHist ▶" + inMap );
        int updated = 0;        
    	try{
    		updated = generalDao.update("kt.sms.insertIfMmsSendHist",inMap);
    	}catch(Exception e){
			logger.debug(e.toString());
		}
    	logger.debug( "insertIfMmsSendHist ◀" + inMap ); 
    	return updated;
    }
	    
    /**
     * sms - TSYCO_SMS_SEND,mms - TSYCO_MMS_SEND
     */
    public int insertTsycoSmsSend(Map<String, Object> inMap){
    	
    	logger.debug( "insertTsycoSmsSend 1 :" + inMap );        
    	
        int updated = 0;      
        int nReslt  = 0;
      
    	try{
    		updated = generalDao.update("kt.sms.insertTsycoSmsSend",inMap);
    		logger.debug( "insertTsycoSmsSend updated :" + updated );
    	}catch(Exception e){
			logger.debug(e.toString());
		}    			
		
    	logger.debug( "insertTsycoSmsSend 2 :" + inMap ); 
    	
    	logger.debug( "insertTsycoSmsSend MSGCODE :" + inMap.get("MSGCODE") ); 
    	logger.debug( "insertTsycoSmsSend MESSAGE :" + inMap.get("MESSAGE") ); 
    	
    	if ( "SUCCESS".equals( (String) inMap.get( "MSGCODE" ) ))
        {
    		nReslt =  1;
        }else{
        	nReslt = -1;
        }
    	
    	return nReslt;
    }
}