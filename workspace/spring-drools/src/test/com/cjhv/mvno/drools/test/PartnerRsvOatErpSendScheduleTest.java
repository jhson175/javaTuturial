package com.cjhv.mvno.drools.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cjhv.mvno.drools.schedule.partner.PartnerRsvPayErpSendSchedule;

public class PartnerRsvOatErpSendScheduleTest {
	
	public static void main( String[] args )
    {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("/job/partner/PartnerRsvPayErpSendSchedule-job.xml");
    	
    	PartnerRsvPayErpSendSchedule cj = (PartnerRsvPayErpSendSchedule)appContext.getBean("PartnerRsvPayErpSendSchedule");
//    	AcctInquiryHanaSchedule cj = (AcctInquiryHanaSchedule)appContext.getBean("AcctInquiryHanaScheduleManual");
   	
    	
    	try{
    	      cj.execute(null,null);

    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	
    }

}
