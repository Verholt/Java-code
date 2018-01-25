package com.dsv.road.master_data.client;

import com.dsv.road.shared.masterdata.dto.*;
import com.dsv.road.shared.masterdata.dto.constraints.MasterDataConstants;
import com.dsv.shared.joda.JodaUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status.Family;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.*;

/**
 * @deprecated This client is deprecated in favor of more standardized {@link com.dsv.road.shared.masterdata.rest.MasterDataRestClientImpl}
 */
@Deprecated
public class MasterDataClient extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataClient.class);
    private static final String PROFILE_ATTRIBUTES_PATH = "/attributes";
    private static final String TRANSPORT_LOCATION_PATH_EXTENDED = "/extended";
    private static final String LOCATION = "Location";
    private static RestClient restClient;
    private String restURL;
    private Gson gson;

    public MasterDataClient() {
        restURL = ClientApplication.baseURL() + "/" ;
        restClient = ClientApplication.getClient();
        gson = JodaUtility.getGson();
    }

    public MasterDataClient(String restURL) {
        this.restURL = restURL + "/" ;
        restClient = ClientApplication.getClient();
        gson = JodaUtility.getGson();
    }

    public static String extractId(String uri) {
        if (uri == null || uri.lastIndexOf("/") == -1) {
            return null;
        }
        return uri.substring(uri.lastIndexOf("/") + 1);
    }



    public List<DtoIncoTerm> getIncoTerms() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + INCO_TERMS);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoIncoTerm>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public DtoIncoTerm getIncoTerm(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + INCO_TERMS + "/" + shortAbbreviation);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoIncoTerm.class);
    }

    public String updateIncoTerm(DtoIncoTerm dtoIncoTerm) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + INCO_TERMS + "/" + dtoIncoTerm.getShortAbbreviation());
        ClientResponse response = resource.put(dtoIncoTerm);
        validateHttpResponseSuccessful(response);
        DtoIncoTerm incoTerm=response.getEntity(DtoIncoTerm.class);
        return incoTerm.getShortAbbreviation();
    }

    public String createIncoTerm(DtoIncoTerm dtoIncoTerm) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + INCO_TERMS);
        ClientResponse response = resource.post(dtoIncoTerm);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public boolean deleteIncoTerm(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResource(restURL + INCO_TERMS + "/" + shortAbbreviation);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
        return true;
    }

    public List<DtoCurrency> getCurrencies() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + CURRENCIES);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoCurrency>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public DtoCurrency getCurrency(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + CURRENCIES + "/" + shortAbbreviation);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoCurrency.class);
    }

    public String updateCurrency(DtoCurrency dtoDtoCurrency) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + CURRENCIES + "/" + dtoDtoCurrency.getShortAbbreviation());
        ClientResponse response = resource.put(dtoDtoCurrency);
        validateHttpResponseSuccessful(response);
        DtoCurrency currency = response.getEntity(DtoCurrency.class);
        return currency.getShortAbbreviation();
    }

    public String createCurrency(DtoCurrency dtoDtoCurrency) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + CURRENCIES);
        ClientResponse response = resource.post(dtoDtoCurrency);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public boolean deleteCurrency(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResource(restURL + CURRENCIES + "/" + shortAbbreviation);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
        return true;
    }

    public List<DtoPackageType> getPackageTypes() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PACKAGE_TYPES);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoPackageType>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public DtoPackageType getPackageType(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoPackageType.class);
    }

    public String updatePackageType(DtoPackageType dtoPackageType) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PACKAGE_TYPES + "/" + dtoPackageType.getShortAbbreviation());
        ClientResponse response = resource.put(dtoPackageType);
        validateHttpResponseSuccessful(response);
        DtoPackageType packageType = response.getEntity(DtoPackageType.class);
        return packageType.getShortAbbreviation();
    }

    public String createPackageType(DtoPackageType dtoPackageType) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PACKAGE_TYPES);
        ClientResponse response = resource.post(dtoPackageType);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public boolean deletePackageType(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResource(restURL + PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
        return true;
    }


    private void validateHttpResponseSuccessful(ClientResponse response) throws MasterDataClientException {
        if (response.getStatusType().getFamily() != Family.SUCCESSFUL) {
            LOGGER.error("HTTP Response code: " + response.getStatusCode());
            throw new MasterDataClientException("HTTP Response: " + response.getStatusCode() + " " + response.getMessage());
        } else {
            LOGGER.debug("HTTP Response code: " + response.getStatusCode());
        }
    }


    public long createProfile(DtoProfile profile) throws MasterDataClientException {
        String url = restURL + PROFILES;
        Resource resource = getResourceJsonContentAccept(url);
        String json = gson.toJson(profile);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.valueOf(extractId(response.getHeaders().getFirst(LOCATION)));
    }

    public long createProfileKey(DtoProfileKey profileKey) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILE_KEYS);
        String json = gson.toJson(profileKey);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.valueOf(extractId(response.getHeaders().getFirst(LOCATION)));
    }

    public DtoProfile getProfile(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoProfile.class);
    }

    public DtoProfileKey getProfileKey(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILE_KEYS + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoProfileKey.class);
    }

    public void addAttributeToProfile(Long id, DtoProfileAttribute attr) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILES + "/" + id + PROFILE_ATTRIBUTES_PATH);
        ClientResponse response = resource.post(attr);
        validateHttpResponseSuccessful(response);
    }

    public void deleteAttributeFromProfile(Long profileId, Long attrId) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILES + "/" + profileId + PROFILE_ATTRIBUTES_PATH + "/" + attrId);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
    }

    public void updateProfileKey(DtoProfileKey profileKey) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILE_KEYS + "/" + profileKey.getId());
        ClientResponse response = resource.put(profileKey);
        validateHttpResponseSuccessful(response);
    }

    public void deleteProfile(Long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + PROFILES + "/" + id);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
    }

    public void deleteProfileKey(Long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + PROFILE_KEYS + "/" + id);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
    }

    public DtoProfileList searchProfiles(String filter) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILES);
        ClientResponse response = resource.queryParam("q", filter).get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoProfileList.class);
    }

    public DtoProfileKeyList searchProfileKeys(String filter) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + PROFILE_KEYS);
        ClientResponse response = resource.queryParam("q", filter).get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoProfileKeyList.class);
    }

    public String getCurrentId(String prefix) {
        Resource resource = getResourceJsonContentAccept(restURL + IDS + "/" + prefix);
        ClientResponse response = resource.get();
        return response.getEntity(String.class);
    }

    public String getNextId(String prefix) {
        Resource resource = getResourceJsonContentAccept(restURL + IDS + "/" + prefix);
        ClientResponse response = resource.post("");
        return response.getEntity(String.class);
    }

    public List<DtoRole> searchRoles(String filter) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + ROLES);
        ClientResponse response = resource.queryParam("q", filter).get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoRole>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public String createRole(DtoRole dtoRole) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + ROLES);
        String json = gson.toJson(dtoRole);
        LOGGER.info("POST (create) to endpoint [" + restURL + ROLES + "]");
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public DtoRole getRole(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + ROLES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoRole.class);
    }

    public int updateRole(DtoRole dtoRole) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + ROLES);
        ClientResponse clientResponse = resource.put(dtoRole);
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode();
    }

    public int deleteRole(long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + ROLES + "/" + id);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode();
    }

    public List<DtoReferenceType> getReferenceTypes() throws MasterDataClientException {
        return getReferenceTypesWithFilter(null);
    }

    public List<DtoReferenceType> getDefaultReferenceTypes() throws MasterDataClientException {
        return getReferenceTypesWithFilter("default");
    }

    List<DtoReferenceType> getReferenceTypesWithFilter(String filter) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + REFERENCE_TYPES);
        ClientResponse response = query(filter, resource).get();
        LOGGER.debug("response code " + response.getStatusCode());
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoReferenceType>>() {
        }.getType();
        String json = response.getEntity(String.class);
        LOGGER.debug("data " + json);
        return gson.fromJson(json, list);
    }

    private Resource query(String filter, Resource resource) {
        return StringUtils.isNotEmpty(filter) ? resource.queryParam("q", filter) : resource;
    }

    public Long createReferenceType(DtoReferenceType dtoReferenceType) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + REFERENCE_TYPES);
        String json = gson.toJson(dtoReferenceType);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.valueOf(extractId(response.getHeaders().getFirst("Location")));
    }

    public DtoReferenceType getReferenceType(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + REFERENCE_TYPES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoReferenceType.class);
    }

    public boolean updateReferenceType(DtoReferenceType dtoReferenceType) throws MasterDataClientException {
        Objects.requireNonNull(dtoReferenceType);
        Objects.requireNonNull(dtoReferenceType.getId());
        Resource resource = getResourceJsonContentAccept(restURL + REFERENCE_TYPES + "/" + dtoReferenceType.getId());
        ClientResponse clientResponse = resource.put(dtoReferenceType);
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_OK;
    }

    public boolean deleteReferenceType(long id) throws MasterDataClientException {
        Resource resource = restClient.resource(restURL + REFERENCE_TYPES + "/" + id);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT;
    }

    public List<String> getNumbers(String name,int amount,String country, String location) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION+"/numbers/"+name).queryParam("amount", amount).queryParam("country",country).queryParam("location",location);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<String>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public Long createNumberCollection(NumberCollectionDto numberCollectionDto) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION);
        String json = gson.toJson(numberCollectionDto);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.valueOf(extractId(response.getHeaders().getFirst("Location")));
    }

    public NumberCollectionDto getNumberCollection(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(NumberCollectionDto.class);
    }

    public boolean updateNumberCollection(NumberCollectionDto numberCollectionDto) throws MasterDataClientException {
        Objects.requireNonNull(numberCollectionDto);
        Objects.requireNonNull(numberCollectionDto.getId());
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION + "/" + numberCollectionDto.getId());
        LOGGER.info(restURL + NUMBER_COLLECTION + "/" + numberCollectionDto.getId());
        ClientResponse clientResponse = resource.put(numberCollectionDto);
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_OK;
    }

    public boolean deleteNumberCollection(long id) throws MasterDataClientException {
        Resource resource = restClient.resource(restURL + NUMBER_COLLECTION + "/" + id);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT;
    }

    public List<NumberCollectionDto> searchNumberCollections(String filter, MasterDataConstants.DetailLevel format) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION).queryParam("q", filter).queryParam("format",format);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<NumberCollectionDto>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    //    ---------------------
    public Long createNumberRange(NumberRangeDto numberCollectionDto, Long collectionId) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION + "/" + collectionId + "/" + NUMBER_RANGES);
        String json = gson.toJson(numberCollectionDto);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.valueOf(extractId(response.getHeaders().getFirst("Location")));
    }

    public NumberRangeDto getNumberRange(Long collectionId,Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION +"/"+collectionId+  "/" +NUMBER_RANGES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(NumberRangeDto.class);
    }

    public boolean updateNumberRange(NumberRangeDto numberRangeDto) throws MasterDataClientException {
        Objects.requireNonNull(numberRangeDto);
        Objects.requireNonNull(numberRangeDto.getId());
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION +"/"+numberRangeDto.getNumberCollectionId()+ "/" + NUMBER_RANGES + "/" + numberRangeDto.getId());
        ClientResponse clientResponse = resource.put(numberRangeDto);
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_OK;
    }

    public boolean deleteNumberRange(Long collectionId, long id) throws MasterDataClientException {
        Resource resource = restClient.resource(restURL + NUMBER_COLLECTION +"/"+collectionId+  "/" +NUMBER_RANGES + "/" + id);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT;
    }

    public List<NumberRangeDto> getRangesForCollectionCollections(Long collectionId) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + NUMBER_COLLECTION+"/"+collectionId + "/" + NUMBER_RANGES);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<NumberRangeDto>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }
