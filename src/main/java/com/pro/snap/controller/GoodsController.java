package com.pro.snap.controller;

import com.pro.snap.pojo.Users;
import com.pro.snap.service.IGoodsService;
import com.pro.snap.service.IUsersService;
import com.pro.snap.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IGoodsService goodsService;
    // 跳转到商品列表页
    @RequestMapping("/toList")
    public String toList(Model model, Users users){
        model.addAttribute("users", users);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }


    // 跳转到商品详情页
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, Users users, @PathVariable Long goodsId){
        model.addAttribute("users", users);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int snapStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀持续时间
        //int lastSeconds = (int)((endDate.getTime() - startDate.getTime())/1000);
        //未开始
        if(nowDate.before(startDate)){
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime())/1000);
        }
        //秒杀结束
        else if(nowDate.after(endDate)){
            snapStatus = 2;
            remainSeconds = -1;
        }
        //秒杀进行中
        else{
            snapStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("snapStatus", snapStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}
