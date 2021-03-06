package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;

import java.util.Set;

class EncontraUsuario {

    private EncontraUsuario() {
    }

    static DadosUsuario procurar(int idUsuario, Set<DadosUsuario> usuarios) {
        return usuarios
                .stream()
                .filter(usuario -> usuario.idUsuario == idUsuario)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
    }

}
