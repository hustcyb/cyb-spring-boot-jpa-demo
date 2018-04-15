package com.cyb.spring.jpa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cyb.spring.jpa.demo.domain.Student;

/**
 * 学生数据仓库
 * 
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer>,
		JpaSpecificationExecutor<Student> {

}
