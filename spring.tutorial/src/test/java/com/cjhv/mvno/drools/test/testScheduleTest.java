package com.cjhv.mvno.drools.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.schedule.DataSchedule;

public class testScheduleTest 
{
    /**
     * @param args
     */
    public static void main( String[] args )
    {
    	
    	System.out.println("START");
        Logger logger = LoggerFactory.getLogger(testScheduleTest.class.getName());
        System.out.println("START01");
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("/job/Schedule-job.xml");
    	System.out.println("START1");
    	// 테스트
    	DataSchedule schedule = (DataSchedule)appContext.getBean("ScheduleTest");

    	
    	System.out.println("START");
		
		try{ schedule.execute(null,null);
		  
		}catch (Exception e) { e.printStackTrace(); }
		 
		String currMonth = ("20191211140001").toString().substring(0, 6); //당월
		String prevMonth = null;
	
    }
}
