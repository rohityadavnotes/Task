package com.task.data.local.sqlite;

public class Cart {

    private Integer rowId;

    private String categoryId;
    private String categoryName;

    private String itemId;
    private String itemName;
    private String itemPrice;
    private String itemImage;
    private String itemQuantity;
    private String itemTotalPrice;

    public Cart(String itemId, String itemQuantity, String itemTotalPrice) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public Cart() {
    }

    public Cart(Integer rowId, String categoryId, String categoryName, String itemId, String itemName, String itemPrice, String itemImage, String itemQuantity, String itemTotalPrice) {
        this.rowId = rowId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public Cart(String categoryId, String categoryName, String itemId, String itemName, String itemPrice, String itemImage, String itemQuantity, String itemTotalPrice) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(String itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }
}
