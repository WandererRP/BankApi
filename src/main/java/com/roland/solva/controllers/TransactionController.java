package com.roland.solva.controllers;

import com.roland.solva.dto.TransactionDto;
import com.roland.solva.services.LimitService;
import com.roland.solva.services.TransactionService;
import com.roland.solva.util.Exception.CurrencyRatesNotFoundedException;
import com.roland.solva.util.Exception.InternalException;
import com.roland.solva.util.Exception.ServerErrorResponse;
import com.roland.solva.util.Exception.WrongDataException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Roland Pilpani 04.12.2022
 */

@RestController
@RequestMapping("/transaction")
@Api(value = "Transaction Api")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService, LimitService limitService) {
        this.transactionService = transactionService;
    }

    @ApiOperation("This apiOperation receives transaction information from a Client and saves it to the Data Base")
    @PostMapping
    private ResponseEntity<HttpStatus> registerTransaction(@RequestBody @Valid TransactionDto transactionDto, BindingResult bindingResult)
            throws WrongDataException, InternalException {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new WrongDataException(errorMsg.toString());
        }
        try {
            transactionService.save(transactionDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new InternalException("Server Error");
        }

    }


    @ExceptionHandler
    private ResponseEntity<ServerErrorResponse> handleException(InternalException e){
        ServerErrorResponse response = new ServerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<ServerErrorResponse> handleException(WrongDataException e){
        ServerErrorResponse response = new ServerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
