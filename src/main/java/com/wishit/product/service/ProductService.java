package com.wishit.product.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wishit.product.dto.ProductUploadDTO;
import com.wishit.product.entity.Product;
import com.wishit.product.entity.ProductImage;
import com.wishit.product.repository.ProductImageRepository;
import com.wishit.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired ProductRepository productRepo; 
	@Autowired ProductImageRepository imageRepo;

	public Product createProduct(ProductUploadDTO prodDTO) throws IOException {

	    Product product = new Product();

	    product.setName(prodDTO.getName());
	    product.setDescription(prodDTO.getDescription());
	    product.setPrice(prodDTO.getPrice());
	    product.setStockQuantity(prodDTO.getStockQuantity());
	    product.setVendorId(prodDTO.getVendorId());
	    List<ProductImage> images = new ArrayList<>();
	    Path uploadDir = Paths.get("uploads/products");
	    if (!Files.exists(uploadDir)) {
	        Files.createDirectories(uploadDir);
	    }

	    for (MultipartFile image : prodDTO.getImages()) {
	        if (image.isEmpty()) {
	            continue;
	        }
	        String fileName =
	                UUID.randomUUID() + "_" + image.getOriginalFilename();

	        Path filePath = uploadDir.resolve(fileName);

	        Files.copy(
	                image.getInputStream(),
	                filePath,
	                StandardCopyOption.REPLACE_EXISTING
	        );

	        ProductImage img = new ProductImage();
	        img.setImageUrl(uploadDir.toString() + fileName);
	        img.setProduct(product);
	        images.add(img);
	    }
	    product.setImages(images);
        return productRepo.save(product);
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
}
