package com.example.restaurantmanagement;

public class EmployeeManagementModel {
    String ID, Name, Date, Address, Phone, Job, Status;
    Double Salary;

    public EmployeeManagementModel(String ID, String Name, String Date, String Address, String Phone, Double Salary, String Job, String Status){
        this.ID = ID;
        this.Name = Name;
        this.Date = Date;
        this.Address = Address;
        this.Phone = Phone;
        this.Salary = Salary;
        this.Job = Job;
        this.Status = Status;
    }

    public EmployeeManagementModel() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
