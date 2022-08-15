package com.eauction.seller.Integrator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eauction.seller.model.User;

@Component
public class HystrixClientFallbackFactory implements UserServiceClient {

	@Override
	public User addUser(User user) {
		return User.builder().userId("FAIL123").build();
	}

	@Override
	public Map<String, User> getUsers(List<String> userIdList) {
		return null;
	}

}
