package xyz.cybertheye.mybatis.proxy;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import xyz.cybertheye.app.vo.QuestionPracticeDetail;
import xyz.cybertheye.mybatis.annotation.Column;
import xyz.cybertheye.mybatis.annotation.Param;
import xyz.cybertheye.mybatis.annotation.Select;
import xyz.cybertheye.mybatis.handler.IntegerTypeHandler;
import xyz.cybertheye.mybatis.handler.StringTypeHandler;
import xyz.cybertheye.mybatis.handler.TypeHandler;
import xyz.cybertheye.mybatis.session.SqlSession;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @description:
 */
public class MapperProxyFactory {
    static Map<Class, TypeHandler> typeHandlerMap =  new HashMap<>();

    static{
        typeHandlerMap.put(String.class,new StringTypeHandler());
        typeHandlerMap.put(Integer.class,new IntegerTypeHandler());
        typeHandlerMap.put(int.class,new IntegerTypeHandler());
    }

    public static <T> T getMapper(Class<T> type, SqlSession sqlSession){

        //代理
        Object o = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Map<String,Object> formalToActualParametersMapping = new HashMap<>();

                Parameter[] parameters = method.getParameters();
                //javac -parameters xxx.java
                int index = 0;
                for(Parameter parameter:parameters){
                    String placeholderSignature = parameter.getName();
                    Param paramAnnotation = parameter.getAnnotation(Param.class);
                    if(paramAnnotation != null) {
                        placeholderSignature = paramAnnotation.value();
                    }
                    formalToActualParametersMapping.put(placeholderSignature,args[index]);
                    index++;
                }

                String regex = "#\\{(\\w+)\\}";

                String orignalSql = getOriginalSql(method);

                List<String> sqlPlaceholderParameters = retrievePlaceholderParameter(orignalSql,regex);

                String sql = parse(orignalSql,regex);

                //JDBC
                //Establishing a connection.
                try(Connection connection = sqlSession.getConnection()){

                    //Create a statement.
                    PreparedStatement pst = connection.prepareStatement(sql);

                    //Execute the query.

                    index = 1;
                    for (String sqlPlaceholderParameter : sqlPlaceholderParameters) {
                        Object value = formalToActualParametersMapping.get(sqlPlaceholderParameter);
                        typeHandlerMap.get(value.getClass()).setParameter(pst,index,value);
                        index++;
                    }

                    //question is calling which setter method



                    ResultSet resultSet = pst.executeQuery();

                    //Process the ResultSet object.

                    Class<?> returnType = method.getReturnType();

                    List<Object> ret = null;
                    if(returnType.equals(List.class)){
                        ret = new ArrayList<>();
                        Type genericReturnType = method.getGenericReturnType();

                        Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericReturnType).getActualTypeArguments();
                        String typeName = actualTypeArguments[0].getTypeName();

                        returnType = Class.forName(typeName);
                    }

                    Map<String,Method> columnSetterMethodMapping = new HashMap<>();

                    Field[] declaredFields = returnType.getDeclaredFields();
                    for(Field field: declaredFields){
                        String fieldName = field.getName();
                        String columnName = fieldName;
                        Column annotation = field.getAnnotation(Column.class);
                        if(annotation!=null){
                            columnName = annotation.value();
                        }

                        String methodName = "set"+ fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                        //set
                        Method setterMethod= returnType.getMethod(methodName, field.getType());

                        columnSetterMethodMapping.put(columnName,setterMethod);
                    }


                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
//                    metaData.getColumnName()

                    while(resultSet.next()){

                        Object instance = returnType.newInstance();

                        for(int i =1; i <=columnCount ; i++){
                            String columnName = metaData.getColumnName(i);

                            Method setterMethod = columnSetterMethodMapping.get(columnName);
                            //
                            Class<?> parameterType = setterMethod.getParameterTypes()[0];
                            Object result = typeHandlerMap.get(parameterType).getResult(resultSet, columnName);

                            setterMethod.invoke(instance,result);
                        }

                        if(ret == null){
                            return instance;
                        }
                        ret.add(instance);
                    }

                    return ret;
                }


                //Close the connection.
            }
        });

        return (T) o;
    }

    private static List<String> retrievePlaceholderParameter(String orignalSql, String regex) {
        //#\{(\w+\)} #{id} #{pid}
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(orignalSql);
        List<String> rets = new ArrayList<>();

        while(matcher.find()){
            String parameterName= matcher.group(1);
            rets.add(parameterName);
        }

        return rets;
    }

    private static String parse(String orignalSql,String regex) {

        return orignalSql.replaceAll(regex,"?");
    }

    private static String getOriginalSql(Method method){
        Select selectAnnoation = method.getAnnotation(Select.class);
        String originalSql = selectAnnoation.value();
        return originalSql;
    }


}
