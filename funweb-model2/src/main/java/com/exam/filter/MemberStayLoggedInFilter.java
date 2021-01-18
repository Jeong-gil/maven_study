package com.exam.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class MemberStayLoggedInFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext application = fConfig.getServletContext();
		application.setAttribute("test", "데이터");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// 다음 필터로 실행을 넘기는 작업을 수행함
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
