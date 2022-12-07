package com.roland.solva.controllers;

import com.roland.solva.dto.MonthLimitDto;
import com.roland.solva.dto.TransactionDto;
import com.roland.solva.services.LimitService;
import com.roland.solva.services.TransactionService;
import com.roland.solva.util.Exception.InternalException;
import com.roland.solva.util.Exception.ServerErrorResponse;
import com.roland.solva.util.Exception.WrongDataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/client")
@Api("Client Api")
public class ClientController {
    private final TransactionService transactionService;
    private final LimitService limitService;


    public ClientController(TransactionService transactionService, LimitService limitService) {
        this.transactionService = transactionService;
        this.limitService = limitService;
    }



    @ApiOperation("This API operation returns all operations that exceed the monthly limit")
    @GetMapping("/get_exceeded/{id}")
    private List<TransactionDto> getAllExceeded(@PathVariable long id){
        return transactionService.getAllExceeded(id);
    }

    @ApiOperation("This API operation allows the setting of a new monthly limit")
    @PostMapping("/set_limit")
    private ResponseEntity<HttpStatus> setLimit(@RequestBody @Valid MonthLimitDto monthLimitDto, BindingResult bindingResult){
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


        try{
            limitService.convertAndSave(monthLimitDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
