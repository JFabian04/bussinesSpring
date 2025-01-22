package bussinesSpring.controller;

import bussinesSpring.dto.ProductDTO;
import bussinesSpring.exception.ResponseWrapper;
import bussinesSpring.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    // Obtener lista de todos los productos
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.status(200).body(new ResponseWrapper<>(products, "", 200));
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<ResponseWrapper<ProductDTO>> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(201).body(new ResponseWrapper<>(createdProduct, "Producto registrado correctamente", 201));
    }

    //Obtener detalle de producto por ID principal
    @GetMapping("/{id}")
    public  ResponseEntity<ResponseWrapper<ProductDTO>> getProductById(@PathVariable Long id){
        ProductDTO productDTO = productService.getProductById(id);
        return  ResponseEntity.status(200).body(new ResponseWrapper<>(productDTO, "", 200));
    }

    //Atualizar producto por ID
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ProductDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.status(200).body(new ResponseWrapper<>(updatedProduct, "Producto actualizado correctamente", 200));
    }

    //eliminado fisico de producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(200).body(new ResponseWrapper<>(null, "Producto eliminado correctamente", 200));
    }
}
