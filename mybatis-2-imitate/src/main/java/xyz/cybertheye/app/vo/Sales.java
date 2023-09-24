package xyz.cybertheye.app.vo;

import lombok.Data;
import lombok.ToString;
import xyz.cybertheye.mybatis.annotation.Column;

/**
 * @description:
 */
@Data
@ToString
public class Sales {

    private int id;
    private int amount;
    @Column("salesperson_id")
    private int salesPersonId;

    @Column("customer_name")
    private String customerName;

}
