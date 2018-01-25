package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.HsCodeException;
import com.dsv.road.masterdata.exception.HsCodeTextException;
import com.dsv.road.masterdata.model.HsCode;
import com.dsv.road.masterdata.model.HsCodeText;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO / entity manager for commodities.
 */
@Named
@Interceptors({LoggerInterceptor.class})
public class CommodityManager {
    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    public List<HsCode> getHsCodes() {
        TypedQuery<HsCode> list = em.createNamedQuery(HsCode.FIND_ALL_HS_CODES_QUERY, HsCode.class);
        List<HsCode> HsCodeList = list.getResultList();
        return new ArrayList<>(HsCodeList);
    }

    public List<HsCodeText> getHsCodeTexts() {
        TypedQuery<HsCodeText> list = em.createNamedQuery(HsCodeText.FIND_ALL_HS_CODE_TEXTS_QUERY, HsCodeText.class);
        List<HsCodeText> HsCodeTextList = list.getResultList();
        return new ArrayList<>(HsCodeTextList);
    }

    public HsCode getHsCode(long id) {
        try {
            return em.find(HsCode.class, id);
        } catch (PersistenceException e) {
            throw new HsCodeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading HsCode by id: " + id, e);
        }
    }

    public HsCodeText getHsCodeText(long id) {
        try {
            return em.find(HsCodeText.class, id);
        } catch (PersistenceException e) {
            throw new HsCodeTextException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading HsCode text by id: " + id, e);
        }
    }

    public HsCode insertHsCode(HsCode hsCode) {
        if (hsCode == null) {
            throw new HsCodeException(ResponseErrorCode.INSERT_WITH_NULL, "Attempting to insert null HsCode");
        }
        if( hsCode.getId() != null) {
            throw new HsCodeException(ResponseErrorCode.INSERT_WITH_NOTNULL_ID, "Attempting to insert with existing id");
        }
        try {
            em.persist(hsCode);
            em.flush();
            em.refresh(hsCode);
            return hsCode;
        } catch (PersistenceException e) {
            throw new HsCodeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error, while inserting HsCode", e);
        }
    }

    public HsCodeText insertHsCodeText(HsCodeText hsCodeText) {
        if (hsCodeText == null) {
            throw new HsCodeTextException(ResponseErrorCode.INSERT_WITH_NULL, "Attempting to insert null HsCodeText");
        }
        if( hsCodeText.getId() != null){
            throw new HsCodeTextException(ResponseErrorCode.INSERT_WITH_NOTNULL_ID, "Attempting to insert with existing id");
        }
        try {
            em.persist(hsCodeText);
            em.flush();
            em.refresh(hsCodeText);
            return hsCodeText;
        } catch (PersistenceException e) {
            throw new HsCodeTextException(ResponseErrorCode.INTERNAL_PROBLEM, "Error, while inserting HsCode Text", e);
        }
    }

    public List<HsCode> insertHsCodes(List<HsCode> hsCodes) {
        if (hsCodes == null) {
            throw new HsCodeException(ResponseErrorCode.INSERT_WITH_NULL, "Attempting to insert null ArrayList<HsCode>");
        }
        try {
            for (HsCode hsCode : hsCodes) {
                insertHsCode(hsCode);
            }
            return hsCodes;
        } catch (PersistenceException e) {
            throw new HsCodeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while inserting array of HsCode", e);
        }
    }

    public List<HsCodeText> insertHsCodeTexts(List<HsCodeText> hsCodeTexts) {
        if (hsCodeTexts == null) {
            throw new HsCodeTextException(ResponseErrorCode.INSERT_WITH_NULL, "Attempting to insert null ArrayList<HsCodeText>");
        }
        try {
            for (HsCodeText hsCodeText : hsCodeTexts) {
                insertHsCodeText(hsCodeText);
            }
            return hsCodeTexts;
        } catch (PersistenceException e) {
            throw new HsCodeTextException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while inserting array of HsCode Text", e);
        }
    }

    public HsCode updateHsCode(long id, HsCode hsCode) {
        if (hsCode == null) {
            throw new HsCodeException(ResponseErrorCode.UPDATE_WITH_NULL, "Attempting to update null HsCode");
        }
        try {
            HsCode existingHsCode = getHsCode(id);
            existingHsCode.setHsCode(hsCode.getHsCode());
            existingHsCode.setValidFrom(hsCode.getValidFrom());
            existingHsCode.setValidTo(hsCode.getValidTo());
            em.persist(existingHsCode);
            return existingHsCode;
        } catch (PersistenceException e) {
            throw new HsCodeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating HsCode", e);
        }
    }

    public HsCodeText updateHsCodeText(long id, HsCodeText hsCodeText) {
        if (hsCodeText == null) {
            throw new HsCodeTextException(ResponseErrorCode.UPDATE_WITH_NULL, "Attempting to update null HsCodeText");
        }
        try {
            HsCodeText existingHsCodeText = getHsCodeText(id);
            existingHsCodeText.setHsCode(hsCodeText.getHsCode());
            existingHsCodeText.setLanguage(hsCodeText.getLanguage());
            existingHsCodeText.setDescription(hsCodeText.getDescription());
            em.persist(existingHsCodeText);
            return existingHsCodeText;
        } catch (PersistenceException e) {
            throw new HsCodeTextException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating HsCode Text", e);
        }
    }

    public void deleteHsCode(long id) {
        try {
            HsCode hsCodeObject = em.find(HsCode.class, id);
            if (hsCodeObject == null) {
                throw new HsCodeException(ResponseErrorCode.NOT_FOUND, "HsCode with id " + id + " not found!");
            }
            em.remove(hsCodeObject);
        } catch (PersistenceException e) {
            throw new HsCodeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting HsCode of id: " + id, e);
        }
    }

    public void deleteHsCodeText(long id) {
        try {
            HsCodeText hsCodeText = em.find(HsCodeText.class, id);
            if(hsCodeText == null) {
                throw new HsCodeTextException(ResponseErrorCode.NOT_FOUND, "HsCodeText with id "+ id + " not found");
            }
            em.remove(hsCodeText);
        } catch (PersistenceException e) {
            throw new HsCodeTextException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting HsCode Text of id: " + id, e);
        }
    }

    public List<HsCodeText> searchHsCodeTexts(String filter, int limit) {
        TypedQuery<HsCodeText> result = em.createNamedQuery(HsCodeText.SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_QUERY, HsCodeText.class);
        result.setParameter("hsCode", filter+"%");
        result.setParameter("filter", prepareForSearch(filter));
        result.setMaxResults(limit);
        return new ArrayList<>(result.getResultList());
    }

    public List<HsCodeText> searchHsCodeTexts(String filter, String language, int limit) {
        TypedQuery<HsCodeText> result = em.createNamedQuery(HsCodeText.SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_AND_LANGUAGE_QUERY, HsCodeText.class);
        result.setParameter("hsCode", filter + "%");
        result.setParameter("filter", prepareForSearch(filter));
        result.setParameter("language", language.toUpperCase());
        result.setMaxResults(limit);
        return new ArrayList<>(result.getResultList());
    }

    private String prepareForSearch(String filter) {
        return "%" + filter.toUpperCase().replace(' ', '%') + "%";
    }
}
