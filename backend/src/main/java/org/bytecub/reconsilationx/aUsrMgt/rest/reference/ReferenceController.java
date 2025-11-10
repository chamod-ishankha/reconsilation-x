package org.bytecub.reconsilationx.aUsrMgt.rest.reference;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RRolesDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RStatusDto;
import org.bytecub.reconsilationx.aUsrMgt.service.reference.ReferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reference")
@Slf4j
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    /**
     * Status Endpoints
     */

    @PostMapping("/status")
    public ResponseEntity<RStatusDto> createStatus(@RequestBody RStatusDto statusDto) {
        log.info("Inside ReferenceController.createStatus");
        return new ResponseEntity<>(referenceService.getStatusService().createStatus(statusDto), HttpStatus.CREATED);
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponseDto<List<RStatusDto>>> listStatus(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) Integer perPage,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "statusId", required = false) String sort,
            @RequestParam(defaultValue = "desc", required = false) String direction
    ) {
        log.info("Inside ReferenceController.listStatus");
        return new ResponseEntity<>(referenceService.getStatusService().listStatus(page, perPage, search, sort, direction), HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<RStatusDto> getStatusById(@PathVariable Long statusId) {
        log.info("Inside ReferenceController.getStatusById");
        return new ResponseEntity<>(referenceService.getStatusService().getStatusById(statusId), HttpStatus.OK);
    }

    @PutMapping("/status/{statusId}")
    public ResponseEntity<ResponseDto> updateStatus(@PathVariable Long statusId, @RequestBody RStatusDto statusDto) {
        log.info("Inside ReferenceController.updateStatus");
        return new ResponseEntity<>(referenceService.getStatusService().updateStatus(statusId, statusDto), HttpStatus.OK);
    }

    @DeleteMapping("/status/{statusId}")
    public ResponseEntity<ResponseDto> deleteStatus(@PathVariable Long statusId) {
        log.info("Inside ReferenceController.deleteStatus");
        return new ResponseEntity<>(referenceService.getStatusService().deleteStatus(statusId), HttpStatus.OK);
    }

    /**
     * Role Endpoints
     */

    @PostMapping("/role/assign")
    public ResponseEntity<ResponseDto> assignRoleToUser(
            @RequestParam Long userId,
            @RequestParam Long roleId
    ) {
        log.info("Inside ReferenceController.assignRoleToUser");
        return new ResponseEntity<>(referenceService.getRoleService().assignRoleToUser(userId, roleId), HttpStatus.OK);
    }

    @GetMapping("/role/byRoleName/{roleName}")
    public ResponseEntity<RRolesDto> getRoleByRoleName(@PathVariable String roleName) {
        log.info("Inside ReferenceController.getRoleByRoleName");
        return new ResponseEntity<>(referenceService.getRoleService().getRoleByRoleName(roleName), HttpStatus.OK);
    }

}
