package com.example.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {

	/**
	 * 將字串渲染到客戶端。 TODO: 怪
	 * @param response 渲染對象
	 * @param string 待渲染的字串
	 *
	 * @return null
	 */
	public static String renderString(HttpServletResponse response, String string) {

		try {
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
