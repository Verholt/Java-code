//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package com.dsv.road.masterdata.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService(name = "NumberFountainResource", targetNamespace = "http://soap.masterdata.road.dsv.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NumberFountainResource {


    /**
     * 
     * @param location
     * @param country
     * @param amount
     * @param name
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod(action = "getNumberList")
    @WebResult(name = "numberList", targetNamespace = "")
    @RequestWrapper(localName = "getNumberList", targetNamespace = "http://soap.masterdata.road.dsv.com/", className = "com.dsv.road.masterdata.soap.GetNumberList")
    @ResponseWrapper(localName = "getNumberListResponse", targetNamespace = "http://soap.masterdata.road.dsv.com/", className = "com.dsv.road.masterdata.soap.GetNumberListResponse")
    @Action(input = "getNumberList", output = "http://soap.masterdata.road.dsv.com/NumberFountainResource/getNumberListResponse")
    public List<String> getNumberList(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "amount", targetNamespace = "")
        long amount,
        @WebParam(name = "country", targetNamespace = "")
        String country,
        @WebParam(name = "location", targetNamespace = "")
        String location);

}