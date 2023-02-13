package com.pro.snap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.snap.mapper.SnapOrdersMapper;
import com.pro.snap.pojo.SnapOrders;
import com.pro.snap.service.ISnapOrdersService;
import org.springframework.stereotype.Service;

@Service
public class SnapOrdersServiceImpl extends ServiceImpl<SnapOrdersMapper, SnapOrders> implements ISnapOrdersService {

}
