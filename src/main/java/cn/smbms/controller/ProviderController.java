package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProviderController {

    @RequestMapping("/provider.do")
    public String query(Model model, String queryProName, String queryProCode) {
        if (StringUtils.isNullOrEmpty(queryProName)) {
            queryProName = "";
        }
        if (StringUtils.isNullOrEmpty(queryProCode)) {
            queryProCode = "";
        }
        List<Provider> providerList = new ArrayList<Provider>();
        ProviderService providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList(queryProName, queryProCode);
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("queryProCode", queryProCode);
        return "providerlist";
    }
}
