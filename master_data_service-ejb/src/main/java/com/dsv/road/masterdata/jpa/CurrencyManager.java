package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.CurrencyException;
import com.dsv.road.masterdata.model.Currency;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class CurrencyManager {
	@PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
	private EntityManager em;

	public List<Currency> getCurrencies() {
		try {
			TypedQuery<Currency> list = em.createNamedQuery("findAllCurrencies", Currency.class);
			List<Currency> currencyList = list.getResultList();
			return new ArrayList<>(currencyList);
		} catch (PersistenceException e) {
            throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading currency list", e);
        }
	}

	public Currency createCurrency(Currency currency) {
		try {
			em.persist(currency);
			em.flush();
		} catch (PersistenceException e) {
			throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating / persisting currency", e);
		}
		return currency;
	}

	public Currency getCurrency(String shortAbbreviation) {
		try {
			return em.find(Currency.class, shortAbbreviation);
		} catch (PersistenceException e) {
			throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while reading currency with shortAbbreviation: " + shortAbbreviation, e);
		}
	}

	public Currency updateCurrency(Currency currency) {
		try {
			return em.merge(currency);
		} catch (PersistenceException e) {
			throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating currency", e);
		}
	}

	public boolean deleteCurrency(String shortAbbreviation) {
		try {
			Currency p = getCurrency(shortAbbreviation);
			em.remove(p);
			return true;
		} catch (PersistenceException e) {
			throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while deleting currency with shortAbbreviation: " + shortAbbreviation, e);
		}
	}

	public boolean exists(String shortAbbreviation) {
		try {
			return getCurrency(shortAbbreviation) != null;

		} catch (PersistenceException e) {
			throw new CurrencyException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading", e);
		}
	}
}
