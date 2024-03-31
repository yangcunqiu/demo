package com.cqyang.demo.crawler.medical.builder;

import com.cqyang.demo.crawler.medical.EncryptUtil;
import com.cqyang.demo.crawler.medical.model.FixedHospital;
import com.cqyang.demo.crawler.medical.model.FixedHospitalRequest;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptRequest;
import com.cqyang.demo.crawler.medical.model.enums.MedicalEnum;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptException;

public class FixedHospitalBuilder extends MedicalBuilder<FixedHospital> {

    @Override
    public int getType() {
        return MedicalEnum.FIXED_HOSPITAL.getType();
    }

    @Override
    protected String getUrl() {
        return "https://fuwu.nhsa.gov.cn/ebus/fuwu/api/nthl/api/CommQuery/queryFixedHospital";
    }

    @Override
    protected MedicalEncryptRequest buildEncryptRequest() throws ScriptException {
        FixedHospitalRequest fixedHospitalRequest = new FixedHospitalRequest();
        fixedHospitalRequest.setRegnCode("110000");
        fixedHospitalRequest.setPageNum(1);
        fixedHospitalRequest.setPageSize(10);
        return EncryptUtil.encryptFixedHospital(fixedHospitalRequest);
    }

    @Override
    protected FixedHospital buildPageListData(ScriptObjectMirror scriptObjectMirror) {
        FixedHospital fixedHospital = new FixedHospital();
        fixedHospital.setMedinsTypeName(String.valueOf(scriptObjectMirror.get("medinsTypeName")));
        fixedHospital.setMedinsNatu(String.valueOf(scriptObjectMirror.get("medinsNatu")));
        fixedHospital.setHospLv(String.valueOf(scriptObjectMirror.get("hospLv")));
        fixedHospital.setMedinsType(String.valueOf(scriptObjectMirror.get("medinsType")));
        fixedHospital.setUscc(String.valueOf(scriptObjectMirror.get("uscc")));
        fixedHospital.setOpenElec(String.valueOf(scriptObjectMirror.get("openElec")));
        fixedHospital.setBusinessLvEpc(String.valueOf(scriptObjectMirror.get("businessLvEpc")));
        fixedHospital.setLnt(String.valueOf(scriptObjectMirror.get("lnt")));
        fixedHospital.setBusinessLvCfc(String.valueOf(scriptObjectMirror.get("businessLvCfc")));
        fixedHospital.setBusinessLvMpc(String.valueOf(scriptObjectMirror.get("businessLvMpc")));
        fixedHospital.setMedinsLvName(String.valueOf(scriptObjectMirror.get("medinsLvName")));
        fixedHospital.setMedinsLv(String.valueOf(scriptObjectMirror.get("medinsLv")));
        fixedHospital.setMedinsName(String.valueOf(scriptObjectMirror.get("medinsName")));
        fixedHospital.setAddr(String.valueOf(scriptObjectMirror.get("addr")));
        fixedHospital.setMedinsCode(String.valueOf(scriptObjectMirror.get("medinsCode")));
        fixedHospital.setLat(String.valueOf(scriptObjectMirror.get("lat")));
        fixedHospital.setBusinessLvEbc(String.valueOf(scriptObjectMirror.get("businessLvEbc")));
        return fixedHospital;
    }
}
