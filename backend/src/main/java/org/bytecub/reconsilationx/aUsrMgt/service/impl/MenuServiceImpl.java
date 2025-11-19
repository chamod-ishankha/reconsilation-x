package org.bytecub.reconsilationx.aUsrMgt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MMenuDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MMenuLabelDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MMenuDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MMenu;
import org.bytecub.reconsilationx.aUsrMgt.service.master.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MMenuDao menuDao;
    private final MMenuLabelDao menuLabelDao;

    public MenuServiceImpl(MMenuDao menuDao, MMenuLabelDao menuLabelDao) {
        this.menuDao = menuDao;
        this.menuLabelDao = menuLabelDao;
    }

    @Override
    public ResponseDto createMenu(MMenuDto menuBody) {
        log.info("Inside menu service createMenu method");

        try {
            if (menuBody == null) {
                throw new BadRequestAlertException("Menu body cannot be null", "MenuService", "createMenuError");
            }

            // Convert DTO to Entity (handles labels & children)
            MMenu menuEntity = menuBody.toEntity();

            // Save menu (will cascade labels & children)
            MMenu savedMenu = menuDao.save(menuEntity);

            log.info("Menu saved successfully with ID: {}", savedMenu.getMenuId());

            // Convert back to DTO for response
            MMenuDto savedDto = MMenuDto.fromEntity(savedMenu);

            return new ResponseDto(savedDto.getMenuId(), "Menu created successfully");

        } catch (Exception e) {
            log.error("Error in menu service createMenu method: {}", e.getMessage());
            throw new BadRequestAlertException(e.getMessage(), "MenuService", "createMenuError");
        }
    }

    @Override
    public List<MMenuDto> getAllMenus() {
        log.info("Inside menu service getAllMenus method");
        try {
            // Fetch all menus from repository
            List<MMenu> menus = menuDao.findByParentIsNull(); // assuming you have a JPA repository

            // Convert entities to DTOs

            return menus.stream()
                                           .map(MMenuDto::fromEntity)
                                           .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error in menu service getAllMenus method: {}", e.getMessage());
            throw new BadRequestAlertException(e.getMessage(), "MenuService", "getAllMenusError");
        }
    }


}
