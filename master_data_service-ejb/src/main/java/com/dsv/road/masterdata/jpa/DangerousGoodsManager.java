package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.DangerousGoodsException;
import com.dsv.road.masterdata.model.DangerousGoods;
import com.dsv.road.masterdata.model.DangerousGoodsBundle;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class DangerousGoodsManager {


	@PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
	private EntityManager em;

	public DangerousGoodsBundle insertBundle(DangerousGoodsBundle dangerousGoodsBundle) {
		try {
			if (dangerousGoodsBundle.getId() != null) {
				throw new DangerousGoodsException(ResponseErrorCode.INSERT_WITH_NOTNULL_ID,
						"Trying to create a new dangerous goods with existing ID.");
			}
			if (dangerousGoodsBundle.getValidFrom() == null) {
				Date now = new Date();
				dangerousGoodsBundle.setValidFrom(now);
				updateCurrentBundleToInactiveAt(now);
			} else {
				updateCurrentBundleToInactiveAt(dangerousGoodsBundle.getValidFrom());
			}
			em.persist(dangerousGoodsBundle);
			return dangerousGoodsBundle;
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Insertion of dangerous goods bundle failed ", e);
		}
	}

	public DangerousGoodsBundle getDangerousGoodsBundle(Long id) {
		try {
			return em.find(DangerousGoodsBundle.class, id);
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while retrieving dangerous goods bundle with id: " + id, e);
		}
	}

	public DangerousGoodsBundle getDangerousGoodsBundle(Date time) {
		try {
			TypedQuery<DangerousGoodsBundle> list = em
					.createNamedQuery(DangerousGoodsBundle.GET_BUNDLE_AT, DangerousGoodsBundle.class)
					.setParameter("time", time)
					.setMaxResults(1);
			return list.getSingleResult();
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while searching current dangerousGoodsBundle", e);
		}
	}

	public void deleteDangerousGoodsBundle(Long id) {
		try {
			DangerousGoodsBundle dangerousGoodsBundle = em.find(DangerousGoodsBundle.class, id);
			if (dangerousGoodsBundle.getValidTo() == null) {
				Date borderDate = dangerousGoodsBundle.getValidFrom();
				updatePreviousBundleToActiveAt(borderDate);
			}
			em.remove(dangerousGoodsBundle);
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while deleting dangerous goods bundle with id: " + id, e);
		}
	}

	public DangerousGoods getDangerousGoodById(Long id) {
		try {
			return em.find(DangerousGoods.class, id);
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding dangerous goods by id: " + id, e);
		}
	}

	public void updateDangerousGoodsBundle(DangerousGoodsBundle dangerousGoodsBundle) {
		try {
			em.merge(dangerousGoodsBundle);
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while updating dangerous goods bunlde with id: " + dangerousGoodsBundle.getId(), e);
		}
	}

	public List<DangerousGoods> searchCurrentDangerousGoods(String filter, int limit) {
		try {
			String preparedFilter = prepareForSearch(filter);
			TypedQuery<DangerousGoods> list = em
					.createNamedQuery(DangerousGoodsBundle.SEARCH_IN_CURRENT_DANGEROUS_GOODS, DangerousGoods.class)
					.setParameter("filter", preparedFilter)
					.setMaxResults(limit);
			return new ArrayList<>(list.getResultList());
		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while searching current dangerousGoods", e);
		}
	}

	public boolean exists(Long id) {
		try {
			return getDangerousGoodById(id) != null;

		} catch (PersistenceException e) {
			throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading", e);
		}
	}

	private String prepareForSearch(String filter) {
		return "%" + filter.toUpperCase().replace(' ', '%') + "%";
	}

	private void updateCurrentBundleToInactiveAt(Date validTo) {
		em.createNamedQuery(DangerousGoodsBundle.UPDATE_CURRENT_BUNDLE_TO_INACTIVE, DangerousGoodsBundle.class).setParameter("validTo", validTo, TemporalType.TIMESTAMP).executeUpdate();
	}

	private void updatePreviousBundleToActiveAt(Date validTo) {
		em.createNamedQuery(DangerousGoodsBundle.UPDATE_PREVIOUS_BUNDLE_TO_ACTIVE, DangerousGoodsBundle.class).setParameter("validTo", validTo, TemporalType.TIMESTAMP).executeUpdate();
	}
}
