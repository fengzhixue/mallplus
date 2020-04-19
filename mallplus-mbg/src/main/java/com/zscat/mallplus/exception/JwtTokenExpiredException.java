package com.zscat.mallplus.exception;

/**
 * 自定义异常
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2016年10月27日 下午10:11:27
 */
public class JwtTokenExpiredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errmsg;
    private int errno = 1001;

    public JwtTokenExpiredException(String errmsg) {
        super(errmsg);
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public JwtTokenExpiredException(String errmsg, Throwable e) {
        super(errmsg, e);
        this.errmsg = errmsg;
    }

    public JwtTokenExpiredException(String errmsg, int errno) {
        super(errmsg);
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public JwtTokenExpiredException(String errmsg, int errno, Throwable e) {
        super(errmsg, e);
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }
}
