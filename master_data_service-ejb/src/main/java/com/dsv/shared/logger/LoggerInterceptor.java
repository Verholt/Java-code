package com.dsv.shared.logger;

import org.slf4j.Logger;

import com.dsv.shared.loggerpar.LoggerPar;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class LoggerInterceptor extends LoggerPar {

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {    	    	    	
    	return super.logMethod(ic);    	    	
    } 
}
