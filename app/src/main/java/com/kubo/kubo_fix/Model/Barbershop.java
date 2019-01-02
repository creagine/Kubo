package com.kubo.kubo_fix.Model;

public class Barbershop {

    private String Image;
    private String Nama;
    private String Alamat;

    public Barbershop() {
    }

    public Barbershop(String image, String nama, String alamat) {
        Image = image;
        Nama = nama;
        Alamat = alamat;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }
}
