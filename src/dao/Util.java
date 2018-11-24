package dao;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Util {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public static String getParameter(String name) {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		return params.get(name);
	}

	public static void addToSession(String key, String value) {
		HttpSession s = getSession();
		s.setAttribute(key, value);
	}

	public static String getFullname() {
		HttpSession s = getSession();
		return s.getAttribute("fullname").toString();
	}

	public static String getUname() {
		HttpSession s = getSession();
		return s.getAttribute("uname").toString();
	}

	public static void terminateSession() {
		HttpSession s = getSession();
		s.invalidate();
	}
}
