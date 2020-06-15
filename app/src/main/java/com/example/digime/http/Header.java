package com.example.digime.http;

import com.google.android.gms.common.api.Api;

public class Header {
    String ApiNm;
    String Tsymd;
    String Trtm;
    String Iscd;
    String FintechApsno;
    String ApiSvcCd;
    String IsTuno;
    String AccessToken;

    public String getRpcd() {
        return Rpcd;
    }

    public void setRpcd(String rpcd) {
        Rpcd = rpcd;
    }

    public String getRsms() {
        return Rsms;
    }

    public void setRsms(String rsms) {
        Rsms = rsms;
    }

    String Rpcd;
    String Rsms;

    public Header(String ApiNm , String Tsymd, String Trtm, String Iscd, String FintechApsno, String ApiSvcCd, String IsTuno , String AccessToken) {
        this.AccessToken=AccessToken;
        this.ApiNm= ApiNm;
        this.Tsymd=Tsymd;
        this.Trtm=Trtm;
        this.Iscd=Iscd;
        this.FintechApsno=FintechApsno;
        this.ApiSvcCd=ApiSvcCd;
        this.IsTuno=IsTuno;
    }

    @Override
    public String toString() {
        return "Header{" +
                "ApiNm='" + ApiNm + '\'' +
                ", Tsymd='" + Tsymd + '\'' +
                ", Trtm='" + Trtm + '\'' +
                ", Iscd='" + Iscd + '\'' +
                ", FintechApsno='" + FintechApsno + '\'' +
                ", ApiSvcCd='" + ApiSvcCd + '\'' +
                ", IsTuno='" + IsTuno + '\'' +
                ", AccessToken='" + AccessToken + '\'' +
                ", Rpcd='" + Rpcd + '\'' +
                ", Rsms='" + Rsms + '\'' +
                '}';
    }

    public Header(String ApiNm , String Tsymd, String Trtm, String Iscd, String FintechApsno, String ApiSvcCd, String IsTuno , String AccessToken, String Rpcd, String Rsms) {
        this.AccessToken=AccessToken;
        this.ApiNm= ApiNm;
        this.Tsymd=Tsymd;
        this.Trtm=Trtm;
        this.Iscd=Iscd;
        this.FintechApsno=FintechApsno;
        this.ApiSvcCd=ApiSvcCd;
        this.IsTuno=IsTuno;
        this.Rpcd= Rpcd;
        this.Rsms = Rsms;
    }


    public String getApiNm() {
        return ApiNm;
    }

    public void setApiNm(String apiNm) {
        ApiNm = apiNm;
    }

    public String getTsymd() {
        return Tsymd;
    }

    public void setTsymd(String tsymd) {
        Tsymd = tsymd;
    }

    public String getTrtm() {
        return Trtm;
    }

    public void setTrtm(String trtm) {
        Trtm = trtm;
    }

    public String getIscd() {
        return Iscd;
    }

    public void setIscd(String iscd) {
        Iscd = iscd;
    }

    public String getFintechApsno() {
        return FintechApsno;
    }

    public void setFintechApsno(String fintechApsno) {
        FintechApsno = fintechApsno;
    }

    public String getApiSvcCd() {
        return ApiSvcCd;
    }

    public void setApiSvcCd(String apiSvcCd) {
        ApiSvcCd = apiSvcCd;
    }

    public String getIsTuno() {
        return IsTuno;
    }

    public void setIsTuno(String isTuno) {
        IsTuno = isTuno;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }


}
