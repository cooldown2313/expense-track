package cool.test.expense.service;

import cool.test.expense.dto.AccountDTO;
import cool.test.expense.dto.LoginDTO;
import cool.test.expense.dto.LoginResponse;

public interface AccountService {

    public AccountDTO saveAccount(AccountDTO accountDTO);

    public boolean updatePassword(LoginDTO loginDTO);

    public AccountDTO getAccount(String userId);

    public LoginResponse authenticate(LoginDTO loginDTO);
}
