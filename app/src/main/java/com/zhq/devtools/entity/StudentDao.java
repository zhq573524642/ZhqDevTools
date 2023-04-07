package com.zhq.devtools.entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/31 16:58
 * Description
 */
@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getStudentList();

    @Query("SELECT * FROM student WHERE id=:id")
    Student getStudentById(int id);

    //范围查询
//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

    //一个参数多次使用
//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);

    //传递一个参数
//    @Query("SELECT * FROM user WHERE age > :minAge")
//    public User[] loadAllUsersOlderThan(int minAge);

    //传递多个参数
//    @Query("SELECT * FROM user WHERE region IN (:regions)")
//    public List<User> loadUsersFromRegions(List<String> regions);

    //查询多个表
//    @Query("SELECT * FROM book " +
//            "INNER JOIN loan ON loan.book_id = book.id " +
//            "INNER JOIN user ON user.id = loan.user_id " +
//            "WHERE user.name LIKE :userName")
//    public List<Book> findBooksBorrowedByNameSync(String userName);
}
