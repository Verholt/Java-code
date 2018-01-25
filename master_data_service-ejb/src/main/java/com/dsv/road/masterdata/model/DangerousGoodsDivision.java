package com.dsv.road.masterdata.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by EXT.Andreas.Froesig on 11-12-2015.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DangerousGoodsDivision {
    protected String properShipmentName;
    protected String classId;
    protected String packingGroup;
    protected String labels;
    protected String specialProvisions;
    protected String exceptedQuantities;
    protected boolean dangerous;

    public DangerousGoodsDivision append(DangerousGoodsDivision other) {
        if (other == null) {
            return this;
        }

        properShipmentName = appendIfDifferent(other.properShipmentName, properShipmentName);
        classId = appendIfDifferent(other.classId, classId);
        packingGroup = appendIfDifferent(other.packingGroup, packingGroup);
        labels = appendIfDifferent(other.labels, labels);
        specialProvisions = appendIfDifferent(other.specialProvisions, specialProvisions);
        exceptedQuantities = appendIfDifferent(other.exceptedQuantities, exceptedQuantities);

        return this;
    }

    private String appendIfDifferent(String otherVal, String currentVal) {
        if (StringUtils.isNotBlank(otherVal)) {
            otherVal = StringUtils.trim(otherVal);
            if (!otherVal.equals(currentVal)) {
                return StringUtils.isBlank(currentVal) ? otherVal : currentVal + ", " + otherVal;
            }
        }
        return currentVal;
    }


}
