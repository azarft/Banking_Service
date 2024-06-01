package com.effectiveMobile.bankingservice.controllers;

import com.effectiveMobile.bankingservice.dto.requests.ChangePhoneNumberDto;
import com.effectiveMobile.bankingservice.dto.entityDto.PhoneNumberDto;
import com.effectiveMobile.bankingservice.exceptions.NotFoundException;
import com.effectiveMobile.bankingservice.services.phoneNumber.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/phoneNumbers")
@Log4j2
@RequiredArgsConstructor
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation
    public List<PhoneNumberDto> getAllPhoneNumbers() {
        return phoneNumberService.findAllPhoneNumbers();
    }

    @PostMapping("/add")
    @io.swagger.v3.oas.annotations.Operation
    public PhoneNumberDto addPhoneNumber(@RequestBody PhoneNumberDto phoneNumberDto) {
        return phoneNumberService.addPhoneNumber(phoneNumberDto);
    }

    @DeleteMapping("/delete/{phoneNumber}")
    @io.swagger.v3.oas.annotations.Operation
    public void removePhoneNumber(@PathVariable String phoneNumber) {
        phoneNumberService.removePhoneNumber(phoneNumber);
    }

    @PutMapping("/update")
    @io.swagger.v3.oas.annotations.Operation
    public PhoneNumberDto changePhoneNumber(@RequestBody ChangePhoneNumberDto changePhoneNumberDto) {
        return phoneNumberService.changePhoneNumber(changePhoneNumberDto)
                .orElseThrow(() -> new NotFoundException("Phone number not found: " + changePhoneNumberDto.getOldPhoneNumber()));
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    @io.swagger.v3.oas.annotations.Operation
    public PhoneNumberDto getPhoneNumber(@PathVariable String phoneNumber) {
        return phoneNumberService.getPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Phone number not found: " + phoneNumber));
    }
}
