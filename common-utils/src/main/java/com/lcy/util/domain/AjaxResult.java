package com.lcy.util.domain;

import java.util.HashMap;
import java.util.Objects;

/**
 * 统一响应结构：{code, msg, data}
 * code = 0 表示成功，500 表示失败。
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 500;

    public AjaxResult() {
    }

    private AjaxResult(int code, String msg, Object data) {
        put("code", code);
        put("msg", msg);
        if (Objects.nonNull(data)) {
            put("data", data);
        }
    }

    public static AjaxResult success() {
        return success("操作成功");
    }

    public static AjaxResult success(String msg) {
        return new AjaxResult(CODE_SUCCESS, msg, null);
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(CODE_SUCCESS, "操作成功", data);
    }

    public static AjaxResult error() {
        return error("操作失败");
    }

    public static AjaxResult error(String msg) {
        return new AjaxResult(CODE_ERROR, msg, null);
    }
}
