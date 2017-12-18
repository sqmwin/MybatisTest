//package com.sqm.dao.impl;
//
//import com.sqm.dao.StudentDao;
//import com.sqm.pojo.Student;
//import com.sqm.util.MyBatisUtil;
//import org.apache.ibatis.session.SqlSession;
//import java.util.List;
//import java.util.Map;
//
///**
// * <p> </p>
// *
// * @author sqm
// * @version 1.0
// */
//public class StudentDaoImpl implements StudentDao {
//    private SqlSession session;
//
//    public void insertStudent(Student student) {
//        try {
//            //1.使用工具类获取SqlSession对象
//            session = MyBatisUtil.getSession();
//            //2.通过SqlSession操作数据,方法中的声明
//            session.insert("com.sqm.dao.StudentDao.insertStudent", student);
//            //3.提交SqlSession
//            session.commit();
//        } finally {
//            //4.关闭SqlSession,判断session是否为null
//            if (session != null) {
//                session.close();
//            }
//        }
//
//
//
//    }
//
//    public void insertStudentCatchId(Student student) {
//        try {
//            session = MyBatisUtil.getSession();
//            session.insert("insertStudentCatchId", student);
//            session.commit();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    public void deleteStudentById(int id) {
//        try {
//            session = MyBatisUtil.getSession();
//            session.delete("deleteStudentById", id);
//            session.commit();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    public void updateStudent(Student student) {
//        try {
//            session = MyBatisUtil.getSession();
//            session.update("updateStudent", student);
//            session.commit();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    public List<Student> selectAllStudents() {
//        //创建接收容器
//        List<Student> students;
//        try {
//            session = MyBatisUtil.getSession();
//            students = session.selectList("selectAllStudents");
//            //select语句无需commit
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return students;
//    }
//
//    public Map selectAllStudents(String mapKey) {
//        //创建接受容器
//        Map studentMap;
//        try {
//            session = MyBatisUtil.getSession();
//            studentMap = session.selectMap("selectAllStudents", mapKey);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return studentMap;
//    }
//
//    public Student selectStudentById(int id) {
//        //定义返回值的容器
//        Student student;
//        try {
//            session = MyBatisUtil.getSession();
//            student= session.selectOne("selectStudentById", id);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return student;
//    }
//
//    public Student selectStudent(Map map) {
//        Student student;
//        try {
//            session = MyBatisUtil.getSession();
//            student = session.selectOne("selectStudent", map);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return student;
//    }
//
//    public List<Student> selectStudentsByName(String name) {
//        List<Student> students;
//        try {
//            session = MyBatisUtil.getSession();
//            students = session.selectList("selectStudentsByName", name);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return students;
//    }
//
//    public List<Student> selectStudentsByMap(Map<String,String> map) {
//        List<Student> students;
//        try {
//            session = MyBatisUtil.getSession();
//            students = session.selectList("selectStudentsByMap", map);
//        } finally {
//            session.close();
//        }
//        return students;
//    }
//}
