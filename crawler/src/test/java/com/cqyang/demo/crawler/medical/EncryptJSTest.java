package com.cqyang.demo.crawler.medical;

import com.cqyang.demo.crawler.util.JavaScriptUtil;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class EncryptJSTest {

    private static final ScriptEngine scriptEngine;
    static {
        try {
            scriptEngine = JavaScriptUtil.getScriptEngine("medical/encrypt.js");
        } catch (ScriptException e) {
            throw new RuntimeException("get scriptEngine fail: " + e);
        }
    }

    @Test
    public void testEnc() throws ScriptException {
        scriptEngine.eval("testHeader()");
        scriptEngine.eval("testEnc()");
    }

    @Test
    public void testDec() throws ScriptException {
        scriptEngine.eval("testDec()");
    }
}
