package cn.ecomb.provider.user.mapper;

import cn.ecomb.provider.api.user.dto.UserDto;
import cn.ecomb.provider.api.user.entity.User;
import cn.ecomb.provider.api.user.param.ListUserPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author brian.zhou
 * @date 2020/9/21
 */
@Mapper
public interface IUserMapper {

	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(@Param("user") User user);

	/**
	 * 更新用户信息
	 * @param user
	 */
	void updateUser(@Param("user") User user);

	/**
	 * 删除用户信息
	 * @param userIds
	 */
	void remove(@Param("list") List<Long> userIds);

	/**
	 * 获取用户信息
	 * @return
	 */
	User getUser(@Param("userId") Long userId);

	/**
	 * 获取用户信息
	 * @return
	 */
	List<User> listUserByUserIds(@Param("list") List<Long> userIds);

	/**
	 * 分页查询用户信息
	 * @param pageQuery
	 * @return
	 */
	List<UserDto> listUser(@Param("pageQuery") ListUserPageQuery pageQuery);
}
