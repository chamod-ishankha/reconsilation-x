package org.bytecub.reconsilationx.aUsrMgt.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MCompanyDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.miscellaneous.ApiResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.service.master.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // get company by id
    @GetMapping("/{companyId}")
    public ResponseEntity<MCompanyDto> getCompanyById(@PathVariable Long companyId) {
        log.info("Inside company controller get company by id method");
        return new ResponseEntity<>(companyService.getCompanyById(companyId), HttpStatus.OK);
    }

    // get all companies
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<MCompanyDto>>> getCompanies(
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "10", required = false) Integer perPage,
        @RequestParam(defaultValue = "", required = false) String search,
        @RequestParam(defaultValue = "companyId", required = false) String sort,
        @RequestParam(defaultValue = "desc", required = false) String direction
    ) {
        log.info("Inside company controller get all companies method");
        return new ResponseEntity<>(companyService.getCompanies(page, perPage, search, sort, direction), HttpStatus.OK);
    }

    // update company
    @PutMapping("/{companyId}")
    public ResponseEntity<MCompanyDto> updateCompany(@PathVariable Long companyId, @RequestBody MCompanyDto companyDto) {
        log.info("Inside company controller update company method");
        return new ResponseEntity<>(companyService.updateCompany(companyId, companyDto), HttpStatus.OK);
    }

    // delete company
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ResponseDto> companyDismiss(@PathVariable Long companyId) {
        log.info("Inside company controller company dismiss method");
        return new ResponseEntity<>(companyService.companyDismiss(companyId), HttpStatus.OK);
    }
}
