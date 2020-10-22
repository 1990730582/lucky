package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.bill.BillServiceImpl;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BillController {

    @Autowired
    @Qualifier("billServiceImpl")
    private  BillService billService;

    @Autowired
    @Qualifier("providerServiceImpl")
    private ProviderService providerService;


    @RequestMapping("/bill.do")
    public String getProviderlist(Model model, String queryProductName, String queryProviderId, String queryIsPayment) {
        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("","");
        model.addAttribute("providerList", providerList);

        if(StringUtils.isNullOrEmpty(queryProductName)){
            queryProductName = "";
        }

        List<Bill> billList = new ArrayList<Bill>();
        BillService billService = new BillServiceImpl();
        Bill bill = new Bill();
        if(StringUtils.isNullOrEmpty(queryIsPayment)){
            bill.setIsPayment(0);
        }else{
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }

        if(StringUtils.isNullOrEmpty(queryProviderId)){
            bill.setProviderId(0);
        }else{
            bill.setProviderId(Integer.parseInt(queryProviderId));
        }
        bill.setProductName(queryProductName);
        billList = billService.getBillList(bill);
        model.addAttribute("billList", billList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }


    @RequestMapping("/billview")
    public String billview(Model model,String billid){
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill",bill);
        return "billview";
    }

    @RequestMapping("/billmodify")
    public String toBillModify(Model model,String billid){
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill",bill);
        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("","");
        model.addAttribute("providerList", providerList);
        return "billmodify";
    }


    @RequestMapping("/updbill")
    public String billmodify(Bill bill){
        billService.modify(bill);
        return "billmodify";
    }


    @RequestMapping("/delbill")
    @ResponseBody
    public Object delbill(@RequestParam("billid") String billid, Model model){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(billid)){
            boolean flag = billService.deleteBillById(billid);
            if(flag){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        return JSONArray.toJSONString(resultMap);
    }
}
