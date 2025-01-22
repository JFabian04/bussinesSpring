package bussinesSpring.service;

import bussinesSpring.dto.ProductDTO;
import bussinesSpring.exception.ProductNotFoundException;
import bussinesSpring.model.Product;
import bussinesSpring.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;  // Importa el método 'when' de Mockito


import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetProductById_Success() {
        Long productId = 1L;
        Product mockProduct = new Product(); //
        mockProduct.setId(productId);
        mockProduct.setName("Product Name");
        mockProduct.setDescription("Description product");
        mockProduct.setPrice(100.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        ProductDTO productDTO = productService.getProductById(productId);

        // Assert
        assertNotNull(productDTO);
        assertEquals(productId, productDTO.getId());
        assertEquals("Product Name", productDTO.getName());
    }

    @Test()
    public void testGetProductById_NotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        //Act - Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        Long productId = 1L;
        ProductDTO mockProductDTO = new ProductDTO(productId, "Product Name", "Description product", 100.0);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Product Name");
        mockProduct.setDescription("Description product");
        mockProduct.setPrice(100.0);

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // Act
        ProductDTO result = productService.createProduct(mockProductDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Product Name", result.getName());
        assertEquals(100.0, result.getPrice());
        assertEquals(productId, result.getId());
    }
}
