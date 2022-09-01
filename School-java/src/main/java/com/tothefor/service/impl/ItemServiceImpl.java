package com.tothefor.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tothefor.mapper.ItemMapper;
import com.tothefor.pojo.entity.Item;
import com.tothefor.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品实现类
 * @Author Steven
 * @Date 2022/08/31 13:28
 
 */
@Service("ItemServiceImpl")
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}

