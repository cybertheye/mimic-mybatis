package xyz.cybertheye.app;

import xyz.cybertheye.app.dao.QuestionPracticeDetailMapper;
import xyz.cybertheye.app.dao.SalesMapper;
import xyz.cybertheye.app.vo.QuestionPracticeDetail;
import xyz.cybertheye.app.vo.Sales;
import xyz.cybertheye.mybatis.session.Configuration;
import xyz.cybertheye.mybatis.session.SqlSession;
import xyz.cybertheye.mybatis.session.SqlSessionFactory;
import xyz.cybertheye.mybatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 */
public class MyMyBatisApplication {
    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("datasource.properties"));

        Configuration configuration = new Configuration();
        configuration.initFromProperties(properties);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);

        try(SqlSession sqlSession = factory.openSession()){

            QuestionPracticeDetailMapper qpdMapper = sqlSession.getMapper(QuestionPracticeDetailMapper.class);
            SalesMapper salesMapper= sqlSession.getMapper(SalesMapper.class);
            List<QuestionPracticeDetail> result= qpdMapper.getQuestionDetailByCondition(111,"right");
            System.out.println(result);


            List<Sales> allSales = salesMapper.getAllSales();
            System.out.println(allSales);

            Sales salesById = salesMapper.getSalesById(1);
            System.out.println(salesById);

            List<Sales> k = salesMapper.getSalesByCondition(30, "k");
            System.out.println(k);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
