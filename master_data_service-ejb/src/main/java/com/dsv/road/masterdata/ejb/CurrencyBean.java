package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.CurrencyManager;
import com.dsv.road.masterdata.model.Currency;
import com.dsv.road.shared.masterdata.dto.DtoCurrency;
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
public class CurrencyBean {

    @Inject
    CurrencyManager currencyManager;

    Mapper mapper = DozerUtility.getMapper();

    public CurrencyBean() {
        super();
    }

    public List<DtoCurrency> getCurrencies() {
        List<Currency> currencies = currencyManager.getCurrencies();
        List<DtoCurrency> dtoCurrencies = new ArrayList<>();
        for (Currency currency : currencies) {
            dtoCurrencies.add(mapper.map(currency, DtoCurrency.class));
        }
        return dtoCurrencies;
    }

    public DtoCurrency getCurrency(String shortAbbreviation) {
        Currency currency = currencyManager.getCurrency(shortAbbreviation);
        if (currency != null) {
            return mapper.map(currency, DtoCurrency.class);
        } else {
            return null;
        }
    }

    public DtoCurrency createCurrency(DtoCurrency dtoCurrency) {
        Currency currency = mapper.map(dtoCurrency, Currency.class);
        currency.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        currency.setUpdatedBy("Manually created");
        currency = currencyManager.createCurrency(currency);
        return mapper.map(currency, DtoCurrency.class);
    }

    public boolean deleteCurrency(String shortAbbreviation) {
        return currencyManager.deleteCurrency(shortAbbreviation);
    }

    public DtoCurrency updateCurrency(DtoCurrency newCurrency) {
        Currency currency = mapper.map(newCurrency, Currency.class);
        currency.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        currency.setUpdatedBy("Manually updated");
        currency = currencyManager.updateCurrency(currency);
        return mapper.map(currency, DtoCurrency.class);
    }

    public boolean exists(String shortAbbreviation) {
        return currencyManager.exists(shortAbbreviation);
    }
}