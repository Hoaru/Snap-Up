package com.pro.snap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pro.snap.pojo.Orders;
import com.pro.snap.pojo.Users;
import com.pro.snap.vo.GoodsVo;

public interface IOrdersService extends IService<Orders> {

    //秒杀
    Orders snap(Users users, GoodsVo goods);
}
