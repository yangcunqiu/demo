package com.cqyang.demo.crawler.medical;

import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.FixedHospitalRequest;
import com.cqyang.demo.crawler.medical.model.MedicalEncData;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptData;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptRequest;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptResponse;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import com.cqyang.demo.crawler.util.JavaScriptUtil;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Map;

public class EncryptUtil {
    private static final ScriptEngine scriptEngine;

    static {
        try {
            scriptEngine = JavaScriptUtil.getScriptEngine("medical/encrypt.js");
        } catch (ScriptException e) {
            throw new RuntimeException("get scriptEngine fail: " + e);
        }
    }

    public static Map<String, Object> getHeader() throws ScriptException {
        Object data = scriptEngine.eval("getHeaders()");
        if (data instanceof ScriptObjectMirror) {
            return (ScriptObjectMirror) data;
        }
        return null;
    }

    public static MedicalEncryptRequest encryptFixedHospital(FixedHospitalRequest fixedHospitalRequest) throws ScriptException {
        scriptEngine.put("fixedHospitalRequest", fixedHospitalRequest);
        Object data = scriptEngine.eval("encryptFixedHospital(fixedHospitalRequest)");
        if (data instanceof ScriptObjectMirror) {
            return buildMedicalEncryptRequest((ScriptObjectMirror) data);
        }
        return null;
    }

    public static <T> MedicalPageResponse<T> decrypt(MedicalEncryptResponse medicalEncryptResponse, MedicalBuilder<T> medicalBuilder) throws ScriptException {
        scriptEngine.put("medicalEncryptResponse", medicalEncryptResponse);
        Object data = scriptEngine.eval("decryptMedical(medicalEncryptResponse)");
        if (data instanceof ScriptObjectMirror) {
            return medicalBuilder.buildPageResponse((ScriptObjectMirror) data);
        }
        return null;
    }

    private static MedicalEncryptRequest buildMedicalEncryptRequest(ScriptObjectMirror scriptObjectMirror) {
        MedicalEncryptRequest medicalEncryptRequest = new MedicalEncryptRequest();
        // MedicalEncryptData
        Object medicalEncryptDataObject = scriptObjectMirror.get("data");
        if (medicalEncryptDataObject instanceof ScriptObjectMirror) {
            ScriptObjectMirror medicalEncryptDataScriptObjectMirror = (ScriptObjectMirror) medicalEncryptDataObject;
            MedicalEncryptData medicalEncryptData = new MedicalEncryptData();
            // MedicalEncData
            Object medicalEncDataObject = medicalEncryptDataScriptObjectMirror.get("data");
            if (medicalEncDataObject instanceof ScriptObjectMirror) {
                ScriptObjectMirror medicalEncDataScriptObjectMirror = (ScriptObjectMirror) medicalEncDataObject;
                MedicalEncData medicalEncData = new MedicalEncData();
                medicalEncData.setEncData(String.valueOf(medicalEncDataScriptObjectMirror.get("encData")));
                medicalEncryptData.setData(medicalEncData);
            }
            medicalEncryptData.setAppCode(String.valueOf(medicalEncryptDataScriptObjectMirror.get("appCode")));
            medicalEncryptData.setVersion(String.valueOf(medicalEncryptDataScriptObjectMirror.get("version")));
            medicalEncryptData.setEncType(String.valueOf(medicalEncryptDataScriptObjectMirror.get("encType")));
            medicalEncryptData.setTimestamp(String.valueOf(medicalEncryptDataScriptObjectMirror.get("timestamp")));
            medicalEncryptData.setSignData(String.valueOf(medicalEncryptDataScriptObjectMirror.get("signData")));
            medicalEncryptData.setSignType(String.valueOf(medicalEncryptDataScriptObjectMirror.get("signType")));
            medicalEncryptRequest.setData(medicalEncryptData);
        }
        return medicalEncryptRequest;
    }
}
