package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ValidarEmprestimo {


    Set<EmprestimoConcedido> validar(Set<PreEmprestimo> emprestimos, LocalDate dataExpiracao) {
        Set<EmprestimoConcedido> emprestimosConcedidos = new HashSet<>();
        Map<Integer, Integer> countEmprestimosPadrao = new HashMap<>();

        for (PreEmprestimo emprestimo : emprestimos) {
            var dataDevolucaoEstimada = LocalDate.now().plusDays(emprestimo.getEmprestimo().tempo);
            if (livroEmprestavelEDevolvidoAntesDaDataConsiderada(dataExpiracao, emprestimo.getUsuario(), emprestimo.getExemplar(), dataDevolucaoEstimada, countEmprestimosPadrao)) {
                registrarEmprestimo(emprestimosConcedidos, emprestimo.getUsuario(), emprestimo.getExemplar(), dataDevolucaoEstimada, countEmprestimosPadrao);
            }
        }
        return emprestimosConcedidos;
    }

    private void registrarEmprestimo(Set<EmprestimoConcedido> emprestimosConcedidos, DadosUsuario usuario, DadosExemplar exemplar, LocalDate dataDevolucaoEstimada, Map<Integer, Integer> countEmprestimosPadrao) {
        EmprestimoConcedido emprestimoConcedido = new EmprestimoConcedido(usuario.idUsuario, exemplar.idExemplar, dataDevolucaoEstimada);
        emprestimosConcedidos.add(emprestimoConcedido);
        if (usuario.padrao == TipoUsuario.PADRAO) {
            countEmprestimosPadrao.putIfAbsent(usuario.idUsuario, 0);
            countEmprestimosPadrao.put(usuario.idUsuario, countEmprestimosPadrao.get(usuario.idUsuario) + 1);
        }
    }

    private boolean livroEmprestavelEDevolvidoAntesDaDataConsiderada(LocalDate dataExpiracao, DadosUsuario usuario, DadosExemplar exemplar, LocalDate dataDevolucao, Map<Integer, Integer> countEmprestimosPadrao) {
        if (usuario.padrao == TipoUsuario.PADRAO) {
            return ValidadorUsuario.validarEmprestimoDeUsuarioPadrao(dataExpiracao, usuario, exemplar, dataDevolucao, countEmprestimosPadrao);
        } else { //Pesquisador
            return ValidadorUsuario.validarEmprestimoDePesquisador(dataExpiracao, dataDevolucao);
        }
    }


}
