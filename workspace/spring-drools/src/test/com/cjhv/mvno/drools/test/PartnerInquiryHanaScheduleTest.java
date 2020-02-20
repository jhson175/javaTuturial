package com.cjhv.mvno.drools.test;

import java.util.Date;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cjhv.mvno.drools.schedule.partner.PartnerInquiryHanaSchedule;

public class PartnerInquiryHanaScheduleTest {
	
	public static void main( String[] args )
    {
		Properties prop = System.getProperties();
		
		System.out.println("Properties -=-==> " + prop.getProperty("SERVER_MODE"));
		System.out.println("Properties -=-==> " + prop.getProperty("trustnet.home"));
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("/job/partner/PartnerInquiryHanaSchedule-job.xml");
    	
    	System.out.println("1");
    	PartnerInquiryHanaSchedule cj = (PartnerInquiryHanaSchedule)appContext.getBean("PartnerInquiryHanaSchedule");
//    	AcctInquiryHanaSchedule cj = (AcctInquiryHanaSchedule)appContext.getBean("AcctInquiryHanaScheduleManual");
   	
    	System.out.println("TEST MAIN");
    	System.exit(1);
    	try{
    	      cj.execute(null,null);

    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	
    }

}
