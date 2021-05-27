package com.zeynep.mymessageproject.Model;

public class Chat {
    String alici;
    String gonderen;
    String mesaj;
    String resim;
    Boolean goruldu;

    public Chat(String alici, String gonderen, String mesaj, String resim, Boolean goruldu) {
        this.alici = alici;
        this.gonderen = gonderen;
        this.mesaj = mesaj;
        this.resim = resim;
        this.goruldu = goruldu;
    }

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public Boolean getGoruldu() {
        return goruldu;
    }

    public void setGoruldu(Boolean goruldu) {
        this.goruldu = goruldu;
    }

    public Chat() {
    }
}
