package org.bytecub.reconsilationx.aUsrMgt.service.impl.transaction;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.master.MUserRoleDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.reference.RRolesDao;
import org.bytecub.reconsilationx.aUsrMgt.dao.reference.RStatusDao;
import org.bytecub.reconsilationx.aUsrMgt.dto.common.ResponseDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.master.MUserRoleDto;
import org.bytecub.reconsilationx.aUsrMgt.dto.reference.RRolesDto;
import org.bytecub.reconsilationx.aUsrMgt.error.BadRequestAlertException;
import org.bytecub.reconsilationx.aUsrMgt.mappers.master.UserRoleMapper;
import org.bytecub.reconsilationx.aUsrMgt.mappers.reference.RolesMapper;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MUserRole;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RRoles;
import org.bytecub.reconsilationx.aUsrMgt.service.reference.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.bytecub.reconsilationx.aUsrMgt.constants.Status.NEW;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RRolesDao rolesDao;
    private final MUserDao userDao;
    private final MUserRoleDao userRoleDao;
    private final UserRoleMapper userRoleMapper;
    private final RStatusDao statusDao;
    private final RolesMapper rolesMapper;

    public RoleServiceImpl(RRolesDao rolesDao, MUserDao userDao, MUserRoleDao userRoleDao, UserRoleMapper userRoleMapper, RStatusDao statusDao, RolesMapper rolesMapper) {
        this.rolesDao = rolesDao;
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.userRoleMapper = userRoleMapper;
        this.statusDao = statusDao;
        this.rolesMapper = rolesMapper;
    }

    @Override
    public ResponseDto assignRoleToUser(Long userId, Long roleId) {
        log.info("Assigning role with ID {} to user with ID {}", roleId, userId);
        try {
            // validations
            if (!userDao.existsByUserIdAndIsActive(userId, true)) {
                throw new BadRequestAlertException("User not found", "RoleService", "userNotFound");
            }
            if (!rolesDao.existsByRoleIdAndIsActive(roleId, true)) {
                throw new BadRequestAlertException("Role not found", "RoleService", "roleNotFound");
            }
            if (userRoleDao.existsByUserUserIdAndRolesRoleIdAndIsActive(userId, roleId, true)) {
                throw new BadRequestAlertException("Role already assigned to user", "RoleService", "roleAlreadyAssigned");
            }

            MUserRoleDto userRoleDto = new MUserRoleDto();
            userRoleDto.setUserId(userId);
            userRoleDto.setRoleId(roleId);
            userRoleDto.setStatusId(statusDao.findByStatusName(NEW).get().getStatusId());
            userRoleDto.setIsActive(true);
            MUserRole userRole = userRoleDao.save(userRoleMapper.toEntity(userRoleDto));

            return new ResponseDto(userRole.getUserRoleId(), "Role assigned successfully");
        } catch (Exception e) {
            log.error("Error assigning role to user: {}", e.getMessage());
            throw new BadRequestAlertException(e.getMessage(), "RoleService", "roleAssignmentError");
        }
    }

    @Override
    public RRolesDto getRoleByRoleName(String roleName) {
        log.info("Fetching role by name: {}", roleName);
        try {
            Optional<RRoles> roleOptional = rolesDao.findByRoleNameAndIsActive(roleName, true);
            if (roleOptional.isEmpty()) {
                throw new BadRequestAlertException("Role not found", "RoleService", "roleNotFound");
            }

            return rolesMapper.toDto(roleOptional.get());
        } catch (Exception e) {
            log.error("Error fetching role by name: {}", e.getMessage());
            throw new BadRequestAlertException(e.getMessage(), "RoleService", "roleFetchError");
        }
    }
}
