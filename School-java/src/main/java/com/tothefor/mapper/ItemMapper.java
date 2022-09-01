package com.tothefor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tothefor.pojo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Steven
 * @Date 2022/08/31 13:28
 
 */
@Component
@Repository
@Mapper
public interface ItemMapper extends BaseMapper<Item> {

}

