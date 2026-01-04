package com.soumyadeep.demo.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soumyadeep.demo.utils.Constants;
import com.soumyadeep.demo.utils.DemoUtils;

import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component @Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestTracingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    
        long startTime = System.currentTimeMillis();
        
        String requestId = request.getHeader(Constants.HDR_X_REQUEST_ID);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        
        String sourceIp = (request.getHeader(Constants.SOURCE_IP) == null) ? DemoUtils.getSourceIp(request) : request.getHeader(Constants.SOURCE_IP);
        String apiEndpoint = request.getMethod() + " " + request.getRequestURI();
        
        Baggage baggage = Baggage.builder()
                .put(Constants.HDR_X_REQUEST_ID, requestId)
                .build();

        Span currentSpan = Span.current();
        currentSpan.setAttribute(Constants.SOURCE_IP, sourceIp);
        currentSpan.setAttribute(Constants.API_ENDPOINT, apiEndpoint);
        currentSpan.setAttribute(Constants.LOG_TYPE, Constants.REQUEST);

        MDC.put(Constants.HDR_X_REQUEST_ID, requestId);
        MDC.put(Constants.SOURCE_IP, sourceIp);
        MDC.put(Constants.API_ENDPOINT, apiEndpoint);
        MDC.put(Constants.LOG_TYPE, Constants.REQUEST);
        MDC.put(Constants.SERVICE_NAME,Constants.DEMO_SERVICE);

        try (Scope scope = baggage.makeCurrent()) {
            chain.doFilter(request, response); 
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            
            currentSpan.setAttribute(Constants.LOG_TYPE, Constants.RESPONSE);
            currentSpan.setAttribute(Constants.STATUS, String.valueOf(response.getStatus()));
            currentSpan.setAttribute(Constants.DURATION, duration);

            MDC.put(Constants.LOG_TYPE, Constants.RESPONSE);
            MDC.put(Constants.STATUS, String.valueOf(response.getStatus()));
            MDC.put(Constants.DURATION, String.valueOf(duration));
            
            log.info("Request Completed");
            MDC.clear();
        }
    }
}