package fit.se.democart.beans;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String model;
    private double price;
    private int quantity;
    private String description;
    private String imgURL; // Bổ sung thuộc tính imgURL

    // Constructor không tham số (JavaBean standard)
    public Product() {
    }

    // Constructor đầy đủ tham số (đã thêm imgURL)
    public Product(int id, String model, double price, int quantity, String description, String imgURL) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imgURL = imgURL;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Bổ sung Getter và Setter cho imgURL
    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    // Đổi tên getter phụ để JSP EL tự đọc được kể cả khi gọi ${p.imgUrl}
    public String getImgUrl() {
        return imgURL;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}