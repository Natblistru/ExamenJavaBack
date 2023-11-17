package edu.step.examenJavaRest.service;

import edu.step.examenJavaRest.dto.EmployeeDTO;
import edu.step.examenJavaRest.repository.EmployeeRepository;
import edu.step.examenJavaRest.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public List<Employee> findAll() {

        return repository.findAll();
    }

    public List<EmployeeDTO> findAllEmployeeDTOs() {
        List<Employee> employees = repository.findAll();

        // Utilizarea ModelMapper pentru a converti lista de Employee în EmployeeDTO
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO findEmployeeDTOById(Integer id) {
        Employee employee = findById(id);

        // Convertim Employee în EmployeeDTO
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee findById(Integer id) {
        return repository.findById(id)
                .orElse(null);
    }

    public List<Employee> findByName(String name) {
        return repository.findEmployeesByNameLike(name);
    }

    public Page<Employee> findPage(int i) {
        Pageable page= PageRequest.of(i - 1, 6);
        return repository.findAll(page);
    }

    public Page<EmployeeDTO> findPage(int page, int size, Integer sortColumn, String sortOrd, String search) {
        Sort sortOrder = getSortOrder(sortColumn,sortOrd);
        Pageable pageable = PageRequest.of(page - 1, size, sortOrder);
        if (search != null && !search.isEmpty()) {
            String formattedKeyword = "%" + search.toLowerCase() + "%";
            return repository.findEmployeesByKeywordIgnoreCaseAndPartialMatch(formattedKeyword, pageable).map(this::convertToDTO);
        } else {
            return repository.findAll(pageable).map(this::convertToDTO);
        }
    }

    private Sort getSortOrder(Integer sortColumn, String sortOrder) {
        String[] sortProperties = {"name", "surname", "birthdate", "department.name", "department.company.name"};
        if (sortColumn == null || sortColumn <= 0 || sortColumn > sortProperties.length) {
            return Sort.unsorted();
        }
        String sortProperty = sortProperties[sortColumn - 1];
        if ("desc".equals(sortOrder)) {
            return Sort.by(sortProperty).descending();
        }
        return Sort.by(sortProperty);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee newEmployee = convertToEntity(employeeDTO);
        Employee savedEmployee = repository.save(newEmployee);
        return convertToDTO(savedEmployee);
    }

    public void save(Employee employee) {
        repository.save(employee);
    }

    public void updateEmployeeDTO(Integer id, EmployeeDTO updatedEmployeeDTO) {
        Employee updatedEmployee = convertToEntity(updatedEmployeeDTO);
        update(id, updatedEmployee);
    }

    public void update(Integer id, Employee updatedEmployee) {
        Employee existingEmployee = repository.findById(id).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setSurname(updatedEmployee.getSurname());
            existingEmployee.setBirthdate(updatedEmployee.getBirthdate());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            repository.save(existingEmployee);
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }


    public EmployeeDTO deleteEmployee(Integer id) {
        Employee deletedEmployee = repository.findById(id).orElse(null);

        if (deletedEmployee != null) {
            repository.deleteById(id);
            return convertToDTO(deletedEmployee);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
    }
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }
}


