// com.back.multitenantback.Controllers.AuthController
package com.back.projectmanagermultitenantback.Controllers;

import com.back.projectmanagermultitenantback.Services.JwtService;
import com.back.projectmanagermultitenantback.Services.OrganizationMemberService;
import com.back.projectmanagermultitenantback.Services.UserService;
import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;
import com.back.projectmanagermultitenantback.dto.UserCreateDto;
import com.back.projectmanagermultitenantback.dto.UserDto;
import com.back.projectmanagermultitenantback.dto.UserLoginDto;
import com.back.projectmanagermultitenantback.utiils.EnumMessages;
import com.back.projectmanagermultitenantback.utiils.ReponseHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder pe;
    private final JwtService jwt;
    private final OrganizationMemberService  organizationMemberService;


    @PostMapping("/register")
    public ResponseEntity<ReponseHttp> register(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserDto userDto = userService.create(userCreateDto);
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(userDto);
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ReponseHttp> login(@RequestBody UserLoginDto loginDto) {
        try {
            UserDto userDto = userService.login(loginDto.getEmail(), loginDto.getPassword());

            // charger les organizations du user
            List<OrganizationMemberDto> org = organizationMemberService.findByIdUser(userDto.getId());
            userDto.setOrganization(org);

            // ⚠️ Assure-toi que userDto.getRoles() est bien rempli (["ROLE_USER", ...])
            String token = jwt.generateToken(userDto);

            // Option cookie HttpOnly
            var responseToSetCookieHeader = jwt.toSetCookieHeader(token);

            ReponseHttp rep = new ReponseHttp();
            rep.setSuccess(true);
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(Map.of("token", token, "user", userDto));



            return ResponseEntity.ok()
                    .header("Set-Cookie", responseToSetCookieHeader)
                    .body(rep);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage(), null, null);
            return new ResponseEntity<>(rep, HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> onAuthError(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }

}
