package com.cjhv.mvno.drools.provision.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cjhv.mvno.framework.exception.ServiceException;

public class BatchLogServiceImpl implements BatchLogService {

	private final transient Log logger = LogFactory.getLog(getClass());
	private final char NL = '\n';
    private SimpleJdbcTemplate jdbcTemplate;
    
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
    /**
	 * 배치로그 SEQ 조회
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getBatchLogSeq() throws ServiceException {
		
		String str = "SELECT FCMCO_SEQ_BATCH_LOG_SEQ() AS BATCH_LOG_SEQ FROM DUAL\n";
		return jdbcTemplate.queryForMap(str);
	}

    /**
	 * 배치시작정보저장
	 * @return
	 * @throws Exception
	 */
	public void setStartBatchLog(Map<String, Object> m) throws ServiceException {
    	StringBuffer strSql = new StringBuffer("");
    	strSql.append("INSERT INTO TCMCO_BATCH_LOG_MAST(      ").append(NL);
    	strSql.append("  BATCH_LOG_SEQ                        ").append(NL);
    	strSql.append("  ,BATCH_ID                            ").append(NL);
    	strSql.append("  ,BATCH_START_DT                      ").append(NL);
		strSql.append("  ,READ_CNT                            ").append(NL);
		strSql.append("  ,PROC_CNT                            ").append(NL);
		strSql.append("  ,ERR_CNT                             ").append(NL);
		strSql.append("  ,RESULT                              ").append(NL);
		strSql.append("  ,REMARK                              ").append(NL);
		strSql.append("  ,REGR_ID                             ").append(NL);
		strSql.append("  ,REG_DATE                            ").append(NL);
		strSql.append("  ,CHGR_ID                             ").append(NL);
		strSql.append("  ,CHG_DATE                            ").append(NL);
		strSql.append(") VALUES (                             ").append(NL);
		strSql.append("  :BATCH_LOG_SEQ                       ").append(NL);
		strSql.append("  ,:BATCH_ID                           ").append(NL);
		strSql.append("  ,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ").append(NL);
		strSql.append("  ,0                                   ").append(NL);
		strSql.append("  ,0                                   ").append(NL);
		strSql.append("  ,0                                   ").append(NL);
		strSql.append("  ,'01'                                ").append(NL);
		strSql.append("  ,''                                  ").append(NL);
		strSql.append("  ,:REG_ID                             ").append(NL);
		strSql.append("  ,SYSDATE                             ").append(NL);
		strSql.append("  ,:REG_ID                             ").append(NL);
		strSql.append("  ,SYSDATE                             ").append(NL);
		strSql.append(")                                      ").append(NL);
		
		//logger.debug("BatchLogServiceImpl :: setStartBatchLog()");
		//logger.debug("setStartBatchLog param:"+m+"\n"+strSql.toString());
		
		jdbcTemplate.update(strSql.toString(), m);
	}

	/**
	 * 배치종료정보저장
	 * @param
	 * @throws Exception
	 */    
	public void setEndBatchLog(Map<String, Object> m) throws ServiceException {
		StringBuffer strSql = new StringBuffer();
		strSql.append("UPDATE TCMCO_BATCH_LOG_MAST                                ").append(NL);
		strSql.append("   SET BATCH_END_DT = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ").append(NL);
		strSql.append("       ,RESULT      = :RESULT                              ").append(NL);
//		strSql.append("       ,REMARK      = :REMARK                              ").append(NL);
		strSql.append("       ,CHGR_ID     = :REG_ID                              ").append(NL);
		strSql.append("       ,CHG_DATE    = SYSDATE                              ").append(NL);
		strSql.append(" WHERE BATCH_LOG_SEQ = :BATCH_LOG_SEQ                      ").append(NL);

		//logger.debug("BatchLogServiceImpl :: setEndBatchLog()");
		//logger.debug("setEndBatchLog param:"+m+"\n"+strSql.toString());
		
		jdbcTemplate.update(strSql.toString(), m);
	}

