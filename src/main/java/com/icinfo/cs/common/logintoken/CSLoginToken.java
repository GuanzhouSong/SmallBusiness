package com.icinfo.cs.common.logintoken;

import com.icinfo.framework.security.shiro.LoginToken;

/**
 * 描述：
 *
 * @author 朱德峰
 * @date 2016/12/23
 */
public class CSLoginToken extends LoginToken {
    private String userType;

    public CSLoginToken(String username, String password) {
        super(username, password);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
