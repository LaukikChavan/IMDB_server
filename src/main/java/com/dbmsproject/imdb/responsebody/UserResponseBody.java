package com.dbmsproject.imdb.responsebody;

import com.dbmsproject.imdb.enums.RESPONSE_CODE;

public class UserResponseBody {
    public RESPONSE_CODE code;
    public String res;

    public RESPONSE_CODE getCode() {
        return code;
    }

    public void setCode(RESPONSE_CODE code) {
        this.code = code;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public UserResponseBody() {
    }

    public UserResponseBody(RESPONSE_CODE code, String res) {
        this.code = code;
        this.res = res;
    }
}
