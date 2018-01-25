package com.dsv.road.masterdata.idgeneration;


import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsv.road.masterdata.helpers.MockFactory;
import com.dsv.road.masterdata.idgenerator.IdType;
import com.dsv.road.masterdata.idgenerator.IdTypeBean;
import com.dsv.road.masterdata.idgenerator.IdTypeManager;

public class IdGenerationTest {
	Logger logger = LoggerFactory.getLogger(IdGenerationTest.class);
	IdTypeManager mIdTypeManager = MockFactory.getIdTypeManager();
	IdTypeBean idTypeBean = new IdTypeBean();
	
	public IdGenerationTest() {
		idTypeBean.setIdTypeManager(mIdTypeManager);
	}
	    
    @Test
    public void testNextId() {
		when(mIdTypeManager.getIdType("CO")).thenReturn(new IdType("CO", 9L));
    	Assert.assertEquals(idTypeBean.nextId("CO"),"CO10");
    }

    @Test
    public void testIdOverFlow() {
		when(mIdTypeManager.getIdType("CO")).thenReturn(new IdType("CO", 999999999999999L));
    	Assert.assertEquals(idTypeBean.nextId("CO"),"CO1");
    }
    
 
}
