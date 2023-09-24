package xyz.cybertheye.app.dao;

import xyz.cybertheye.app.vo.Sales;
import xyz.cybertheye.mybatis.annotation.Param;
import xyz.cybertheye.mybatis.annotation.Select;

import java.util.List;

/**
 * @description:
 */
public interface SalesMapper {
    @Select("select * from all_sales")
    List<Sales> getAllSales();


    @Select("select * from all_sales where id = #{arg0}")
    Sales getSalesById( int id);

    @Select("select * from all_sales where amount > #{amount} and customer_name like concat('%',#{name} ,'%')")
    List<Sales> getSalesByCondition(@Param("amount") int amount,@Param("name")String customerName);
}
