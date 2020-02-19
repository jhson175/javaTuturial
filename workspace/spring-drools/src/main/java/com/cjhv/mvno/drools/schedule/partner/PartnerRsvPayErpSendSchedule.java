package com.cjhv.mvno.drools.schedule.partner;

import java.util.HashMap;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import util.KnowledgeSessionHelper;

/* 
 * 제휴사(하나은행) 지급적립금 생성/ERP연동
 * 작성일 : 2019.11.07
 * */

public class PartnerRsvPayErpSendSchedule extends PartnerCommon implements
		Tasklet, InitializingBean {

	static KieContainer kieContainer;
	StatelessKieSession sessionStateless = null;
	KieSession sessionStatefull = null;

	public PartnerRsvPayErpSendSchedule() {
		logger.debug("배치시작[Date]: " + System.currentTimeMillis()
				+ ", PartnerRsvPayErpSchedule Started..");
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(
				kieContainer, "ksession-rules02");

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
		partnerCommon.setPROGRAM_ID("CJMFI047");
		partnerCommon.setBUS_DIV("A001");
		partnerCommon.setBATCH_ID(BATCH_ID);

		HashMap<String, Object> batchMap = new HashMap<String, Object>();
		sessionStatefull.setGlobal("batchMapG", batchMap);

		sessionStatefull.getAgenda().getAgendaGroup("RsvPay").setFocus();
		sessionStatefull.fireAllRules();

		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug(" ");

	}

}
