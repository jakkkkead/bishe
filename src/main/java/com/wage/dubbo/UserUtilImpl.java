package com.wage.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.yun.comom.UserUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

;import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Service
public class UserUtilImpl implements UserUtil {
    @Override
    public Object getSessionUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return session.getAttribute("user");
    }
}
