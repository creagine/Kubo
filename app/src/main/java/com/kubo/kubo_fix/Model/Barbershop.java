package com.kubo.kubo_fix.Model;

public class Barbershop {

    private String Image;
    private String Nama;
    private String Alamat;
    private String Phone;

    public Barbershop() {
    }

    public Barbershop(String image, String nama, String alamat, String phone) {
        Image = image;
        Nama = nama;
        Alamat = alamat;
        Phone = phone;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
