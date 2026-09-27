package com.wishit.product.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishit.product.dto.ProductDTO;
import com.wishit.product.dto.ProductUploadDTO;
import com.wishit.product.exception.BackendException;
import com.wishit.product.service.ProductService;

@RestController
@RequestMapping("wishit/vendor/products")
public class VendorProductController {

    @Autowired
    private ProductService prodServ;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> uploadProduct(
            @ModelAttribute ProductUploadDTO proddto)
            throws BackendException, IOException {

        ProductDTO savedProd = prodServ.createProduct(proddto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedProd);
    }
}
