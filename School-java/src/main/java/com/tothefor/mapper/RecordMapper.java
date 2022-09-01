package com.tothefor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tothefor.pojo.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author Steven
 * @Date 2022/08/31 13:28
 
 */
@Component
@Repository
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

}

