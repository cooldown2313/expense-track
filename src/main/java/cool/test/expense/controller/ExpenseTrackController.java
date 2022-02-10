package cool.test.expense.controller;

import cool.test.expense.dto.ExpenseDTO;
import cool.test.expense.exception.NotFoundException;
import cool.test.expense.service.ExpenseService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseTrackController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass=String.class, example="Bearer access_token")
    @ResponseBody
    public ResponseEntity<ExpenseDTO> saveExpense(@RequestBody ExpenseDTO expenseDTO) throws NotFoundException {
        ExpenseDTO expDTO = this.expenseService.saveExpense(expenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(expDTO);
    }

    @DeleteMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass=String.class, example="Bearer access_token")
    @ResponseBody
    public ResponseEntity<ExpenseDTO> deleteExpense(@RequestParam long expenseId) throws NotFoundException {
        ExpenseDTO expDTO = this.expenseService.deleteExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(expDTO);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass=String.class, example="Bearer access_token")
    @ResponseBody
    public ResponseEntity<ExpenseDTO> getExpense(@RequestParam long expenseId) throws NotFoundException {
        ExpenseDTO expDTO = this.expenseService.getExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).body(expDTO);
    }

    @GetMapping(value = "/listAllExpenses")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass=String.class, example="Bearer access_token")
    @ResponseBody
    public ResponseEntity<List<ExpenseDTO>> listAllExpenses() throws NotFoundException {
        List<ExpenseDTO> expList = this.expenseService.getExpenses();
        return ResponseEntity.status(HttpStatus.OK).body(expList);
    }
}
