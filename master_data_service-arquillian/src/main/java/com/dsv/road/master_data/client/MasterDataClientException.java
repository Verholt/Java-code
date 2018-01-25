package com.dsv.road.master_data.client;

/**
 * Created by EXT.K.Theilgaard on 18-05-2015.
 */
public class MasterDataClientException extends Exception {
    private static final long serialVersionUID = 1L;

    public MasterDataClientException () {
        super(); 
    }

    public MasterDataClientException(String message) {
        super(message);
    }

    public MasterDataClientException(Throwable cause) {
        super(cause);
    }

    public MasterDataClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
