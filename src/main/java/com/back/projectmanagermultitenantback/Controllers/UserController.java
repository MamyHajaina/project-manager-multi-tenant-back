package com.back.projectmanagermultitenantback.Controllers;

import com.back.projectmanagermultitenantback.Services.UserService;
import com.back.projectmanagermultitenantback.dto.UserCreateDto;
import com.back.projectmanagermultitenantback.utiils.EnumMessages;
import com.back.projectmanagermultitenantback.utiils.ReponseHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ReponseHttp> getAll() {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.userService.findAll());
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ReponseHttp> create(@RequestBody UserCreateDto userCreateDto) {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.userService.create(userCreateDto));
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReponseHttp> getById(@PathVariable Long id) {
        try {
            ReponseHttp rep = new ReponseHttp();
            rep.setMessages(EnumMessages.SELECT_SUCCESS.getMessage());
            rep.setData(this.userService.findById(id));
            rep.setSuccess(true);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ReponseHttp rep = new ReponseHttp(false, e.getMessage().toString(), null, null);
            return new ResponseEntity<ReponseHttp>(rep, HttpStatus.BAD_REQUEST);
        }
    }
}
