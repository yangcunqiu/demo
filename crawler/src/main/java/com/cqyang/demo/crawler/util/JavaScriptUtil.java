package com.cqyang.demo.crawler.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JavaScriptUtil {

    public static ScriptEngine getScriptEngine(String jsFilePath) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.eval(new InputStreamReader(ClasspathFileReadUtil.getFileAsStream(jsFilePath), StandardCharsets.UTF_8));
        return scriptEngine;
    }
}
