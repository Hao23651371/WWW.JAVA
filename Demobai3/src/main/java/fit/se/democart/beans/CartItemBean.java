package fit.se.democart.beans;
import java.io.Serializable;

public class CartItemBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;

    // Constructor không tham số (Bắt buộc đối với JavaBean)
    public CartItemBean() {
    }

    // Constructor đầy đủ tham số (như trong ảnh)
    public CartItemBean(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters và Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Phương thức tính thành tiền cho 1 sản phẩm chọn mua
    public double getSubtotal() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0.0;
    }
}
