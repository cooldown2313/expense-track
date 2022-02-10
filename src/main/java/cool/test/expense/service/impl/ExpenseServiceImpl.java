package cool.test.expense.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.test.expense.dao.AccountDao;
import cool.test.expense.dao.ExpenseDao;
import cool.test.expense.dto.ExpenseDTO;
import cool.test.expense.entity.Account;
import cool.test.expense.entity.Expense;
import cool.test.expense.exception.NotFoundException;
import cool.test.expense.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private Validator validator;

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) throws NotFoundException {
        log.info("ExpenseServiceImpl saveExpense");

        Set<ConstraintViolation<ExpenseDTO>> violations = validator.validate(expenseDTO);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ExpenseDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Account account = null;
            if (auth != null) {
                String userId = (String) auth.getPrincipal();
                Optional<Account> found = this.accountDao.findById(userId);

                if (found != null) {
                    account = found.get();
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(expenseDTO);

            log.debug("jsonString test is " + jsonString);

            Expense expense = null;
            if (expenseDTO.getId() > 0) {
                Optional<Expense> found = this.expenseDao.findById(expenseDTO.getId());
                expense = found != null ? found.get() : null;
                if (expense == null) throw new cool.test.expense.exception.NotFoundException("expense not found");
            }

            expense = modelMapper.map(expenseDTO, Expense.class);
            expense.setAccount(account);

            expense = this.expenseDao.save(expense);

            log.debug("expense test is " + expense.getId());

            expenseDTO = modelMapper.map(expense, ExpenseDTO.class);

        } catch (JsonProcessingException e) {
            log.error("saveExpense exception is ", e);
        }

        return expenseDTO;
    }

    @Override
    public ExpenseDTO deleteExpense(long expenseId) throws NotFoundException {
        log.info("ExpenseServiceImpl deleteExpense");

        ExpenseDTO expenseDTO = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Account account = null;
        if (auth != null) {
            String userId = (String) auth.getPrincipal();
            Optional<Account> found = this.accountDao.findById(userId);

            if (found != null) {
                account = found.get();
            }
        }

        Expense expense = null;
        if (expenseId > 0) {
            expense = this.expenseDao.findExpense(account.getUserId(), expenseId);
        }

        if (expense == null) throw new cool.test.expense.exception.NotFoundException("expense not found");

        this.expenseDao.deleteById(expenseId);

        log.debug("expense test is " + expense.getId());

        expenseDTO = modelMapper.map(expense, ExpenseDTO.class);

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> getExpenses() {
        log.info("ExpenseServiceImpl getExpenses");

        List<ExpenseDTO> expenseDTOList = new ArrayList<ExpenseDTO>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Account account = null;
        if (auth != null) {
            String userId = (String) auth.getPrincipal();
            Optional<Account> found = this.accountDao.findById(userId);

            if (found != null) {
                account = found.get();

                List<Expense> expenses = this.expenseDao.findExpensesByUserId(account.getUserId());
                expenses.forEach(exp -> {
                    ExpenseDTO expenseDTO = modelMapper.map(exp, ExpenseDTO.class);
                    expenseDTOList.add(expenseDTO);
                });
            }
        }

        return expenseDTOList;
    }

    @Override
    public ExpenseDTO getExpense(long expenseId) {
        log.info("ExpenseServiceImpl getExpense");

        ExpenseDTO expenseDTO = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Account account = null;
        if (auth != null) {
            String userId = (String) auth.getPrincipal();
            Optional<Account> found = this.accountDao.findById(userId);

            if (found != null) {
                account = found.get();

                Expense expense = this.expenseDao.findExpense(account.getUserId(), expenseId);
                expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
            }
        }

        return expenseDTO;
    }
}