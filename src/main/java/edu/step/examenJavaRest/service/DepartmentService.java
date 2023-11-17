package edu.step.examenJavaRest.service;

import edu.step.examenJavaRest.dto.DepartmentDTO;
import edu.step.examenJavaRest.repository.DepartmentRepository;
import edu.step.examenJavaRest.model.Department;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Department> findAll() {
        return repository.findAll();
    }

    public List<DepartmentDTO> findAllDepartmentDTOs() {
        List<Department> departments = repository.findAll();

        // Utilizarea ModelMapper pentru a converti lista de Department în DepartmentDTO
        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO findDepartmentDTOById(Integer id) {
        Department department = findById(id);

        // Convertim Department în DepartmentDTO
        return modelMapper.map(department, DepartmentDTO.class);
    }

    public Department findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        Department newDepartment = convertToEntity(departmentDTO);
        Department savedDepartment = repository.save(newDepartment);
        return convertToDTO(savedDepartment);
    }

    public void updateDepartmentDTO(Integer id, DepartmentDTO updatedDepartmentDTO) {
        Department updatedDepartment = convertToEntity(updatedDepartmentDTO);
        update(id, updatedDepartment);
    }

    public void update(Integer id, Department updatedDepartment) {
        Department existingDepartment = repository.findById(id).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(updatedDepartment.getName());
            // Alte actualizări, dacă este cazul
            repository.save(existingDepartment);
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public DepartmentDTO deleteDepartment(Integer id) {
        Department deletedDepartment = repository.findById(id).orElse(null);

        if (deletedDepartment != null) {
            repository.deleteById(id);
            return convertToDTO(deletedDepartment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with id: " + id);
        }
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, Department.class);
    }

    private DepartmentDTO convertToDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }
}
