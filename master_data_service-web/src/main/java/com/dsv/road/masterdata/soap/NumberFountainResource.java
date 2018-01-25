package com.dsv.road.masterdata.soap;

import com.dsv.road.masterdata.ejb.NumberCollectionBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@Stateless
@WebService
public class NumberFountainResource {

    @EJB
    private NumberCollectionBean numberCollectionBean;

    @WebMethod(action = "getNumberList", operationName = "getNumberList")
    @WebResult(name = "numberList")
    public List<String> getNumberList(@WebParam(name = "name") String name, @WebParam(name = "amount") long amount, @WebParam(name = "country") String country, @WebParam(name = "location") String location) {
        return numberCollectionBean.getNumberList(name, amount, country, location);
    }


}
