package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;

import java.time.LocalDate;
import java.util.Set;

public class ValidadorUsuario {

    private ValidadorUsuario() {
    }

    static boolean validarEmprestimoDeUsuarioPadrao(DadosUsuario usuario, DadosExemplar exemplar, LocalDate dataDevolucao, Set<EmprestimoConcedido> emprestimoConcedidos, LocalDate dataAtual) {
        long countEmprestimosFeito = emprestimoConcedidos
                .stream()
                .filter(emprestimoConcedido -> emprestimoConcedido.idUsuario == usuario.idUsuario)
                .count();
        boolean naoPossuiEmprestimoVencido = emprestimoConcedidos.stream().noneMatch(emprestimoConcedido -> emprestimoConcedido.dataPrevistaDevolucao.isBefore(dataAtual));
        return LimiteMaximoDeEmprestimo.validar(dataDevolucao)
                && exemplar.tipo == TipoExemplar.LIVRE
                && countEmprestimosFeito <= 5
                && naoPossuiEmprestimoVencido;
    }

    static boolean validarEmprestimoDePesquisador(LocalDate devolucao, Set<EmprestimoConcedido> emprestimoConcedidos, LocalDate dataAtual) {
        boolean naoPossuiEmprestimoVencido = emprestimoConcedidos.stream().noneMatch(emprestimoConcedido -> emprestimoConcedido.dataPrevistaDevolucao.isBefore(dataAtual));
        return (devolucao == null || LimiteMaximoDeEmprestimo.validar(devolucao))
                && naoPossuiEmprestimoVencido;
    }
}
