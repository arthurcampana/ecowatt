package com.example.demo.service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Long incluirUsuario(Usuario usuario) {
        if (usuario.getNome() == null ||
            usuario.getCpf() == null ||
            usuario.getEmail() == null ||
            usuario.getSenha() == null ||
            usuario.getCelular() == null ||
            usuario.getCpf().length() != 11) {

            return null;
        }

        String cpf = usuario.getCpf();
        Usuario user = usuarioRepository.findByCpf(cpf);

        if (user == null) {
            String senhaUsuario = usuario.getSenha();
            usuario.setSenha(this.codificarSenhaUsuario(senhaUsuario));
            return usuarioRepository.save(usuario).getIdUsuario();
        } else {
            return null;
        }
    }

    public Boolean excluirUsuario(Long idUsuario) {
        try {
            usuarioRepository.deleteById(idUsuario);
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao excluir usuário ID: " + idUsuario + " Erro: " + ex.getLocalizedMessage());
            return false;
        }
    }

    public Optional<Usuario> consultarUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Boolean atualizarUsuario(Usuario usuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getIdUsuario());
        if (optionalUsuario.isPresent()) {
            Usuario u = optionalUsuario.get();
            u.setCelular(usuario.getCelular());
            u.setNome(usuario.getNome());
            u.setCpf(usuario.getCpf());
            u.setEmail(usuario.getEmail());
            usuarioRepository.save(u);
            return true;
        } else {
            return false;
        }
    }

    public Usuario loginUsuario(String cpf, String senha) {
        Usuario user = usuarioRepository.findByCpf(cpf);
        if (user != null) {
            String senhaCod = codificarSenhaUsuario(senha);
            if (user.getSenha().equals(senhaCod)) {
                return user;
            }
        }
        return null;
    }

    public String codificarSenhaUsuario(String senha) {
        String senhaCod = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            senhaCod = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.getLocalizedMessage();
        }
        return senhaCod;
    }
}
