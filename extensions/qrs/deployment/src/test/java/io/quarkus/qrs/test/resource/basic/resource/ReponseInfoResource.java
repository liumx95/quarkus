package io.quarkus.qrs.test.resource.basic.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;

import io.quarkus.qrs.runtime.util.HttpHeaderNames;
import io.quarkus.qrs.test.simple.PortProviderUtil;

@Path("/")
public class ReponseInfoResource {
    private static Logger logger = Logger.getLogger(ReponseInfoResource.class);

    @Path("/simple")
    @GET
    public String get(@QueryParam("abs") String abs) {
        logger.info("abs query: " + abs);
        URI base;
        if (abs == null) {
            base = PortProviderUtil.createURI("/new/one");
        } else {
            base = PortProviderUtil.createURI("/" + abs + "/new/one");
        }
        Response response = Response.temporaryRedirect(URI.create("new/one")).build();
        URI uri = (URI) response.getMetadata().getFirst(HttpHeaderNames.LOCATION);
        logger.info("Location uri: " + uri);
        Assertions.assertEquals(base.getPath(), uri.getPath());
        return "CONTENT";
    }
}
