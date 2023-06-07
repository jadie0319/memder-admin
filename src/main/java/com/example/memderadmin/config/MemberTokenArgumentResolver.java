package com.example.memderadmin.config;

import com.example.memderadmin.app.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;


public class MemberTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public MemberTokenArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        parameter.hasParameterAnnotation(AuthenticationMember.class);
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String tokenValue = getTokenValue(httpServletRequest);
        return authService.authentication(tokenValue, LocalDateTime.now());
    }

    private static String getTokenValue(HttpServletRequest httpServletRequest) {
        Enumeration<String> headers = httpServletRequest.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
            String element = headers.nextElement();
            if (element.startsWith("Bearer")) {
                httpServletRequest.setAttribute("loginToken", "Bearer");
                return element.substring("Bearer".length()).trim();
            }
        }
        return null;
    }
}
