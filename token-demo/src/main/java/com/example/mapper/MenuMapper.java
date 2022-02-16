package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
	List<String> selectPermsByUserId(Long userId);
}
