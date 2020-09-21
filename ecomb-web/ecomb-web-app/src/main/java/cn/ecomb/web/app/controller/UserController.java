package cn.ecomb.web.app.controller;

import cn.ecomb.common.web.annotation.Crypt;
import cn.ecomb.common.web.annotation.CryptKeyConstant;
import cn.ecomb.web.app.controller.request.GetUserRequest;
import cn.ecomb.web.app.controller.response.GetUserResponse;
import cn.ecomb.web.app.service.IWebUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户相关接口")
@RestController
@RequestMapping("/user")
//@Crypt(key = CryptKeyConstant.COUPON_QUICK_CRYPT_KEY)
public class UserController {

    @Autowired
    private IWebUserService webUserService;

    @PostMapping
    @ApiOperation("根据用户 ID，获取用户信息")
    public GetUserResponse getUser(@RequestBody GetUserRequest request) {
        return webUserService.getUser(request.getUserId());
    }

    @GetMapping
    @ApiOperation("根据用户 ID，获取用户信息")
    public GetUserResponse getUserVanGet(GetUserRequest request) {
        return webUserService.getUser(request.getUserId());
    }

    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public void login() {

    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public void logout() {

    }

}
