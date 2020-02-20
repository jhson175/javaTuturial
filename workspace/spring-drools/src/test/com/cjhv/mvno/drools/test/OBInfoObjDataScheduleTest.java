package com.cjhv.mvno.drools.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cjhv.mvno.drools.util.DateUtil;
import com.cjhv.mvno.drools.schedule.OBObjDataSchedule;


public class OBInfoObjDataScheduleTest 
{
    /**
     * @param args
     */
    public static void main( String[] args )
    {
    	
    	System.out.println("START");
        Logger logger = LoggerFactory.getLogger(OBInfoObjDataScheduleTest.class.getName());
        System.out.println("START01");
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("/job/OBInfoObjDataSchedule-job.xml");
    	System.out.println("START1");
    	// 테스트
    	OBObjDataSchedule cj = (OBObjDataSchedule)appContext.getBean("obInfoObjDataScheduleTest");

    	
    	System.out.println("START");
		try{
    		cj.execute(null,null);
    	
    	}catch (Exception e) {
    		e.printStackTrace();
		}
		
		String currMonth = ("20191211140001").toString().substring(0, 6); //당월
		String prevMonth = null;
		try {
			prevMonth = DateUtil.addMonth(new SimpleDateFormat("yyyyMM").parse(currMonth), -1).toString().replaceAll("-", "").substring(0, 6);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //전월
		//String currMonth = "201911";
		//String prevMonth = "201910";
		
		logger.debug("currMonth --------------------------------------> :" + currMonth);	
		logger.debug("prevMonth --------------------------------------> :" + prevMonth);
		logger.debug(DateUtil.getYYYYMMDD(DateUtil.addDay(DateUtil.getDateYYYYMMDD(currMonth+"01"), -1))); //전월말일				
		/*
		String preNowDate   = DateUtil.getAddDays(-2); // 전일
		String nowDate      = DateUtil.getDate("yyyyMMdd");
		
		System.out.println("preNowDate -> " + preNowDate);
		System.out.println("nowDate -> " + nowDate);
		*/
    }
}
