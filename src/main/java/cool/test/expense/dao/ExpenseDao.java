package cool.test.expense.dao;

import cool.test.expense.entity.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDao extends PagingAndSortingRepository<Expense, Long> {

    @Query(value = "SELECT e FROM Expense e WHERE e.account.userId = :userId ")
    List<Expense> findExpensesByUserId(@Param("userId") String userId);

    @Query(value = "SELECT e FROM Expense e WHERE e.account.userId = :userId and e.id = :expenseId ")
    Expense findExpense(@Param("userId") String userId, @Param("expenseId") long expenseId);
}
