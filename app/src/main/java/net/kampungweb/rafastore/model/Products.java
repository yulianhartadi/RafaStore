package net.kampungweb.rafastore.model;

public class Products {

    private String category;
    private String date;
    private String image;
    private String imageAlt1;
    private String imageAlt2;
    private String imageAlt3;
    private String pid;
    private String productDescription;
    private String productName;
    private String productPrice;
    private String productStock;
    private String time;

    public Products() {


    }

    public Products(String category, String date, String image, String imageAlt1, String imageAlt2, String imageAlt3, String pid, String productDescription, String productName, String productPrice, String productStock, String time) {
        this.category = category;
        this.date = date;
        this.image = image;
        this.imageAlt1 = imageAlt1;
        this.imageAlt2 = imageAlt2;
        this.imageAlt3 = imageAlt3;
        this.pid = pid;
        this.productDescription = productDescription;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt1() {
        return imageAlt1;
    }

    public void setImageAlt1(String imageAlt1) {
        this.imageAlt1 = imageAlt1;
    }

    public String getImageAlt2() {
        return imageAlt2;
    }

    public void setImageAlt2(String imageAlt2) {
        this.imageAlt2 = imageAlt2;
    }

    public String getImageAlt3() {
        return imageAlt3;
    }

    public void setImageAlt3(String imageAlt3) {
        this.imageAlt3 = imageAlt3;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
