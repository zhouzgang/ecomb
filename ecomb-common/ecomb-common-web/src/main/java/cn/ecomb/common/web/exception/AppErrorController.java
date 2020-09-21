package cn.ecomb.common.web.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试不同异常处理方式
 *
 * @author brian.zhou
 * @date 2020/9/30
 */
@RestController
@RequestMapping("/error")
public class AppErrorController extends AbstractErrorController {

	public AppErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
		super(errorAttributes, errorViewResolvers);
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}
