package com.sam.relationshiphub.onetoone.service.impl;

import com.sam.relationshiphub.onetoone.dto.request.EmployeeDetailRequestDto;
import com.sam.relationshiphub.onetoone.dto.request.EmployeeRequestDto;
import com.sam.relationshiphub.onetoone.dto.response.EmployeeDetailResponseDto;
import com.sam.relationshiphub.onetoone.dto.response.EmployeeResponseDto;
import com.sam.relationshiphub.onetoone.entity.Employee;
import com.sam.relationshiphub.onetoone.entity.EmployeeDetail;
import com.sam.relationshiphub.onetoone.repository.EmployeeRepository;
import com.sam.relationshiphub.onetoone.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = toEntity(requestDto);
        Employee saved = employeeRepository.save(employee);
        return toResponseDto(saved);
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return toResponseDto(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        existing.setName(requestDto.getName());
        existing.setDepartment(requestDto.getDepartment());
        existing.setEmail(requestDto.getEmail());

        if (existing.getEmployeeDetail() != null && requestDto.getEmployeeDetail() != null) {
            EmployeeDetail detail = existing.getEmployeeDetail();
            detail.setAddress(requestDto.getEmployeeDetail().getAddress());
            detail.setDob(requestDto.getEmployeeDetail().getDob());
            detail.setAadharNumber(requestDto.getEmployeeDetail().getAadharNumber());
        }

        return toResponseDto(employeeRepository.save(existing));
    }

    @Override
    public EmployeeResponseDto patchEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (requestDto.getName() != null) existing.setName(requestDto.getName());
        if (requestDto.getDepartment() != null) existing.setDepartment(requestDto.getDepartment());
        if (requestDto.getEmail() != null) existing.setEmail(requestDto.getEmail());

        if (requestDto.getEmployeeDetail() != null) {
            if (existing.getEmployeeDetail() == null) {
                existing.setEmployeeDetail(toDetailEntity(requestDto.getEmployeeDetail()));
            } else {
                EmployeeDetail detail = existing.getEmployeeDetail();
                if (requestDto.getEmployeeDetail().getAddress() != null)
                    detail.setAddress(requestDto.getEmployeeDetail().getAddress());
                if (requestDto.getEmployeeDetail().getDob() != null)
                    detail.setDob(requestDto.getEmployeeDetail().getDob());
                if (requestDto.getEmployeeDetail().getAadharNumber() != null)
                    detail.setAadharNumber(requestDto.getEmployeeDetail().getAadharNumber());
            }
        }

        return toResponseDto(employeeRepository.save(existing));
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(existing);
    }

    // Utility mapping methods

    private Employee toEntity(EmployeeRequestDto dto) {
        return Employee.builder()
                .name(dto.getName())
                .department(dto.getDepartment())
                .email(dto.getEmail())
                .employeeDetail(toDetailEntity(dto.getEmployeeDetail()))
                .build();
    }

    private EmployeeDetail toDetailEntity(EmployeeDetailRequestDto dto) {
        if (dto == null) return null;

        return EmployeeDetail.builder()
                .address(dto.getAddress())
                .dob(dto.getDob())
                .aadharNumber(dto.getAadharNumber())
                .build();
    }

    private EmployeeResponseDto toResponseDto(Employee employee) {
        EmployeeDetail detail = employee.getEmployeeDetail();
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .email(employee.getEmail())
                .employeeDetail(detail == null ? null :
                        EmployeeDetailResponseDto.builder()
                                .id(detail.getId())
                                .address(detail.getAddress())
                                .dob(detail.getDob())
                                .aadharNumber(detail.getAadharNumber())
                                .build())
                .build();
    }
}
