package com.cjhv.mvno.drools.job.parameter;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class TimestampJobParameter
    implements JobParametersIncrementer
{

    final JobParametersBuilder builder = new JobParametersBuilder();

    public JobParameters getNext( JobParameters parameters )
    {
        return builder.addLong( "run.id", System.currentTimeMillis() ).toJobParameters();
    }
}