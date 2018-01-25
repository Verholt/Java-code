package com.dsv.road.masterdata.importtool;

import com.dsv.shared.importtool.RemoteImportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by EXT.K.Theilgaard on 27-05-2015.
 */
public class MasterdataRemoteImportConfiguration extends RemoteImportConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterdataRemoteImportConfiguration.class);

    /**
     * Constructs a new RemoteImportConfiguration from a specified path and with a specified input file
     * @param configPath
     */
    public MasterdataRemoteImportConfiguration(String configPath) {
        File configFile = new File(configPath);
        if (configFile.exists()) {
            LOGGER.info("Loading configuration file: " + configPath);
            try {
                readFromFile(configFile);
            } catch (JAXBException e) {
                LOGGER.error("Error parsing config file.", e);
                System.exit(-1);
            }
        } else {
            LOGGER.error("Configuration file not found: (" + configPath + ")");
        }
    }
}
