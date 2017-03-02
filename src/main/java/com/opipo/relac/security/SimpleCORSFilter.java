package com.opipo.relac.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

	public SimpleCORSFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("origin");
		if (!response.getHeaders("Access-Control-Allow-Origin").stream().anyMatch(p -> p.contains(origin))) {
			response.addHeader("Access-Control-Allow-Origin", origin);
			response.addHeader("Access-Control-Expose-Headers", "X-Auth-Token");
		}
		response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT, OPTION");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Origin, Authorization, X-Auth-Token");

		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}
