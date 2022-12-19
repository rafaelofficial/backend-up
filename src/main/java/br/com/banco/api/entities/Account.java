package br.com.banco.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CONTA")
public class Account {


    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty("id_conta")
    private Integer id_conta;
    @JsonProperty("nome_responsavel")
    private String nome_responsavel;


}
