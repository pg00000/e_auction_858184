package com.eauction.seller.services;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.seller.exception.InvalidRequestException;
import com.eauction.seller.exception.ProductNotFoundException;
import com.eauction.seller.model.Bids;
import com.eauction.seller.model.Category;
import com.eauction.seller.model.ProductBids;
import com.eauction.seller.model.ProductBids.ProductBidsBuilder;
import com.eauction.seller.model.ProductRequest;
import com.eauction.seller.model.Products;
import com.eauction.seller.model.User;
import com.eauction.seller.model.UserAndBids;
import com.eauction.seller.repositories.BidsRepository;
import com.eauction.seller.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BidsRepository bidsRepository;
	
	@Override
	public Products getProduct(String productId) {
		log.info("ProductServiceImpl >> getProduct >>");
		return productRepository.findByProductId(productId);
	}
	
	@Override
	public List<Products> getProducts() {
		log.info("ProductServiceImpl >> getProducts >>");
		List<Products> result = new ArrayList<>();
		productRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public Products saveProduct(ProductRequest productInfo, User user, String sellerId) {
		log.info("ProductServiceImpl >> saveProduct >> ", productInfo);
		Products product = buildProjectObject(productInfo, user, sellerId);

		return productRepository.save(product);
	}

	private Products buildProjectObject(ProductRequest productInfo, User user, String sellerId) {
		log.info("ProductServiceImpl >> buildProjectObject >> " + productInfo);
		UUID uuid = UUID.randomUUID();   
		return Products.builder()
				.productId(uuid.toString())
				.productName(productInfo.getProductName())
				.shortDescription(productInfo.getShortDescription())
				.detailedDescription(productInfo.getDetailedDescription())
				.category(Category.getIfPresent(productInfo.getCategory().toUpperCase()))
				.startingPrice(productInfo.getStartingPrice())
				.bidEndDate(productInfo.getBidEndDate())
				.sellerId(sellerId)
				.seller(user)
				.build();
	}

	@Override
	public String deleteProduct(String productId) {
		log.info("ProductServiceImpl >> deleteProduct >>" + productId);
		Products prod = productRepository.findByProductId(productId);
		if (isNull(prod)) {
			throw new ProductNotFoundException("No Product Found");
		}
		if (prod.getBidEndDate().isBefore(LocalDate.now())) {
			throw new InvalidRequestException("Unable to Delete Product after Bid End Date");
		}
		List<Bids> bid = bidsRepository.findByProductId(prod.getProductId());
		if (!bid.isEmpty()) {
			throw new InvalidRequestException("Unable to Delete Product as Bid Found");
		}
		Integer deletedResult = productRepository.deleteByProductId(productId);
		if (deletedResult >= 1) {
			return prod.getProductId() + " Deleted Successfully";
		} else {
			throw new ProductNotFoundException("No Product Found");
		}
	}

	@Override
	public ProductBids getProductBids(String productId) {
		log.info("ProductServiceImpl >> deleteProduct >> productId : {}", productId);
		ProductBidsBuilder productBidsBuilder = ProductBids.builder();
		//Fetch Product details
		Products productObj = productRepository.findByProductId(productId);
		if(isNull(productObj)) {
			throw new ProductNotFoundException("No Product was found for the give productId : "+ productId);
		}
		productBidsBuilder.productDetail(productObj);
		
		//Fetch User details
		List<Bids> bids = bidsRepository.findByProductId(productObj.getProductId());
		List<UserAndBids> userAndBidsVOList = new ArrayList<>();
		
		if (!bids.isEmpty()) {
			bids.forEach(bid -> {
				User usr = bid.getBuyer();
				UserAndBids userAndBidsObj = UserAndBids.builder()
						.bidAmount(Double.valueOf(bid.getBidAmount()))
						.userName(usr.getFirstName()+ " " + usr.getLastName())
						.userEmail(usr.getEmail())
						.phone(usr.getPhone())
						.build();
				userAndBidsVOList.add(userAndBidsObj);
			});

			userAndBidsVOList.sort(Comparator.comparing(UserAndBids::getBidAmount).reversed());
		}
		productBidsBuilder.userBidsList(userAndBidsVOList);
		
		return productBidsBuilder.build();
	}

}
