package com.wor.dash.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages={"com.fitlog.wor.*.model.dao"})
public class DBConfig {

}
