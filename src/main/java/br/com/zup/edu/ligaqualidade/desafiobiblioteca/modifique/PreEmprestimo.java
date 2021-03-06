package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;

import java.util.Objects;

class PreEmprestimo {


    private DadosEmprestimo emprestimo;

    private DadosUsuario usuario;

    private DadosExemplar exemplar;

    public DadosEmprestimo getEmprestimo() {
        return emprestimo;
    }

    public DadosUsuario getUsuario() {
        return usuario;
    }

    public DadosExemplar getExemplar() {
        return exemplar;
    }

    private PreEmprestimo() {
    }

    public PreEmprestimo(DadosEmprestimo emprestimo, DadosUsuario usuario, DadosExemplar exemplar) {
        if(Objects.isNull(emprestimo)) throw new IllegalArgumentException("Empréstimo não pode ser nulo");
        if(Objects.isNull(usuario)) throw new IllegalArgumentException("Usuário não pode ser nulo");
        if(Objects.isNull(exemplar)) throw new IllegalArgumentException("Exemplar não pode ser nulo");
        this.emprestimo = emprestimo;
        this.usuario = usuario;
        this.exemplar = exemplar;
    }
}
