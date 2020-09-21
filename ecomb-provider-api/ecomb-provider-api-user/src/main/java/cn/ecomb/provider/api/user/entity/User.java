package cn.ecomb.provider.api.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体
 *
 * @author brian.zhou
 * @date 2020/9/21
 */
@Data
public class User {

	private int id;
	/** 用户Id */
	private Long userId;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String password;
	/** 昵称 */
	private String nickName;
	/** 手机号 */
	private String phone;
	/** 帐号启用状态:0->禁用；1->启用 */
	private Integer status;
	/** 注册时间 */
	private Date createTime;
	/** 性别：0->未知；1->男；2->女 */
	private Integer gender;
	/** 所在城市 */
	private String city;
}
