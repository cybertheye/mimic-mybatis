package xyz.cybertheye.app.vo;

import lombok.Data;
import lombok.ToString;
import xyz.cybertheye.mybatis.annotation.Column;

import java.util.Date;

/**
 * @description:
 */
@ToString
public class QuestionPracticeDetail {


    private int id;
    @Column("device_id")
    private int deviceId;
    @Column("question_id")
    private int questionId;
    private String result;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
