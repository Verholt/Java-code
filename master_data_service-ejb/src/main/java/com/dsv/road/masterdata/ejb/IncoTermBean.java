package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.IncoTermManager;
import com.dsv.road.masterdata.model.IncoTerm;
import com.dsv.road.shared.masterdata.dto.DtoIncoTerm;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ext.jesper.munkholm
 * 
 *         This implements the business layer of the customer order module
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class IncoTermBean {

    @Inject
    IncoTermManager incoTermManager;

    Mapper mapper = DozerUtility.getMapper();

    public IncoTermBean() {
        super();
    }

    public List<DtoIncoTerm> getIncoterms() {
        List<IncoTerm> incoTerms = incoTermManager.getIncoTerms();
        List<DtoIncoTerm> dtoIncoTerms = new ArrayList<>();
        for (IncoTerm incoTerm : incoTerms) {
            dtoIncoTerms.add(mapper.map(incoTerm, DtoIncoTerm.class));
        }
        return dtoIncoTerms;
    }

    public DtoIncoTerm getIncoTerm(String shortAbbreviation) {
        IncoTerm incoTerm = incoTermManager.getIncoTerm(shortAbbreviation);
        if (incoTerm != null) {
            return mapper.map(incoTerm, DtoIncoTerm.class);
        } else {
            return null;
        }
    }

    public DtoIncoTerm createIncoTerm(DtoIncoTerm dtoIncoTerm) {
        IncoTerm incoTerm = mapper.map(dtoIncoTerm, IncoTerm.class);
        incoTerm.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        incoTerm.setCreatedBy("Manually created");
        incoTerm = incoTermManager.createIncoTerm(incoTerm);
        return mapper.map(incoTerm, DtoIncoTerm.class);
    }

    public boolean deleteIncoTerm(String shortAbbreviation) {
        return incoTermManager.deleteIncoTerm(shortAbbreviation);
    }

    public DtoIncoTerm updateIncoTerm(DtoIncoTerm newIncoTerm) {
        IncoTerm incoTerm = mapper.map(newIncoTerm, IncoTerm.class);
        incoTerm.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        incoTerm.setCreatedBy("Manually updated");
        incoTerm = incoTermManager.updateIncoTerm(incoTerm);
        return mapper.map(incoTerm, DtoIncoTerm.class);
    }
    public boolean exists(String shortAbbreviation) {
        return incoTermManager.exists(shortAbbreviation);
    }
}