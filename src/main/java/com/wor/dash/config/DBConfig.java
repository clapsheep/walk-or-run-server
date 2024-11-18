package com.wor.dash.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages={"com.wor.dash.*.model.mapper"})
public class DBConfig {

}
