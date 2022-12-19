package br.com.banco.api.controllers;

import br.com.banco.api.dto.BankDataTransferAccountDto;
import br.com.banco.api.entities.Transfer;
import br.com.banco.api.services.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/account")
public class BankController {

    final private TransferService transferService;

    @GetMapping(value = "/all-transfers")
    public ResponseEntity<Page<BankDataTransferAccountDto>> findAll(@PageableDefault(sort = {"id"}) Pageable pagination) {
        Page<Transfer> list = transferService.findAll(pagination);
        Page<BankDataTransferAccountDto> listDto = list.map(BankDataTransferAccountDto::new);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/transfer-by-account/{accountId}")
    public ResponseEntity<BankDataTransferAccountDto> findById(@PathVariable final Integer accountId) {
        return transferService.findById(accountId).map(obj ->
            ResponseEntity.ok().body(new BankDataTransferAccountDto(obj))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/transfers/findByPeriod")
    public ResponseEntity<List<BankDataTransferAccountDto>> findByPeriod(@RequestParam(value="start", required = false) final String startDate,
                                                                @RequestParam(value="end", required = false) final String endDate) {
        List<BankDataTransferAccountDto> bankDataTransferAccountDtoList = getPeriod(startDate, endDate);
        return ResponseEntity.ok().body(bankDataTransferAccountDtoList);
    }

    private List<BankDataTransferAccountDto> getPeriod(String startDate, String endDate) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = dateTimeFormat.parse(startDate, LocalDate::from);
        LocalDate end = dateTimeFormat.parse(endDate, LocalDate::from);

        List<Transfer> listOfDates = transferService.findByPeriod(start, end);
        return listOfDates.stream().map(BankDataTransferAccountDto::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/transfer-by-name")
    public ResponseEntity<List<BankDataTransferAccountDto>> findByName(@RequestParam(value="name", required = false) final String name) {
        List<Transfer> list = transferService.findByName(name);
        List<BankDataTransferAccountDto> listDto = list.stream().map(BankDataTransferAccountDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
