package cool.test.expense.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
@Setter
public class AccountDTO {

    @NotBlank(message = "userId cannot be null or empty")
    private String userId;

    @NotBlank(message = "fullName cannot be null or empty")
    private String fullName;

    private Date createdAt;

    private Date updatedAt;

}
