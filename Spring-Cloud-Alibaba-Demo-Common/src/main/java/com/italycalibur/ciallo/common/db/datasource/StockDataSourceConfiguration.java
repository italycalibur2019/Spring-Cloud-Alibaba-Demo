package com.italycalibur.ciallo.common.db.datasource;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description: stock模型数据源配置
 * @author dhr
 * @date 2025-02-11 15:06:18
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.italycalibur.ciallo.common.models.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryDs5")
public class StockDataSourceConfiguration {

    @Resource(name = "dataSource5")
    private DataSource datasource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryDs5() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(datasource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml")
        );
        //向Mybatis过滤器链中添加拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        factoryBean.setPlugins(interceptor);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateDs5() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryDs5());
    }

    @Bean
    public DataSourceTransactionManager transactionManager5() {
        return new DataSourceTransactionManager(datasource);
    }
}
