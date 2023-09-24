package xyz.cybertheye.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import xyz.cybertheye.vo.QuestionPracticeDetail;

import java.util.List;

/**
 * @description:
 */
public interface QuestionPracticeDetailMapper {

    @Select("select * from question_practice_detail")
    List<QuestionPracticeDetail> getAll();

    @Select("select * from question_practice_detail where question_id = #{id}")
    List<QuestionPracticeDetail> getByQuestionId(@Param("id") int id);
}
