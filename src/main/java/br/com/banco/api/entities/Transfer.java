package br.com.banco.api.entities;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Transfer")
@Table(name = "TRANSFERENCIA")
public class Transfer {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private LocalDate data_transferencia;
    private Double valor;
    private String tipo;
    private String nome_operador_transacao;
    private Integer conta_id;

    public Transfer(Transfer transfer) {
        this.id = transfer.getId();
        this.data_transferencia = transfer.getData_transferencia();
        this.tipo = transfer.getTipo();
        this.valor = transfer.getValor();
        this.nome_operador_transacao = transfer.getNome_operador_transacao();
        this.conta_id = transfer.getConta_id();
    }
}
