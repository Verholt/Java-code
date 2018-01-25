package com.dsv.road.masterdata.helpers;


import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dsv.road.masterdata.idgenerator.IdType;
import com.dsv.road.masterdata.idgenerator.IdTypeManager;

public class MockFactory {
	
	

	public static IdTypeManager getIdTypeManager() {
		IdTypeManager mIdTypeManager = Mockito.mock(IdTypeManager.class);
		when(mIdTypeManager.insertIdType((IdType) anyObject())).thenAnswer(new Answer<IdType>() {
			@Override
			public IdType answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (IdType) args[0];
			}
		});
		return mIdTypeManager;
	}
}
