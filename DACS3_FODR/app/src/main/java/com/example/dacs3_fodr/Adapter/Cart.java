package com.example.dacs3_fodr.Adapter;

public class Cart {
    private String IDCart;
    private String IdUser;
    private String Image;
    private Double Price;
    private int Quantity;
    private String Title;
    private Double TotalPrice;
    private int IdProduct;
    private int IdCategory;
    private String addressShop;
    private int idShop;

    public Cart() {
    }

    public Cart(String IDCart, String idUser, String image, Double price, int quantity, String title, Double totalPrice, int idProduct, int idCategory, String addressShop, int idShop) {
        this.IDCart = IDCart;
        IdUser = idUser;
        Image = image;
        Price = price;
        Quantity = quantity;
        Title = title;
        TotalPrice = totalPrice;
        IdProduct = idProduct;
        IdCategory = idCategory;
        this.addressShop = addressShop;
        this.idShop = idShop;
    }

    public String getIDCart() {
        return IDCart;
    }

    public void setIDCart(String IDCart) {
        this.IDCart = IDCart;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public String getAddressShop() {
        return addressShop;
    }

    public void setAddressShop(String addressShop) {
        this.addressShop = addressShop;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }
}
