package cn.ecomb.web.app.service;

import cn.ecomb.provider.api.user.entity.User;
import cn.ecomb.web.app.controller.response.GetUserResponse;

/**
 * 用户相关接口
 *
 * @author brian.zhou
 * @date 2020/9/18
 */
public interface IWebUserService {

	/**
	 * 根据用户 ID，获取用户信息
	 * @param userId
	 */
	GetUserResponse getUser(Long userId);
}
