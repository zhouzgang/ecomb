package cn.ecomb.provider.user.service.impl;

import cn.ecomb.provider.api.user.dto.UserDto;
import cn.ecomb.provider.api.user.entity.User;
import cn.ecomb.provider.api.user.param.ListUserPageQuery;
import cn.ecomb.provider.api.user.service.IUserServiceApi;
import cn.ecomb.provider.user.mapper.IUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户接口实现类
 *
 * @author brian.zhou
 * @date 2020/9/21
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserServiceApi {

	@Autowired
	private IUserMapper userMapper;

	@Override
	public void addUser(User user) {
		userMapper.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public void remove(List<Long> userIds) {
		userMapper.remove(userIds);
	}

	@Override
	public User getUser(Long userId) {
		return userMapper.getUser(userId);
	}

	@Override
	public List<User> listUser(List<Long> userIds) {
		return userMapper.listUserByUserIds(userIds);
	}

	@Override
	public List<UserDto> listUser(ListUserPageQuery pageQuery) {
		return userMapper.listUser(pageQuery);
	}
}
