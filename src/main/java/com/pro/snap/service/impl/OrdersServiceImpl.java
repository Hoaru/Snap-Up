package com.pro.snap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.snap.mapper.OrdersMapper;
import com.pro.snap.pojo.Orders;
import com.pro.snap.pojo.SnapGoods;
import com.pro.snap.pojo.SnapOrders;
import com.pro.snap.pojo.Users;
import com.pro.snap.service.IOrdersService;
import com.pro.snap.service.ISnapGoodsService;
import com.pro.snap.service.ISnapOrdersService;
import com.pro.snap.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private ISnapGoodsService snapGoodsService;
    @Autowired
    private ISnapOrdersService snapOrdersService;
    @Autowired
    private OrdersMapper ordersMapper;

    //秒杀
    @Override
    public Orders snap(Users users, GoodsVo goods) {
        //获取秒杀商品表并减库存
        SnapGoods snapGoods = snapGoodsService.getOne(new QueryWrapper<SnapGoods>().
                eq("goods_id", goods.getId()));
        snapGoods.setStockCount(snapGoods.getStockCount() - 1);
        snapGoodsService.updateById(snapGoods);
        //生成订单
        Orders orders = new Orders();
        orders.setUsersId(users.getId());
        orders.setGoodsId(goods.getId());
        orders.setDeliveryAddrId(0L);
        orders.setGoodsName(goods.getGoodsName());
        orders.setGoodsCount(1);
        orders.setGoodsPrice(snapGoods.getSnapPrice());
        orders.setOrdersChannel(1);
        orders.setStatus(0);
        orders.setCreateDate(new Date());
        ordersMapper.insert(orders);
        //生成秒杀订单
        SnapOrders snapOrders = new SnapOrders();
        snapOrders.setUsersId(users.getId());
        snapOrders.setOrdersId(orders.getId());
        snapOrders.setGoodsId(goods.getId());
        snapOrdersService.save(snapOrders);
        return orders;
    }
}
