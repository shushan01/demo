package com.effective.wechat.public_account;

import com.effective.wechat.public_account.model.WxOAuth;
import com.effective.wechat.public_account.model.WxUserInfo;
import com.effective.wechat.public_account.service.WxBaseService;
import com.effective.wechat.public_account.service.WxOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;

@Controller
public class TestController extends HttpServlet {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WxBaseService wxBaseService;
    @Autowired
    private WxOAuthService wxOAuthService;

    /**
     * 获得accessToken接口
     *
     * @return
     */
    @RequestMapping("/base/getAccessToken")
    @ResponseBody
    public String wx() {
        return wxBaseService.getAccessToken();
    }

    /**
     * 获取网页授权登录的第一步地址
     *
     * @return
     */
    @RequestMapping("/oauth/getOAuth2FirstLoginUrl")
    @ResponseBody
    public String getOAuth2FirstLoginUrl() {
        return wxOAuthService.oauth2LoginUrl();
    }

    /**
     * 同意授权后，微信主动回掉的地址，在application中配置
     * 接口返回微信用户用户详细信息
     *
     * @param code
     * @return
     */
    @RequestMapping("")
    public String redirectUrl(String code, ModelMap map) {
        logger.info("first code ={}", code);
        WxOAuth wxOAuth = wxOAuthService.oauth2AccessToken(code);
        WxUserInfo wxUserInfo = wxOAuthService.getUserInfo(wxOAuth);
        map.addAttribute("user", wxUserInfo);
        return "/mine";
    }

    @RequestMapping("view")
    public String view(Model model) {
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setNickname("杨");
        wxUserInfo.setHeadImg("http://thirdwx.qlogo.cn/mmopen/vi_32/HKLsu3OLFqkrzJmR8HF1jO9VkIgCcLLsYHAGRUd09PQCdZOsHYRWQL8CneaeSvZAzkicvPa1maeN6mnvfxWEneA/132");
        model.addAttribute("user", wxUserInfo);
        return "/test";
    }
}
