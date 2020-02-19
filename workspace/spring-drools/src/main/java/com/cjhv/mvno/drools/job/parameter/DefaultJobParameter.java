package com.cjhv.mvno.drools.job.parameter;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class DefaultJobParameter
    implements JobParametersIncrementer
{
    final JobParametersBuilder builder = new JobParametersBuilder();

    /**
     * @see org.springframework.batch.core.JobParametersIncrementer#getNext(org.springframework.batch.core.JobParameters)
     */
    public JobParameters getNext( JobParameters parameters )
    {
        System.out.println( "got job parameters: " + parameters );
        if ( parameters == null || parameters.isEmpty() )
        {
            return new JobParametersBuilder().addLong( "run.id", 1L ).toJobParameters();
        }
        long id = parameters.getLong( "run.id", 1L ) + 1;
        return new JobParametersBuilder().addLong( "run.id", id ).toJobParameters();
    }
}