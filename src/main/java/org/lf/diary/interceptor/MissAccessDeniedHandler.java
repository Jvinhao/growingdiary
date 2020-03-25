package org.lf.diary.interceptor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/13 18:09
 * @Description: TODO
 */
@Component
public class MissAccessDeniedHandler extends AccessDeniedHandlerImpl {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException exception) throws IOException, ServletException {
        if (exception instanceof MissingCsrfTokenException && isSessionInvalid(req)) {
            requestCache.saveRequest(req, res);
            req.getRequestDispatcher("/login").forward(req, res);
        }
        super.handle(req, res, exception);
    }
    private boolean isSessionInvalid(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);
            return session == null || !req.isRequestedSessionIdValid();
        }
        catch (IllegalStateException ex) {
            return true;
        }
    }

}
