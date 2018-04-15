package com.cyb.spring.jpa.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.spring.jpa.demo.domain.Student;
import com.cyb.spring.jpa.demo.dto.StudentDTO;
import com.cyb.spring.jpa.demo.query.StudentQuery;
import com.cyb.spring.jpa.demo.service.StudentService;
import com.cyb.spring.jpa.demo.util.JsonUtils;

/**
 * 学生控制器
 * 
 * @author Administrator
 *
 */
@RequestMapping("students")
@RestController
public class StudentController {

	/**
	 * 日志接口
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StudentController.class);

	/**
	 * 学生服务
	 */
	private final StudentService studentService;

	/**
	 * 初始化
	 * 
	 * @param studentService
	 *            学生服务
	 */
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * 根据学生编号获取学生
	 * 
	 * @param studentId
	 *            学生编号
	 * @return 学生
	 */
	@GetMapping("{studentId}")
	public StudentDTO getById(@PathVariable Integer studentId) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.getById: start, id = {}", studentId);
		}
		
		Student student = studentService.getById(studentId);
		StudentDTO studentDTO = createStudentDTO(student);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.getById: end, return = {}", JsonUtils.bean2Json(studentDTO));
		}

		return studentDTO;
	}

	/**
	 * 根据条件查询学生列表
	 * 
	 * @param query
	 *            学生查询条件
	 * @return 学生列表
	 */
	@GetMapping
	public List<StudentDTO> listByQuery(StudentQuery query) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.listByQuery: start, query = {}", query);
		}
		
		List<Student> students = studentService.listByQuery(query);
		List<StudentDTO> studentDTOs = createStudentDTOs(students);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.listByQuery: end, return = {}", JsonUtils.bean2Json(studentDTOs));
		}

		return studentDTOs;
	}

	/**
	 * 保存学生
	 * 
	 * @param studentDTO
	 *            学生
	 * @return 学生编号
	 */
	@PostMapping
	public Integer save(@RequestBody StudentDTO studentDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.save: start, student = {}",
					JsonUtils.bean2Json(studentDTO));
		}

		Student student = createStudent(studentDTO);
		student = studentService.save(student);
		Integer studentId = student.getId();
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.save: end, return = {}", studentId);
		}

		return studentId;
	}

	/**
	 * 根据学生编号更新学生
	 * 
	 * @param studentDTO
	 *            学生
	 */
	@PutMapping
	public void updateById(@RequestBody StudentDTO studentDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"StudentController.updateById: start, studentDTO = {}",
					studentDTO);
		}

		Student student = createStudent(studentDTO);
		studentService.save(student);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.updateById: end");
		}
	}

	/**
	 * 删除学生
	 * 
	 * @param studentId
	 *            学生编号
	 */
	@DeleteMapping("{studentId}")
	public void deleteById(@PathVariable Integer studentId) {
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.deleteById: start, studentId = {}",
					studentId);
		}

		studentService.deleteById(studentId);
		if (logger.isDebugEnabled()) {
			logger.debug("StudentController.deleteById: end");
		}
	}

	private StudentDTO createStudentDTO(Student student) {
		StudentDTO studentDTO = null;
		if (student != null) {
			studentDTO = new StudentDTO();
			BeanUtils.copyProperties(student, studentDTO);
		}

		return studentDTO;
	}

	/**
	 * 创建学生实体
	 * 
	 * @param studentDTO
	 *            学生传输层实体
	 * @return
	 */
	private Student createStudent(StudentDTO studentDTO) {
		Student student = null;
		if (studentDTO != null) {
			student = new Student();
			BeanUtils.copyProperties(studentDTO, student);
		}

		return student;
	}

	private List<StudentDTO> createStudentDTOs(List<Student> students) {
		List<StudentDTO> studentDTOs = null;
		if (students != null) {
			studentDTOs = new ArrayList<StudentDTO>();

			for (Student student : students) {
				StudentDTO studentDTO = createStudentDTO(student);
				studentDTOs.add(studentDTO);
			}
		}

		return studentDTOs;
	}
}
