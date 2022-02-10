package cool.test.expense.controller;

import cool.test.expense.dto.LoginDTO;
import cool.test.expense.dto.LoginResponse;
import cool.test.expense.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value="/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse>  authenticate(@RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = this.accountService.authenticate(loginDTO);

        if (loginResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}