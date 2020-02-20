
/**
 * ----------------------------------------------------------------------------
 * @author jongyeolkim
 * @since 2019.10.07
 * 
 * [파트너 이벤트배치 공통 클래스]
 * 
 * 프로그램설명 : 파트너 이벤트 배치 공통함수
 * 
 * 테이블 : SEQ_BATCH_LOG_SEQ(배치 시퀀스), TCMCO_BATCH_LOG_MAST(배치 로그마스터),  
 *        TCMCO_BATCH_LOG_ERR_INFO(배치에러로그정보), TSYCM_CODE_DETAIL(코드정보)    
 * 
 * (업무처리 사항)
 * 1. 배치 로그 저장 관련 함수
 * 2. 공통 코드 조회 관련 함수
 * 	  
 * HISTORY       변경자       변경사항
 * ----------------------------------------------------------------------------
 * 2019-10-07    jongyeolkim       최초생성
 *    
 * 
 * ----------------------------------------------------------------------------
 */

package com.cjhv.mvno.drools.schedule.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cjhv.mvno.drools.common.exception.CommonInfraDataAccessException;
import com.cjhv.mvno.drools.common.ktbiz.KtSmsBizCommon;
import com.cjhv.mvno.drools.common.sgi.excp.BatchException;
import com.cjhv.mvno.drools.common.utils.StringUtils;
import com.cjhv.mvno.framework.exception.ServiceException;
import com.cjhv.mvno.framework.jdbc.SqlIBatisClient;

