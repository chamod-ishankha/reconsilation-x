package org.bytecub.reconsilationx.aUsrMgt.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MMenuDto;
import org.bytecub.reconsilationx.aUsrMgt.service.master.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createMenu(@RequestBody MMenuDto menuBody) {
        log.info("Inside menu controller createMenu method");
        return new ResponseEntity<>(menuService.createMenu(menuBody), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MMenuDto>> getAllMenus() {
        log.info("Inside menu controller getAllMenus method");
        return new ResponseEntity<>(menuService.getAllMenus(), HttpStatus.OK);
    }
}
