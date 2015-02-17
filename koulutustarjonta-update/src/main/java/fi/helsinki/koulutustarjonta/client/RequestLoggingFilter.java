package fi.helsinki.koulutustarjonta.client;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLoggingFilter extends ClientFilter {

    private static final Logger LOG = LoggerFactory.getLogger(TarjontaClient.class);

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
        ClientResponse response = getNext().handle(request);
        LOG.info(String.format("%s - HTTP %s", request.getURI(), response.getStatus()));
        return response;
    }
}
