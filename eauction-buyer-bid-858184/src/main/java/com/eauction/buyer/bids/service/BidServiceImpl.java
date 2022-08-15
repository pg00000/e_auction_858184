package com.eauction.buyer.bids.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eauction.buyer.bids.exception.InvalidRequestException;
import com.eauction.buyer.bids.exception.ProductNotFoundException;
import com.eauction.buyer.bids.model.BidRequest;
import com.eauction.buyer.bids.model.Bids;
import com.eauction.buyer.bids.model.Products;
import com.eauction.buyer.bids.model.User;
import com.eauction.buyer.bids.repository.BidsRepository;
import com.eauction.buyer.bids.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BidServiceImpl implements BidService {

	@Autowired
	BidsRepository bidsRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

    public List<Bids> list() {
        return bidsRepository.findAll();
    }

	@Override
    public String placeBid(BidRequest bidRequest, String buyerEmailId) {
		log.info("ProductServiceImpl >> placeBid >> bids : {}", bidRequest);
		Products prod = productRepository.findByProductId(bidRequest.getProductId());

		if (isNull(prod)) {
			throw new ProductNotFoundException("No Product Found");
		}
		if (prod.getSellerId().equalsIgnoreCase(buyerEmailId)) {
			throw new InvalidRequestException("Seller cannot Place the Bid");
		}
		if (prod.getBidEndDate().isBefore(LocalDate.now())) {
			throw new InvalidRequestException("Unable to Place Bid after Bid End Date");
		}
		Bids existingBid = bidsRepository.findByProductIdAndBuyerId(prod.getProductId(), buyerEmailId);
		if (nonNull(existingBid)) {
			throw new InvalidRequestException("Bid Already Placed for the User");
		}
		User user = this.buildUserObject(bidRequest);
		Bids bid = this.buildBidObject(bidRequest, user, buyerEmailId);
		bidsRepository.save(bid);
		return "Bid was created succesfully..";
	}
	
    @Override
	public void updateBid(String productId, String buyerEmailId, String newBidAmount) {
		log.info("ProductServiceImpl >> updateBid >> productId : {}", productId);
		Bids bidDetail = getBidDetails(productId, buyerEmailId);
		if (null != bidDetail) {
			bidDetail.setBidAmount(newBidAmount);
			bidsRepository.save(bidDetail);
		}
	}

	@Override
	public Bids getBidDetails(String productId, String buyerEmailId) {
		log.info("ProductServiceImpl >> getBidDetails >> productId : {}", productId);
		return bidsRepository.findByProductIdAndBuyerId(productId, buyerEmailId);
	}
	
	private User buildUserObject(BidRequest bidRequest) {
		log.info("BidServiceImpl >> buildUserRequest");
		return User.builder()
				.firstName(bidRequest.getFirstName())
				.lastName(bidRequest.getLastName())
				.address(bidRequest.getAddress())
				.city(bidRequest.getCity())
				.state(bidRequest.getState())
				.pin(bidRequest.getPin())
				.phone(bidRequest.getPhone())
				.email(bidRequest.getEmail())
				.build();
	}	
	
	private Bids buildBidObject(BidRequest bidRequest, User user, String buyerEmailId) {
		log.info("BidServiceImpl >> buildBidObject >> bidRequest : {}", bidRequest);
		return Bids.builder()
				.productId(bidRequest.getProductId())
				.bidAmount(bidRequest.getBidAmount())
				.buyerId(buyerEmailId)
				.buyer(user)
				.build();
	}	
}
