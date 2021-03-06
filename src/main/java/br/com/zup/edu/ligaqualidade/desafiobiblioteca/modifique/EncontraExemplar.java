package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;

import java.util.Set;

class EncontraExemplar {

    private EncontraExemplar() {
    }

    static DadosExemplar procurar(int idLivro, Set<DadosExemplar> exemplares) {
        return exemplares
                .stream()
                .filter(exemplar -> exemplar.idLivro == idLivro)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Exemplar não encontrado."));
    }
}
