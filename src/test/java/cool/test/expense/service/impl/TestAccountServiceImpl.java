package cool.test.expense.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cool.test.expense.dao.AccountDao;
import cool.test.expense.dto.AccountDTO;
import cool.test.expense.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import javax.validation.Validator;
import java.sql.Date;

public class TestAccountServiceImpl {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountDao accountDao;

    @Mock
    Validator validator;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveAccountTest()
    {
        String userId = "tatleong";
        String fullName = "Tat Leong";

        AccountDTO dummyAccount = new AccountDTO();
        dummyAccount.setUserId(userId);
        dummyAccount.setFullName(fullName);
        dummyAccount.setCreatedAt(new Date(System.currentTimeMillis()));

        dummyAccount = accountService.saveAccount(dummyAccount);
        Account account = modelMapper.map(dummyAccount, Account.class);

        verify(accountDao, times(1)).save(account);
    }

    @Test
    public void getAccountTest()
    {
        String userId = "tatleong";
        AccountDTO accountDTO = accountService.getAccount(userId);
        verify(accountDao, times(1)).findById(userId);
    }
}
