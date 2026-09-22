package fit.se.democart.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CartItemBean> items;

    // Constructor khởi tạo danh sách sản phẩm trong giỏ hàng
    public CartBean() {
        items = new ArrayList<>();
    }

    // Lấy danh sách các sản phẩm đã chọn mua trong giỏ hàng
    public List<CartItemBean> getItems() {
        return items;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProduct(Product p) {
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == p.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItemBean(p, 1));
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProduct(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    // Cập nhật số lượng sản phẩm
    public void updateQuantity(int productId, int quantity) {
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == productId) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    // Nếu số lượng <= 0 thì xóa luôn sản phẩm khỏi giỏ
                    removeProduct(productId);
                }
                return;
            }
        }
    }

    // Tính tổng tiền đơn hàng
    public double getTotal() {
        double total = 0;
        for (CartItemBean item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Xóa sạch giỏ hàng
    public void clear() {
        items.clear();
    }
}