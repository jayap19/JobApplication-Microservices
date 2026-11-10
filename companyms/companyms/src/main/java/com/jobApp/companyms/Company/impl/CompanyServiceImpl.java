package com.jobApp.companyms.Company.impl;
import com.jobApp.companyms.Company.Company;
import com.jobApp.companyms.Company.CompanyRepository;

import java.util.List;
import java.util.Optional;

import com.jobApp.companyms.Company.clients.ReviewClient;
import com.jobApp.companyms.Company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements com.jobApp.companyms.Company.CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> all = companyRepository.findAll();
        return all;
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if(byId.isPresent()){
            Company company1 = byId.get();
            company1.setName(company.getName());
            company1.setDescription(company.getDescription());
            companyRepository.save(company1);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        else{
        return false;}
    }

    @Override
    public Company getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        return company;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()->new NotFoundException("Company not found"));
        double averageRating = reviewClient.getAverageratingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);

    }
}
