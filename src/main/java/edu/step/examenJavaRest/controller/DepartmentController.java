package edu.step.examenJavaRest.controller;

import edu.step.examenJavaRest.dto.DepartmentDTO;
import edu.step.examenJavaRest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentDTOs() {
        List<DepartmentDTO> departmentDTOs = departmentService.findAllDepartmentDTOs();
        return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentDTOById(@PathVariable Integer id) {
        DepartmentDTO departmentDTO = departmentService.findDepartmentDTOById(id);
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO newDepartmentDTO = departmentService.addDepartment(departmentDTO);
        return new ResponseEntity<>(newDepartmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Integer id, @RequestBody DepartmentDTO updatedDepartmentDTO) {
        departmentService.updateDepartmentDTO(id, updatedDepartmentDTO);
        return new ResponseEntity<>(updatedDepartmentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Integer id) {
        DepartmentDTO deletedDepartmentDTO = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(deletedDepartmentDTO, HttpStatus.OK);
    }
}
