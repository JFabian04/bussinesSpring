package bussinesSpring.service;

import bussinesSpring.dto.ProductDTO;
import bussinesSpring.exception.ProductNotFoundException;
import bussinesSpring.model.Product;
import bussinesSpring.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ModelMapper modelMapper = new ModelMapper();

    // Convertir Entidad Product a ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    // Convertir ProductDTO a Entidad Product
    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    //Listar productos
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Registrar nuevo produto
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Obtener detalle de registro por ID
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Registro con id " + id + " no encontrado."));
        return convertToDTO(product);
    }

    //Actualizar nuevo producto.
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Registro con id " + id + " no encontrado"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);//Guardar producto en la BD
        return convertToDTO(updatedProduct);
    }

    //Eliminado Logico del producto por ID principal
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Registro con id " + id + " no encontrado");
        }
        productRepository.deleteById(id);
    }
}