package com.revature.controllers;

import com.revature.daos.AccountsDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Accounts;
import com.revature.models.Transactions;
import com.revature.models.Users;
import com.revature.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //This combines @Controller and @ResponseBody
@CrossOrigin //lets us take in HTTP requests (might have to specify ports in your P2)
@RequestMapping(value="/users")
public class UserController {

    //autowiring the DigimonDAO with constructor injection

    private final UserDAO uDAO;

    @Autowired
    public UserController(UserDAO uDAO) {
        super();
        this.uDAO = uDAO;
    }

    @Autowired
    private AccountService accountService;

    //HTTP Requests-------------------------------

    //insert digimon - every POST request to /digimon will go here
    @PostMapping(value = "/registerUser")
    //url : localhost:5556/data/registerUser
    public ResponseEntity addAccount(@RequestBody Users u) {

        //Thanks to @RequestBody, our Digimon d parameter is filled with the body of the HTTP Request
        //Automatic JSON conversion :)

        //the save() method from our DAO is how we can insert new data
        Users newUser = uDAO.save(u);

        //if insert failed...
        if (newUser == null) {
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newUser); //send a 202 status code and the new digimon
    }




    @GetMapping (value="/getAllUsers")
    //url: localhost:5556/data/users/getAllUsers
    public ResponseEntity<List<Users>> getAllAccounts(){

        return ResponseEntity.ok(uDAO.findAll()); //.ok() returns a 200 level status code

        //note that I'm nesting the DB call in the actual return
        //this is shorthand - I could have done .ok().body(dDAO.findAll());

    }

    @GetMapping (value="/login")
    //url: localhost:5556/data/users/login
    public ResponseEntity<List<Users>> getByUsernameAndPassword(@RequestParam("username") String username, @RequestParam("password")String password){

        Optional<List<Users>> userOptional = uDAO.getByUsernameAndPassword(username, password );

        //we can check if the optional has data with .isPresent(), or .isEmpty()
        if(userOptional.isPresent()){
            //we can extract the Optional's data with .get()
            List<Users> extractedUser = userOptional.get();

            return ResponseEntity.ok(extractedUser);
        }
        //if get by name failed...
        return ResponseEntity.badRequest().build(); //returning a 400 with no response body

    }


}

/*

    @GetMapping(value="/id/{id}")
    //url : localhost:5556/data/accounts/id/1
    public ResponseEntity<Accounts> findById(@PathVariable int id){

        /* findById from JpaRepository returns an Optional
          Optionals lend to code flexibility because it MAY OR MAY NOT have the request object.
          This helps us avoid NullPointerExceptions
         */
    /*
        Optional<Accounts> accountOptional = aDAO.findById(id);

        //we can check if the optional has data with .isPresent(), or .isEmpty()
        if(accountOptional.isPresent()){
            //we can extract the Optional's data with .get()
            Accounts extractedAccount = accountOptional.get();

            return ResponseEntity.ok(extractedAccount);
        }
        //if get by ID failed...
        return ResponseEntity.badRequest().build(); //returning a 400 with no response body
    }
/*

/*

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

    @PostMapping(value="/deposit")
    //url: localhost:5556/data/transactions/submitTransaction
    public ResponseEntity depositMoney(@RequestBody Accounts a, Double transactionAmount, Double accountBalance){

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
    public ResponseEntity withdrawMoney(@RequestBody Accounts a, Double transactionAmount, Double accountBalance){

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
    public ResponseEntity transferMoney(@RequestBody Accounts a, Double transactionAmount){

        //the save() method from our DAO is how we can insert new data
        Accounts newTransferRequest = aDAO.save(a);

        //if insert failed...
        if(newTransferRequest == null){
            return ResponseEntity.badRequest().build(); //send a 400 status code, and no response body
        }

        //if insert succeeded...
        return ResponseEntity.accepted().body(newTransferRequest); //send a 202 status code and the new digimon
    }

*/
