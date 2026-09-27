package com.wishit.product.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wishit.product.dto.ProductDTO;
import com.wishit.product.dto.ProductUploadDTO;
import com.wishit.product.entity.Product;
import com.wishit.product.entity.ProductImage;
import com.wishit.product.repository.ProductImageRepository;
import com.wishit.product.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired ProductRepository productRepo; 
	@Autowired ProductImageRepository imageRepo;
	
	private static final Logger log =
	        LoggerFactory.getLogger(ProductService.class);

	public ProductDTO createProduct(ProductUploadDTO prodDTO) throws IOException {

	    log.info("Creating product | name={} | vendorId={}",
	            prodDTO.getName(),
	            prodDTO.getVendorId());

	    Product product = new Product();

	    product.setName(prodDTO.getName());
	    product.setDescription(prodDTO.getDescription());
	    product.setPrice(prodDTO.getPrice());
	    product.setStockQuantity(prodDTO.getStockQuantity());
	    product.setVendorId(prodDTO.getVendorId());

	    List<ProductImage> images = new ArrayList<>();

	    Path uploadDir = Paths.get("uploads/products/");

	    if (!Files.exists(uploadDir)) {

	        log.info("Product upload directory does not exist. Creating directory | path={}",
	                uploadDir);

	        Files.createDirectories(uploadDir);

	        log.info("Product upload directory created successfully | path={}",
	                uploadDir);
	    }

	    for (MultipartFile image : prodDTO.getImages()) {

	        if (image.isEmpty()) {

	            log.warn("Skipping empty product image | productName={}",
	                    prodDTO.getName());

	            continue;
	        }

	        String originalFileName = image.getOriginalFilename();

	        String fileName =
	                UUID.randomUUID() + "_" + originalFileName;

	        Path filePath = uploadDir.resolve(fileName);

	        log.debug("Uploading product image | originalFileName={} | storedFileName={}",
	                originalFileName,
	                fileName);

	        try {

	            Files.copy(
	                    image.getInputStream(),
	                    filePath,
	                    StandardCopyOption.REPLACE_EXISTING
	            );

	            log.info("Product image uploaded successfully | fileName={}",
	                    fileName);

	        } catch (IOException e) {

	            log.error("Failed to upload product image | fileName={} | productName={}",
	                    fileName,
	                    prodDTO.getName(),
	                    e);

	            throw e;
	        }

	        ProductImage img = new ProductImage();

	        img.setImageUrl(fileName);
	        img.setProduct(product);

	        images.add(img);
	    }

	    product.setImages(images);

	    log.info("Saving product | name={} | vendorId={} | imageCount={}",
	            product.getName(),
	            product.getVendorId(),
	            images.size());

	    try {

	        Product savedProduct = productRepo.save(product);

	        log.info("Product created successfully | productId={} | name={} | imageCount={}",
	                savedProduct.getId(),
	                savedProduct.getName(),
	                images.size());

	        return convertToDTO(savedProduct);

	    } catch (Exception e) {

	        log.error("Failed to save product | name={} | vendorId={}",
	                product.getName(),
	                product.getVendorId(),
	                e);

	        throw e;
	    }
	}

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public List<Product> getProductByVendor(Long vendorId){
		return productRepo.findByVendorId(vendorId);
	}
	
	public List<Product> getProductByCategory(String categoryName){
		return productRepo.findProductsByCategoryName(categoryName);
	}
	
	public ProductDTO getProductDetails(Long prodId) {

	    Product product = productRepo.findById(prodId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    return convertToDTO(productRepo.save(product));
	}
	
	public ProductDTO updateQuantity(Long id, int change) {

	    Product product = productRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Product not found"));

	    int quantity = product.getStockQuantity() + change;

	    if (quantity < 0) {
	        throw new IllegalArgumentException(
	                "Stock quantity cannot be negative"
	        );
	    }

	    product.setStockQuantity(quantity);

	    return convertToDTO(productRepo.save(product));
	}
	
	private ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setVendorId(product.getVendorId());

        return dto;
    }
}
