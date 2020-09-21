package cn.ecomb.provider.api.order.entity.type;

/**
 * 操作员类型
 * @author brian.zhou
 * @date 2020/10/10
 */
public enum OrderOperateManEnum {

	USER("USER"),
	ADMIN("ADMIN");

	private String operateMan;

	OrderOperateManEnum(String operateMan) {
		this.operateMan = operateMan;
	}

	public String getOperateMan() {
		return operateMan;
	}

	public void setOperateMan(String operateMan) {
		this.operateMan = operateMan;
	}
}