	/**
	 * 배치오류정보저장
	 * @param
	 * @throws Exception
	 */
	public void setErrorBatchLog(Map<String, Object> m) throws ServiceException {
		StringBuffer strSql = new StringBuffer("");
		strSql.append("INSERT INTO TCMCO_BATCH_LOG_ERR_INFO (      ").append(NL);
		strSql.append("   BATCH_LOG_SEQ                            ").append(NL);
		strSql.append("   ,BATCH_ERR_SEQ                           ").append(NL);
		strSql.append("   ,CTRT_ID                                 ").append(NL);
		strSql.append("   ,ERR_DESC                                ").append(NL);
		strSql.append("   ,REGR_ID                                 ").append(NL);
		strSql.append("   ,REG_DATE                                ").append(NL);
		strSql.append("   ,CHGR_ID                                 ").append(NL);
		strSql.append("   ,CHG_DATE                                ").append(NL);
		strSql.append(") VALUES (                                  ").append(NL);
		strSql.append("   :BATCH_LOG_SEQ                           ").append(NL);
		strSql.append("   ,FCMCO_SEQ_BATCH_ERR_SEQ()               ").append(NL);
		strSql.append("   ,:CTRT_ID                                ").append(NL);
		strSql.append("   ,:ERR_DESC                               ").append(NL);
		strSql.append("   ,:REG_ID                                 ").append(NL);
		strSql.append("   ,SYSDATE                                 ").append(NL);
		strSql.append("   ,:REG_ID                                 ").append(NL);
		strSql.append("   ,SYSDATE                                 ").append(NL);
		strSql.append(")                                           ").append(NL);
		
		setBatchCount((String)m.get("BATCH_LOG_SEQ"), "ERR_CNT");
		//logger.debug("배치오류정보저장:\n"+strSql.toString());
		jdbcTemplate.update(strSql.toString(), m);		
	}

	/**
	 * 배치 카운트 저장
	 * 읽은건수,처리건수,에러건수 
	 * @param
	 * @throws Exception
	 */	
	public void setBatchCount(String batchLogSeq, String str) throws ServiceException {
		StringBuffer strSql = new StringBuffer("");
		strSql.append("UPDATE TCMCO_BATCH_LOG_MAST           ").append(NL);
		strSql.append("   SET "+ str +" = "+ str + "+1       ").append(NL);
		strSql.append(" WHERE BATCH_LOG_SEQ = :batchLogSeq   ").append(NL);

		//logger.debug("배치카운트정보저장:\n"+strSql.toString());
		jdbcTemplate.update(strSql.toString(), batchLogSeq);		
	}

	/**
	 * 배치 카운트 저장
	 * 읽은건수,처리건수,에러건수 
	 * @param
	 * @throws Exception
	 */	
	public void setBatchCount(String batchLogSeq, String str, int cnt) throws ServiceException {
		StringBuffer strSql = new StringBuffer("");
		strSql.append("UPDATE TCMCO_BATCH_LOG_MAST           ").append(NL);
		strSql.append("   SET "+ str +" = TO_NUMBER( :cnt )  ").append(NL);
		strSql.append(" WHERE BATCH_LOG_SEQ = :batchLogSeq   ").append(NL);

		//logger.debug("배치카운트정보저장:\n"+strSql.toString());
		jdbcTemplate.update(strSql.toString(), cnt, batchLogSeq);		
	}

	/**
	 * 배치종료정보저장
	 * @param
	 * @throws Exception
	 */    
	public void setEndBatchAllLog(Map<String, Object> m) throws ServiceException {
		StringBuffer strSql = new StringBuffer();
		strSql.append("UPDATE TCMCO_BATCH_LOG_MAST                                ").append(NL);
		strSql.append("   SET BATCH_END_DT = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ").append(NL);
		strSql.append("       ,RESULT      = :RESULT                              ").append(NL);
		strSql.append("       ,READ_CNT    = :READ_CNT                              ").append(NL);
		strSql.append("       ,PROC_CNT    = :PROC_CNT                              ").append(NL);
		strSql.append("       ,ERR_CNT     = :ERR_CNT                              ").append(NL);
		strSql.append("       ,CHGR_ID     = :REG_ID                              ").append(NL);
		strSql.append("       ,CHG_DATE    = SYSDATE                              ").append(NL);
		strSql.append(" WHERE BATCH_LOG_SEQ = :BATCH_LOG_SEQ                      ").append(NL);

		logger.debug("BatchLogServiceImpl :: setEndBatchLog()");
		//logger.debug("setEndBatchLog param:"+m+"\n"+strSql.toString());
		
		jdbcTemplate.update(strSql.toString(), m);
	}
	
	/**
	 * 배치종료정보저장
	 * @param
	 * @throws Exception
	 */    
	public void setEndBatchRemarkLog(Map<String, Object> m) throws ServiceException {
		StringBuffer strSql = new StringBuffer();
		strSql.append("UPDATE TCMCO_BATCH_LOG_MAST                                ").append(NL);
		strSql.append("   SET BATCH_END_DT = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ").append(NL);
		strSql.append("       ,RESULT      = :RESULT                              ").append(NL);
		strSql.append("       ,REMARK      = :REMARK                              ").append(NL);
		strSql.append("       ,CHGR_ID     = :REG_ID                              ").append(NL);
		strSql.append("       ,CHG_DATE    = SYSDATE                              ").append(NL);
		strSql.append(" WHERE BATCH_LOG_SEQ = :BATCH_LOG_SEQ                      ").append(NL);

		//logger.debug("BatchLogServiceImpl :: setEndBatchLog()");
		//logger.debug("setEndBatchLog param:"+m+"\n"+strSql.toString());
		
		jdbcTemplate.update(strSql.toString(), m);
	}
}
