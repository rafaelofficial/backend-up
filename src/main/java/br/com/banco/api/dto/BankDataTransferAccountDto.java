package br.com.banco.api.dto;

import br.com.banco.api.entities.Transfer;
import lombok.Data;

import java.time.LocalDate;
@Data
public class BankDataTransferAccountDto {
    private Integer id;
    private LocalDate dateTransfer;
    private Double value;
    private String type;
    private String nameOperatorTransaction;
    private Integer accountTransferId;

    public BankDataTransferAccountDto(Transfer transfer) {
        this.id = transfer.getId();
        this.dateTransfer = transfer.getData_transferencia();
        this.type = transfer.getTipo();
        this.value = transfer.getValor();
        this.nameOperatorTransaction = transfer.getNome_operador_transacao();
        this.accountTransferId = transfer.getConta_id();
    }
}
