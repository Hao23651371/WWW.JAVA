package vn.fit.jakartaee_baitap2_tuan3.service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.fit.jakartaee_baitap2_tuan3.model.Product;
import vn.fit.jakartaee_baitap2_tuan3.model.ProductSalesSummary;
import vn.fit.jakartaee_baitap2_tuan3.model.RepriceReportDTO;
import vn.fit.jakartaee_baitap2_tuan3.repository.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    public boolean createProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean updateProduct(Product product) {
        return productRepository.update(product);
    }

    public boolean deleteProduct(int id) {
        return productRepository.delete(id);
    }

    public List<RepriceReportDTO> applyDynamicRepricing() {
        List<RepriceReportDTO> report = new ArrayList<>();

        for (ProductSalesSummary product : productRepository.findAllWithTotalOrdered()) {
            long quantity = product.getTotalQuantityOrdered();
            double oldPrice = product.getPrice();
            double newPrice = oldPrice;
            String adjustment = null;

            if (quantity >= 5) {
                newPrice = BigDecimal.valueOf(oldPrice)
                        .multiply(BigDecimal.valueOf(1.10))
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                adjustment = "+10%";
            } else if (quantity == 0) {
                newPrice = BigDecimal.valueOf(oldPrice)
                        .multiply(BigDecimal.valueOf(0.95))
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                adjustment = "-5%";
            }

            // Quantities 1–4 keep the current price and are omitted from the change report.
            if (adjustment != null) {
                if (!productRepository.updatePrice(product.getProductId(), newPrice)) {
                    throw new IllegalStateException("Price was not updated for product " + product.getProductId());
                }
                report.add(new RepriceReportDTO(product.getProductId(), product.getProductName(),
                        quantity, oldPrice, newPrice, adjustment));
            }
        }
        return report;
    }
}
