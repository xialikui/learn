package com.example.sharding_jdbc.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    /**
     * 一般数据 key = data
     *
     * @param value
     * @return
     */
    public R data(Object value) {
        super.put("data", value);
        return this;
    }

    /**
     * 分页 key = page
     *
     * @param iPage
     * @return
     */
    public R page(IPage<?> iPage) {
        super.put("page", new PageUtils(iPage));
        return this;
    }

    /**
     * 分页，key = page
     *
     * @param pageUtils
     * @return
     */
    public R page(PageUtils pageUtils) {
        super.put("page", pageUtils);
        return this;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
