package br.com.examplefatec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.examplefatec.entity.Usuario;
import br.com.examplefatec.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario save(Usuario usuario) {
        String senhaUsuario = usuario.getSenhaUsuario();
        if (senhaUsuario != null
                && !senhaUsuario.isBlank()
                && !senhaUsuario.startsWith("$2a$")
                && !senhaUsuario.startsWith("$2b$")
                && !senhaUsuario.startsWith("$2y$")) {
            usuario.setSenhaUsuario(passwordEncoder.encode(senhaUsuario));
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado: " + id));
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> autenticar(String emailUsuario, String senhaUsuario) {
        return usuarioRepository.findByEmailUsuario(emailUsuario)
                .filter(usuario -> passwordEncoder.matches(senhaUsuario, usuario.getSenhaUsuario()));
    }
}
