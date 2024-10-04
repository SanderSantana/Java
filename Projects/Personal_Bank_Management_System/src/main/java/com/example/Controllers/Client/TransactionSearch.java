package com.example.Controllers.Client;

public class TransactionSearch {

    int amount, accountNumber;

    String beneficiary, myReference, theirReference, status, date;

    public TransactionSearch(int amount, int accountNumber, String beneficiary, String myReference, String theirReference, String status, String date) {
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.beneficiary = beneficiary;
        this.myReference = myReference;
        this.theirReference = theirReference;
        this.status = status;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getMyReference() {
        return myReference;
    }

    public void setMyReference(String myReference) {
        this.myReference = myReference;
    }

    public String getTheirReference() {
        return theirReference;
    }

    public void setTheirReference(String theirReference) {
        this.theirReference = theirReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
