package com.redhat.demo.bookinfo.productpage;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiRoute extends RouteBuilder {

    public ApiRoute(@Autowired CamelContext context) {
        super(context);
    }

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .bindingPackageScan("com.redhat.demo.bookinfo.productpage")
                .apiContextPath("/api-docs");

        
        rest().openApi().specification("productpage-api.json").missingOperation("ignore");
        

        
        from("direct:sampleOperationId")
                .removeHeaders("*")
                .to("rest-openapi:details-api.json#getProductDetails?host={{openapi.client.details.host}}");
        

    }
}
