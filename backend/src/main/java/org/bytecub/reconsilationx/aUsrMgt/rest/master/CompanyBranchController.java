package org.bytecub.reconsilationx.aUsrMgt.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyBranchDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.service.master.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@Slf4j
public class CompanyBranchController {

    private final BranchService branchService;

    public CompanyBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // get branch by id
    @GetMapping("/{branchId}")
    public ResponseEntity<MCompanyBranchDto> getBranchById(@PathVariable Long branchId) {
        log.info("Inside branch controller get branch by id method");
        return new ResponseEntity<>(branchService.getBranchById(branchId), HttpStatus.OK);
    }

    // get all branches
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<MCompanyBranchDto>>> getBranches(
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "10", required = false) Integer perPage,
        @RequestParam(defaultValue = "", required = false) String search,
        @RequestParam(defaultValue = "branchId", required = false) String sort,
        @RequestParam(defaultValue = "desc", required = false) String direction,
        @RequestParam(required = true) Long companyId
    ) {
        log.info("Inside branch controller get all branches method");
        return new ResponseEntity<>(branchService.getBranches(page, perPage, search, sort, direction, companyId), HttpStatus.OK);
    }

    // update branch
    @PutMapping("/{branchId}")
    public ResponseEntity<MCompanyBranchDto> updateBranch(@PathVariable Long branchId, @RequestBody MCompanyBranchDto branchDto) {
        log.info("Inside branch controller update branch method");
        return new ResponseEntity<>(branchService.updateBranch(branchId, branchDto), HttpStatus.OK);
    }

    // delete branch
    @DeleteMapping("/{branchId}")
    public ResponseEntity<ResponseDto> deleteBranch(@PathVariable Long branchId) {
        log.info("Inside branch controller delete company method");
        return new ResponseEntity<>(branchService.deleteBranch(branchId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createBranch(@RequestBody MCompanyBranchDto branchDto) {
        log.info("Inside branch controller create branch method");
        return new ResponseEntity<>(branchService.createBranch(branchDto), HttpStatus.CREATED);
    }

    @PostMapping("/assign")
    public ResponseEntity<ResponseDto> assignBranchToUser(@RequestParam Long branchId, @RequestParam Long userId) {
        log.info("Inside branch controller assign branch to user method");
        return new ResponseEntity<>(branchService.assignBranchToUser(branchId, userId), HttpStatus.OK);
    }
}
