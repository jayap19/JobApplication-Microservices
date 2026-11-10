package com.jobApp.companyms.Company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company){
        boolean b = companyService.updateCompany(company, id);
        if(b){
            return new ResponseEntity<>("Company details updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Company with the specified id not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>(company,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        boolean isDeleted = companyService.deleteCompany(id);
        if(isDeleted){
            return new ResponseEntity<>("Company successfully deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company c = companyService.getCompanyById(id);
        if(c != null){
            return new ResponseEntity<>(c,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
