package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
@Entity(name = "dash")
@Table(name = "dash")
public class Dash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long identicador_balanco;

    public Dash(Long id, Long identicador_balanco) {
        this.id = id;
        this.identicador_balanco = identicador_balanco;
    }
}