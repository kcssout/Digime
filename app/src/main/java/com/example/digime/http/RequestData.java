package com.example.digime.http;

public class RequestData {

        String Bncd;
        String Acno;
        Header Header;

        public RequestData(Header head , String Bncd, String Acno){
            this.Header = head;
            this.Bncd=Bncd;
            this.Acno=Acno;
        }

    public String getBncd() {
        return Bncd;
    }

    public void setBncd(String bncd) {
        Bncd = bncd;
    }

    public String getAcno() {
        return Acno;
    }

    public void setAcno(String acno) {
        Acno = acno;
    }

    public Header getHeader() {
        return Header;
    }

    public void setHeader(Header header) {
        this.Header = header;
    }
}
