package com.cjhv.mvno.drools.common.exception;

import org.springframework.dao.DataAccessException;

public class CommonInfraDataAccessException
    extends DataAccessException
{
    private static final long serialVersionUID = 129230193843384710L;

    public CommonInfraDataAccessException( String arg0 )
    {
        super( arg0 );
    }

    public CommonInfraDataAccessException( String arg0, Throwable arg1 )
    {
        super( arg0, arg1 );
    }
}
