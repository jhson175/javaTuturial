/*

*/

package com.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

public class DataSchedule implements  Tasklet, InitializingBean {

	private final transient Log logger = LogFactory.getLog(getClass());  
	
	/**
     * 생성자.
     */
	public DataSchedule()
	{
		logger.debug("[Date]: " +System.currentTimeMillis()+ ", CtrtApprObjDataSchedule Started.." );
		System.out.println("Construct Start");
		//kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("START Execute");
        return RepeatStatus.FINISHED;
	}



	
    
  
    
}