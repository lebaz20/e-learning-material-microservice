package com.hnj.product.service.impl;

import com.hnj.product.model.Product;
import com.hnj.product.model.request.ProductRequest;
import com.hnj.product.repository.ProductRepository;
import com.hnj.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product addProduct(ProductRequest productRequest) {
		Integer quantity = 0;
		if (productRequest.getQuantity() != null && productRequest.getQuantity() > 0){
			quantity = productRequest.getQuantity();
		}
		Product product = new Product().builder()
				.productCode(productRequest.getProductCode())
				.productTitle(productRequest.getProductTitle())
				.imageUrl(productRequest.getImageUrl())
				.price(productRequest.getPrice())
				.quantity(quantity)
				.build();

		return productRepository.save(product);
	}

	@Override
	public Product editProduct(Integer id, ProductRequest productRequest) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()){
			product.get().setPrice(productRequest.getPrice());
			product.get().setQuantity(productRequest.getQuantity());
			product.get().setProductCode(productRequest.getProductCode());
		    product.get().setProductTitle(productRequest.getProductTitle());
		    product.get().setImageUrl(productRequest.getImageUrl());
			return productRepository.save(product.get());
		} else {
			return null;
		}
	}

	@Override
	public void deleteProduct(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()){
			productRepository.deleteById(id);
		}
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAllByOrderByPriceAsc();
	}

	@Override
	public Optional<Product> getProductById(Integer productId) {
		return productRepository.findById(productId);
	}

	@Override
	public void updateInventory(Integer productId, Integer quantity) {
		Optional<Product> product = getProductById(productId);
		if(product.isPresent()){
			product.get().setQuantity(quantity);
			productRepository.save(product.get());
		}
	}

	@Override
	public Product addPrice(Integer id, Double price) {
		if (price <= 0)
			return null;
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()){
				product.get().setPrice(price);
			return productRepository.save(product.get());
		} else {
			return null;
		}
	}
}
