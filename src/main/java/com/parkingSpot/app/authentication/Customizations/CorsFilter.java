package com.parkingSpot.app.authentication.Customizations;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "*");
    response.addHeader("Access-Control-Allow-Headers", "*");

    if (request.getMethod().equals("OPTIONS")) {
      response.setStatus(200);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
