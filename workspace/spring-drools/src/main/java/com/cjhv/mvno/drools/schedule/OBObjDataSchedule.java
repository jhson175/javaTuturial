/*

*/

package com.cjhv.mvno.drools.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import util.KnowledgeSessionHelper;

import com.cjhv.mvno.drools.ObInfo;
import com.sample.DroolsTest.Message;

public class OBObjDataSchedule implements  Tasklet, InitializingBean {

    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;
    private EntryPoint tickStream;
    
	private final transient Log logger = LogFactory.getLog(getClass());  
	
	/**
     * 생성자.
     */
	public OBObjDataSchedule()
	{
		logger.debug("[Date]: " +System.currentTimeMillis()+ ", CtrtApprObjDataSchedule Started.." );
		//kieContainer = KnowledgeSessionHelper.createRuleBase();
	}
	
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
    	 
		logger.debug ("====================================	");
		
		KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
		config.setOption(EventProcessingOption.STREAM);
		
		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-rules");
		this.tickStream = this.sessionStatefull.getEntryPoint( "StockTick stream" );
		
		Map<String, String> param = new HashMap<String,String>();
		param.put("ORDER_NO", "OB00947730");
		
		/*
        for(Object obj : obInfoList ) {
        	Map<String,String> getMap = (Map<String,String>)obj;
        	logger.debug(getMap.get("ORDER_NO"));
        	
    	    ObInfo a = new ObInfo();
    	    a.setObNo(getMap.get("ORDER_NO"));
    	    sessionStatefull.insert(a);

        }
		*/
        Message Message1 = new Message();
        Message1.setMessage("Second Rule");
        Message1.setStatus(Message.HELLO);
        sessionStatefull.insert(Message1);
        
       /* Message message2 = new Message();
        message2.setMessage("Third Rule");
        message2.setStatus(Message.GOODBYE);
        sessionStatefull.insert(message2);*/
        
        
        sessionStatefull.getAgenda().getAgendaGroup("Gruop01").setFocus();
		sessionStatefull.fireAllRules();
		
        return RepeatStatus.FINISHED;
    }  


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
    
  
    
}