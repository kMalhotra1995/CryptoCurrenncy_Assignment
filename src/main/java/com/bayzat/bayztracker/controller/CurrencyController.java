package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.common.ApiConstants;
import com.bayzat.bayztracker.entity.Admin;
import com.bayzat.bayztracker.entity.BaseUser;
import com.bayzat.bayztracker.entity.Currency;
import com.bayzat.bayztracker.exception.UnsupportedCurrencyCreationException;
import com.bayzat.bayztracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bayztracker")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @PostMapping(ApiConstants.ADD_CURRENCY)
    public ResponseEntity<?> addCurrency(@RequestBody Currency currency, @RequestHeader(value = "username") String username) throws Exception {
        BaseUser baseUser = this.currencyService.getBaseUser(username);
        Admin admin = this.currencyService.getAdmin(baseUser);
        if(admin != null){
            if (currencyService.validateCurrency(currency.getName())){
                Currency newCurrency = this.currencyService.saveCurrency(currency) ;
                return ResponseEntity.ok(newCurrency);
            }else {
                return new ResponseEntity<>(new UnsupportedCurrencyCreationException(),HttpStatus.FORBIDDEN);
            }

        }
        else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping(ApiConstants.DELETE_CURRENCY)
    public ResponseEntity<HttpStatus> deleteCurrency(@RequestBody Currency currency, @RequestHeader(value = "username") String username , @RequestParam("currencyId") Long id) throws Exception {
        BaseUser baseUser = this.currencyService.getBaseUser(username);
        Admin admin = this.currencyService.getAdmin(baseUser);
        if(admin != null){
            if(this.currencyService.getCurrency(id) == true) {
                this.currencyService.deleteCurrency(id);
                return ResponseEntity.ok(HttpStatus.OK);
            }
            else {
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}
