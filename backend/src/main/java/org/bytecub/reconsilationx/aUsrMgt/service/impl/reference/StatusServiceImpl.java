package org.bytecub.reconsilationx.aUsrMgt.service.impl.reference;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.reference.RStatusDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.PaginationDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.mappers.reference.StatusMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;
import org.bytecub.reconsilationx.aUsrMgt.service.reference.StatusService;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterCriteria;
import org.bytecub.reconsilationx.aUsrMgt.utils.Search.FilterUtility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StatusServiceImpl implements StatusService {

    private final RStatusDao statusDao;
    private final StatusMapper statusMapper;
    private final FilterUtility<RStatus, Long> statusFilterUtility;

    public StatusServiceImpl(RStatusDao statusDao, StatusMapper statusMapper) {
        this.statusDao = statusDao;
        this.statusMapper = statusMapper;
        this.statusFilterUtility = new FilterUtility<>(statusDao);
    }

    @Override
    public RStatusDto createStatus(RStatusDto statusDto) {
        log.info("Inside StatusServiceImpl.createStatus");
        try {
            statusDto.setStatusId(null);
            statusDto.setIsActive(true);

            if (statusDto.getStatusName() == null || statusDto.getStatusName().isEmpty()) {
                throw new BadRequestAlertException("Status name is required", "Status", "createStatus");
            }

            return statusMapper.toDto(statusDao.save(statusMapper.toEntity(statusDto)));
        } catch (Exception e) {
            log.error("Error in StatusServiceImpl.createStatus", e);
            throw new BadRequestAlertException("Error in creating status", "Status", "createStatus");
        }
    }

    @Override
    public ApiResponseDto<List<RStatusDto>> listStatus(int page, Integer perPage, String search, String sort, String direction) {
        log.info("Inside StatusServiceImpl.listStatus");
        try {
            List<FilterCriteria<RStatus>> filterCriteriaList = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("statusName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteriaList.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("statusDescription").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            filterCriteriaList.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<RStatus> pageable = statusFilterUtility.filterRecords(page, perPage, sort, direction, filterCriteriaList);

            ApiResponseDto<List<RStatusDto>> response = new ApiResponseDto<>();
            PaginationDto paginationDto = new PaginationDto();
            paginationDto.setTotal((int) pageable.getTotalElements());
            response.setPagination(paginationDto);
            response.setResult(statusMapper.listToDto(pageable.getContent()));

            return response;
        } catch (Exception e) {
            log.error("Error in StatusServiceImpl.listStatus", e);
            throw new BadRequestAlertException("Error in listing status", "Status", "listStatus");
        }
    }

    @Override
    public RStatusDto getStatusById(Long statusId) {
        log.info("Inside StatusServiceImpl.getStatusById");
        try {
            Optional<RStatus> statusOp = statusDao.findByStatusIdAndIsActive(statusId, true);
            return statusOp.map(statusMapper::toDto).orElseThrow(
                    () -> new BadRequestAlertException("Status not found", "Status", "getStatusById")
            );
        } catch (Exception e) {
            log.error("Error in StatusServiceImpl.getStatusById", e);
            throw new BadRequestAlertException("Error in getting status", "Status", "getStatusById");
        }
    }

    @Override
    public ResponseDto updateStatus(Long statusId, RStatusDto statusDto) {
        log.info("Inside StatusServiceImpl.updateStatus");
        try {
            Optional<RStatus> statusOp = statusDao.findByStatusIdAndIsActive(statusId, true);
            if (statusOp.isEmpty()) {
                throw new BadRequestAlertException("Status not found", "Status", "updateStatus");
            } else {
                RStatus status = statusOp.get();
                if (status.getStatusName().isEmpty()) {
                    throw new BadRequestAlertException("Status name is required", "Status", "updateStatus");
                }
                status.setStatusName(statusDto.getStatusName());
                status.setStatusDescription(statusDto.getStatusDescription());

                return new ResponseDto(statusDao.save(status).getStatusId(), "Status updated successfully");
            }
        } catch (Exception e) {
            log.error("Error in StatusServiceImpl.updateStatus", e);
            throw new BadRequestAlertException("Error in updating status", "Status", "updateStatus");
        }
    }

    @Override
    public ResponseDto deleteStatus(Long statusId) {
        log.info("Inside StatusServiceImpl.deleteStatus");
        try {
            Optional<RStatus> statusOp = statusDao.findByStatusIdAndIsActive(statusId, true);
            if (statusOp.isEmpty()) {
                throw new BadRequestAlertException("Status not found", "Status", "deleteStatus");
            } else {
                statusDao.deleteById(statusId);
                return new ResponseDto(statusId, "Status deleted successfully");
            }
        } catch (DataIntegrityViolationException ex) {
            log.error("Error in StatusServiceImpl.deleteStatus", ex);
            throw new BadRequestAlertException("Cannot delete Status, It's in use.", "Status", "deleteStatus");
        } catch (Exception e) {
            log.error("Error in StatusServiceImpl.deleteStatus", e);
            throw new BadRequestAlertException("Error in deleting status.", "Status", "deleteStatus");
        }
    }
}
