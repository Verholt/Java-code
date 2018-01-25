package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.NumberCollectionException;
import com.dsv.road.masterdata.model.NumberCollection;
import com.dsv.road.masterdata.model.NumberRange;
import com.dsv.road.shared.masterdata.dto.ActiveState;
import com.dsv.road.shared.masterdata.dto.constraints.MasterDataConstants;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class NumberCollectionManager {
    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    public NumberCollection insert(NumberCollection numberCollection) {
        try {
            if (existsCollectionByParameters(numberCollection.getName(), numberCollection.getCountry(), numberCollection.getLocation()).size() != 0) {
                throw new NumberCollectionException(ResponseErrorCode.WRONG_INPUT, "number collection with same name, country and location already exists");
            }
            em.persist(numberCollection);
            return numberCollection;
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while inserting NumberCollections", e);
        }
    }

    public NumberCollection find(Long id) {
        try {
            return em.find(NumberCollection.class, id);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding NumberCollection with id: " + id, e);
        }
    }

    private List<Long> existsCollectionByParameters(String name, String country, String location) {
        TypedQuery<Long> list = em.createNamedQuery(NumberCollection.GET_ID_OF_NUMBER_COLLECTION_BY_PARAMETERS, Long.class)
                .setParameter(NumberCollection.NAME_FILTER, name)
                .setParameter(NumberCollection.COUNTRY_FILTER, country)
                .setParameter(NumberCollection.LOCATION_FILTER, location)
                .setMaxResults(1);
        return list.getResultList();
    }

    public NumberRange findRange(Long id) {
        try {
            return em.find(NumberRange.class, id);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding NumberRange with id: " + id, e);
        }
    }

    public NumberCollection update(NumberCollection numberCollection) {
        try {
            List<Long> existingCollections = existsCollectionByParameters(numberCollection.getName(), numberCollection.getCountry(), numberCollection.getLocation());
            if (existingCollections.size() > 1 || (existingCollections.size() == 1 && !existingCollections.get(0).equals(numberCollection.getId()))) {
                throw new NumberCollectionException(ResponseErrorCode.WRONG_INPUT, "an other number collection with same name, country and location already exists");
            }
            em.merge(numberCollection);
            return numberCollection;
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating NumberCollection", e);
        }
    }

    public void delete(Long id) {
        try {
            NumberCollection numberCollection = em.getReference(NumberCollection.class, id);
            em.remove(numberCollection);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting NumberCollection by id: " + id, e);
        }
    }

    public ArrayList<NumberCollection> search(int first, int pageSize, String filter, MasterDataConstants.DetailLevel format) {
        try {
            String namedQuery = "";
            switch (format) {
                case FULL:
                    namedQuery = NumberCollection.GET_ALL_COLLECTIONS_NAMED_QUERY;
                    break;
                case LITE:
                    namedQuery = NumberCollection.GET_ALL_COLLECTIONS_LITE_NAMED_QUERY;
                    break;
            }
            TypedQuery<NumberCollection> list = em.createNamedQuery(namedQuery, NumberCollection.class)
                    .setFirstResult(first).setMaxResults(pageSize).setParameter(NumberCollection.SEARCH_FILTER, prepareForSearch(filter));
            List<NumberCollection> collections = list.getResultList();
            return new ArrayList<>(collections);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading number collection list", e);
        }
    }

    public NumberRange addToRange(NumberRange numberRange) {
        try {
            em.persist(numberRange);
            return numberRange;
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while inserting numberRange", e);
        }
    }

    public NumberRange updateRange(NumberRange numberRange) {
        return updateRange(numberRange, false);
    }

    public NumberRange updateRange(NumberRange numberRange, boolean unlock) {
        try {
            em.merge(numberRange);
            if (unlock){
                em.lock(numberRange,LockModeType.NONE);
            }
            return numberRange;
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating numberRange", e);
        }
    }

    public void deleteRange(Long id) {
        try {
            NumberRange numberRange = em.getReference(NumberRange.class, id);
            em.remove(numberRange);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting numberRange by id: " + id, e);
        }
    }

    public List<NumberRange> getRangesForCollection(Long id) {
        try {
            TypedQuery<NumberRange> list = em.createNamedQuery(NumberRange.GET_RANGES_FOR_COLLECTION, NumberRange.class)
                    .setParameter(NumberRange.ID_PARAM, id);
            List<NumberRange> numberRanges = list.getResultList();
            return new ArrayList<>(numberRanges);
        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while getting numberRanges from collection wit id: " + id, e);
        }
    }

    private String prepareForSearch(String filter) {
        return "%" + filter.toUpperCase().replace(' ', '%') + "%";
    }


    public boolean exists(Long id) {
        try {
            return find(id) != null;

        } catch (PersistenceException e) {
            throw new NumberCollectionException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading NumberCollection", e);
        }
    }

    private NumberRange getNextNumberRange(String name, String country, String location) {
        try {
            TypedQuery<NumberRange> list = em.createNamedQuery(NumberCollection.GET_NEXT_NUMBER_RANGE, NumberRange.class)
                    .setParameter(NumberCollection.NAME_FILTER, name)
                    .setParameter(NumberCollection.COUNTRY_FILTER, country)
                    .setParameter(NumberCollection.LOCATION_FILTER, location)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .setMaxResults(1);
            return list.getSingleResult();
        } catch (NoResultException e) {
            throw new NumberCollectionException(ResponseErrorCode.WRONG_INPUT, "There are not enough numbers left in the collection");
        }
    }

    private NumberRange getActiveNumberRange(String name, String country, String location) {
        TypedQuery<NumberRange> list = em.createNamedQuery(NumberCollection.GET_ACTIVE_NUMBER_RANGE, NumberRange.class)
                .setParameter(NumberCollection.NAME_FILTER, name)
                .setParameter(NumberCollection.COUNTRY_FILTER, country)
                .setParameter(NumberCollection.LOCATION_FILTER, location)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setMaxResults(1);
        List<NumberRange> rangeList = list.getResultList();
        if (rangeList.size() == 0) {
            return null;
        } else {
            return rangeList.get(0);
        }
    }

    public NumberRange getCurrentNumberRange(String name, String country, String location) {
        NumberRange range = getActiveNumberRange(name, country, location);
        if (range != null && isInvalidTimeRange(range)) {
            range.setState(ActiveState.CLOSED);
            updateRange(range,true);
        }
        if (range == null || isInvalidTimeRange(range)) {
            range = getNextNumberRange(name, country, location);
            range.setState(ActiveState.ACTIVE);
            updateRange(range);
        }
        return range;
    }

    private boolean isInvalidTimeRange(NumberRange range) {
        return range.getValidFrom().after(new Date()) || range.getValidTo().before(new Date());
    }
}
