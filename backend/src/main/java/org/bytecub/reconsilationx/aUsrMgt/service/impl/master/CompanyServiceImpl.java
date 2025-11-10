package org.bytecub.reconsilationx.aUsrMgt.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MCompanyDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.PaginationDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.CompanyMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompany;
import org.bytecub.reconsilationx.aUsrMgt.service.master.CompanyService;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterCriteria;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterUtility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final MCompanyDao companyDao;
    private final CompanyMapper companyMapper;
    private final FilterUtility<MCompany, Long> filterUtility;

    public CompanyServiceImpl(MCompanyDao companyDao, CompanyMapper companyMapper) {
        this.companyDao = companyDao;
        this.companyMapper = companyMapper;
        this.filterUtility = new FilterUtility<>(companyDao);
    }

    @Override
    public MCompanyDto getCompanyById(Long companyId) {
        log.info("Fetching company by ID: {}", companyId);
        try {
            MCompany company = companyDao.findByCompanyIdAndIsActive(companyId, true)
                    .orElseThrow(() -> new BadRequestAlertException("Company not found", "Company", "company.not.found"));
            return companyMapper.toDto(company);
        } catch (Exception e) {
            log.error("Error fetching company by ID: {}", companyId, e);
            throw new BadRequestAlertException(e.getMessage(), "Company", "Company");
        }
    }

    @Override
    public ApiResponseDto<List<MCompanyDto>> getCompanies(int page, Integer perPage, String search, String sort, String direction) {
        log.info("Fetching companies with pagination: page={}, perPage={}, search={}, sort={}, direction={}", page, perPage, search, sort, direction);
        try {
            List<FilterCriteria<MCompany>> filterCriteriaList = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("name").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("companyDescription").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("website").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("address").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            filterCriteriaList.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<MCompany> pageable = filterUtility.filterRecords(page, perPage, sort, direction, filterCriteriaList);

            ApiResponseDto<List<MCompanyDto>> response = new ApiResponseDto<>();
            PaginationDto pagination = new PaginationDto();
            pagination.setTotal((int) pageable.getTotalElements());
            response.setPagination(pagination);
            response.setResult(companyMapper.listToDto(pageable.getContent()));

            return response;
        } catch (Exception e) {
            log.error("Error fetching companies", e);
            throw new BadRequestAlertException(e.getMessage(), "Company", "Company");
        }
    }

    @Override
    public MCompanyDto updateCompany(Long companyId, MCompanyDto companyDto) {
        log.info("Updating company with ID: {}", companyId);
        try {
            MCompanyDto company = getCompanyById(companyId);

            company.setName(companyDto.getName());
            company.setEmail(companyDto.getEmail());
            company.setContactNo(companyDto.getContactNo());
            company.setAddress(companyDto.getAddress());
            company.setWebsite(companyDto.getWebsite());
            company.setCompanyDescription(companyDto.getCompanyDescription());
            company.setMaxFailedLoginAttempts(companyDto.getMaxFailedLoginAttempts());
            company.setEnableEmailVerification(companyDto.getEnableEmailVerification());
            company.setAllowLeaveRequests(companyDto.getAllowLeaveRequests());
            company.setCompanyDefaultLeaveRequestCount(companyDto.getCompanyDefaultLeaveRequestCount());

            company = companyMapper.toDto(companyDao.save(companyMapper.toEntity(company)));
            return company;
        } catch (Exception e) {
            log.error("Error updating company with ID: {}", companyId, e);
            throw new BadRequestAlertException(e.getMessage(), "Company", "Company");
        }
    }

    @Override
    public ResponseDto companyDismiss(Long companyId) {
        log.info("Dismissing company with ID: {}", companyId);
        try {
            MCompanyDto company = getCompanyById(companyId);
            // perform delete
            companyDao.deleteById(companyId);

            return new ResponseDto(companyId, "Company deleted successfully");
        } catch (DataIntegrityViolationException de){
            log.error("Cannot delete company {} - referenced in another table", companyId, de);
            throw new BadRequestAlertException("Cannot delete company because it is used in another table.", "Company", "dependency_exists");
        } catch (Exception e) {
            log.error("Error dismissing company with ID: {}", companyId, e);
            throw new BadRequestAlertException(e.getMessage(), "Company", "Company");
        }
    }
}
