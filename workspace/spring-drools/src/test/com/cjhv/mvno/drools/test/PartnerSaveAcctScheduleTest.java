package com.cjhv.mvno.drools.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cjhv.mvno.drools.schedule.partner.PartnerSaveAcctSchedule;
import com.cjhv.mvno.drools.util.DateUtil;

public class PartnerSaveAcctScheduleTest {
	
	public static void main( String[] args )
    {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("/job/partner/PartnerSaveAcctSchedule-job.xml");
    	
    	PartnerSaveAcctSchedule cj = (PartnerSaveAcctSchedule)appContext.getBean("PartnerSaveAcctSchedule");
//    	AcctInquiryHanaSchedule cj = (AcctInquiryHanaSchedule)appContext.getBean("AcctInquiryHanaScheduleManual");
   	
    	
    	try{
    	      cj.execute(null,null);

    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	
    }

}
