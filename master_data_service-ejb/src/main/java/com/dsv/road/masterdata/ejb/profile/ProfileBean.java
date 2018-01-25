package com.dsv.road.masterdata.ejb.profile;

import com.dsv.road.masterdata.exception.CurrencyException;
import com.dsv.road.masterdata.jpa.PersistenceConstants;
import com.dsv.road.masterdata.model.CustomerOrderProfile;
import com.dsv.road.masterdata.model.CustomerOrderProfileAttribute;
import com.dsv.road.masterdata.security.User;
import com.dsv.road.shared.masterdata.dto.DtoProfile;
import com.dsv.road.shared.masterdata.dto.DtoProfileAttribute;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.dsv.road.masterdata.model.CustomerOrderProfile.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProfileBean {

    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    @Inject
    private Mapper mapper;

    public DtoProfile get(long id) {

        CustomerOrderProfile profile = em.find(CustomerOrderProfile.class, id);
        return profile == null ? null : mapper.map(profile, DtoProfile.class);
    }

    public long save(DtoProfile profile, User user) {
        CustomerOrderProfile customerOrderProfile = mapper.map(profile, CustomerOrderProfile.class);
        setCreationData(customerOrderProfile, user);
        for (CustomerOrderProfileAttribute keyValue : customerOrderProfile.getAttributes()) {
            keyValue.setProfile(customerOrderProfile);
        }
        em.persist(customerOrderProfile);
        em.flush();
        return customerOrderProfile.getId();
    }

    private void setCreationData(CustomerOrderProfile customerOrderProfile, User user) {
        Timestamp now = now();
        customerOrderProfile.setCreatedAt(now);
        customerOrderProfile.setCreatedBy(user.getName());
        for (CustomerOrderProfileAttribute keyValue : customerOrderProfile.getAttributes()) {
            keyValue.setCreatedAt(now);
            keyValue.setCreatedBy(user.getName());
        }
    }

    public List<DtoProfile> find(String filter, int limit) {
        TypedQuery<CustomerOrderProfile> query = getSearchQuery(filter, limit);
        List<CustomerOrderProfile> profiles = query.getResultList();
        List<DtoProfile> dtoProfiles = Lists.newArrayListWithCapacity(profiles.size());

        for (CustomerOrderProfile profile : profiles) {
            dtoProfiles.add(mapper.map(profile, DtoProfile.class));
        }

        return dtoProfiles;

    }

    public List<DtoProfile> findAll() {
        return find(StringUtils.EMPTY, Integer.MAX_VALUE);
    }

    private TypedQuery<CustomerOrderProfile> getSearchQuery(String filter, int limit) {
        TypedQuery query;
        if (StringUtils.isBlank(filter)) {
            query = em.createNamedQuery(FIND_ALL_PROFILES_NAMED_QUERY, CustomerOrderProfile.class);
        } else {
            query = em.createNamedQuery(SEARCH_PROFILES_NAMED_QUERY, CustomerOrderProfile.class);
            query.setParameter(FILTER_QUERY_PARAM, prepareForSearch(filter));
        }
        query.setMaxResults(limit);
        return query;
    }

    private String prepareForSearch(String filter) {
        return "%" + filter.toUpperCase().replace(' ', '%') + "%";
    }

    public void delete(long id) {
        CustomerOrderProfile profile = em.getReference(CustomerOrderProfile.class, id);
        em.remove(profile);
    }

    public void updateName(Long id, String newName, User user) {
        CustomerOrderProfile profile = em.getReference(CustomerOrderProfile.class, id);
        profile.setName(newName);
        setUpdateData(profile, user);
    }

    public void updateDescription(Long id, String newDescription, User user) throws EntityNotFoundException {
        CustomerOrderProfile profile = em.getReference(CustomerOrderProfile.class, id);
        profile.setDescription(newDescription);
        setUpdateData(profile, user);
    }


    public void addAttribute(Long profileId, DtoProfileAttribute attribute, User user) {
        CustomerOrderProfile profile = em.getReference(CustomerOrderProfile.class, profileId);
        CustomerOrderProfileAttribute profileAttribute = mapper.map(attribute, CustomerOrderProfileAttribute.class);
        profileAttribute.setProfile(profile);
        profileAttribute.setCreatedAt(now());
        profileAttribute.setCreatedBy(user.getName());
        profile.getAttributes().add(profileAttribute);
        setUpdateData(profile, user);
    }

    private void setUpdateData(CustomerOrderProfile profile, User user) {
        profile.setUpdatedBy(user.getName());
        profile.setUpdatedAt(now());
    }

    private Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void removeAttribute(Long profileId, Long attrId, User user) {
        CustomerOrderProfile profile = em.find(CustomerOrderProfile.class, profileId);
        for (CustomerOrderProfileAttribute attr : profile.getAttributes()) {
            if (attr.getId().equals(attrId)) {
                em.remove(attr);
                setUpdateData(profile, user);
                break;
            }
        }
    }

    public boolean exists(Long id) {
        try {
            return get(id) != null;
        } catch (PersistenceException e) {
            throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading", e);
        }
    }
}
