package com.cyb.spring.jpa.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cyb.spring.jpa.demo.domain.Student;
import com.cyb.spring.jpa.demo.query.StudentQuery;
import com.cyb.spring.jpa.demo.repository.StudentRepository;
import com.cyb.spring.jpa.demo.util.JsonUtils;

/**
 * 学生服务
 * 
 * @author Administrator
 *
 */
@Service
public class StudentService {

	/**
	 * 日志接口
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StudentService.class);

	/**
	 * 学生数据仓库
	 */
	private final StudentRepository studentRepository;

	/**
	 * 初始化
	 * 
	 * @param studentRepository
	 *            学生数据仓库
	 */
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	/**
	 * 根据学生编号获取学生
	 * 
	 * @param id
	 *            学生编号
	 * @return 学生
	 */
	public Student getById(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.getById: id = {}", id);
		}

		Optional<Student> optional = studentRepository.findById(id);
		Student student = optional.orElse(null);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.getById: end, return = {}",
					JsonUtils.bean2Json(student));
		}

		return student;
	}

	/**
	 * 根据条件查询学生数据
	 * 
	 * @param studentQuery
	 *            学生查询条件
	 * @return 学生列表
	 */
	public List<Student> listByQuery(StudentQuery studentQuery) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"StudentService.listByQuery: start, studentQuery = {}",
					JsonUtils.bean2Json(studentQuery));
		}

		List<Student> students = studentRepository
				.findAll(new Specification<Student>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Predicate toPredicate(Root<Student> root,
							CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<Predicate>();

						String name = studentQuery.getName();
						if (!StringUtils.isEmpty(name)) {
							Path<String> namePath = root.<String> get("name");
							Predicate namePredicate = criteriaBuilder.equal(
									namePath, name);
							predicates.add(namePredicate);
						}

						Byte minAge = studentQuery.getMinAge();
						if (minAge != null) {
							Path<Byte> agePath = root.<Byte> get("age");
							Predicate agePredicate = criteriaBuilder.ge(
									agePath, minAge);
							predicates.add(agePredicate);
						}

						Byte maxAge = studentQuery.getMinAge();
						if (minAge != null) {
							Path<Byte> agePath = root.<Byte> get("age");
							Predicate agePredicate = criteriaBuilder.le(
									agePath, maxAge);
							predicates.add(agePredicate);
						}

						return query.where(
								predicates.toArray(new Predicate[predicates
										.size()])).getRestriction();
					}

				});

		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.listByQuery: end, return = {}",
					JsonUtils.bean2Json(students));
		}

		return students;
	}

	/**
	 * 保存学生
	 * 
	 * @param student
	 *            学生
	 */
	public Student save(Student student) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.save: start, student = {}",
					JsonUtils.bean2Json(student));
		}

		student = studentRepository.save(student);
		if (logger.isDebugEnabled()) {
			logger.debug("Studentservice.save: end, return = {}",
					JsonUtils.bean2Json(student));
		}

		return student;
	}

	/**
	 * 删除学生
	 * 
	 * @param id
	 *            学生编号
	 */
	public void deleteById(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.deleteById: start, id = {}", id);
		}

		studentRepository.deleteById(id);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentService.deleteById: end");
		}
	}
}
