package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.exception.RoleException;
import com.dsv.road.masterdata.jpa.RoleManager;
import com.dsv.road.masterdata.model.Role;
import com.dsv.road.shared.masterdata.dto.DtoRole;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ext.jesper.munkholm
 * 
 *         This implements the business layer of the customer order module
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class RoleBean {

    @Inject
    RoleManager roleManager;

    Mapper mapper = DozerUtility.getMapper();

    public RoleBean() {
        super();
    }

    public List<DtoRole> searchRoles(String filter, int limit) {
        List<Role> roles = roleManager.searchRoles(filter, limit);
        List<DtoRole> dtoRoleList = new ArrayList<>();
        for (Role role : roles) {
            dtoRoleList.add(mapper.map(role, DtoRole.class));
        }
        return dtoRoleList;
    }

    public DtoRole readRole(Long id) {
        Role role = roleManager.findRole(id);
        if (role == null) {
            return null;
        }
        return mapper.map(role, DtoRole.class);
    }

    public void deleteRole(Long id) {
        roleManager.deleteRole(id);
    }

    public void deleteRole(DtoRole dtoRole) throws RoleException {
        if (dtoRole == null) {
            throw new RoleException(ResponseErrorCode.DELETE_WITH_NULL, "Cannot delete null DtoRole");
        }
        Role role = mapper.map(dtoRole, Role.class);
        roleManager.deleteRole(role);
    }

    public DtoRole createRole(DtoRole dtoRole) throws RoleException {
        if (dtoRole == null) {
            throw new RoleException(ResponseErrorCode.INSERT_WITH_NULL, "Cannot create null DtoRole");
        }
        Role role = mapper.map(dtoRole, Role.class);
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return mapper.map(roleManager.instertRole(role), DtoRole.class);
    }

    public DtoRole updateRole(DtoRole dtoRole) throws RoleException {
        if (dtoRole == null) {
            throw new RoleException(ResponseErrorCode.UPDATE_WITH_NULL, "Cannot update null DtoRole");
        }
        Role role = mapper.map(dtoRole, Role.class);
        role.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return mapper.map(roleManager.updateRole(role), DtoRole.class);
    }

    public boolean exists(Long id) {
        return roleManager.exists(id);
    }
}