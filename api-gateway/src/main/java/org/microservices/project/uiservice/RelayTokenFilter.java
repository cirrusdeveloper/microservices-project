package org.microservices.project.uiservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Custom Zuul filter implementation.
 * Ensures authorization headers are forwarded to microservices.
 * @author mcaleerj
 */
@Component
public class RelayTokenFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 10000;

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        @SuppressWarnings("unchecked") Set<String> ignoredHeaders = (Set<String>) context.get("ignoredHeaders");
        if (ignoredHeaders != null) {
            ignoredHeaders.remove("authorization");
        }
        return null;
    }
}