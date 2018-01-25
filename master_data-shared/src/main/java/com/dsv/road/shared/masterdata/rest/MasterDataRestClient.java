package com.dsv.road.shared.masterdata.rest;

import com.dsv.road.shared.masterdata.dto.*;

import java.util.List;

public interface MasterDataRestClient {

    String generateId(String prefix);

    Long createDangerousGoodsBundle(DtoDangerousGoodsBundle dtoDangerousGoodsBundle);

    String createHsCode(DtoHsCode hsCodes);

    void createHsCodeText(DtoHsCodeText hsCodeText);

    List<DtoRole> searchRoles(String s);

    void deleteRole(Long id);

    Long createRole(DtoRole r);


}
