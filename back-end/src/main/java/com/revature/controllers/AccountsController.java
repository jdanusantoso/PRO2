package com.revature.controllers;

import com.revature.daos.AccountsDAO;
import com.revature.exception.OverDraftException;
import com.revature.models.Accounts;
import com.revature.models.Transactions;
import com.revature.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;

@RestController //This combines @Controller and @ResponseBody
@CrossOrigin //lets us take in HTTP requests (might have to specify ports in your P2)
@RequestMapping(value="/accounts")
public class AccountsController {

    //autowiring the DigimonDAO with constructor injection

    private final AccountsDAO aDAO;

    @Autowired
    public AccountsController(AccountsDAO aDAO) {
        super();
        this.aDAO = aDAO;
    }

    @Autowired
    private AccountService accountService;

    //HTTP Requests-------------------------------

    //insert digimon - every POST request to /digimon will go here
    @PostMapping(value="/new")
    //url : localhost:5556/data/accounts/new
    public ResponseEntity addAccount(@RequestBody Accounts a){

        //Thanks to @RequestBody, our Digimon d parameter is filled with the body of the HTTP Request
        //Automatic JSON conversion :)

        //the save() method from our DAO is how we can insert new data
        Accounts newAccount = aDAO.save(a);

        //if insert failed...
        if(newAccount == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newAccount); //send a 202 status code and the new digimon
    }


    @GetMapping (value="/getAllAccounts")
    //url: localhost:5556/data/accounts/getAllAccounts
    public ResponseEntity<List<Accounts>> getAllAccounts(){

        return ResponseEntity.ok(aDAO.findAll()); //.ok() returns a 200 level status code

        //note that I'm nesting the DB call in the actual return
        //this is shorthand - I could have done .ok().body(dDAO.findAll());

    }

    @GetMapping(value="/accountId/{accountId}")
    //url : localhost:5556/data/accounts/accountId/1
    public ResponseEntity<Accounts> findById(@PathVariable int accountId){

        /* findById from JpaRepository returns an Optional
          Optionals lend to code flexibility because it MAY OR MAY NOT have the request object.
          This helps us avoid NullPointerExceptions
         */
        Optional<Accounts> accountOptional = aDAO.findById(accountId);

        //we can check if the optional has data with .isPresent(), or .isEmpty()
        if(accountOptional.isPresent()){
            //we can extract the Optional's data with .get()
            Accounts extractedAccount = accountOptional.get();

            return ResponseEntity.ok(extractedAccount);
        }
        //if get by ID failed...
        return ResponseEntity.badRequest().build(); //returning a 400 with no response body
    }

    @GetMapping(value="/accountIdRecipient/{accountIdRecipient}")
    //url : localhost:5556/data/accounts/accountId/1
    public ResponseEntity<Accounts> findByAccountIdRecipient(@PathVariable int accountIdRecipient){

        /* findById from JpaRepository returns an Optional
          Optionals lend to code flexibility because it MAY OR MAY NOT have the request object.
          This helps us avoid NullPointerExceptions
         */
        Optional<Accounts> accountRecipientOptional = aDAO.findById(accountIdRecipient);

        //we can check if the optional has data with .isPresent(), or .isEmpty()
        if(accountRecipientOptional.isPresent()){
            //we can extract the Optional's data with .get()
            Accounts extractedAccount = accountRecipientOptional.get();

            return ResponseEntity.ok(extractedAccount);
        }
        //if get by ID failed...
        return ResponseEntity.badRequest().build(); //returning a 400 with no response body
    }



    @GetMapping(value="/accountHolder/{accountHolder}")
    // localhost:5556/data/accounts/accountHolder/someone's name
    public ResponseEntity<List<Accounts>> findByName(@PathVariable String accountHolder){

        Optional<List<Accounts>> accountOptional = aDAO.findByAccountHolder(accountHolder);

        //we can check if the optional has data with .isPresent(), or .isEmpty()
        if(accountOptional.isPresent()){
            //we can extract the Optional's data with .get()
            List<Accounts> extractedAccount = accountOptional.get();

            return ResponseEntity.ok(extractedAccount);
        }
        //if get by name failed...
        return ResponseEntity.badRequest().build(); //returning a 400 with no response body

    }

    @PatchMapping(value="/deposit/{accountId}")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity depositMoney(@PathVariable int accountId, @RequestBody Accounts a, double transactionAmount){

       Accounts updateAccountBalance = aDAO.findByAccountId(accountId);

//        Accounts updateRecipientAccountBalance = aDAO.findByAccountRecipientId(accountRecipientId);




//        Accounts updateWithdrawBalance;
        updateAccountBalance.setAccountBalance(updateAccountBalance.getAccountBalance() - transactionAmount);
//        updateRecipientAccountBalance.setAccountRecipientBalance(updateRecipientAccountBalance.getAccountRecipientBalance() + transactionAmount);

        aDAO.save(updateAccountBalance);
//        aDAO.save(updateRecipientAccountBalance);

        return ResponseEntity.ok(updateAccountBalance);

    }

    /*---------------------------------------------------------------------------------------*/
/*
    @PostMapping(value="/deposit")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity depositMoney(@RequestBody Accounts a, double transactionAmount){

        //the save() method from our DAO is how we can insert new data
        Accounts newTransaction = aDAO.save(a);

        //if insert failed...
        if(newTransaction == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newTransaction); //send a 202 status code and the new digimon
    }

    @PostMapping(value="/withdraw")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity withdrawMoney(@RequestBody Accounts a, double transactionAmount){

        //the save() method from our DAO is how we can insert new data
        Accounts newTransaction = aDAO.save(a);

        //if insert failed...
        if(newTransaction == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newTransaction); //send a 202 status code and the new digimon
    }

    @PostMapping(value="/transfer")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity transferMoney(@RequestBody Accounts a, double transactionAmount){

        //the save() method from our DAO is how we can insert new data
        Accounts newTransaction = aDAO.save(a);

        //if insert failed...
        if(newTransaction == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newTransaction); //send a 202 status code and the new digimon
    }

    @PostMapping(value="/send")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity sendMoney(@RequestBody Accounts a, double transactionAmount){

        //the save() method from our DAO is how we can insert new data
        Accounts newTransaction = aDAO.save(a);

        //if insert failed...
        if(newTransaction == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newTransaction); //send a 202 status code and the new digimon
    }


*/

}