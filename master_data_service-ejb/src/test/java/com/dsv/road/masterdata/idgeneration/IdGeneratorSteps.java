package com.dsv.road.masterdata.idgeneration;

import org.junit.Assert;

import com.dsv.road.masterdata.helpers.MockFactory;
import com.dsv.road.masterdata.idgenerator.IdType;
import com.dsv.road.masterdata.idgenerator.IdTypeBean;
import com.dsv.road.masterdata.idgenerator.IdTypeManager;

import static org.mockito.Mockito.when;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;




public class IdGeneratorSteps {
	private IdTypeManager mIdTypeManager;
	private String id;
	IdTypeBean idTypeBean;
	public IdGeneratorSteps(){	
		
		idTypeBean = new IdTypeBean();
		mIdTypeManager = MockFactory.getIdTypeManager();
		idTypeBean.setIdTypeManager(mIdTypeManager);

	}
	
	@Given("^I am creating an order$")
	public void i_am_creating_an_order() throws Throwable {
	}

	@When("^last Order id is CO(\\d+)$")
	public void last_Order_id_is_CO(long arg1) throws Throwable {
    	when(mIdTypeManager.getIdType("CO")).thenReturn(new IdType("CO", arg1));
		id = idTypeBean.nextId("CO");
	}

	@Then("^the system provides an unique Number \\(id\\) starting with CO\\.$")
	public void the_system_provides_an_unique_Number_id_starting_with_CO() throws Throwable {
		Assert.assertEquals("CO", id.replaceAll("[0-9]", ""));
	}

	@Then("^the ID is CO(\\d+)$")
	public void the_ID_is_CO(long arg1) throws Throwable {
		Assert.assertEquals(Long.toString(arg1), id.replaceAll("[\\D]", ""));
	}
}
