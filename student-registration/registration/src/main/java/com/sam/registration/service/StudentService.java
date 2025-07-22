package com.sam.registration.service;

import com.sam.registration.config.rabbitMQ.RabbitMQSender;
import com.sam.registration.dto.EnrollmentResponseDto;
import com.sam.registration.dto.StudentResponseDto;
import com.sam.registration.entity.Student;
import com.sam.registration.exception.EntityNotFoundException;
import com.sam.registration.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final RabbitMQSender rabbitMQSender;

    public List<StudentResponseDto> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();

        return studentList.stream().map(student -> {
            StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);

            List<EnrollmentResponseDto> enrollmentResponseDtos = student.getEnrollments().stream().map(
                    enrollment -> EnrollmentResponseDto.builder()
                            .studentId(enrollment.getStudent().getId())
                            .courseId(enrollment.getCourse().getId()).build()).toList();
            studentResponseDto.setEnrollmentResponseDtos(enrollmentResponseDtos);
            return studentResponseDto;
        }).toList();
    }


    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Requested student not found"));
        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        List<EnrollmentResponseDto> enrollmentResponseDtos = student.getEnrollments().stream().map(enrollment -> EnrollmentResponseDto.builder().studentId(enrollment.getStudent().getId()).courseId(enrollment.getCourse().getId()).build()).toList();
        studentResponseDto.setEnrollmentResponseDtos(enrollmentResponseDtos);
        return studentResponseDto;
    }

    public Student createStudent(Student student) {
        Student tobeSaved = studentRepository.save(student);

        rabbitMQSender.sendEmailRequest(tobeSaved);

        return student;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

