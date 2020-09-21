package cn.ecomb.provider.api.user.service;

import cn.ecomb.provider.api.user.dto.UserDto;
import cn.ecomb.provider.api.user.entity.User;
import cn.ecomb.provider.api.user.param.ListUserPageQuery;

import java.util.List;

/**
 * 用户相关接口
 * @author brian.zhou
 * @date 2020/9/21
 */
public interface IUserServiceApi {

	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 更新用户信息
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * 删除用户信息
	 * @param userIds
	 */
	void remove(List<Long> userIds);

	/**
	 * 获取用户信息
	 * @return
	 */
	User getUser(Long userId);

	/**
	 * 获取用户信息
	 * @return
	 */
	List<User> listUser(List<Long> userIds);

	/**
	 * 分页查询用户信息
	 * @param pageQuery
	 * @return
	 */
	List<UserDto> listUser(ListUserPageQuery pageQuery);
}
