package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LimiteMaximoDeEmprestimo {

    private LimiteMaximoDeEmprestimo() {
    }

    private static final int LIMITE_DIAS_POR_EMPRESTIMO = 60;

    static boolean validar(LocalDate dataDevolucao) {
        return ChronoUnit.DAYS.between(LocalDate.now(), dataDevolucao) <= LIMITE_DIAS_POR_EMPRESTIMO;
    }
}
