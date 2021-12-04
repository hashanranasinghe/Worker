package com.example.paidstatapp;

public class PaymentClass {

    private int paymentId;
    private String paymentDate;
    private String paymentDescription;
    private float payment;
    private int workerId;


    public PaymentClass() {
    }

    public PaymentClass(int paymentId, String paymentDate, String paymentDescription, float payment, int workerId) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentDescription = paymentDescription;
        this.payment = payment;
        this.workerId = workerId;
    }

    public PaymentClass(int paymentId, String paymentDate, String paymentDescription, float payment) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentDescription = paymentDescription;
        this.payment = payment;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "PaymentClass{" +
                "paymentId=" + paymentId +
                ", paymentDate='" + paymentDate + '\'' +
                ", payment=" + payment +
                ", workerId=" + workerId +
                ", paymentDescription='" + paymentDescription + '\'' +
                '}';
    }
}
