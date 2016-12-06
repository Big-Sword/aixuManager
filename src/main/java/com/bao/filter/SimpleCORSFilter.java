/*
 * Copyright (c) 2015. 上海趣医网络科技有限公司 版权所有 Shanghai QuYi Network Technology Co., Ltd. All Rights
 * Reserved.
 *
 * This is NOT a freeware,use is subject to license terms.
 */

package com.bao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 允许跨域.
 *
 * @author fuzhengxin
 * @version 1.0, 2015-04-13
 * @since 1.0
 */
@Component
public class SimpleCORSFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 初始化
	 *
	 * @param filterConfig
	 * @throws ServletException
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// left blank intentionally
	}

	/**
	 * 过滤
	 *
	 * @param req
	 * @param res
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,accept,Content-Type,authorization,x-token");
		if (request.getMethod().equalsIgnoreCase("options")) {
			response.setStatus(200);
			return;
		}
		String uri = request.getRequestURI();
		String token = request.getHeader("x-token");
		logger.info("token :{}", token);
		logger.info("uri:{}", uri);
		if (uri.indexOf("/common") < 0) {//
			if (StringUtils.isNotBlank((token))) {
				response.setStatus(401);
				return;
			} else if (!token.equals("q1w2e3r4t5y6u7i8o9p0")) {
				String id = stringRedisTemplate.opsForValue().get("USER_TOKEN_" + token);
				logger.info("loginId:{}", id);
				if (StringUtils.isNotBlank(id)) {
					response.setStatus(402);
					return;
				}
				request.setAttribute("loginId", id);
			} else {
				request.setAttribute("loginId", "testId");
			}
		} else {
			request.setAttribute("loginId", "commonId");
		}
		filterChain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// left blank intentionally
	}
}
