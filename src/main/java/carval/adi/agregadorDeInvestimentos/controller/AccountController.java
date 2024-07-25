package carval.adi.agregadorDeInvestimentos.controller;


import carval.adi.agregadorDeInvestimentos.dto.AccountStockCreateRecordDto;
import carval.adi.agregadorDeInvestimentos.dto.AccountStockGetRecordDto;
import carval.adi.agregadorDeInvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> storeAccountStock(@PathVariable(name = "accountId") String accountId,
                                                  @RequestBody AccountStockCreateRecordDto accountStockCreateRecordDto)
    {
        accountService.createAccountStock(accountId, accountStockCreateRecordDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockGetRecordDto>> getAccountStock(@PathVariable(name = "accountId") String accountId)
    {
        var accountStocks = accountService.getAccountStocks(accountId);
        return ResponseEntity.ok(accountStocks);
    }

}
