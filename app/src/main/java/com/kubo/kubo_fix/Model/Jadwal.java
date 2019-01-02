package com.kubo.kubo_fix.Model;

public class Jadwal {

    private String nama ;
    private String waktu ;


    public Jadwal() {
    }

    public Jadwal(String nama, String waktu) {
        this.nama = nama;
        this.waktu = waktu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
