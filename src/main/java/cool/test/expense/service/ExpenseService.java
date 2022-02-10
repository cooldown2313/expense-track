package cool.test.expense.service;

import cool.test.expense.dto.AccountDTO;
import cool.test.expense.dto.ExpenseDTO;
import cool.test.expense.exception.NotFoundException;

import java.util.List;

public interface ExpenseService {

    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) throws NotFoundException;

    public ExpenseDTO deleteExpense(long expenseId) throws NotFoundException;

    public List<ExpenseDTO> getExpenses();

    public ExpenseDTO getExpense(long expenseId);
}
