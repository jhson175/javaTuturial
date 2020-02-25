#!/bin/sh

cd /dev/batchProgram
export MAINCLASS=org.springframework.batch.core.launch.support.CommandLineJobRunner
export JOB_XML=classpath:/job/DataSchedule-job.xml                              
export JOB_ID=DataScheduleJob
    
java -DSERVER_MODE=dev -classpath "batch-1.0-SNAPSHOT.jar:./lib/*" $MAINCLASS $JOB_XML $JOB_ID batchId=CT500 startDate= endDate= -next
