package cn.ecomb.park.hello;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author brian.zhou
 * @date 2021/1/12
 */
public class Hello {

	public static void main(String[] args) {
		System.out.println("hello");
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(new Date()));

		Map<String, Bo> map = new HashMap<>();
//		Bo bo = new Bo();
//		bo.setId("123");
//		bo.setAo(new Ao("abc"));
//		map.put("1", bo);
//		map.put("2", bo);
//		map.put("3", bo);
		List<Bo> bos = Arrays.asList(new Bo("a", new Ao("1")), new Bo("b", new Ao("2")));
		User user = User.builder().id("dfa").map(map).bos(bos).build();

		UserBo userBo = new UserBo();

		copyProperties(user, userBo);

		System.out.println(userBo);
	}

	@Data
	@Builder
	static class User {
		private String id;
		private Map<String, Bo> map;
		private List<Bo> bos;
	}

	@Data
	static class UserBo {
		private String id;
		private Map<String, Bo> map;
		private List<Bo> bos;
	}

	@Data
	static class Bo {
		private String id;
		private Ao ao;

		public Bo() {
		}

		public Bo(String id, Ao ao) {
			this.id = id;
			this.ao = ao;
		}
	}

	@Data
	static class Ao {
		private String id;

		public Ao(String id) {
			this.id = id;
		}
	}


}
