package com.parkingSpot.app.authentication.Customizations;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    response.addHeader("Acess-Control-Allow-Origin", "*");
    response.addHeader("Acess-Control-Allow-Methods", "*");
    response.addHeader("Acess-Control-Allow-Headers", "*");

    filterChain.doFilter(request, response);
  }
}
