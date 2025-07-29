package com.sam.relationshiphub.onetoone.controller;

import com.sam.relationshiphub.onetoone.dto.request.EmployeeRequestDto;
import com.sam.relationshiphub.onetoone.dto.response.EmployeeResponseDto;
import com.sam.relationshiphub.onetoone.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public EmployeeResponseDto create(@RequestBody EmployeeRequestDto requestDto) {
        return employeeService.createEmployee(requestDto);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDto get(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDto update(@PathVariable Long id, @RequestBody EmployeeRequestDto requestDto) {
        return employeeService.updateEmployee(id, requestDto);
    }

    @PatchMapping("/{id}")
    public EmployeeResponseDto patch(@PathVariable Long id, @RequestBody EmployeeRequestDto requestDto) {
        return employeeService.patchEmployee(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
