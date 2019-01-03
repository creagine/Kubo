package com.kubo.kubo_fix.Model;

public class Order {

    private String idOrder;
    private String idUser;
    private String atasnama;
    private String idBarbershop;
    private String namaBarbershop;
    private String barberman;
    private String service;
    private String totalharga;
    private String jadwal;
    private String status;
    private String phoneBarbershop;
    private String phoneUser;


    public Order() {
    }

    public Order(String idOrder, String idUser, String atasnama, String idBarbershop,
                 String namaBarbershop, String barberman, String service, String totalharga,
                 String jadwal, String status, String phoneBarbershop, String phoneUser) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.atasnama = atasnama;
        this.idBarbershop = idBarbershop;
        this.namaBarbershop = namaBarbershop;
        this.barberman = barberman;
        this.service = service;
        this.totalharga = totalharga;
        this.jadwal = jadwal;
        this.status = status;
        this.phoneBarbershop = phoneBarbershop;
        this.phoneUser = phoneUser;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }

    public String getIdBarbershop() {
        return idBarbershop;
    }

    public void setIdBarbershop(String idBarbershop) {
        this.idBarbershop = idBarbershop;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(String totalharga) {
        this.totalharga = totalharga;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getNamaBarbershop() {
        return namaBarbershop;
    }

    public void setNamaBarbershop(String namaBarbershop) {
        this.namaBarbershop = namaBarbershop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarberman() {
        return barberman;
    }

    public void setBarberman(String barberman) {
        this.barberman = barberman;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPhoneBarbershop() {
        return phoneBarbershop;
    }

    public void setPhoneBarbershop(String phoneBarbershop) {
        this.phoneBarbershop = phoneBarbershop;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }
}