//--------

    public void createMDGPartyStatus(DtoMDGPartyStatus dtoMDGPartyStatus) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + MDG_PARTY_STATUSES);
        String json = gson.toJson(dtoMDGPartyStatus);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
    }

    public DtoMDGPartyStatus getMDGPartyStatus(String statusCode, String locality) throws MasterDataClientException {
        String clearedStatuCode = StringUtils.replace(statusCode, " ", "");
        String newLocality = StringUtils.replace(locality, " ", "");
        Resource resource = getResourceJsonContentAccept(restURL + MDG_PARTY_STATUSES + "/" + clearedStatuCode + "/" + newLocality);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoMDGPartyStatus.class);
    }

    public void updateMDGPartyStatus(DtoMDGPartyStatus mdgPartyStatus) throws MasterDataClientException {
        Objects.requireNonNull(mdgPartyStatus);
        Objects.requireNonNull(mdgPartyStatus.getLocality());
        Objects.requireNonNull(mdgPartyStatus.getStatusCode());
        Resource resource = getResourceJsonContentAccept(restURL + MDG_PARTY_STATUSES  + "/" + mdgPartyStatus.getStatusCode() + "/" + mdgPartyStatus.getLocality());
        ClientResponse clientResponse = resource.put(mdgPartyStatus);
        validateHttpResponseSuccessful(clientResponse);
    }

    public void deleteMDGPartyStatus(String statusCode, String locality) throws MasterDataClientException {
        String clearedStatuCode = StringUtils.replace(statusCode, " ", "");
        String newLocality = StringUtils.replace(locality, " ", "");
        Resource resource = restClient.resource(restURL + MDG_PARTY_STATUSES + "/" + clearedStatuCode + "/" + newLocality);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
    }

    public String createDangerousGoodsPackageType(DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_PACKAGE_TYPES);
        String json = gson.toJson(dtoDangerousGoodsPackageType);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        String uri = response.getHeaders().getFirst(LOCATION);
        return extractId(uri);
    }

    public DtoDangerousGoodsPackageType getDangerousGoodsPackageType(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoDangerousGoodsPackageType.class);
    }

    public void updateDangerousGoodsPackageType(DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType) throws MasterDataClientException {
        Objects.requireNonNull(dtoDangerousGoodsPackageType);
        Objects.requireNonNull(dtoDangerousGoodsPackageType.getShortAbbreviation());
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_PACKAGE_TYPES + "/" + dtoDangerousGoodsPackageType.getShortAbbreviation());
        ClientResponse clientResponse = resource.put(dtoDangerousGoodsPackageType);
        validateHttpResponseSuccessful(clientResponse);
    }

    public void deleteDangerousGoodsPackageType(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = restClient.resource(restURL + DANGEROUS_GOODS_PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
    }

    public List<DtoDangerousGoodsPackageType> getDangerousGoodsPackageTypes() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_PACKAGE_TYPES);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoDangerousGoodsPackageType>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public boolean existsDangerousGoodsPackageType(String shortAbbreviation) throws MasterDataClientException {
        Resource resource = restClient.resource(restURL + DANGEROUS_GOODS_PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

//    =================================================================================================================

    public DtoDangerousGoodsBundle getDangerousGoodsBundle(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_BUNDLES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoDangerousGoodsBundle.class);
    }

    public Long createDangerousGoodsBundle(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS_BUNDLES);
        String json = gson.toJson(dtoDangerousGoodsBundle);
        LOGGER.info("POST (create) to endpoint [" + restURL + DANGEROUS_GOODS_BUNDLES + "]");
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return Long.parseLong(extractId(response.getHeaders().getFirst(LOCATION)));
    }

    public int deleteDangerousGoodsBundle(long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + DANGEROUS_GOODS_BUNDLES + "/" + id);
        ClientResponse clientResponse = resource.delete();
        validateHttpResponseSuccessful(clientResponse);
        return clientResponse.getStatusCode();
    }

    public DtoDangerousGoods getDangerousGoods(Long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoDangerousGoods.class);
    }

    public List<DtoDangerousGoodsLite> searchCurrentDangerousGoodsList(String filter,TransportDivision division) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + DANGEROUS_GOODS);
        ClientResponse response = resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).queryParam("q", filter).queryParam("division",division).get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoDangerousGoods>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public DtoHsCode getHsCode(long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODES + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoHsCode.class);
    }

    public List<DtoHsCode> getHsCodes() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODES);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoHsCode>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public DtoHsCodeText getHsCodeText(long id) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODE_TEXTS + "/" + id);
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        return response.getEntity(DtoHsCodeText.class);
    }

    public List<DtoHsCodeText> getHsCodeTexts() throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODE_TEXTS + "/");
        ClientResponse response = resource.get();
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<DtoHsCodeText>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public String createHsCode(DtoHsCode dtoHsCode) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODES);
        String json = gson.toJson(dtoHsCode);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public List<String> createHsCodeS(List<DtoHsCode> HsCodes) throws MasterDataClientException {
        Resource resource = getResourceJsonContent(restURL + COMMODITIES + "/" + HSCODES);
        ClientResponse response = resource.post(HsCodes);
        validateHttpResponseSuccessful(response);
        Type list = new TypeToken<ArrayList<String>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, list);
    }

    public String createHsCodeText(DtoHsCodeText dtoHsCodeText) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODE_TEXTS);
        String json = gson.toJson(dtoHsCodeText);
        ClientResponse response = resource.post(json);
        validateHttpResponseSuccessful(response);
        return extractId(response.getHeaders().getFirst(LOCATION));
    }

    public void createHsCodeTexts(List<DtoHsCodeText> HsCodetexts) throws MasterDataClientException {
        Resource resource = getResourceJsonContent(restURL + COMMODITIES + "/" + HSCODE_TEXTS);
        ClientResponse response = resource.post(HsCodetexts);
        validateHttpResponseSuccessful(response);
    }

    public void updateHsCode(long id, DtoHsCode dtoHsCode) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODES + "/" + id);
        ClientResponse response = resource.put(dtoHsCode);
        validateHttpResponseSuccessful(response);
    }

    public void updateHsCodeText(long id, DtoHsCodeText dtoHsCodeText) throws MasterDataClientException {
        Resource resource = getResourceJsonContent(restURL + COMMODITIES + "/" + HSCODE_TEXTS + "/" + id);
        ClientResponse response = resource.put(dtoHsCodeText);
        validateHttpResponseSuccessful(response);
    }

    public void deleteHsCode(long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + COMMODITIES + "/" + HSCODES + "/" + id);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
    }

    public void deleteHsCodeText(long id) throws MasterDataClientException {
        Resource resource = getResource(restURL + COMMODITIES + "/" + HSCODE_TEXTS + "/" + id);
        ClientResponse response = resource.delete();
        validateHttpResponseSuccessful(response);
    }

    public List<DtoHsCodeText> searchHsCodeTexts(String filter, String language) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODE_TEXTS);
        ClientResponse response = resource.queryParam("q", filter).queryParam("language", language).get();
        validateHttpResponseSuccessful(response);
        Type listOfTestObject = new TypeToken<ArrayList<DtoHsCodeText>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, listOfTestObject);
    }

    public List<DtoHsCodeText> searchHsCodeTexts(String filter) throws MasterDataClientException {
        Resource resource = getResourceJsonContentAccept(restURL + COMMODITIES + "/" + HSCODE_TEXTS);
        ClientResponse response = resource.queryParam("q", filter).get();
        validateHttpResponseSuccessful(response);
        Type listOfTestObject = new TypeToken<ArrayList<DtoHsCodeText>>() {
        }.getType();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, listOfTestObject);
    }

    public boolean existsCurrency(String shortAbbreviation) {
        Resource resource = restClient.resource(restURL + CURRENCIES + "/" + shortAbbreviation);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsIncoterms(String shortAbbreviation) {
        Resource resource = restClient.resource(restURL + INCO_TERMS + "/" + shortAbbreviation);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsPackageType(String shortAbbreviation) {
        Resource resource = restClient.resource(restURL + PACKAGE_TYPES + "/" + shortAbbreviation);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsProfileKey(Long id) {
        Resource resource = restClient.resource(restURL + PROFILE_KEYS + "/" + id);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsProfile(Long id){
        Resource resource = restClient.resource(restURL + PROFILES + "/" + id);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsReferenceType(Long id){
        Resource resource = restClient.resource(restURL + REFERENCE_TYPES + "/" + id);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsRole(Long id)  {
        Resource resource = restClient.resource(restURL + ROLES + "/" + id);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }

    public boolean existsTransportLocation(Long id) {
        Resource resource = restClient.resource(restURL + TRANSPORT_LOCATIONS + "/" + id);
        ClientResponse clientResponse = resource.head();
        return clientResponse.getStatusCode() == 200;
    }


    private Resource getResource(String url) {
        return restClient.resource(url);
    }

    private Resource getResourceJsonContent(String url) {
        return getResource(url).contentType(MediaType.APPLICATION_JSON);
    }

    private Resource getResourceJsonContentAccept(String url) {
        return getResourceJsonContent(url).accept(MediaType.APPLICATION_JSON);
    }

}
