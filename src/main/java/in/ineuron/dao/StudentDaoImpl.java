package in.ineuron.dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import in.ineuron.Model.Student;
import in.ineuron.util.HibernateUtil;

//Persistence logic using JDBC API
public class StudentDaoImpl implements IStudentDao {

	Session session = HibernateUtil.getSession();

	@SuppressWarnings("finally")
	public String addStudent(Student student) {

		Transaction transaction = null;
		Boolean flag = false;
		try {
			transaction = session.beginTransaction();
			session.save(student);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				transaction.commit();
				return "success";
			} else {
				transaction.rollback();
				return "failure";
			}
		}
	}

	@Override
	public Student searchStudent(Integer sid) {

		Student student = session.get(Student.class, sid);

		return student;
	}

	@Override
	public String deleteStudent(Integer sid) {

		Student student = searchStudent(sid);

		Transaction transaction = session.beginTransaction();

		String status = "";
		boolean flage = false;
		try {
			if (transaction != null) {
				if (student != null) {
					session.delete(student);
					flage = true;
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flage) {
				transaction.commit();
				status = "success";
			} else {
				transaction.rollback();
				status = "not found";
			}
		}

		return status;

	}



	@Override
	public String updateStudent(Student student) {

		Transaction transaction = session.beginTransaction();

		String status = "";
		boolean flage = false;
		try {
			if (transaction != null) {
				if (student != null) {
					
					session.merge(student);
					flage = true;
					
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flage) {
				transaction.commit();
				status = "sucess";
			} else {
				transaction.rollback();
				status = "failure";
			}
		}

		return status;
	}

}
