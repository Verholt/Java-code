package com.dsv.road.masterdata.ejb.profile;

import com.dsv.road.masterdata.exception.CurrencyException;
import com.dsv.road.masterdata.jpa.PersistenceConstants;
import com.dsv.road.masterdata.model.CustomerOrderProfileKey;
import com.dsv.road.masterdata.security.User;
import com.dsv.road.shared.masterdata.dto.DtoProfileKey;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.dsv.road.masterdata.model.CustomerOrderProfileKey.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProfileKeyBean {

    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    @Inject
    private Mapper mapper;

    public DtoProfileKey get(Long id) {
        CustomerOrderProfileKey key = em.find(CustomerOrderProfileKey.class, id);
        return key == null ? null : mapper.map(key, DtoProfileKey.class);
    }

    public long save(DtoProfileKey profileKey, User user) {
        CustomerOrderProfileKey key = mapper.map(profileKey, CustomerOrderProfileKey.class);
        setCreationData(key, user);
        em.persist(key);
        em.flush();
        return key.getId();
    }

    public void update(DtoProfileKey profileKey, User user) {
        CustomerOrderProfileKey key = mapper.map(profileKey, CustomerOrderProfileKey.class);
        em.merge(key);
    }

    public void delete(Long id) {
        CustomerOrderProfileKey key = em.getReference(CustomerOrderProfileKey.class, id);
        em.remove(key);
    }

    public List<DtoProfileKey> find(String filter, int limit) {
        TypedQuery<CustomerOrderProfileKey> query = getSearchQuery(filter, limit);
        List<CustomerOrderProfileKey> profiles = query.getResultList();
        List<DtoProfileKey> dtoProfileKeys = new ArrayList<>(profiles.size());

        for (CustomerOrderProfileKey profile : profiles) {
            dtoProfileKeys.add(mapper.map(profile, DtoProfileKey.class));
        }

        return dtoProfileKeys;
    }

    public List<DtoProfileKey> findAll() {
        return find(StringUtils.EMPTY, Integer.MAX_VALUE);
    }

    private TypedQuery<CustomerOrderProfileKey> getSearchQuery(String filter, int limit) {
        TypedQuery<CustomerOrderProfileKey> query;
        if (StringUtils.isBlank(filter)) {
            query = em.createNamedQuery(FIND_ALL_PROFILE_KEYS_NAMED_QUERY, CustomerOrderProfileKey.class);
        } else {
            query = em.createNamedQuery(SEARCH_PROFILE_KEYS_NAMED_QUERY, CustomerOrderProfileKey.class);
            query.setParameter(FILTER_QUERY_PARAM, prepareForSearch(filter));
        }
        query.setMaxResults(limit);
        return query;
    }


    public boolean exists(Long id) {
        try {
            return get(id) != null;

        } catch (PersistenceException e) {
            throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading", e);
        }
    }

    private String prepareForSearch(String filter) {
        return "%" + filter.toUpperCase().replace(' ', '%') + "%";
    }


   private void setCreationData(CustomerOrderProfileKey profileKey, User user) {
        profileKey.setCreatedAt(now());
        profileKey.setCreatedBy(user.getName());
    }


    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
}
