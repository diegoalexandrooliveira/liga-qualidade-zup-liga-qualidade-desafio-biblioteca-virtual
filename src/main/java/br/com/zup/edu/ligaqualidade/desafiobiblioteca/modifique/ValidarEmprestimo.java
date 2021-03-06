package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosDevolucao;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

import java.time.LocalDate;
import java.util.*;

class ValidarEmprestimo {


    Set<EmprestimoConcedido> validar(Set<PreEmprestimo> emprestimos, LocalDate dataAtual, Set<DadosDevolucao> devolucoes) {
        Set<EmprestimoConcedido> emprestimosConcedidos = new HashSet<>();
        Map<Integer, Integer> countEmprestimosEfetivados = new HashMap<>();

        for (PreEmprestimo emprestimo : emprestimos) {
            var dataDevolucaoEstimada = LocalDate.now().plusDays(emprestimo.getEmprestimo().tempo);
            if (livroEmprestavelEDevolvidoAntesDaDataConsiderada(emprestimo.getUsuario(), emprestimo.getExemplar(), dataDevolucaoEstimada, emprestimosConcedidos, dataAtual)) {
                registrarEmprestimo(emprestimosConcedidos, emprestimo, dataDevolucaoEstimada, countEmprestimosEfetivados, devolucoes);
            }
        }
        return emprestimosConcedidos;
    }

    private void registrarEmprestimo(Set<EmprestimoConcedido> emprestimosConcedidos, PreEmprestimo preEmprestimo, LocalDate dataDevolucaoEstimada, Map<Integer, Integer> countEmprestimosEfetivados, Set<DadosDevolucao> devolucoes) {
        DadosEmprestimo emprestimo = preEmprestimo.getEmprestimo();
        EmprestimoConcedido emprestimoConcedido = new EmprestimoConcedido(emprestimo.idUsuario, preEmprestimo.getExemplar().idExemplar, dataDevolucaoEstimada);
        devolucoes.stream()
                .filter(devolucao -> devolucao.idEmprestimo == emprestimo.idPedido)
                .findAny()
                .ifPresent(devolucao -> emprestimoConcedido.registraDevolucao());
        emprestimosConcedidos.add(emprestimoConcedido);
        countEmprestimosEfetivados.compute(emprestimo.idUsuario, (chave, valor) -> Optional.ofNullable(valor).orElse(0) + 1);
    }

    private boolean livroEmprestavelEDevolvidoAntesDaDataConsiderada(DadosUsuario usuario, DadosExemplar exemplar, LocalDate dataDevolucao, Set<EmprestimoConcedido> emprestimoConcedidos, LocalDate dataAtual) {
        if (usuario.padrao == TipoUsuario.PADRAO) {
            return ValidadorUsuario.validarEmprestimoDeUsuarioPadrao(usuario, exemplar, dataDevolucao, emprestimoConcedidos, dataAtual);
        } else { //Pesquisador
            return ValidadorUsuario.validarEmprestimoDePesquisador(dataDevolucao, emprestimoConcedidos, dataAtual);
        }
    }


}