import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PartnerCommon {

	protected SqlIBatisClient generalDao;
    protected SimpleJdbcTemplate jdbcTemplate;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String BTCH_TP_CD ;
    
    public String getBTCH_TP_CD() {
		return BTCH_TP_CD;
	}
	protected String BATCH_ID ; 
    public String getBATCH_ID() {
		return BATCH_ID;
	}
	protected String SMS_TYPE ;
    protected String MANUAL_DATE ;
    protected String PROGRAM_ID;
    protected String OPRTUNI_KEY;
    protected String STD_DT;
    protected boolean ERP_DUP;
    
    
    public boolean isERP_DUP() {
		return ERP_DUP;
	}

	public void setERP_DUP(boolean eRP_DUP) {
		ERP_DUP = eRP_DUP;
	}

	public String getSTD_DT() {
		return STD_DT;
	}

	public void setSTD_DT(String sTD_DT) {
		STD_DT = sTD_DT;
	}

	public String getOPRTUNI_KEY() {
		return OPRTUNI_KEY;
	}

	public void setOPRTUNI_KEY(String oPRTUNI_KEY) {
		OPRTUNI_KEY = oPRTUNI_KEY;
	}

	public String getPROGRAM_ID() {
		return PROGRAM_ID;
	}

	public void setPROGRAM_ID(String pROGRAM_ID) {
		PROGRAM_ID = pROGRAM_ID;
	}

	public String getBUS_DIV() {
		return BUS_DIV;
	}

	public void setBUS_DIV(String bUS_DIV) {
		BUS_DIV = bUS_DIV;
	}
	protected String BUS_DIV;
    
    public String getMANUAL_DATE() {
		return MANUAL_DATE;
	}
	protected String AUTOEXE_DATE ;
    protected String BATCH_TYPE ;    
    protected KtSmsBizCommon ktSmsApi;
    
    protected String acctNoUrl; 
	protected String acctInUrl; 
	protected String syncId; 
	protected String systemId;
	
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	protected String hostname; 
	protected String ipString; 
	protected String ipHostAddress; 
	/**
     * 생성자.
     */
	public PartnerCommon()
	{
		try {	
			InetAddress ip = InetAddress.getLocalHost();
		    hostname = StringUtils.nullToStr(ip.getHostName());
		    ipString = StringUtils.nullToStr(ip.toString());
		    ipHostAddress = StringUtils.nullToStr(ip.getHostAddress());		  
		}
		catch(Exception e) {			
			e.printStackTrace();		
		}
	}
	private static Properties properties;
	
	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	
	public void setAcctNoUrl(String acctNoUrl) {
		this.acctNoUrl = acctNoUrl;
	}

	public void setAcctInUrl(String acctInUrl) {
		this.acctInUrl = acctInUrl;
	}

	public void setJdbcTemplate( SimpleJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setBTCH_TP_CD( String BTCH_TP_CD )
	{
		this.BTCH_TP_CD = BTCH_TP_CD; 
	}	
	
	public void setBATCH_ID( String BATCH_ID )
	{
		this.BATCH_ID = BATCH_ID;
	}
	public void setSMS_TYPE(String MANUAL_DATE) {
		this.SMS_TYPE = SMS_TYPE;
	}
	public void setMANUAL_DATE(String MANUAL_DATE) {
		this.MANUAL_DATE = MANUAL_DATE;
	}
	public void setBATCH_TYPE(String BATCH_TYPE) {
		this.BATCH_TYPE = BATCH_TYPE;
	}
	
	public SqlIBatisClient getGeneralDao() {
		return generalDao;
	}
	
	public void setGeneralDao(SqlIBatisClient generalDao) {
		this.generalDao = generalDao;
	} 
	
	public void setKtSmsApi(KtSmsBizCommon ktSmsApi) {
		this.ktSmsApi = ktSmsApi;
	}	
	
	/**
	 * 배치정보 조회 SEQ 조회
	 * @return
	 * @throws Exception
	 */
	public HashMap getBatchInfo() throws Exception
	{
		StringBuffer sql = new StringBuffer(""); 
		
		HashMap mapReturn = new HashMap();
		
		try{
			mapReturn.put("BTCH_TP_CD"    , BTCH_TP_CD); 
			mapReturn.put("BATCH_ID"      , BATCH_ID); 
			mapReturn.put("READ_CNT", 0);                            // 대상 건수 
			mapReturn.put("PROC_CNT", 0);                            // 처리 건수 
			mapReturn.put("ERR_CNT",  0);                            // 에러 건수 
			mapReturn.put("REGR_ID"       , BATCH_ID); 
			mapReturn.put("CHGR_ID"       , BATCH_ID); 			
			logger.debug("mapReturn ===> " + mapReturn.toString());

		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			logger.debug(e.toString());
			return null;
		}catch(Exception e){
			logger.debug(e.toString());
			return null;
		}		
		return mapReturn;		
	}	

	/**
     * 배치로그정보저장
     * @param
     * @throws Exception
     */
	public void insertBatchLogMast(Map map) throws Exception
	{		
		try {
			 generalDao.insert("uf.Commmon.insertBatchLogMast", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		} 	
	}
	 
	/**
	 * 배치로그오류정보저장
	 * @param
	 * @throws Exception
	 */
	public void insertBatchErrLog(Map map) throws Exception
	{
		try {
			generalDao.insert("uf.Commmon.insertBatchErrLog", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		}  
	
	}
	
	/**
	 * 배치로그정보저장
	 * @param
	 * @throws Exception
	 */
	public void updateBatchLogMast(Map map) throws Exception
	{
		try {			 

			generalDao.update("uf.Commmon.updateBatchLogMast", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		}
	}
	
	
	/**
	 * 배치정보 최종 처리 일자 저장
	 * @param
	 * @throws Exception
	 */
	public void updateBatchInfo(Map map) throws Exception
	{

		try {			 
			generalDao.update("uf.Commmon.updateBatchInfo", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		}  

	}
	
	/**
	 * 템플릿조회
	 * @param
	 * @throws Exception
	 */
	public HashMap getTempletInfo(HashMap<String, Object> smsInfoParam) throws Exception
	{
		HashMap<String, Object> smsTemplet = new HashMap<String, Object>();
		try{   		
    		//logger.debug("smsInfoParam :: " + smsInfoParam);    		
    		smsTemplet = (HashMap<String, Object>) generalDao.queryForObject("partner.partnerEvent.getSmsTemplet", smsInfoParam);
    		//logger.debug("smsTemplet :: " + smsTemplet);
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			logger.debug(e.toString()); 
			return null;
		}catch(Exception e){
			logger.debug(e.toString()); 
			return null;
		}
		return smsTemplet;		
	}
	
	/**
     * 파트너정보 추가
     * @param
     * @throws Exception
     */
	public int insertPartnerInfo(Map map) throws Exception
	{		
		int resultCnt = 0;
		try {
			 //파트너정보    			
			int partnerSeq = Integer.parseInt(String.valueOf(generalDao.queryForObject("partner.partnerEvent.getPartnerIfMsgInfoSeq", map)));
			logger.debug("partnerSeq::"+partnerSeq);
			map.put("TCMCE_PARTNER_IF_INFO_SEQ", partnerSeq);			
			map.put("SYNC_ID", BATCH_ID);    							
			map.put("PARTNER_CD", "01");
			map.put("MNO_BIZ_CD","MNOKT");	
			
			resultCnt = generalDao.update("partner.partnerEvent.insertHanaPartner", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		} 
		return resultCnt;
	}
	
	/**
     * 파트너정보 업데이트
     * @param
     * @throws Exception
     */
	public int updatePartnerInfo(Map map) throws Exception
	{		
		int resultCnt = 0;
		try {	
			map.put("SYNC_ID", BATCH_ID);    							
			map.put("PARTNER_CD", "01");
			map.put("MNO_BIZ_CD","MNOKT");	
			resultCnt = generalDao.update("partner.partnerEvent.updateHanaPartner", map);
		} catch(Exception e) {
			logger.debug("Exception :" + e.toString());			
		} 
		return resultCnt;
	}
	
	/**
     * SMS전송
     * @param
     * @throws Exception
     */
	public int smsKTsend(String smsId, Map map, Map batchMap, boolean histYn) throws Exception
	{		
		int successYn = 0;
		try {			
			String curDate = getCurrentTime();    
			
			//SMS 전송템플릿 정보조회
			HashMap<String, Object> smsInfoParam = new HashMap<String, Object>();
    		smsInfoParam.put("SMS_ID", smsId);
    		//smsInfoParam.put("MNO_BIZ_CD", "MNOKT");    		
    		HashMap<String, Object> smsTemplet = getTempletInfo(smsInfoParam);
    		//logger.debug("smsTemplet :: " + smsTemplet);     		
			
    		//메세지문구 치환
    		String str = smsTemplet.get("TEMPLET").toString();    					
			str = str.replace("#PROD_NM#",map.get("PROD_NM").toString()); //상품명적용     
			
			//LMS 전송
			Map<String, Object> sendMapKt = new HashMap<String, Object>();
			sendMapKt.put("PHONE", 		map.get("SVC_TEL_NO"));	// 필수 : 받는 사람 전화 번호			
			sendMapKt.put("CTRT_ID",	map.get("CTRT_ID"));			         					      					
			sendMapKt.put("MSG", 		str);		          // 필수 : 메세지 문구  
			
			sendMapKt.put("CALLBACK", "18551144");
			
			// 필수 : 문자 발송시간 (NULL: 실시간)
			String sendTime = StringUtils.nullToStr((String)map.get("SEND_DATE"));
			if(sendTime.length()>0){
				if(sendTime.equals("WORKING_TIME")){	
					sendTime = getLmsSendTime();	
					logger.debug("WORKING_TIME #####################################:: " + sendTime);
				}
				sendMapKt.put("SEND_DATE", 	sendTime);
				logger.debug("SEND_DATE #####################################:: " + sendTime);
			}else{
				//(NULL: 실시간) SEND_DATE 미설정시 즉시발송
				sendTime = curDate;
				logger.debug("SEND_DATE 미설정 #####################################:: ");
			}
			sendMapKt.put("REG_ID", 	BATCH_ID);
			sendMapKt.put("BATCH_ID", 	BATCH_ID);
			sendMapKt.put("FORCE_MMS", 	true);					// 선택 : MMS여부(true:MMS)
			sendMapKt.put("ETC4", "10112"); //통계코드
			
			logger.debug("최종 PARAM #####################################  sendMapKt :: " + sendMapKt);
			
			//LMS 발송 API 호출
			successYn  = ktSmsApi.smsSend(sendMapKt);
			logger.debug("최종 전송결과 #####################################  successYn :: " + successYn);
			
			//SMS 히스토리 저장
			if(successYn != -1) {				
				map.put("SEND_TIME",sendTime);	  // 발송시간				
				if(histYn){
					int cnt = generalDao.update("partner.partnerEvent.insertSmsSendHist", map);
					logger.debug("발송이력 :: " + cnt);	
				} 								
			}else {
				String remark = "====SMS 전송 에러 ====";				
				batchMap.put("ERR_DESC", remark);
				insertBatchErrLog(batchMap);			     						
			}			
			
		} catch(Exception e) {			
			logger.debug("Exception :" + e.toString());			
		} 
		return successYn;
	}
	
    /**
	 * Http Url 호출 
	 * Url : 호출 URL
	 * parameters : 인자값설정 ex)aaa=111&bbb=222&ccc=333
	 * @return
	 * @throws Exception
	 */
    public String httpConnResult(String Url, String parameters ) throws UnknownHostException, SocketException, IOException {
    	//Url = "https://dev11-m.kebhana.com:18680/corporation/cjhello/acctNoInquiry.do";
    	//parameters = URLEncoder.encode(parameters, "utf-8");
    	logger.debug ("접근 Url::::::::"+Url);    
        logger.debug ("접근 parameters::::::::"+parameters);   
    	URL url = new URL(Url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        
        //connection.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
        //connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");  
        //requestXml.append(parameters);
         
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(parameters.toString().getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

        int response = connection.getResponseCode();

        String headerType = connection.getContentType();
        
        
      	logger.debug("[http headerType ===>  " + headerType);
        
        if (response != 200) {
        	logger.debug("[URL CONNECTION]요청에 실패하였습니다. (code:" + response + ")");
        }

        logger.debug("[URL CONNECTION]데이터를 수신합니다.");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String buffer = "";
        StringBuffer responseXmlBuffer = new StringBuffer();
        while ((buffer = br.readLine()) != null) {
            responseXmlBuffer.append(buffer + "\n");
        }
        String responseXml = responseXmlBuffer.toString();
        //reader.close();
        logger.debug("[RES]" + responseXml);  
        return responseXml;
    }
    
    /**
	 * Json -> HashMap 형태로 변환
	 * jsonStr : json형식의 FULL String
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * @return
	 * @throws Exception
	 */
	public HashMap getJsonParser(String jsonStr, HashMap mapParam) throws Exception
	{		
		HashMap mapReturn = new HashMap();
		//logger.debug("jsonStr::"+jsonStr);
		//logger.debug("mapParam::"+mapParam);
		try{
			JSONObject resultJsonObject = new JSONObject();	
	        
	        JSONParser parser = new JSONParser();			
	        Object obj = new Object();
	        obj = parser.parse(jsonStr);
	        JSONObject jsonObject = (JSONObject) obj;	
	        
	        Set key = mapParam.keySet();
	        String writeOneLine = "";
	        int aaa = 0;
	        for (Iterator iterator = key.iterator(); iterator.hasNext();) {
	        	String keyName = String.valueOf(iterator.next());             // 속성명
   			    String value   = String.valueOf(mapParam.get(keyName));  // 속성에 해당하는 값
   			    //logger.debug("keyName::"+keyName);
   			    //logger.debug("value::"+value);
   			    //logger.debug("jsonObject.get(keyName)::"+jsonObject.get(keyName));
   			    mapReturn.put(keyName, jsonObject.get(keyName));
   			    aaa++;
   			    //if(aaa>10){
   			    //	break;
   			    //}   			    
	        }	        

        } catch (ParseException e) {			
			e.printStackTrace();
			
		} catch (Exception e) {			   
			e.printStackTrace();			
		}		
		return mapReturn;		
	}
	
	/**
	 * JsonArr -> HashMap 형태로 변환(ROOF식일때 사용)
	 * jsonStr : json형식의 FULL String
	 * jsonArrNm : ROOF있는 데이터 컬럼명
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * @return
	 * @throws Exception
	 */
	public HashMap getJsonParserArr(String jsonStr, String jsonArrNm, HashMap mapParam) throws Exception
	{			
		HashMap mapReturn = new HashMap();
		logger.debug("jsonStr::"+jsonStr);
		logger.debug("mapParam::"+mapParam);
		try{
			JSONObject resultJsonObject = new JSONObject();		        
	        JSONParser parser = new JSONParser();			
	        
	        Object obj = new Object();        
	        obj = parser.parse(jsonStr);
	        JSONObject jsonObject = (JSONObject) obj;	
	        
	        
	        /* SAMPLE LOCAL 
	        Object obj = new Object();  
			obj = parser.parse(new FileReader("D:\\DELETE\\temp\\test.json")); //LOCAL
	        //Object obj = parser.parse(new FileReader("/home/HVMVNO/stg/commoninfraStg/WebContent/pdf/test.json"));  //STAGE	        
			JSONObject jsonObject = (JSONObject) obj;
			 SAMPLE END */
	        

			JSONArray arrStr = (JSONArray) jsonObject.get(jsonArrNm);
			if(arrStr != null){
				Iterator<JSONObject> iterator = arrStr.iterator();
				int i = 1;			
				while (iterator.hasNext()) {
					HashMap<String, Object> arrDetailMap = new HashMap<String, Object>();
					//logger.debug("["+i+"]");					
					JSONObject jsonObjectDialogue = (JSONObject)iterator.next();					
					Set key = mapParam.keySet();
			        String writeOneLine = "";
			        int aaa = 0;
			        for (Iterator iteratorArr = key.iterator(); iteratorArr.hasNext();) {
			        	String keyName = String.valueOf(iteratorArr.next());             // 속성명		   			    
		   			    //logger.debug("keyName::"+keyName);
		   			    //logger.debug("jsonObjectDialogue_value::"+(String) jsonObjectDialogue.get(keyName));		   			    
		   			    arrDetailMap.put(keyName, (String) jsonObjectDialogue.get(keyName));		   			    		    
			        }
			        mapReturn.put(String.valueOf(i), arrDetailMap);
					i++;
				}
			}        

        } catch (ParseException e) {			
			e.printStackTrace();
			
		} catch (Exception e) {			   
			e.printStackTrace();			
		}	
		logger.debug("mapReturn:FINAL:"+mapReturn);
		return mapReturn;		
	}
	

	

	
	/**
	 * 하나은행 신청서번호를 인자로 계좌정보 가져옴
	 * jsonStr : json형식의 FULL String
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * @return
	 * @throws Exception
	 */
	public HashMap getHanaAcctInfo(String paramStr) throws Exception
	{		
		HashMap mapParam = new HashMap();
		HashMap tmpRresultHm = new HashMap();
		HashMap resultHm = new HashMap();
		//logger.debug("paramStr::"+paramStr);
		//logger.debug("mapParam::"+mapParam);
		//logger.debug("syncId::::" + syncId);
		//logger.debug("acctNoUrl:" + acctNoUrl);
		//logger.debug("acctInUrl:" + acctInUrl);
		
		try{    		
			logger.debug("FINAL_PARAM::::" + paramStr);
			String encStr = ""; //getEncHanaStr(paramStr);
    		encStr = URLEncoder.encode(encStr, "utf-8");
    		String parameters = "encData="+encStr;   		
    		logger.debug("FINAL_ENC::::" + parameters);
    		logger.debug("FINAL_acctNoUrl::::" + acctNoUrl);
    		
    		String responseResult = httpConnResult(acctNoUrl, parameters);    		
    		mapParam.put("encData","Y");
    		tmpRresultHm = getJsonParser(responseResult, mapParam); 
    		logger.debug("resultHm::"+tmpRresultHm);
    		String encData = (String)tmpRresultHm.get("encData");
    		//복호화
    		//responseResult = getDecHanaStr(encData);
    		//디코딩
    		responseResult = URLDecoder.decode(responseResult, "UTF-8");
    		
    		logger.debug("FINAL responseResult::"+responseResult);
    		
    		mapParam = new HashMap();
    		mapParam.put("ciRcgnNo", "Y");
    		mapParam.put("prdCd", "Y");
    		mapParam.put("apcNo", "Y");
    		mapParam.put("acctNo", "Y");
    		mapParam.put("errorCode", "Y");
    		mapParam.put("errorMessage", "Y");
    		mapParam.put("actDvCd", "Y");    		
    		
    		resultHm = getJsonParser(responseResult, mapParam); 
    		//logger.debug("resultHm::"+resultHm);
    		//String decData = getDecHanaStr(encData);
    		//resultHm2 = getParamParser(decData);
    		
    		//logger.debug("resultHm1::"+resultHm1);
    		//logger.debug("decData::"+decData);
    		//logger.debug("resultHm2::"+resultHm2);

        } catch (ParseException e) {			
			e.printStackTrace();
			
		} catch (Exception e) {			   
			e.printStackTrace();			
		}		
		return resultHm;		
	}
	
	public HashMap getParamParser(String param) throws Exception
	{
		logger.debug("param::"+param);
		HashMap mapReturn = new HashMap();		
		String[] allColNmValArr = param.split("&");// EX) String param = "aaa=111&bbb=222&ccc=333";
		for(int i = 0; i < allColNmValArr.length; i++){
			String[] colNmValArr = ((String)allColNmValArr[i]).split("=");     		
			if(colNmValArr.length == 2){    			
				mapReturn.put(colNmValArr[0], colNmValArr[1]);    	        
				//log.debug("resultHm:::::::::::::" + mapReturn); 
			}else{
				logger.debug("파싱인자 오류::" + allColNmValArr[i].toString());
			}    		    		 		
		}	  
		logger.debug("getParamParser resultHm::::::::::FANAL:::" + mapReturn); 
		return mapReturn;		
	}
	
	//현재시간(yyyyMMddHHmmss)
	public String getCurrentTime() throws Exception
	{
		String curDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		logger.debug("curDate : "+ curDate);
		return curDate;		
	}
		
	String curDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	
	//LMS 발송시간(업무시간만 보낼수 있게)
	public String getLmsSendTime() throws Exception
	{
		String sendDtm = ""; // 발송시간 (빈 값이면 실시간)
		int todayHh = Integer.parseInt(new SimpleDateFormat("HH").format(new Date())); // 현재시간
		System.out.println("todayHh::"+todayHh);
		// 처리시간이 9시 이전이면 금일 10시 Set 
		if ( todayHh < 9 ){
			sendDtm = new SimpleDateFormat("yyyyMMdd").format(new Date()).concat("100000"); // 금일 10시로 세팅
		}
		// 처리시간이 18시 이후이면 익일 10시 Set
		else if ( todayHh >= 18){
			sendDtm = new SimpleDateFormat("yyyyMMdd").format(new Date().getTime()+(1000 * 60 * 60 * 24)).concat("100000"); // 익일 10시로 세팅
		}
		// 빈값은 현재시간
		else{
			logger.debug("빈값은 현재시간 : ");
		}							
		logger.debug("sendDtm : "+ sendDtm);
		return sendDtm;		
	}
	
	/**
	 * 공통코드 조회
	 * @param
	 * @throws Exception
	 */
	private List getCommonCodeList(Map<String, Object> map) throws Exception
	{
        List<Map<String, Object>> listReturn;
 
		try{
			
			listReturn =  generalDao.queryForList("partner.partnerEvent.getCommonCodeList", map);
			
		}catch(org.springframework.dao.EmptyResultDataAccessException e){
			logger.debug(e.toString());
			return null;
		}catch(Exception e){
			logger.debug(e.toString());
			return null;
		}
 		
		return listReturn;		
		
	}
	
	//하나은행 계좌정보 미결합시 LMS 재전송
	public boolean sendLMSnoAccResend(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;
		int STND_SEND_CNT = 0;
		int STND_SEND_AFTER_DAY = 0;
		String BASIC_PARAM = "";
		
		try {		
			for(   Map<String, Object> codeMap : listReturn){
	        	if(codeMap.get("COMMON_CD").equals("RESEND")) STND_SEND_CNT = Integer.parseInt((String)codeMap.get("REF_CODE2"));
	        	if(codeMap.get("COMMON_CD").equals("RESEND")) STND_SEND_AFTER_DAY = Integer.parseInt((String)codeMap.get("REF_CODE3"));
	        	if(codeMap.get("COMMON_CD").equals("API02")) BASIC_PARAM = (String)codeMap.get("REF_CODE2");
	        }	        

			logger.debug("STND_SEND_CNT : " +STND_SEND_CNT); 
			logger.debug("STND_SEND_AFTER_DAY : " +STND_SEND_AFTER_DAY); 
			logger.debug("BASIC_PARAM : " +BASIC_PARAM);			
			
			String curDate = getCurrentTime();
			String remark  = "";
			int readCnt    = 0;
    		//SMS 전송대상 조회
    		List<Map<String, Object>> smsReSendList = new ArrayList<Map<String, Object>>();	
    		batchMap.put("STND_SEND_CNT",STND_SEND_CNT);
    		smsReSendList = generalDao.queryForList("partner.partnerEvent.getSmsReSendList", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (smsReSendList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + smsReSendList.size());						
				
				for(Map<String, Object> map : smsReSendList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					readCnt++;
					
					String accNo        = StringUtils.nullToStr((String)map.get("ACC_NO"));
					String apcNo        = StringUtils.nullToStr((String)map.get("APC_NO"));
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI")); 
					String ctrtUseYn    = StringUtils.nullToStr((String)map.get("CTRT_USE_YN"));
					int sendCnt         = Integer.parseInt(StringUtils.nullToStr((String)map.get("SEND_CNT")));
					int sendAfterDay    = Integer.parseInt(StringUtils.nullToStr((String)map.get("SEND_AFTER_DAY")));    					
					
					logger.debug("accNo::"+accNo); 
					logger.debug("apcNo::"+apcNo); 
					logger.debug("decCi::"+decCi); 
					logger.debug("ctrtUseYn::"+ctrtUseYn); 
					logger.debug("sendCnt::"+sendCnt); 
					logger.debug("sendAfterDay::"+sendAfterDay);        		
	        		if(sendAfterDay < STND_SEND_AFTER_DAY){
	        			logger.debug("LMS 발송후 "+STND_SEND_AFTER_DAY+"일이 지나지 않아서 SKIP 합니다."); 
	        			continue;
	        		}else{
	        			logger.debug("LMS 발송후 "+STND_SEND_AFTER_DAY+"일이 경과하여 조건만족하여 LMS 발송합니다.");
	        			//LMS 전송
	        			map.put("SEND_DATE", "WORKING_TIME");
	        			String templetId = "";
	        			if(sendCnt==0){
	        				templetId = "SMS0040901";
	        			}else{
	        				templetId = "SMS0040902";
	        			} 	        			
    					int successYn = smsKTsend(templetId,map ,batchMap, true);		
    					if(successYn != -1) result = false;
	        		}    	        		
				}
			} 					
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("sendLMSnoAccResend function finish");    
		}
		return result;
	}
	
	//하나은행 신청서번호만 들어온경우 계좌정보 업데이트 실시
	public boolean updateAccNoOnlyApcNo(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;
		int STND_SEND_CNT = 0;
		int STND_SEND_AFTER_DAY = 0;
		String BASIC_PARAM = "";
		
		try {		
			for(   Map<String, Object> codeMap : listReturn){
	        	if(codeMap.get("COMMON_CD").equals("RESEND")) STND_SEND_CNT = Integer.parseInt((String)codeMap.get("REF_CODE2"));
	        	if(codeMap.get("COMMON_CD").equals("RESEND")) STND_SEND_AFTER_DAY = Integer.parseInt((String)codeMap.get("REF_CODE3"));
	        	if(codeMap.get("COMMON_CD").equals("API02")) BASIC_PARAM = (String)codeMap.get("REF_CODE2");
	        }	        

			logger.debug("STND_SEND_CNT : " +STND_SEND_CNT); 
			logger.debug("STND_SEND_AFTER_DAY : " +STND_SEND_AFTER_DAY); 
			logger.debug("BASIC_PARAM : " +BASIC_PARAM);			
			
			String curDate = getCurrentTime();
			String remark  = "";
			int readCnt    = 0;
    		//SMS 전송대상 조회
    		List<Map<String, Object>> smsReSendList = new ArrayList<Map<String, Object>>();	
    		batchMap.put("STND_SEND_CNT",STND_SEND_CNT);
    		smsReSendList = generalDao.queryForList("partner.partnerEvent.getOnlyApcNoList", batchMap);    		
    		
            String sendTime = getLmsSendTime();
			if (smsReSendList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + smsReSendList.size());						
				
				for(Map<String, Object> map : smsReSendList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					readCnt++;
					
					String accNo        = StringUtils.nullToStr((String)map.get("ACC_NO"));
					String apcNo        = StringUtils.nullToStr((String)map.get("APC_NO"));
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI"));
					String ctrtId       = StringUtils.nullToStr((String)map.get("CTRT_ID"));
					String ctrtUseYn    = StringUtils.nullToStr((String)map.get("CTRT_USE_YN"));
										
					logger.debug("accNo::"+accNo); 
					logger.debug("apcNo::"+apcNo); 
					logger.debug("decCi::"+decCi); 
					logger.debug("ctrtUseYn::"+ctrtUseYn); 					
					
					try { 
    					//계좌연동이 되지 않고 신청서 번호가 있는경우 하나은행 I/F통해서 시도한다.
    	        		if(apcNo.trim().length()>0 && accNo.length()<1){
    	        			String hanaAccNo = "";
    	        			String hanaProdCd = "";    	        		
	        				String paramStr = "apcNo="+apcNo+"&ciRcgnNo="+decCi;	
    	        			//String paramStr = "apcNo=20190930396450&ciRcgnNo=01094910463|8501011";
    	        			paramStr = paramStr+"&"+BASIC_PARAM;
        	        		HashMap getResultHana = getHanaAcctInfo(paramStr);            	        		
        	        		logger.debug("getResultHana::"+getResultHana);            	        		      	        		
        	        		
        	        		hanaAccNo = StringUtils.nullToStr((String)getResultHana.get("acctNo"));
        	        		hanaProdCd = StringUtils.nullToStr((String)getResultHana.get("prdCd"));   	        			 	        			
        	        		
        	        		if(hanaAccNo.length()>0){
        	        			logger.debug("하나은행 신청번호에 해당하는 계좌번호 존재");
        	        			logger.warn("======= CASE 36 : 파트너(O), 계좌번호(X), 신청서번호(O)-> 하나은행 API-2 통해서 파트너 계좌정보 UPDATE =======");
        	        			HashMap<String, Object> upParam = new HashMap<String, Object>();         	        			
        	        			upParam.put("ACC_NO", hanaAccNo);
	    						upParam.put("ACC_PROD_CD", hanaProdCd);//전달 상품   
        	        			upParam.put("ACC_USE_YN", "Y");
        	        			upParam.put("ACC_START_DT", curDate);
        	        			if(ctrtId.length()>0 && ctrtUseYn.equals("Y")){
        	        				upParam.put("COMBINE_USE_YN", "Y"); //결합정보 활성화
    	    						upParam.put("COMBINE_START_DT", curDate); //결합정보 활성화 시간	   
        	        			}        	        			    	        			
        	        			upParam.put("TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ"));
        	        			upParam.put("SYNC_ID", BATCH_ID);	        
        	        			upParam.put("REMARK", "PARTNER_RE_APC_RELMS P_UPDATE"+"["+curDate+"_"+BATCH_ID+"]"); 
        	        			int accUpCnt = generalDao.update("partner.partnerEvent.updateHanaPartner", upParam);
	        	        		if(accUpCnt >0){
	        	        			logger.debug("하나은행 계좌정보 업데이트 완료");  
	        	        			continue; // 정상적으로 업데이트한 경우는 SMS보내지 않고 다음 순번으로 이동
	        	        		}
        	        		}else{
        	        			logger.debug("하나은행 계좌번호 미존재"); 
        	        			continue;
        	        		}        	        		
    	        		}
					} catch (Exception e) {			    			
		    			e.printStackTrace();			
		    		}		        		        		
				}
			} 					
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("updateAccNoOnlyApcNo function finish");    
		}
		return result;
	}
	
	
	//결합 후 재신청서 중 계좌번호 있는것 처리
	public boolean updateAccReApc(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;		
		String BASIC_PARAM = "";
		
		try {		
			for(   Map<String, Object> codeMap : listReturn){	        	
	        	if(codeMap.get("COMMON_CD").equals("API02")) BASIC_PARAM = (String)codeMap.get("REF_CODE2");
	        }		
			logger.debug("BASIC_PARAM : " +BASIC_PARAM);
			
			
			String curDate = getCurrentTime();
			String remark  = "";
			int readCnt    = 0;
    		//결합이후 새로운 신청서번호만(계좌정보X)있는경우 업데이트 실시
    		List<Map<String, Object>> updateAccReApcList = new ArrayList<Map<String, Object>>();	
    		batchMap.put("APC_USE_YN","Y");
    		updateAccReApcList = generalDao.queryForList("partner.partnerEvent.getPartnerReApcInfo", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (updateAccReApcList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + updateAccReApcList.size());				
				
				for(Map<String, Object> map : updateAccReApcList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					readCnt++;					
					
					String accNo        = StringUtils.nullToStr((String)map.get("ACC_NO"));
					String apcNo        = StringUtils.nullToStr((String)map.get("APC_NO"));
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI")); 
					String accProdCd    = StringUtils.nullToStr((String)map.get("ACC_PROD_CD"));
					String apcUseYn     = StringUtils.nullToStr((String)map.get("APC_USE_YN"));	
					String ctrtId       = StringUtils.nullToStr((String)map.get("CTRT_ID"));
					String ctrtUseYn    = StringUtils.nullToStr((String)map.get("CTRT_USE_YN"));
										
					logger.debug("accNo::"+accNo); 
					logger.debug("apcNo::"+apcNo); 
					logger.debug("decCi::"+decCi); 
					logger.debug("accProdCd::"+accProdCd); 
					logger.debug("apcUseYn::"+apcUseYn); 
					logger.debug("ctrtUseYn::"+ctrtUseYn);					
					
					try { 
    					//계좌연동이 되지 않고 신청서 번호가 있는경우 하나은행 I/F통해서 먼저 시도한다.
    	        		if(apcUseYn.trim().equals("Y") && apcNo.trim().length()>0 && accNo.length()<1){
    	        			String hanaAccNo = "";    	        			
	        				String paramStr = "apcNo="+apcNo+"&ciRcgnNo="+decCi;	
    	        			//String paramStr = "apcNo=20190930396450&ciRcgnNo=01094910463|8501011";
    	        			paramStr = paramStr+"&"+BASIC_PARAM;
        	        		HashMap getResultHana = getHanaAcctInfo(paramStr);
        	        		logger.debug("getResultHana::"+getResultHana);
        	        		hanaAccNo = StringUtils.nullToStr((String)getResultHana.get("acctNo"));    	        			
    	        			
    	        			//String hanaAccNo = "12323";
        	        		if(hanaAccNo.length()>0){
        	        			logger.debug("하나은행 신청번호에 해당하는 계좌번호 존재");
        	        			logger.warn("======= CASE 35 : 재신청서 테이블에 존재하는 경우 -> 하나은행 API-2 통해서 UPDATE(파트너,재신청) 후 INSERT =======");
        	        			// 파트너 계좌정보 업데이트        	        			
        	        			HashMap<String, Object> upParam = new HashMap<String, Object>();   
		    			    	upParam.put("TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ"));	    						
	    						upParam.put("ACC_USE_YN", "N"); 
	    						upParam.put("ACC_END_DT", curDate);    						
	    						upParam.put("COMBINE_USE_YN", "N"); //결합정보 비활성화
	    						upParam.put("COMBINE_END_DT", curDate); //결합정보 비활성화 시간	
	    						upParam.put("SYNC_ID", BATCH_ID); //연동ID	    						
	    						upParam.put("REMARK", "PARTNER_RE_APC_REAPC P_UPDATE"+"["+curDate+"_"+BATCH_ID+"]");     
	    						int accUpCnt = updatePartnerInfo(upParam);  
	    						if(accUpCnt >0){
	        	        			logger.debug("하나은행 계좌정보 UPDATE 완료");	        	        			
	        	        		}
	    						
	    						// 재신청서 정보 업데이트
	    						upParam = new HashMap<String, Object>();  
	    						upParam.put("TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ"));	   
        	        			upParam.put("ACC_NO", hanaAccNo);
        	        			upParam.put("APC_NO", apcNo);
        	        			upParam.put("APC_USE_YN", "N");  
        	        			upParam.put("SYNC_ID", BATCH_ID); //연동ID  
        	        			upParam.put("REMARK", "PARTNER_RE_APC_REAPC A_UPDATE"+"["+curDate+"_"+BATCH_ID+"]");
        	        			int apcUpCnt = generalDao.update("partner.partnerEvent.updateHanaPartnerReApc", upParam);
	        	        		if(accUpCnt >0){
	        	        			logger.debug("재신청서 정보 업데이트 완료");	        	        			
	        	        		}
		    					
	    						logger.debug("map::"+map);
	    						logger.debug("upParam::"+upParam);
	    						
	    						// 파트너 계좌정보 인서트   
		    					map.put("PARTNER_CD", "01"); //;파트너 CD 	
		    					map.put("CI", map.get("DEC_CI")); //전달 계좌 	
		    					map.put("ACC_NO", hanaAccNo); //전달 계좌 	
		    					map.put("APC_NO", apcNo);
		    					map.put("ACC_PROD_CD", accProdCd);//상품  	    					   						
		    					map.put("ACC_USE_YN", "Y"); 
		    					map.put("ACC_START_DT", curDate); 
		    					if(ctrtUseYn.equals("Y")){//계약이 유효한 경우 결합완료
        							upParam.put("COMBINE_USE_YN", "Y"); //결합정보 활성화
    	    						upParam.put("COMBINE_START_DT", curDate); //결합정보 활성화 시간    	
        						}  
		    					map.put("SYNC_ID", BATCH_ID); //연동ID  
		    					map.put("P_TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ")); 
		    					map.put("REMARK", "PARTNER_RE_APC_REAPC P_INSERT"+"["+curDate+"_"+BATCH_ID+"]"); //비고  	    			    
		    					
	    						int partInsCnt = insertPartnerInfo(map);
	    						if(partInsCnt >0){
	        	        			logger.debug("하나은행 계좌정보 INSERT 완료");	        	        			
	        	        		}    						
        	        			
        	        		}else{
        	        			logger.debug("하나은행 계좌번호 미존재");  
        	        		}        	        		       	        		
    	        		}
					} catch (Exception e) {		    			
		    			e.printStackTrace();			
		    		}	        		       		
				}
			} 					
		}		
		catch(Exception e) {			    
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("updateAccReApc function finish");    
		}
		return result;
	}	
	
	//제휴요금제 해지로 인한 결합 해제
	public boolean termForNotCombine(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;		
		String BASIC_PARAM = "";
		
		try {		
			for(   Map<String, Object> codeMap : listReturn){	        	
	        	if(codeMap.get("COMMON_CD").equals("API02")) BASIC_PARAM = (String)codeMap.get("REF_CODE2");
	        }		
			logger.debug("BASIC_PARAM : " +BASIC_PARAM);
			
			
			String curDate = getCurrentTime();
			String remark  = "";
			int readCnt    = 0;
    		//결합된 파트너중 해지회선 리스트
    		List<Map<String, Object>> termForNotCombineList = new ArrayList<Map<String, Object>>();    		
    		termForNotCombineList = generalDao.queryForList("partner.partnerEvent.getTermForNotCombineList", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (termForNotCombineList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + termForNotCombineList.size());				
				
				for(Map<String, Object> map : termForNotCombineList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					readCnt++;				
					
					String accNo        = StringUtils.nullToStr((String)map.get("ACC_NO"));	
					String accUseYn     = StringUtils.nullToStr((String)map.get("ACC_USE_YN"));	
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI")); 
					String ctrtId       = StringUtils.nullToStr((String)map.get("CTRT_ID"));
					String svcTelno     = StringUtils.nullToStr((String)map.get("SVC_TEL_NO"));	
					String orderTp      = StringUtils.nullToStr((String)map.get("ORDER_TP"));
					String ctrtStat     = StringUtils.nullToStr((String)map.get("CTRT_STAT"));	
					String termDt       = StringUtils.nullToStr((String)map.get("TERM_DT"));	
					
					logger.debug("accNo::"+accNo); 
					logger.debug("ctrtId::"+ctrtId); 
					logger.debug("decCi::"+decCi); 
					logger.debug("svcTelno::"+svcTelno); 
					logger.debug("orderTp::"+orderTp); 
					logger.debug("ctrtStat::"+ctrtStat); 
					logger.debug("termDt::"+termDt); 
					
					try { 
						logger.debug("계약이 해지되어 해지처리 ");
						logger.warn("======= CASE 37 : 파트너(O), CTRT(O) 해지회선인 경우 -> 비활성화 처리 =======");
    			    	HashMap<String, Object> upParam = new HashMap<String, Object>();   
    			    	upParam.put("TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ"));     		    			    	
						upParam.put("CTRT_USE_YN", "N"); 
						upParam.put("CTRT_END_DT", curDate);						
						upParam.put("COMBINE_USE_YN", "N"); //결합정보 비활성화
						upParam.put("COMBINE_END_DT", curDate); //결합정보 비활성화 시간
						upParam.put("CTRT_TERM_DT", termDt); //해지일자
						upParam.put("REMARK", "PARTNER_TERM P_UPDATE"+"["+curDate+"_"+BATCH_ID+"]"); 
    					int upCnt = updatePartnerInfo(upParam);
					} catch (Exception e) {			    			
		    			e.printStackTrace();			
		    		}	        		       		
				}
			} 					
		}		
		catch(Exception e) {			    
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("termForNotCombine function finish");    
		}
		return result;
	}
	
	
	//해지복구로 인한 재결합
	public boolean canselTermReCombine(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;		
		String BASIC_PARAM = "";
		
		try {		
			for(   Map<String, Object> codeMap : listReturn){	        	
	        	if(codeMap.get("COMMON_CD").equals("API02")) BASIC_PARAM = (String)codeMap.get("REF_CODE2");
	        }		
			logger.debug("BASIC_PARAM : " +BASIC_PARAM);
			
			
			String curDate = getCurrentTime();
			String remark  = "";
			int readCnt    = 0;
    		//결합된 파트너중 해지회선 리스트
    		List<Map<String, Object>> termForNotCombineList = new ArrayList<Map<String, Object>>();    		
    		termForNotCombineList = generalDao.queryForList("partner.partnerEvent.getCanselTermReCombineList", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (termForNotCombineList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + termForNotCombineList.size());				
				
				for(Map<String, Object> map : termForNotCombineList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					readCnt++;				
					
					String accNo        = StringUtils.nullToStr((String)map.get("ACC_NO"));	
					String accUseYn     = StringUtils.nullToStr((String)map.get("ACC_USE_YN"));	
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI")); 
					String ctrtId       = StringUtils.nullToStr((String)map.get("CTRT_ID"));
					String svcTelno     = StringUtils.nullToStr((String)map.get("SVC_TEL_NO"));	
					String orderTp      = StringUtils.nullToStr((String)map.get("ORDER_TP"));
					String ctrtStat     = StringUtils.nullToStr((String)map.get("CTRT_STAT"));					
					
					logger.debug("accNo::"+accNo); 
					logger.debug("ctrtId::"+ctrtId); 
					logger.debug("decCi::"+decCi); 
					logger.debug("svcTelno::"+svcTelno); 
					logger.debug("orderTp::"+orderTp); 
					logger.debug("ctrtStat::"+ctrtStat); 					
					
					try { 
						logger.debug("계약이 해지복구되어 복구처리 ");
						logger.warn("======= CASE 38 : 파트너(O), CTRT(O) 해지복구인 경우 -> 활성화 처리 =======");
    			    	HashMap<String, Object> upParam = new HashMap<String, Object>();   
    			    	upParam.put("TCMCE_PARTNER_IF_INFO_SEQ", map.get("TCMCE_PARTNER_IF_INFO_SEQ"));     		    			    	
						upParam.put("CTRT_USE_YN", "Y");							
						if(accNo.length()>0){ //ACC_NO(O)
							upParam.put("ACC_USE_YN", "Y");
							if(ctrtId.length()>0){//ACC_NO(O), CTRT_ID(O)
								upParam.put("COMBINE_USE_YN", "Y"); 
							}
						}
						upParam.put("TYPE", "CANSEL_TERM"); //종료시간 취소	
						upParam.put("CTRT_TERM_DT", "99991231"); //해지일자 초기화
						logger.debug("upParam"+upParam); 
						upParam.put("REMARK", "PARTNER_CANSEL_TERM P_UPDATE"+"["+curDate+"_"+BATCH_ID+"]"); 
    					int upCnt = updatePartnerInfo(upParam);
					} catch (Exception e) {		    			
		    			e.printStackTrace();			
		    		}	        		       		
				}
			} 					
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("canselTermReCombine function finish");    
		}
		return result;
	}
	
	
	/**
	 * 하나은행  계좌거리내역정보 조회 대상 가져옴
	 * jsonStr : json형식의 FULL String
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getHanaAcctTgtList(HashMap mapParam) throws Exception
	{		
		HashMap tmpRresultHm = new HashMap();
		HashMap resultHm = new HashMap();
		HashMap result01Hm = new HashMap();
		HashMap result02Hm = new HashMap();
		List<Map<String, Object>> hanaAcctTgtList = new ArrayList<Map<String, Object>>();
		
		logger.debug("mapParam::"+mapParam);
		logger.debug("syncId::::" + syncId);
		logger.debug("acctNoUrl:" + acctNoUrl);
		logger.debug("acctInUrl:" + acctInUrl);
		
		try{			
			
			//계좌거리내역정보 조회 대상 리스트    		
    		hanaAcctTgtList = generalDao.queryForList("partner.partnerEvent.getHanaAcctTgtList", mapParam);
    		
		} catch (Exception e) {			   
			e.printStackTrace();			
		}	
		finally{
			logger.debug("updateAccReApc function finish");    
		}		
		return hanaAcctTgtList;		
	}		
	
		
	
	/**
	 * 하나은행 CI식별번호, 계좌번호를 인자로 계좌거리내역정보 가져옴
	 * jsonStr : json형식의 FULL String
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * @return
	 * @throws Exception
	 */
	public HashMap getHanaAcctInquiryInfo(String paramStr) throws Exception
	{		
		HashMap mapParam = new HashMap();
		HashMap tmpRresultHm = new HashMap();
		HashMap resultHm = new HashMap();
		/*
		logger.debug("paramStr::"+paramStr);
		logger.debug("mapParam::"+mapParam);
		logger.debug("syncId::::" + syncId);
		logger.debug("acctNoUrl:" + acctNoUrl);
		logger.debug("acctInUrl:" + acctInUrl);
		*/
		try{	
			logger.debug("FINAL_PARAM::::" + paramStr);
    		String encStr = ""; //getEncHanaStr(paramStr);
    		String encodeURI = URLEncoder.encode(encStr, "utf-8");
    		String parameters = "encData="+encodeURI;    			

    		logger.debug("FINAL_ENC::::" + parameters);
    		logger.debug("FINAL_acctInUrl::::" + acctInUrl);

    		String responseResult = httpConnResult(acctInUrl, parameters); 
    		String resultMsg = responseResult;
    		
    		mapParam.put("encData","Y");
    		tmpRresultHm = getJsonParser(responseResult, mapParam); 
    		logger.debug("resultHm::"+tmpRresultHm);
    		String encData = (String)tmpRresultHm.get("encData");
    		//복호화
    		//responseResult = getDecHanaStr(encData);
    		//디코딩
    		String decData = URLDecoder.decode(responseResult, "UTF-8");
    		
    		mapParam = new HashMap();
    		mapParam.put("prsBal", "Y");              // 현재잔액
    		mapParam.put("newDt", "Y");               // 개설일
    		mapParam.put("prdNm", "Y");               // 상품명
    		mapParam.put("acctNo", "Y");              // 계좌번호
    		mapParam.put("atfAcctNo", "Y");           // 연동출금계좌(요구불계좌번호)
    		mapParam.put("prdCd", "Y");               // 상품코드1)
    		mapParam.put("actDvCd", "Y");             // 활동구분코드2)
    		mapParam.put("lstTrscDt", "Y");           // 최종거래일
    		mapParam.put("apclIrrt", "Y");            // 적용이율
    		mapParam.put("pbldIrrt", "Y");            // 고시이율
    		mapParam.put("dfrmTpRsvgPrimIrrt", "Y");  // 우대이율
    		mapParam.put("expiDt", "Y");              // 만기일자
    		mapParam.put("mmInslmPayTcnt", "Y");      // 월부금납부횟수
    		mapParam.put("atfAmt", "Y");              // 자동이체금액
    		mapParam.put("atfDt", "Y");               // 자동이체일자
    		//mapParam.put("mmBtwRcvCamt", "Y");        // 월간입금누계액
    		mapParam.put("trmnDt", "Y");              // 해지일자
    		mapParam.put("errorCode", "Y");           // 오류코드
    		mapParam.put("errorMessage", "Y");        // 오류메시지

    		//거래내역 기본정보  
    		//resultHm = getJsonParser(responseResult, mapParam);
    		resultHm = getJsonParser(decData, mapParam);
    		
    		//한글처리 확인 
    		//resultHm.put("RESULT_MSG", resultMsg);
    		//resultHm.put("ENC_DATA", encData);
    		//resultHm.put("DEC_DATA", decData);
    		
    		//거래내역 상세  - 입금누적금액 가져오기 
    		mapParam.clear();
    		mapParam.put("trscId", "Y");              // 거래건 ID
    		mapParam.put("trscDt", "Y");              // 거래일자
    		mapParam.put("trscTm", "Y");              // 거래시간
    		mapParam.put("trscAmt", "Y");             // 거래금액
    		mapParam.put("trscKindNm", "Y");          // 거래종류명
    		mapParam.put("trscAfBal", "Y");           // 거래후잔액
    		mapParam.put("summPsbkRmrk", "Y");        // 요약통장적요
    		mapParam.put("balFlctDvCd", "Y");         // 잔액변동구분코드  
    		
    		BigDecimal mmBtwRcvCamt = new BigDecimal("0");
    		mmBtwRcvCamt = getMmBtwRcvCamt(decData, "dealHistoryList", mapParam) ;
    		resultHm.put("mmBtwRcvCamt", mmBtwRcvCamt);
    		
    		
			logger.debug("resultHm:FINAL:"+resultHm);

        } catch (ParseException e) {			
			e.printStackTrace();
			
		} catch (Exception e) {			   
			e.printStackTrace();			
		}		
		return resultHm;		
	}			
	
	
	/**
	 * 입금누적금액 구하기
	 * jsonStr : json형식의 FULL String
	 * jsonArrNm : ROOF있는 데이터 컬럼명
	 * mapParam : FULL String에서 파싱하여 가져올 컬럼(값은 "Y"로 선택하면 Return시에는 parsing한 값으로 변경되어 Return)
	 * 입금누적금액 : 잔액변동구분 = 1이면서 요약통장적요 <> "예금이자"
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getMmBtwRcvCamt(String jsonStr, String jsonArrNm, HashMap mapParam) throws Exception
	{			
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		BigDecimal mmBtwRcvCamt = new BigDecimal("0");
		
		logger.debug("jsonStr::"+jsonStr);
		logger.debug("mapParam::"+mapParam);
		try{
			JSONObject resultJsonObject = new JSONObject();		        
	        JSONParser parser = new JSONParser();			
	        
	        Object obj = new Object();        
	        obj = parser.parse(jsonStr);
	        JSONObject jsonObject = (JSONObject) obj;	

			JSONArray arrStr = (JSONArray) jsonObject.get(jsonArrNm);
			if(arrStr != null){
				Iterator<JSONObject> iterator = arrStr.iterator();
				int i = 1;			
				while (iterator.hasNext()) {
					HashMap<String, Object> arrDetailMap = new HashMap<String, Object>();
					//logger.debug("["+i+"]");					
					JSONObject jsonObjectDialogue = (JSONObject)iterator.next();					
					Set key = mapParam.keySet();
			        String writeOneLine = "";
			        int aaa = 0;
			        for (Iterator iteratorArr = key.iterator(); iteratorArr.hasNext();) {
			        	String keyName = String.valueOf(iteratorArr.next());             // 속성명		   			    
		   			    arrDetailMap.put(keyName, jsonObjectDialogue.get(keyName));		   			    		    
			        }
			        resultList.add(arrDetailMap);
					i++;
				}
			}        

			logger.debug("resultList:FINAL:"+resultList);

			int i = 1;
			

			for(Map<String, Object> resultMap : resultList){
				BigDecimal trscAmt= new BigDecimal(String.valueOf(resultMap.get("trscAmt")));
				BigDecimal trscAfBal = new BigDecimal(String.valueOf(resultMap.get("trscAfBal")));
				String trscKindNm = String.valueOf(resultMap.get("trscKindNm"));
				String summPsbkRmrk = String.valueOf(resultMap.get("summPsbkRmrk"));

				logger.debug("seq_no : " +i); 
				logger.debug("trscAmt : " +trscAmt); 
				logger.debug("trscAfBal : " +trscAfBal); 
				logger.debug("trscKindNm : " +trscKindNm);
				logger.debug("summPsbkRmrk : " +summPsbkRmrk);
				
				if ( String.valueOf(resultMap.get("balFlctDvCd")).equals("1") && //잔액변동구분 - 1:잔액증가 
					 !String.valueOf(resultMap.get("summPsbkRmrk")).equals("예금이자")) {  //요약통장적요 <> 예금이자
					
					mmBtwRcvCamt = mmBtwRcvCamt.add(trscAmt);
					logger.debug("mmBtwRcvCamt : " +mmBtwRcvCamt);
				}				
				i++ ;
			}	        

        } catch (ParseException e) {			
			e.printStackTrace();
			
		} catch (Exception e) {			   
			e.printStackTrace();			
		}	
		return mmBtwRcvCamt;		
	}    	
	
	
	//제휴해지 가입자 LMS 전송(익월 10시발신)
	public boolean termSendLMS(HashMap batchMap) throws Exception
	{
		boolean result = true;
		String remark  = "";
		
		try {		
    		//SMS 전송대상 조회
    		List<Map<String, Object>> smsTermSendList = new ArrayList<Map<String, Object>>();
    		smsTermSendList = generalDao.queryForList("partner.partnerEvent.getSmsTermSendList", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (smsTermSendList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + smsTermSendList.size());						
				
				for(Map<String, Object> map : smsTermSendList){    					
					map.put("SYNC_ID", BATCH_ID);	    					
					
        			//LMS 전송
        			map.put("SEND_DATE", "WORKING_TIME");
        			String templetId =  "SMS0040904";
        			        			
					int successYn = smsKTsend(templetId,map ,batchMap, false);		
					if(successYn != -1) result = false;
	        	  	        		
				}
			}				
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("termSendLMS function finish");    
		}
		return result;
	}
	
	
	//제휴적금만기 가입자 LMS 전송
	public boolean expSendLMS(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;

		try {		
			String curDate = getCurrentTime();
			String remark  = "";
    		
    		String currFrstDay = curDate.substring(0, 6) + "01"; //당월1일
    		String currDay = curDate.substring(0, 8); //당일
    		String afcrInqYn     = "Y";
    		
    		
    		//SMS 전송대상 조회
    		List<Map<String, Object>> smsReSendList = new ArrayList<Map<String, Object>>();	
    		smsReSendList = generalDao.queryForList("partner.partnerEvent.getSmsExpSendList", batchMap);              
            
            String sendTime = getLmsSendTime();
			if (smsReSendList.size() <=0) {
				remark = "======= 작업할 대상이 존재하지 않습니다. =======";
				logger.debug(remark);
			}else {				
				logger.debug("작업대상 건수 :: " + smsReSendList.size());						
				
				for(Map<String, Object> map : smsReSendList){
					String elpsDateYn	=	"Y";
					map.put("SYNC_ID", BATCH_ID);    					
					
					String decCi        = StringUtils.nullToStr((String)map.get("DEC_CI"));
					String acctNo       = StringUtils.nullToStr((String)map.get("ACC_NO"));
					String expDt        = StringUtils.nullToStr((String)map.get("EXPIRY_DT"));
					String expApiChk    = StringUtils.nullToStr((String)map.get("TERM_API_CHK")); /*해지일자가 없으면(Y) API03 재확인*/
					String accStatus    = StringUtils.nullToStr((String)map.get("ACC_STATUS")); 

					logger.debug("expApiChk :: " + expApiChk);
					HashMap<String, Object> rParam01 = new HashMap<String, Object>();
					if("Y".equals(expApiChk)){
						String paramStr = "ciRcgnNo="+decCi+
								"&acctNo="+acctNo+
								"&inqStrDt="+currFrstDay+
								"&inqEndDt="+currDay+
								"&afcrInqYn="+afcrInqYn ;
			
						logger.debug("paramStr :: " + paramStr);
						
						HashMap resultHm = getHanaAcctInquiryInfo(paramStr);
						logger.debug("getHanaAcctInquiryInfo 결과-----> resultHm:" + resultHm);	
						String hanaAcctNo = StringUtils.nullToStr((String)resultHm.get("acctNo"));
												
						if(hanaAcctNo.length() > 0){ //연동결과 정보가 존재하는 경우
							rParam01.put("TERM_DT", StringUtils.nullToStr((String)resultHm.get("trmnDt"),expDt));  //해지일자						
							rParam01.put("EXPIRY_DT", StringUtils.nullToStr((String)resultHm.get("expiDt"),expDt));  //만기일자
							//rParam01.put("TERM_DT", "20191101");  //해지일자						
							//rParam01.put("EXPIRY_DT",expDt);  //만기일자							
							logger.debug("rParam01 :  "+ rParam01);
														
						}else{
							logger.debug("은행계좌정보 확인불가.... "+acctNo);
							continue;
						}
												
					}else{
						rParam01.put("TERM_DT", 	map.get("TERM_DT"));  //해지일자						
						rParam01.put("EXPIRY_DT", 	map.get("EXPIRY_DT"));  //만기일자						
					}
					
					rParam01.put("PARTNER_CD", (String)map.get("PARTNER_CD"));  
					elpsDateYn  =  (String)generalDao.queryForObject("partner.partnerEvent.getElpsYn", rParam01);
					
					//해지일자를 다시 조회해서 null 또는 해지일자 +기준일 > 만기일자이면 정상 만기로 sms대상,만기여부 'Y' 로 update
					if("Y".equals(elpsDateYn)){
						logger.debug("map :: " + map);

						rParam01.put("ACC_STATUS", "Y");  
						rParam01.put("SYNC_ID", BATCH_ID);
						rParam01.put("TCMCE_PARTNER_IF_INFO_SEQ",map.get("TCMCE_PARTNER_IF_INFO_SEQ"));
						if(!"Y".equals(accStatus)){
							generalDao.update("partner.partnerEvent.updateHanaPartner", rParam01);	
						}
												
						logger.debug("만기후 LMS발신일 입니다.");
	        			//LMS 전송
	        			map.put("SEND_DATE", "WORKING_TIME");
	        			String templetId = "SMS0040905";
	        			
						int successYn = smsKTsend(templetId,map ,batchMap, false);		
						if(successYn != -1) result = false;	
					}else{
						logger.debug("중도해지계좌입니다. "+acctNo);
					}					
				}
			} 					
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("expSendLMS function finish");    
		}
		return result;
	}
	
	// 계좌 불일치 발생으로 캐시백 지급 오류 발생시 고객에게 LMS발송
	public boolean cashSapErrSendLMS(HashMap batchMap, List<Map<String, Object>> listReturn) throws Exception
	{
		boolean result = true;

		try {		
			String curDate = getCurrentTime();
			String remark  = "";
    		
    		String currFrstDay = curDate.substring(0, 6) + "01"; //당월1일
    		String currDay = curDate.substring(0, 8); //당일
    		String afcrInqYn     = "Y";
    		
    		
    		//SMS 전송대상 조회
    		batchMap.put("CURR_FRST_DT",currFrstDay);
    		batchMap.put("WORK_TYPE","03"); // 03 : 지급
    		batchMap.put("PROC_CD","03");   // 03 : 취소/반려 
    	    
    		List<Map<String, Object>> smsSendList = new ArrayList<Map<String, Object>>();	
    		smsSendList = generalDao.queryForList("partner.partnerEvent.getCashSapErrSmsSendList", batchMap);              
           

    		String sendTime = getLmsSendTime();
    		if (smsSendList.size() <=0) {
    			remark = "======= 작업할 대상이 존재하지 않습니다. =======";
    			logger.debug(remark);
    		}else {				
    			logger.debug("작업대상 건수 :: " + smsSendList.size());						

    			for(Map<String, Object> map : smsSendList){    					
    				map.put("SYNC_ID", BATCH_ID);	    					

    				//LMS 전송
    				map.put("SEND_DATE", "WORKING_TIME");
    				String templetId =  "SMS0040906";

    				int successYn = smsKTsend(templetId,map ,batchMap, false);		
					//sms전송결과 처리
					map.put("SMS_SEND_YN", "Y") ;
					generalDao.update("partner.partnerEvent.updateAcctInfoSmsSend", map);
					
    				if(successYn != -1) result = false;
    			}
    		}				
		}		
		catch(Exception e) {			   
			/* [공통] 배치실행중 오류가 발생한 경우 에러로그를 저장한다.*/	
			result = false;
			e.printStackTrace();			
			String strExceptionText;
			if(e.toString().length()>3000){strExceptionText=e.toString().substring(0,3000);}else{strExceptionText=e.toString();}
			batchMap.put("ERR_DESC",  ("Exception :"+strExceptionText));  //에러 사유  3000 자리 이하
			insertBatchErrLog(batchMap);
			e.printStackTrace();			
		}
		finally{
			logger.debug("cashSapErrSendLMS function finish");    
		}
		return result;
	}	
	
	public HashMap<String, Object> getBatchDate() throws ServiceException {
		// TODO Auto-generated method stub
		return (HashMap<String, Object>) generalDao.queryForObject("partner.partnerEvent.getBatchDate");
	}	
	
	public String getOprtUnitKey(HashMap mapType) {
		return (String) generalDao.queryForObject("partner.partnerEvent.getOprtUnitKey",mapType);
	}
	
	public List<Map<String, Object>>  getPartnerSaveAcct(HashMap mapType) {
		return (List<Map<String, Object>> ) generalDao.queryForList("partner.partnerEvent.selectPartnerSaveAcct", mapType);
	}	
	
	public List<Map<String, Object>>  getHanaPartnerIfPay(HashMap mapType) {
		return (List<Map<String, Object>> ) generalDao.queryForList("partner.partnerEvent.selectHanaPartnerIfPay", mapType);
	}
	
	public List<Map<String, Object>>  getPartnerSaveAcctErp(HashMap mapType) {
		return (List<Map<String, Object>> ) generalDao.queryForList("partner.partnerEvent.selectPartnerSaveAcctErp", mapType);
	}
	
	public List<Map<String, Object>>  getPartnerSaveAcctUPDATE(HashMap mapType) {
		return (List<Map<String, Object>> ) generalDao.queryForList("partner.partnerEvent.selectPartnerSaveAcctUPDATE", mapType);
	}		
	
	public List<Map<String, Object>>  getHanaPartnerIfCb(HashMap mapType) {
		return (List<Map<String, Object>> ) generalDao.queryForList("partner.partnerEvent.selectHanaPartnerIfCb", mapType);
	}	
	
	public Integer getTbDataLddngSttusDtlsDuplCnt(HashMap mapType) {
		// TODO Auto-generated method stub
		return (Integer) generalDao.queryForObject("partner.partnerEvent.selectTbDataLddngSttusDtlsDuplCnt", mapType);
	}	
	
	public List<Map<String, Object>> getTbDataLddngSttusDtls(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectTbDataLddngSttusDtlsList", mapType);
	}		
	
	public List<Map<String, Object>> getDchgInfo(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectDchgInfo", mapType);
	}

	public List<Map<String, Object>> getDchgUpdateInfo(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectDchgUpdateInfo", mapType);
	}		

	public List<Map<String, Object>> getIfSlipLnkgSap(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectIfSlipLnkgSap", mapType);
	}	
	
	public List<Map<String, Object>> getHldRfndInfo(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectHldRfndInfo", mapType);
	}	
	
	public List<Map<String, Object>> getRsvPayInfo(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.selectRsvPayInfo", mapType);
	}	
	
	public List<Map<String, Object>> getPartnerTcmotAcctfndSap(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.getPartnerTcmotAcctfndSap", mapType);
	}		
	
	public String getOprtUnitKeyForAcctLinkSap(HashMap mapType) {
		// TODO Auto-generated method stub
		return (String) generalDao.queryForObject("partner.partnerEvent.getOprtUnitKeyForAcctLinkSap",mapType);
	}
	
	public List<Map<String, Object>> selectHanaAcctTgtList(HashMap mapType) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) generalDao.queryForList("partner.partnerEvent.getHanaAcctTgtList", mapType);
	}		
	
}