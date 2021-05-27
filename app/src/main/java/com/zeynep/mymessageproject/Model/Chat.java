package com.zeynep.mymessageproject.Model;

public class Chat {
    String alici;
    String gonderen;
    String mesaj;
    String resim;
    Boolean goruldu;
    String saat;
    String tarih;

    public Chat(String alici, String gonderen, String mesaj, String resim, Boolean goruldu, String saat, String tarih) {
        this.alici = alici;
        this.gonderen = gonderen;
        this.mesaj = mesaj;
        this.resim = resim;
        this.goruldu = goruldu;
        this.saat = saat;
        this.tarih = tarih;
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

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public Chat() {
    }
}
