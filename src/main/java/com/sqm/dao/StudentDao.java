package com.sqm.dao;

import com.sqm.pojo.Student;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *     StudentDao接口
 * </p>
 *
 * @author sqm
 * @version 1.0
 */
public interface StudentDao {
    //插入Student
    void insertStudent(Student student);

    //插入后指定id
    void insertStudentCatchId(Student student);

    //删除,指定id值
    void deleteStudentById(int id);

    //修改Student
    void updateStudent(Student student);

    //查询所有
    List<Student> selectAllStudents();

    //根据id查询单个学生对象
    Student selectStudentById(int id);

    //根据id查询单个学生对象,既可以指定特定id的Student对象来查询,也可以通过直接查询id来查询,将两种查询方法封装在Map中
    Student selectStudent(Map map);

    //根据姓名包含的字符串模糊查询
    List<Student> selectStudentsByName(String name);

    //根据给定Map模糊查询,map的key为字段名,value为模糊查询的条件
    List<Student> selectStudentsByMap(Map<String, Object> map);

    //根据给定的Condition条件来获取student对象,参数顺序即为sql语句中的索引值
    List<Student> selectStudentsByConditions(String name, int age,int score);

}
