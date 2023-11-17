package edu.step.examenJavaRest.controller;

import edu.step.examenJavaRest.dto.EmployeeDTO;
import edu.step.examenJavaRest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) Integer sortColumn,
            @RequestParam(value = "ord", required = false) String sortOrder,
            @RequestParam(value = "search", required = false) String search) {
        try {
            Page<EmployeeDTO> employees = employeeService.findPage(page, size, sortColumn, sortOrder, search);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDTOById(@PathVariable Integer id) {
        EmployeeDTO employeeDTO = employeeService.findEmployeeDTOById(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO newEmployeeDTO = employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>(newEmployeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO updatedEmployeeDTO) {
        employeeService.updateEmployeeDTO(id, updatedEmployeeDTO);
        return new ResponseEntity<>(updatedEmployeeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Integer id) {
        EmployeeDTO deletedEmployeeDTO = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(deletedEmployeeDTO, HttpStatus.OK);
    }
}
