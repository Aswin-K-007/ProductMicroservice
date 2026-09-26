package com.wishit.product.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wishit.product.dto.ProductDTO;
import com.wishit.product.dto.ProductUploadDTO;
import com.wishit.product.entity.Product;
import com.wishit.product.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("wishit/products")
public class ProductController {
    
	@Autowired private ProductService prodServ;
	
	 @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProduct(@ModelAttribute ProductUploadDTO proddto) {
		 try {
				ProductDTO savedProd = prodServ.createProduct(proddto);
				return ResponseEntity.status(HttpStatus.CREATED).body(savedProd);
			} catch(Exception ex) {
				if (ex instanceof IllegalArgumentException) {
		            return ResponseEntity.badRequest()
		                    .body(Map.of("error", ex.getMessage()));
		        } else {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(Map.of("error", "Something went wrong"));
		        }
			}
    }

	@GetMapping("/view_all_products")
    public List<Product> getProducts() {
        return prodServ.getAllProducts();
    }
	
	@GetMapping("/view_product/{id}")
	public ResponseEntity<?> getProductDetails(@PathVariable Long id) {

	    try {
	        ProductDTO product = prodServ.getProductDetails(id);
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(product);
	    } catch (IllegalArgumentException ex) {
	        return ResponseEntity
	                .badRequest()
	                .body(Map.of("error", ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "Something went wrong"));
	    }
	}
	
	@PutMapping("/{id}/add_remove_item")
	public ResponseEntity<?> updateQuantity(
	        @PathVariable Long id,
	        @RequestParam String operation) {

	    try {
	        int change;
	        if ("+".equals(operation)) {
	            change = 1;
	        } else if ("-".equals(operation)) {
	            change = -1;
	        } else {
	            throw new IllegalArgumentException(
	                    "Operation must be '+' or '-'");
	        }
	        ProductDTO product = prodServ.updateQuantity(id, change);
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(product);
	    } catch (IllegalArgumentException ex) {
	        return ResponseEntity
	                .badRequest()
	                .body(Map.of("error", ex.getMessage()));
	    } catch (Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "Something went wrong"));
	    }
	}
 }


