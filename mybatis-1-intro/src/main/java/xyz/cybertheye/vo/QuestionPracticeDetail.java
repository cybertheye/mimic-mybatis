package xyz.cybertheye.vo;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @description:
 */
@Data
@ToString
public class QuestionPracticeDetail {
    private int id;


    private int deviceId;
    private int questionId;
    private String result;
    private Date date;



}
