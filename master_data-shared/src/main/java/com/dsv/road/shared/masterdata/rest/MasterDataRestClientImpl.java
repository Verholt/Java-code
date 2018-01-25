package com.dsv.road.shared.masterdata.rest;

import com.dsv.road.shared.masterdata.dto.*;
import com.dsv.shared.rest.client.RestTemplateApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.*;
import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class MasterDataRestClientImpl extends RestTemplateApplication<MasterDataClientException> implements MasterDataRestClient {

    public MasterDataRestClientImpl(URI baseURI) {
        super(baseURI);
    }

    @Override
    public String generateId(String prefix) throws MasterDataClientException {
        final String path = buildPath(IDS, prefix);
        Resource resource = getResource(path);

        ClientResponse response = resource.post(StringUtils.EMPTY);

        validate(response);
        return response.getEntity(String.class);
    }

    @Override
    public Long createDangerousGoodsBundle(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        Resource resource = getResource(DANGEROUS_GOODS_BUNDLES)
                .contentType(APPLICATION_JSON);

        ClientResponse response = resource.post(dtoDangerousGoodsBundle);

        final String location = response.getHeaders().getFirst(LOCATION);
        return Long.parseLong(extractId(location));
    }

    @Override
    public String createHsCode(DtoHsCode hsCode) {
        final String path = buildPath(COMMODITIES, HSCODES);
        Resource resource = getResource(path)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        final ClientResponse response = resource.post(hsCode);
        System.out.println("RESPONSE: "+ response.getStatusCode());
        validate(response);

        final String responseEntity = response.getEntity(String.class);
        return responseEntity;
    }

    @Override
    public void createHsCodeText(DtoHsCodeText hsCodeText) {
        final String path = buildPath(COMMODITIES, HSCODE_TEXTS);
        Resource resource = getResource(path)
                .contentType(APPLICATION_JSON);

        final ClientResponse response = resource.post(hsCodeText);

        validate(response);
    }

    @Override
    public List<DtoRole> searchRoles(String filter) {
        Resource resource = getResource(ROLES)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .queryParam("q", filter);

        final ClientResponse response = resource.get();

        validate(response);
        String entity = response.getEntity(String.class);
        return readValue(entity, new TypeReference<List<DtoRole>>() {});
    }

    @Override
    public void deleteRole(Long id) {
        if (id == null) {
            throw new MasterDataClientException(BAD_REQUEST.getStatusCode(), "invalidRequest", "ID cannot be 'null'");
        }
        final String path = buildPath(ROLES, id.toString());

        Resource resource = getResource(path);

        final ClientResponse response = resource.delete();
        validate(response);
    }

    @Override
    public Long createRole(DtoRole role) {
        Resource resource = getResource(ROLES)
                .contentType(APPLICATION_JSON);

        final ClientResponse response = resource.post(role);

        validate(response);
        final String location = response.getHeaders().getFirst(LOCATION);
        return Long.valueOf(extractId(location));
    }


    private String extractId(String uri) {
        final int lastSlashIdx = (uri == null) ? -1 : uri.lastIndexOf("/");
        if (lastSlashIdx == -1) {
            return null;
        }
        return uri.substring(lastSlashIdx + 1);
    }

}
