package vn.fit.jakartaee_baitap2_tuan3.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.fit.jakartaee_baitap2_tuan3.model.AddCartResponseDTO;
import vn.fit.jakartaee_baitap2_tuan3.model.CustomerBillDTO;
import vn.fit.jakartaee_baitap2_tuan3.model.CustomerBillItemDTO;
import vn.fit.jakartaee_baitap2_tuan3.model.Product;
import vn.fit.jakartaee_baitap2_tuan3.model.ShoppingCart;
import vn.fit.jakartaee_baitap2_tuan3.repository.ProductRepository;
import vn.fit.jakartaee_baitap2_tuan3.repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShoppingCartService {

    @Inject
    private ShoppingCartRepository cartRepository;

    @Inject
    private ProductRepository productRepository;

    /** Adds an item only when its value does not exceed $5,000. */
    public AddCartResponseDTO addItemToCart(ShoppingCart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Thông tin sản phẩm trong giỏ không được để trống.");
        }
        if (cart.getProductId() <= 0) {
            throw new IllegalArgumentException("productId phải lớn hơn 0.");
        }
        if (cart.getCustomerName() == null || cart.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("customerName không được để trống.");
        }
        if (cart.getQuantity() <= 0) {
            throw new IllegalArgumentException("quantity phải lớn hơn 0.");
        }

        Product product = productRepository.findById(cart.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        }

        double estimatedTotal = product.getPrice() * cart.getQuantity();
        if (estimatedTotal > 5000.0) {
            throw new IllegalStateException(
                    "Giá trị sản phẩm thêm vào vượt quá giới hạn $5,000."
            );
        }

        if (!cartRepository.save(cart)) {
            throw new IllegalStateException("Không thể lưu sản phẩm vào giỏ hàng.");
        }

        return new AddCartResponseDTO(
                cart.getId(),
                product.getName(),
                cart.getQuantity(),
                estimatedTotal
        );
    }

    /**
     * Builds an itemized bill for one customer. A 10% discount applies only
     * when the subtotal is greater than $2,000.
     */
    public CustomerBillDTO calculateCustomerBill(String customerName) {
        List<ShoppingCart> cartItems = cartRepository.findByCustomerName(customerName);
        List<CustomerBillItemDTO> billItems = new ArrayList<>();
        double subTotal = 0.0;

        for (ShoppingCart cart : cartItems) {
            Product product = productRepository.findById(cart.getProductId());
            if (product == null) {
                throw new IllegalStateException(
                        "Không tìm thấy sản phẩm có ID " + cart.getProductId()
                );
            }

            double itemSubTotal = cart.getQuantity() * product.getPrice();
            billItems.add(new CustomerBillItemDTO(
                    cart.getId(),
                    product.getName(),
                    product.getPrice(),
                    cart.getQuantity(),
                    itemSubTotal
            ));
            subTotal += itemSubTotal;
        }

        double discount = subTotal > 2000.0 ? subTotal * 0.10 : 0.0;
        double finalTotal = subTotal - discount;

        return new CustomerBillDTO(customerName, billItems, subTotal, discount, finalTotal);
    }

    public List<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    public ShoppingCart getCartById(int id) {
        return cartRepository.findById(id);
    }

    public boolean createCart(ShoppingCart cart) {
        return cartRepository.save(cart);
    }

    public boolean updateCart(ShoppingCart cart) {
        return cartRepository.update(cart);
    }

    public boolean deleteCart(int id) {
        return cartRepository.delete(id);
    }
}
