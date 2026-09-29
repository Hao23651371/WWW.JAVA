package vn.fit.jakartaee_baitap2_tuan3.model;

/** Product data joined with its total quantity currently present in shopping carts. */
public class ProductSalesSummary {
    private int productId;
    private String productName;
    private double price;
    private long totalQuantityOrdered;

    public ProductSalesSummary() {
    }

    public ProductSalesSummary(int productId, String productName, double price, long totalQuantityOrdered) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.totalQuantityOrdered = totalQuantityOrdered;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public long getTotalQuantityOrdered() { return totalQuantityOrdered; }
    public void setTotalQuantityOrdered(long totalQuantityOrdered) { this.totalQuantityOrdered = totalQuantityOrdered; }
}
