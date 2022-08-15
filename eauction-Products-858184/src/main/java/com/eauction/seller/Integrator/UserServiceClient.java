package com.eauction.seller.Integrator;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eauction.seller.model.User;

@FeignClient(name = "eauction-users", url = "http://localhost:9201", fallback = HystrixClientFallbackFactory.class)
public interface UserServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/e-auction/api/v1/add-user", consumes = "application/json")
	public User addUser(User user);
	
	@RequestMapping(method = RequestMethod.GET, value = "/e-auction/api/v1/get-users")
	public Map<String, User> getUsers(@RequestParam List<String> userIdList);

}
