package com.wishit.product.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wishit.product.dto.ProductDTO;
import com.wishit.product.entity.Product;
import com.wishit.product.entity.ProductImage;
import com.wishit.product.repository.ProductImageRepository;
import com.wishit.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired ProductRepository productRepo; 
	@Autowired ProductImageRepository imageRepo;

	public Product createProduct(ProductDTO prodDTO){
		Product product = new Product();
        product.setName(prodDTO.getName());
        product.setDescription(prodDTO.getDescription());
        product.setPrice(prodDTO.getPrice());
        product.setStockQuantity(prodDTO.getStockQuantity());
        product.setVendorId(prodDTO.getVendorId());
        
        List<ProductImage> images  = prodDTO.getImages().stream().map(imgIn ->{
        	ProductImage img = new ProductImage();
        	img.setImageUrl(imgIn.getUrl());
        	img.setProduct(product);
        	return img;
        }).toList();
            
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
