package com.example.paidstatapp;

public class WorkerClass {

    private int id;
    private String name;
    private int phoneNumber;
    private String address;
    private boolean isMason;
    private boolean isCarpenter;
    private boolean isPainter;

    public WorkerClass(int id, String name, int phoneNumber, String address, boolean isMason, boolean isCarpenter, boolean isPainter) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isMason = isMason;
        this.isCarpenter = isCarpenter;
        this.isPainter = isPainter;
    }


    public WorkerClass() {
    }

    public boolean isPainter() {
        return isPainter;
    }

    public void setPainter(boolean painter) {
        isPainter = painter;
    }

    public WorkerClass(int id, String name, int phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isMason() {
        return isMason;
    }

    public boolean isCarpenter() {
        return isCarpenter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMason(boolean mason) {
        isMason = mason;
    }

    public void setCarpenter(boolean carpenter) {
        isCarpenter = carpenter;
    }

    @Override
    public String toString() {
        return "WorkerClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", isMason=" + isMason +
                ", isCarpenter=" + isCarpenter +
                ", isPainter=" + isPainter +
                '}';
    }
}
