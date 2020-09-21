package cn.ecomb.web.app.service.impl;

import cn.ecomb.provider.api.user.service.IUserServiceApi;
import cn.ecomb.web.app.controller.response.GetUserResponse;
import cn.ecomb.web.app.service.IWebUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author brian.zhou
 * @date 2020/9/18
 */
@Service
@Slf4j
public class IWebUserServiceImpl implements IWebUserService {

	@Autowired
	private IUserServiceApi userServiceApi;

	@Override
	public GetUserResponse getUser(Long userId) {
		return Optional.ofNullable(userServiceApi.getUser(userId))
				.map(user -> {
					GetUserResponse response = new GetUserResponse();
					BeanUtils.copyProperties(user, response);
					return response;
				})
				.orElse(null);
	}
}
