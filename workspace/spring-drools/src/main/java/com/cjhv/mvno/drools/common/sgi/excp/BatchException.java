/*
 * Copyright (c) 2013 CJ Hellovision, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of CJ Hellovision
 * ,Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with CJ Hellovision.
 */
package com.cjhv.mvno.drools.common.sgi.excp;


/**
 * Exception 
 * 
 * @author Lee Min Young
 * @since 2013.05.10
 */
public class BatchException extends Exception {
	private static final long serialVersionUID = -9176524081712121830L;

	public BatchException () {
		super();
	}
	
	public BatchException (String msg) {
		super (msg);
	}
	
	public BatchException (Throwable msg) {
		super (msg);
	}
	
}
