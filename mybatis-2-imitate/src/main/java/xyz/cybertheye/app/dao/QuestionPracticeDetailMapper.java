package xyz.cybertheye.app.dao;


import xyz.cybertheye.mybatis.annotation.Param;
import xyz.cybertheye.mybatis.annotation.Select;
import xyz.cybertheye.app.vo.QuestionPracticeDetail;

import java.util.List;

/**
 * @description:
 */
public interface QuestionPracticeDetailMapper {
    @Select("select * from question_practice_detail")
    List<QuestionPracticeDetail> getAll();

    @Select("select * from question_practice_detail where question_id = #{qid} and id = #{id}  ")
    List<QuestionPracticeDetail> getByQuestionId(@Param("id") int id, @Param("qid")int qid);


    @Select("select id,question_id,result from question_practice_detail where question_id = #{qid} and result = #{result}")
    List<QuestionPracticeDetail> getQuestionDetailByCondition(@Param("qid") int qid, @Param("result") String result);

    @Select("select * from question_practice_detail where question_id = #{qid} and id = #{id} ")
    List<QuestionPracticeDetail> test();
}
