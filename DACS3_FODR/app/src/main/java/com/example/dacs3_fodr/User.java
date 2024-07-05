package com.example.dacs3_fodr;

public class User {
    private String IdUser;
    private String Name;
    private String Email;
    private int Role;
    private String Address;
    private String Phone;
    private String ShopName;
    private int IdShop;

    public User(String idUser, String name, String email, int role, String address, String phone, String shopName, int idShop) {
        IdUser = idUser;
        Name = name;
        Email = email;
        Role = role;
        Address = address;
        Phone = phone;
        ShopName = shopName;
        IdShop = idShop;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "IdUser='" + IdUser + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Role=" + Role +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }

    public String getIdUser() {
        return IdUser;
    }


    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
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

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public int getIdShop() {
        return IdShop;
    }

    public void setIdShop(int idShop) {
        IdShop = idShop;
    }
}
