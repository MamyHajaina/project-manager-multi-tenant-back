package com.back.projectmanagermultitenantback.Controllers;

import com.back.projectmanagermultitenantback.Mappers.ProjectMapper;
import com.back.projectmanagermultitenantback.Mappers.UserMapper;
import com.back.projectmanagermultitenantback.Models.*;
import com.back.projectmanagermultitenantback.Services.ProjectMemberService;
import com.back.projectmanagermultitenantback.Services.ProjectService;
import com.back.projectmanagermultitenantback.dto.*;
import com.back.projectmanagermultitenantback.security.SecurityUtils;
import com.back.projectmanagermultitenantback.tenant.TenantResolverFilter;
import com.back.projectmanagermultitenantback.utiils.EnumMessages;
import com.back.projectmanagermultitenantback.utiils.ReponseHttp;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/project/")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMemberService  projectMemberService;
    private final SecurityUtils  securityUtils;
    private final TenantResolverFilter  tenantResolverFilter;
    private final ProjectMapper  projectMapper;
    private final UserMapper  userMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @GetMapping("/all")
    public ResponseEntity<ReponseHttp> getAll() {
        try {
            UserDto currentUser = securityUtils.currentUser();
            System.out.println("currentUser JSON:\n" + toJson(currentUser));
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            List<ProjectMembership> projects = this.projectMemberService.findByIdUser(currentUser.getId());
            rep.setData( projects);
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/creat")
    public ResponseEntity<ReponseHttp> creat(@RequestBody ProjectCreatDto project) {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());

            Long orgId = project.getOrganizationID();
            String desc = project.getDescription();

            if (Objects.equals(orgId, 1L)) {
                project.setDescription("");
            } else if (Objects.equals(orgId, 2L)) {
                project.setDescription("~~~" + desc + "~~~~~");
            } else if (Objects.equals(orgId, 3L)) {
                project.setDescription("+++++" + desc + "++++");
            } else if (Objects.equals(orgId, 4L)) {
                project.setDescription(desc + "----");
            } else if (Objects.equals(orgId, 5L)) {
                project.setDescription("%%%" + desc + "%%%");
            }

            ProjectMembership projectMembership = new ProjectMembership();
            Project projectCreat = this.projectService.create(projectMapper.toDTO(project));
            User current = userMapper.toDTO(securityUtils.currentUser());
            Organization organization = new Organization();
            organization.setId(project.getOrganizationID());

            projectMembership.setProject(projectCreat);
            projectMembership.setUser(current);
            projectMembership.setOrganization(organization);


            projectMembership = this.projectMemberService.create(projectMembership);
            rep.setData(projectMembership);
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byIdUser")
    public ResponseEntity<ReponseHttp> getByIdUser() {
        try {
            UserDto currentUser = securityUtils.currentUser();
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.projectMemberService.findByIdUser(currentUser.getId()));
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byIdUserAndOrganization")
    public ResponseEntity<ReponseHttp> getByIdUserAndOrganization(@RequestParam("OrganizationId") Long id) {
        try {
            UserDto currentUser = securityUtils.currentUser();
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            if(id > 0){
                rep.setData(this.projectMemberService.findByUser_IdAndOrganization_Id(currentUser.getId(), id));
            }else{
                rep.setData(this.projectMemberService.findByIdUser(currentUser.getId()));
            }
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    private String toJson(Object o) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (Exception e) {
            return String.valueOf(o);
        }
    }
}
