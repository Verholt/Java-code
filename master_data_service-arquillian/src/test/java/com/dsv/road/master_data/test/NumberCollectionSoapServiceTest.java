package com.dsv.road.master_data.test;

import com.dsv.road.master_data.client.ClientApplication;
import com.dsv.road.master_data.client.MasterDataClient;
import com.dsv.road.masterdata.soap.NumberFountainResource;
import com.dsv.road.masterdata.soap.NumberFountainResourceService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;

@RunWith(Arquillian.class)
public class NumberCollectionSoapServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberCollectionSoapServiceTest.class);

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.createFromZipFile(WebArchive.class, new File("../master_data_service-web/target/master_data_service-web.war"));
        war.setWebXML("web_without_security.xml");
        // Add all tests to war
        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve().withTransitivity().asFile();
        war.addAsLibraries(files);
        war.setWebXML("web_without_security.xml");
        // Inject beans.xml and mocks
        String ejbPath = PackageMatcher.getMasterDataServiceEJB(war.toString(true));
        JavaArchive ejb = war.getAsType(JavaArchive.class, ejbPath);
        ejb.addAsResource("META-INF/beans.xml");
        // Inject mock package
        ejb.addPackage("com.dsv.road.master_data.mocks");
        ejb.addPackage("com.dsv.road.master_data.client");
        return war;
    }

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("javax.xml.bind.JAXBContext", "com.sun.xml.internal.bind.v2.ContextFactory"); // Due to: http://stackoverflow.com/questions/14162159
    }

    public static boolean hostAvailabilityCheck(String serverAddress, int tcpServerPort) {
        try (Socket s = new Socket(serverAddress, tcpServerPort)) {
            return true;
        } catch (IOException ex) {
            LOGGER.error("Could not connect to " + serverAddress, ex);
        }
        return false;
    }

    @Test
    public void testNumberCollectionNumberService() throws Exception {
        // following properties is set to display xml soap message in the
        // console.
        System.setProperty(
                "com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump",
                "true");
        System.setProperty(
                "com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump",
                "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump",
                "true");
        System.setProperty(
                "com.sun.xml.internal.ws.transport.http.HttpAdapter.dump",
                "true");
        URL soapUrl;
        int restPort;
        LOGGER.debug("Connecting to Party SOAP service...");
        if (hostAvailabilityCheck("localhost", 9091)) {
            restPort = 9091;
            soapUrl = new URL("http://localhost:9091/master_data_service-web/NumberFountainResourceService?wsdl");
        } else if (hostAvailabilityCheck("localhost", 10039)) {
            restPort = 10039;
            soapUrl = new URL("http://localhost:10039/master_data_service-web/NumberFountainResourceService?wsdl");
            /*soapUrl = new URL("http://qa.roadshipment.dsv.com/party_master_data_service-web/MdgToCWFService/MdgToCWFService.wsdl"); */
        } else {
            LOGGER.debug("Server is not running");
            return;
        }
        MasterDataClient client = new MasterDataClient(ClientApplication.baseURL(restPort));
        int amount = 10;
        String name = "testName";
        String country = "testCountry";
        String location = "testLocation";
        String outputFormat = "(402)5706593{#########}";
        Long id = AppTestPart.createNumberCollection(name, country, location, outputFormat, client);
        NumberFountainResourceService service = new NumberFountainResourceService(soapUrl);
        NumberFountainResource port = service.getNumberFountainResourcePort();
        List<String> list = port.getNumberList(name, amount, country, location);
        Assert.assertEquals(list.size(), amount);
        client.deleteNumberCollection(id);
    }
}
