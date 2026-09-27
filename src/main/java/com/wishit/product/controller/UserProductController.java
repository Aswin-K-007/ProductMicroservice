package com.wishit.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wishit.product.dto.ProductDTO;
import com.wishit.product.entity.Product;
import com.wishit.product.exception.BackendException;
import com.wishit.product.exception.ErrorCodes;
import com.wishit.product.service.ProductService;

@RestController
@RequestMapping("wishit/user/products")
public class UserProductController {

    @Autowired
    private ProductService prodServ;

    @GetMapping("/view_all_products")
    public ResponseEntity<List<Product>> getProducts()
            throws BackendException {

        List<Product> products = prodServ.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/view_product/{id}")
    public ResponseEntity<ProductDTO> getProductDetails(
            @PathVariable Long id)
            throws BackendException {

        ProductDTO product = prodServ.getProductDetails(id);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/update_stock")
    public ResponseEntity<ProductDTO> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity)
            throws BackendException {

        if (quantity < 0) {
        	throw new BackendException(
                    ErrorCodes.INVALID_OPERATION);
        }
        ProductDTO product =
                prodServ.updateQuantity(id, quantity);

        return ResponseEntity.ok(product);
    }
}