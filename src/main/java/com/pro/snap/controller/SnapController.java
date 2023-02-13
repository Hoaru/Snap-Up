package com.pro.snap.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.snap.pojo.Orders;
import com.pro.snap.pojo.SnapOrders;
import com.pro.snap.pojo.Users;
import com.pro.snap.service.IGoodsService;
import com.pro.snap.service.IOrdersService;
import com.pro.snap.service.ISnapOrdersService;
import com.pro.snap.vo.GoodsVo;
import com.pro.snap.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/snap")
public class SnapController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private ISnapOrdersService snapOrdersService;

    //秒杀
    @RequestMapping("/doSnap")
    public String doSnap(Model model, Users users, Long goodsId){
        if (users == null){
            return "login";
        }
        model.addAttribute("users", users);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goods.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "snapFail";
        }
        //判断订单是否重复抢购
        SnapOrders snapOrders = snapOrdersService.getOne(new QueryWrapper<SnapOrders>().
                eq("users_id", users.getId()).
                eq("goods_id", goodsId));
        if (snapOrders != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR.getMessage());
            return "snapFail";
        }
        Orders orders = ordersService.snap(users, goods);
        model.addAttribute("orders", orders);
        model.addAttribute("goods", goods);
        return "ordersDetail";
    }
}
