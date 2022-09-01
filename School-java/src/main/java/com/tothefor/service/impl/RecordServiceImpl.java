package com.tothefor.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tothefor.mapper.ItemMapper;
import com.tothefor.mapper.RecordMapper;
import com.tothefor.pojo.entity.Item;
import com.tothefor.pojo.entity.Record;
import com.tothefor.service.ItemService;
import com.tothefor.service.RecordService;
import org.springframework.stereotype.Service;

/**
 * 物品实现类
 * @Author Steven
 * @Date 2022/08/31 13:28
 
 */
@Service("RecordServiceImpl")
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

}

