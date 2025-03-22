package com.example.register.models;



public class Task {

    String task, tanggal, waktu, priority;
    int nomor;

    public String getTanggal() {
        return tanggal;
    }



    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Task(){

    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }


    public Task(int nomor, String task, String tanggal, String waktu, String priority){
        this.nomor = nomor;
        this.task = task;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.priority = priority;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
