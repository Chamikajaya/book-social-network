package com.chamika.books_project.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
// when a client sends a request to a protected endpoint without valid authentication credentials, the commence method of DelegatedAuthEntryPoint class is invoked.
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {  // * AuthenticationEntryPoint is the first point of contact for the incoming request. It is called when the user is not authenticated.

    private final HandlerExceptionResolver handlerExceptionResolver;

    public DelegatedAuthEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    // * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource that requires authentication.

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}