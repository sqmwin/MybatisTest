import com.sqm.dao.StudentDao;
import com.sqm.pojo.Student;
import com.sqm.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author sqm
 * @version 1.0
 */
public class Test {
    private SqlSession session;
    private StudentDao dao;

    //通过session的getMapper(Class<T> type)动态代理获得dao对象
    @Before
    public void setUp() {
        session = MyBatisUtil.getSession();
        dao = session.getMapper(StudentDao.class);
    }

    //将关闭session方法放入@after中
    @After
    public void tearDown() {
        session.close();
    }

    //每一个测试方法完成后都添加session.commit()

    //插入一条新数据
    @org.junit.Test
    public void test01() {
        String name = "songqiming";
        Student student = new Student(name, 23, 87);
        dao.insertStudent(student);
        System.out.println("name中包含" + name + "的student有:");
        List<Student> students = dao.selectStudentsByName(name);
        for (Student student1 : students) {
            System.out.println(student1.toString());
        }
        session.commit();
    }


    //插入后输出id值
    @org.junit.Test
    public void test02() {
        Student student = new Student("student",22,99);
        System.out.println("插入前student = " + student);
        dao.insertStudentCatchId(student);
        System.out.println("插入后student = " + student);

        session.commit();
    }

    //删除指定的id的数据
    @org.junit.Test
    public void Test03(){
        int id = 6;
        dao.deleteStudentById(id);
        session.commit();
    }

    //修改指定的Student数据
    @org.junit.Test
    public void test04(){
        Student student = new Student("songqiming", 32, 99);
        student.setId(5);
        dao.updateStudent(student);
        session.commit();
    }

    //获取所有Student对象并以List形式返回
    @org.junit.Test
    public void test05() {
        List<Student> students = dao.selectAllStudents();
        for (Student student : students) {
            System.out.println(student.toString());
        }
        session.commit();
    }

    //获取指定id的单个student对象
    @org.junit.Test
    public void test06() {
        Student student = dao.selectStudentById(3);
        System.out.println(student.toString());
        session.commit();
    }

    //通过指定id值或指定特定id值的student对象来获取student对象
    @org.junit.Test
    public void test07() {
        int id = 2;
        Student student = new Student();
        student.setId(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", id);
        map.put("student", student);
        System.out.println("要查询的id值为:" + id + ";要查询的student对象为:" +student.toString());
        student = dao.selectStudent(map);
        System.out.println(student.toString());
        session.commit();
    }

    //模糊查询
    @org.junit.Test
    public void test08() {
        String name = "i";
        List<Student> students = dao.selectStudentsByName(name);
        System.out.println("name中包含" + name + "的student有:");
        for (Student student : students) {
            System.out.println(student.toString());
        }
        session.commit();
    }

    //根据给定的map模糊查询
    @org.junit.Test
    public void test09() {
        Object ageCondition = 22;
        Object scoreCondition = 80;
        //指定的map中的key必须包含mapper中sql语句要查找的项目
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ageCondition",ageCondition);
        map.put("scoreCondition",scoreCondition);
        List<Student> students = dao.selectStudentsByMap(map);
        System.out.println("年龄小于等于" + ageCondition + "且得分大于等于" + scoreCondition + "的student有");
        for (Student student : students) {
            System.out.println(student.toString());
        }
        session.commit();
    }

    //根据给定的条件来筛选数据
    @org.junit.Test
    public void test10() {
        String name = "s";
        int age = 25;
        int score = 85;
        List<Student> students = dao.selectStudentsByConditions(name, age,score);
        System.out.println("名字中包含" + name + "且年龄小于等于" + age + "得分大于" + score +"的学生有");
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }

}
