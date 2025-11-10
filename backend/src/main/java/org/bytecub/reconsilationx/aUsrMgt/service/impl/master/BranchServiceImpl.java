package org.bytecub.reconsilationx.aUsrMgt.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MCompanyBranchDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.PaginationDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.CompanyBranchMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MCompanyBranch;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUser;
import org.bytecub.reconsilationx.aUsrMgt.service.master.BranchService;
import org.bytecub.reconsilationx.aUsrMgt.service.master.CompanyService;
import org.bytecub.reconsilationx.aUsrMgt.service.master.UserService;
import org.bytecub.reconsilationx.aUsrMgt.service.reference.ReferenceService;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterCriteria;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterUtility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
    private final MCompanyBranchDao companyBranchDao;
    private final CompanyBranchMapper companyBranchMapper;
    private final FilterUtility<MCompanyBranch, Long> filterUtility;
    private final CompanyService companyService;
    private final ReferenceService referenceService;
    private final UserService userService;
    private final MUserDao userDao;

    public BranchServiceImpl(MCompanyBranchDao companyBranchDao, CompanyBranchMapper companyBranchMapper, CompanyService companyService, ReferenceService referenceService, UserService userService, MUserDao userDao) {
        this.companyBranchDao = companyBranchDao;
        this.companyBranchMapper = companyBranchMapper;
        this.filterUtility = new FilterUtility<>(companyBranchDao);
        this.companyService = companyService;
        this.referenceService = referenceService;
        this.userService = userService;
        this.userDao = userDao;
    }


    @Override
    public MCompanyBranchDto getBranchById(Long branchId) {
        log.info("Fetching branch by ID: {}", branchId);
        try {
            MCompanyBranch branch = companyBranchDao.findByBranchIdAndIsActive(branchId, true)
                    .orElseThrow(() -> new BadRequestAlertException("Branch not found", "Branch", "branch.not.found"));
            return companyBranchMapper.toDto(branch);
        } catch (Exception e) {
            log.error("Error fetching branch by ID: {}", branchId, e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }

    @Override
    public ApiResponseDto<List<MCompanyBranchDto>> getBranches(int page, Integer perPage, String search, String sort, String direction, Long companyId) {
        log.info("Fetching branches with pagination: page={}, perPage={}, search={}, sort={}, direction={}, companyId={}", page, perPage, search, sort, direction, companyId);
        try {
            List<FilterCriteria<MCompanyBranch>> filterCriteriaList = new ArrayList<>();
            // Search List
            if (search != null && !search.isEmpty()) {
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("branchName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("branchCode").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("branchAddress").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            // Filter List
            if (companyId != null) {
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.equal(root.get("company").get("companyId"), companyId)
                );
            }

            filterCriteriaList.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<MCompanyBranch> pageable = filterUtility.filterRecords(page, perPage, sort, direction, filterCriteriaList);

            ApiResponseDto<List<MCompanyBranchDto>> response = new ApiResponseDto<>();
            PaginationDto pagination = new PaginationDto();
            pagination.setTotal((int) pageable.getTotalElements());
            response.setPagination(pagination);
            response.setResult(companyBranchMapper.listToDto(pageable.getContent()));

            return response;
        } catch (Exception e) {
            log.error("Error fetching branches", e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }

    @Override
    public MCompanyBranchDto updateBranch(Long branchId, MCompanyBranchDto branchDto) {
        log.info("Updating branch with ID: {}", branchId);
        try {
            MCompanyBranchDto branch = getBranchById(branchId);

            branch.setBranchCode(branchDto.getBranchCode());
            branch.setBranchName(branchDto.getBranchName());
            branch.setBranchAddress(branchDto.getBranchAddress());
            branch.setBranchContact1(branchDto.getBranchContact1());
            branch.setBranchContact2(branchDto.getBranchContact2());
            branch.setBranchEmail(branchDto.getBranchEmail());

            MCompanyDto companyDto = companyService.getCompanyById(branchDto.getCompanyId());
            branch.setCompanyId(branchDto.getCompanyId());
            branch.setCompany(null); // To avoid updating company details

            RStatusDto statusDto = referenceService.getStatusService().getStatusById(branchDto.getStatusId());
            branch.setStatusId(branchDto.getStatusId());
            branch.setStatus(null); // To avoid updating status details

            return companyBranchMapper.toDto(companyBranchDao.save(companyBranchMapper.toEntity(branch)));
        } catch (Exception e) {
            log.error("Error updating branch with ID: {}", branchId, e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }

    @Override
    public ResponseDto deleteBranch(Long branchId) {
        log.info("Deleting branch with ID: {}", branchId);
        try {
            MCompanyBranchDto branch = getBranchById(branchId);
            // perform delete
            companyBranchDao.deleteById(branchId);

            return new ResponseDto(branchId, "Branch deleted successfully");
        } catch (DataIntegrityViolationException de){
            log.error("Cannot delete branch {} - referenced in another table", branchId, de);
            throw new BadRequestAlertException("Cannot delete branch because it is used in another table.", "Branch", "dependency_exists");
        } catch (Exception e) {
            log.error("Error deleting branch with ID: {}", branchId, e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }

    @Override
    public ResponseDto createBranch(MCompanyBranchDto branchDto) {
        log.info("Creating new branch");
        try {
            // validations
            MCompanyDto companyDto = companyService.getCompanyById(branchDto.getCompanyId());
            RStatusDto statusDto = referenceService.getStatusService().getStatusById(branchDto.getStatusId());

            branchDto = companyBranchMapper.toDto(companyBranchDao.save(companyBranchMapper.toEntity(branchDto)));
            return new ResponseDto(branchDto.getBranchId(), branchDto.getBranchCode() + "Successfully created for company " + companyDto.getName());
        } catch (DataIntegrityViolationException ex) {
            Throwable rootCause = ex.getRootCause();
            if (rootCause != null && rootCause.getMessage() != null &&
                rootCause.getMessage().toLowerCase().contains("unique")) {
                log.error("Unique constraint violation: {}", rootCause.getMessage());
                throw new BadRequestAlertException(
                    "Duplicate entry. A record with the same unique field already exists.",
                    "Branch",
                    "unique_violation"
                );
            }
            log.error("Data integrity violation while creating branch", ex);
            throw new BadRequestAlertException("Data integrity error occurred.", "Branch", "data_integrity_error");

        } catch (Exception e) {
            log.error("Error creating new branch", e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }

    @Override
    public ResponseDto assignBranchToUser(Long branchId, Long userId) {
        log.info("Assigning branch with ID {} to user with ID {}", branchId, userId);
        try {
            // validations
            MCompanyBranchDto branchDto = getBranchById(branchId);
            MUser user = userDao.findByUserIdAndIsActive(userId, true)
                    .orElseThrow(() -> new BadRequestAlertException("User not found", "Branch", "user.not.found"));

            // Check where user & branch belongs to the same company
            if (!branchDto.getCompanyId().equals(user.getCompany().getCompanyId())) {
                throw new BadRequestAlertException("User and Branch belong to different companies", "Branch", "company.mismatch");
            } else {
                MCompanyBranch branch = new MCompanyBranch();
                branch.setBranchId(branchId);
                user.setBranch(branch);
                userDao.save(user);
                return new ResponseDto(branchId, branchDto.getBranchCode() + " assigned to user " + user.getUsername() + " successfully");
            }
        } catch (Exception e) {
            log.error("Error assigning branch to user", e);
            throw new BadRequestAlertException(e.getMessage(), "Branch", "Branch");
        }
    }
}
