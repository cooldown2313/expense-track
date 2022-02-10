package cool.test.expense.dto;

import cool.test.expense.entity.Expense;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class AccountDTO {

    private String userId;

    private String fullName;

    private Date createdAt;

    private Date updatedAt;

}
