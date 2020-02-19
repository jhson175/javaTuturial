package com.cjhv.mvno.drools.schedule.partner;

import java.util.HashMap;

/* 
 * 제휴사(하나은행) 캐쉬백 적립 ERP 자료생성
 * 작성일 : 2019.10.07
 * */
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import util.KnowledgeSessionHelper;

import com.cjhv.mvno.drools.provision.service.BatchLogService;

public class PartnerSaveAcctSchedule extends PartnerCommon implements Tasklet, InitializingBean {

    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;
    
	private BatchLogService batchLogService;
	
	
	public BatchLogService getBatchLogService() {
		return batchLogService;
	}

	public void setBatchLogService(BatchLogService batchLogService) {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
		this.batchLogService = batchLogService;
	}    
    public PartnerSaveAcctSchedule()
	{
		logger.debug("배치시작[Date]: " +System.currentTimeMillis()+ ", PartnerSaveAcctSchedule Started.." );
	}
    
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-rules01");
		
		String remark = "";
		int cnt = 0;
		int readCnt = 0;
		int procCnt = 0;
		int procCnt01 = 0;
		int errCnt = 0;
		
	/*	HashMap<String, Object> batchMap = super.getBatchInfo();    // 배치 정보 생성
		HashMap batchDate =  (HashMap)generalDao.queryForObject("partner.partnerEvent.getBatchDate", batchMap);
		
		 [개별]배치 파라미터 확인
		logger.debug("paramMap :: " + batchMap);
        int procInt = 0;//처리건수
        int errInt  = 0;//오류건수
        
	*/	
		PartnerCommon partnerCommon = new PartnerCommon();
		if(BTCH_TP_CD.equals("MANUAL")){
			partnerCommon.setBTCH_TP_CD("MANUAL");
			partnerCommon.setGeneralDao(generalDao);
			partnerCommon.setMANUAL_DATE(MANUAL_DATE);
			partnerCommon.setBTCH_TP_CD(BTCH_TP_CD);
			sessionStatefull.insert(partnerCommon);
		} else {
			partnerCommon.setBTCH_TP_CD("AUTOEXE_DATE");
			partnerCommon.setGeneralDao(generalDao);
			partnerCommon.setBTCH_TP_CD(BTCH_TP_CD);
			sessionStatefull.insert(partnerCommon);
		}
		
		partnerCommon.setSystemId("200");
		partnerCommon.setPROGRAM_ID("CJMFI054");
		partnerCommon.setBUS_DIV("A001");
		partnerCommon.setBATCH_ID(BATCH_ID);
		
		HashMap<String, Object> batchMap = new HashMap<String, Object>();
		sessionStatefull.setGlobal("batchMapG", batchMap);
        sessionStatefull.fireAllRules();

		return RepeatStatus.FINISHED;	
	}


	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug(" ");	

	}
}
