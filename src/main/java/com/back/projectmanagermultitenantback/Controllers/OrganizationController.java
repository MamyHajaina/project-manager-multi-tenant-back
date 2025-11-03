package com.back.projectmanagermultitenantback.Controllers;

import com.back.projectmanagermultitenantback.Services.OrganizationMemberService;
import com.back.projectmanagermultitenantback.Services.OrganizationService;
import com.back.projectmanagermultitenantback.dto.*;
import com.back.projectmanagermultitenantback.utiils.EnumMessages;
import com.back.projectmanagermultitenantback.utiils.ReponseHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationMemberService organizationMemberService;

    @GetMapping("/all")
    public ResponseEntity<ReponseHttp> getAll() {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.organizationService.findAll());
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byIdOrganization/{id}")
    public ResponseEntity<ReponseHttp> getByIdOrganization(@PathVariable Long id) {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.organizationMemberService.findByIdOrganization(id));
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byIdUser")
    public ResponseEntity<ReponseHttp> getByIdClient(@RequestParam("userId") Long id) {
        try {
            ReponseHttp rep = new ReponseHttp();
            List<OrganizationMemberDto> organizationMemberDto = this.organizationMemberService.findByIdUser(id);
            List<OrganizationDto> organizationDto = organizationMemberDto.stream()
                            .map(OrganizationMemberDto::getOrganization)
                                    .filter(Objects::nonNull)
                                            .toList();

            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(organizationDto);
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/assignedMember")
    public ResponseEntity<ReponseHttp> assignedMember(@RequestParam("organizationId") Long organizationId, @RequestParam("userId") Long userId) {
        try {
            ReponseHttp rep = new ReponseHttp();
            OrganizationDto organization = new OrganizationDto();
            organization.setId(organizationId);

            UserDto user = new UserDto();
            user.setId(userId);

            OrganizationMemberDto organizationMemberDto = new OrganizationMemberDto();
            organizationMemberDto.setOrganization(organization);
            organizationMemberDto.setUser(user);


            OrganizationMemberDto organizationMemberDtoResult = organizationMemberService.creat(organizationMemberDto);
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(organizationMemberDtoResult);
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }
}
