package cool.test.expense.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "userId cannot be null or empty")
    String userId;

    @NotBlank(message = "password cannot be null or empty")
    String password;
}
