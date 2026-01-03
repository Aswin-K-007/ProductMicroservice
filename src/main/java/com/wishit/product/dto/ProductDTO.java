package com.wishit.product.dto;

import java.util.List;



public class ProductDTO {
	private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Long vendorId;
    
    public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	private List<ProductImageDTO> images;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public List<ProductImageDTO> getImages() {
		return images;
	}
	public void setImages(List<ProductImageDTO> images) {
		this.images = images;
	}
    
    
}
