package br.com.banco.api.services;

import br.com.banco.api.entities.Transfer;
import br.com.banco.api.exceptions.ResourceNotFoundException;
import br.com.banco.api.repositories.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferService {

    final private TransferRepository repository;

    public Page<Transfer> findAll(Pageable pagination) {
        return repository.findAll(pagination).map(Transfer::new);
    }

    public Optional<Transfer> findById(Integer accountId) {
        Optional<Transfer> obj = repository.findById(accountId).map(Transfer::new);
        return Optional.ofNullable(obj.orElseThrow(() -> new ResourceNotFoundException(accountId)));
    }

    public List<Transfer> findByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Transfer> transferList = repository.findAll();
        List<Transfer> transfersByDatesList = new ArrayList<>();
        try {
            transferList.stream()
                    .filter(dates -> dates.getData_transferencia().isBefore(endDate))
                    .forEach(dates -> {
                        dates.getData_transferencia().datesUntil(endDate);
                        transfersByDatesList.add(dates);
                    });

            return transfersByDatesList;
        } catch (DateTimeException e) {
            throw new DateTimeException("Date not valid!:: " + e.getMessage());
        }
    }

    public List<Transfer> findByName(String name) {
        List<Transfer> transferList = repository.findAll();
        List<Transfer> newTransferList;
        try {
            newTransferList = transferList.stream().filter(operator ->
                    operator.getNome_operador_transacao() != null && operator.getNome_operador_transacao().equals(name))
                    .collect(Collectors.toList());
            return newTransferList;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}