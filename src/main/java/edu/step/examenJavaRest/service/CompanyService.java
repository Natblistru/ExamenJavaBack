package edu.step.examenJavaRest.service;

import edu.step.examenJavaRest.dto.CompanyDTO;
import edu.step.examenJavaRest.repository.CompanyRepository;
import edu.step.examenJavaRest.model.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Company> findAll() {
        return repository.findAll();
    }

    public List<CompanyDTO> findAllCompanyDTOs() {
        List<Company> companies = repository.findAll();

        // Utilizarea ModelMapper pentru a converti lista de Company în CompanyDTO
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
    }

    public CompanyDTO findCompanyDTOById(Integer id) {
        Company company = findById(id);

        // Convertim Company în CompanyDTO
        return modelMapper.map(company, CompanyDTO.class);
    }

    public Company findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public CompanyDTO addCompany(CompanyDTO companyDTO) {
        Company newCompany = convertToEntity(companyDTO);
        Company savedCompany = repository.save(newCompany);
        return convertToDTO(savedCompany);
    }

    public void updateCompanyDTO(Integer id, CompanyDTO updatedCompanyDTO) {
        Company updatedCompany = convertToEntity(updatedCompanyDTO);
        update(id, updatedCompany);
    }

    public void update(Integer id, Company updatedCompany) {
        Company existingCompany = repository.findById(id).orElse(null);
        if (existingCompany != null) {
            existingCompany.setName(updatedCompany.getName());
            repository.save(existingCompany);
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public CompanyDTO deleteCompany(Integer id) {
        Company deletedCompany = repository.findById(id).orElse(null);

        if (deletedCompany != null) {
            repository.deleteById(id);
            return convertToDTO(deletedCompany);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
    }

    private Company convertToEntity(CompanyDTO companyDTO) {
        return modelMapper.map(companyDTO, Company.class);
    }

    private CompanyDTO convertToDTO(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }
}
