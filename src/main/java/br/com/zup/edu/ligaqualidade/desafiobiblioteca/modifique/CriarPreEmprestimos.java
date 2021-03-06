package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;

import java.util.Set;
import java.util.stream.Collectors;

class CriarPreEmprestimos {

    private CriarPreEmprestimos() {
    }

    static Set<PreEmprestimo> criar(Set<DadosExemplar> exemplares,
                                    Set<DadosUsuario> usuarios, Set<DadosEmprestimo> emprestimos) {
        return emprestimos
                .stream()
                .map(emprestimo -> new PreEmprestimo(emprestimo,
                                                    EncontraUsuario.procurar(emprestimo.idUsuario, usuarios),
                                                    EncontraExemplar.procurar(emprestimo.idLivro, exemplares)))
                .collect(Collectors.toSet());
    }

}
