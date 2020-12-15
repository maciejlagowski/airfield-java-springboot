package io.github.maciejlagowski.airfield.filter;

import io.github.maciejlagowski.airfield.AirfieldApplication;
import io.github.maciejlagowski.airfield.exception.UserNotActiveException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (MalformedJwtException e) {
            if (AirfieldApplication.debug)
                e.printStackTrace();
            httpServletResponse.sendError(401, "User not logged in");
        } catch (UserNotActiveException e) {
            if (AirfieldApplication.debug)
                e.printStackTrace();
            httpServletResponse.sendError(401, e.getMessage());
        } catch (Exception e) {
            if (AirfieldApplication.debug)
                e.printStackTrace();
            httpServletResponse.sendError(500, e.getMessage());
        }
    }
}
