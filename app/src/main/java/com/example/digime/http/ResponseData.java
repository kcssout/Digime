package com.example.digime.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseData {
    Header Header;
    String Bncd;
    String Dpnm;
    String Acno;


    public ResponseData(Header head, String Bncd, String Dpnm, String Acno){
        this.Header=head;
        this.Bncd= Bncd;
        this.Dpnm = Dpnm;
        this.Acno = Acno;
    }

    public com.example.digime.http.Header getHeader() {
        return Header;
    }

    public void setHeader(com.example.digime.http.Header header) {
        Header = header;
    }

    public String getBncd() {
        return Bncd;
    }

    public void setBncd(String bncd) {
        Bncd = bncd;
    }

    public String getDpnm() {
        return Dpnm;
    }

    public void setDpnm(String dpnm) {
        Dpnm = dpnm;
    }

    public String getAcno() {
        return Acno;
    }

    public void setAcno(String acno) {
        Acno = acno;
    }


    @Override
    public String toString() {
        return "{" + Header +
                ", Bncd='" + Bncd + '\'' +
                ", Dpnm='" + Dpnm + '\'' +
                ", Acno='" + Acno + '\'' +
                '}';
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
