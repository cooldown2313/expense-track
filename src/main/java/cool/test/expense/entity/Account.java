package cool.test.expense.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account {

    @Id
    private String userId;

    private String fullName;

    private String password;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy="account", fetch = FetchType.LAZY)
    private List<Expense> expenses;
}
