package br.com.examplefatec.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String remetente;

    @Value("${app.url:http://localhost:8080}")
    private String appUrl;

    public EmailService(ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.mailSender = mailSenderProvider.getIfAvailable();
    }

    public void enviarTokenRecuperacao(String destinatario, String token) {
        if (mailSender == null) {
            throw new IllegalStateException("Envio de e-mail nao configurado.");
        }

        String link = appUrl + "/redefinir-senha?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        if (remetente != null && !remetente.isBlank()) {
            mensagem.setFrom(remetente);
        }
        mensagem.setTo(destinatario);
        mensagem.setSubject("Recuperacao de senha - FatecADS");
        mensagem.setText("""
                Ola!

                Use o token abaixo para redefinir sua senha:

                %s

                Voce tambem pode acessar: %s

                Este token expira em 15 minutos.
                """.formatted(token, link));

        mailSender.send(mensagem);
    }
}
