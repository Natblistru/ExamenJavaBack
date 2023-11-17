package edu.step.examenJavaRest.controller;

import edu.step.examenJavaRest.dto.CompanyDTO;
import edu.step.examenJavaRest.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanyDTOs() {
        List<CompanyDTO> companyDTOs = companyService.findAllCompanyDTOs();
        return new ResponseEntity<>(companyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyDTOById(@PathVariable Integer id) {
        CompanyDTO companyDTO = companyService.findCompanyDTOById(id);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO newCompanyDTO = companyService.addCompany(companyDTO);
        return new ResponseEntity<>(newCompanyDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Integer id, @RequestBody CompanyDTO updatedCompanyDTO) {
        companyService.updateCompanyDTO(id, updatedCompanyDTO);
        return new ResponseEntity<>(updatedCompanyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable Integer id) {
        CompanyDTO deletedCompanyDTO = companyService.deleteCompany(id);
        return new ResponseEntity<>(deletedCompanyDTO, HttpStatus.OK);
    }
}
