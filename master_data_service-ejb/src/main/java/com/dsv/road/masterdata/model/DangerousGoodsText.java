package com.dsv.road.masterdata.model;

import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsText;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "DANGEROUS_GOODS_TEXT")
public class DangerousGoodsText extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	@Column(name = "LANGUAGE_CODE")
	private String languageCode;

	@Column(name = "DANGEROUS_GOODS_TEXT")
	private String dangerousGoodsText;

	@Column(name = "DANGEROUS_GOODS_ID")
	private Long dangerousGoodsId;

	private static transient Mapper mapper = DozerUtility.getMapper();

	public static List<DtoDangerousGoodsText> toDtoDangerousGoodsTexts(List<DangerousGoodsText> dangerousGoodsTexts) {
		List<DtoDangerousGoodsText> dtoDangerousGoodsTexts = new ArrayList<>();
		for (DangerousGoodsText dangerousGoodsText : dangerousGoodsTexts) {
			dtoDangerousGoodsTexts.add(mapper.map(dangerousGoodsText, DtoDangerousGoodsText.class));
		}
		return dtoDangerousGoodsTexts;
	}
}
