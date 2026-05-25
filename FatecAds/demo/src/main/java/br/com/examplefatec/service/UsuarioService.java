package br.com.examplefatec.service;

import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.examplefatec.entity.Usuario;
import br.com.examplefatec.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final int MINUTOS_EXPIRACAO_TOKEN = 15;

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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

    @Transactional
    public void solicitarRecuperacaoSenha(String emailUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailUsuario(emailUsuario);
        if (usuarioOptional.isEmpty()) {
            return;
        }

        Usuario usuario = usuarioOptional.get();
        String token = gerarTokenRecuperacao();
        usuario.setTokenRecuperacaoSenha(token);
        usuario.setTokenRecuperacaoExpiracao(LocalDateTime.now().plusMinutes(MINUTOS_EXPIRACAO_TOKEN));
        usuarioRepository.save(usuario);

        emailService.enviarTokenRecuperacao(usuario.getEmailUsuario(), token);
    }

    public boolean tokenRecuperacaoValido(String token) {
        return usuarioRepository.findByTokenRecuperacaoSenha(token)
                .filter(usuario -> usuario.getTokenRecuperacaoExpiracao() != null)
                .filter(usuario -> usuario.getTokenRecuperacaoExpiracao().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacaoSenha(token)
                .filter(usuarioEncontrado -> usuarioEncontrado.getTokenRecuperacaoExpiracao() != null)
                .filter(usuarioEncontrado -> usuarioEncontrado.getTokenRecuperacaoExpiracao().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new IllegalArgumentException("Token invalido ou expirado."));

        usuario.setSenhaUsuario(passwordEncoder.encode(novaSenha));
        usuario.setTokenRecuperacaoSenha(null);
        usuario.setTokenRecuperacaoExpiracao(null);
        usuarioRepository.save(usuario);
    }

    private String gerarTokenRecuperacao() {
        return String.format("%06d", secureRandom.nextInt(1_000_000));
    }
}
