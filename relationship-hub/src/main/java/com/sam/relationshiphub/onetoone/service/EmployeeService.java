package com.sam.relationshiphub.onetoone.service;


import com.sam.relationshiphub.onetoone.dto.request.EmployeeRequestDto;
import com.sam.relationshiphub.onetoone.dto.response.EmployeeResponseDto;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);
    EmployeeResponseDto getEmployee(Long id);
    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);
    EmployeeResponseDto patchEmployee(Long id, EmployeeRequestDto requestDto);
    void deleteEmployee(Long id);
}
