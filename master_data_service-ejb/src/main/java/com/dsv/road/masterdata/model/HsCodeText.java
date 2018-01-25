package com.dsv.road.masterdata.model;

import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.interceptor.Interceptors;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = HsCodeText.FIND_ALL_HS_CODE_TEXTS_QUERY, query = "SELECT b FROM HsCodeText b"),
        @NamedQuery(name = HsCodeText.SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_QUERY, query = "SELECT b FROM HsCodeText b WHERE UPPER(b.HsCode) LIKE :hsCode OR UPPER(b.description) LIKE :filter"),
        @NamedQuery(name = HsCodeText.SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_AND_LANGUAGE_QUERY, query = "SELECT b FROM HsCodeText b WHERE (UPPER(b.HsCode) LIKE :hsCode OR UPPER(b.description) LIKE :filter) AND UPPER(b.language) LIKE :language")
})
@Table(name = "HS_CODE_TEXT")
@Interceptors(LoggerInterceptor.class)
public class HsCodeText  extends PersistableEntity {

    public static final String FIND_ALL_HS_CODE_TEXTS_QUERY = "findAllHsCodeTexts";
    public static final String SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_QUERY = "searchHsCodeTextsByDescription";
    public static final String SEARCH_HS_CODE_TEXTS_BY_DESCRIPTION_AND_LANGUAGE_QUERY = "searchHsCodeTextsByDescriptionAndLanguage";

    @Column(name = "HS_CODE", length = 12)
    String hsCode;

    @Column(name = "LANGUAGE", length = 2)
    String language;

    @Column(name = "DESCRIPTION", length = 2048)
    String description;
}
