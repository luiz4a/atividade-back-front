package com.api.produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.produtos.model.ProdutoModelo;
import com.api.produtos.model.RespostaModelo;
import com.api.produtos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository pr;

    @Autowired
    private RespostaModelo rm;

    /**
     * Método para listar todos os produtos.
     */
    public ResponseEntity<Iterable<ProdutoModelo>> listar() {
        return new ResponseEntity<>(pr.findAll(), HttpStatus.OK);
    }
    
    /**
     * Método para buscar um produto pelo ID.
     */
    public ResponseEntity<?> buscarPorId(Long id) {
        if (!pr.existsById(id)) {
            rm.setMensagem("O produto com o ID " + id + " não foi encontrado.");
            return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pr.findById(id).get(), HttpStatus.OK);
    }

    /**
     * Método para cadastrar produtos.
     */
    public ResponseEntity<?> cadastrar(ProdutoModelo pm) {
        if (pm.getNome() == null || pm.getNome().isEmpty()) {
            rm.setMensagem("O nome do produto é obrigatório.");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        } else if (pm.getMarca() == null || pm.getMarca().isEmpty()) {
            rm.setMensagem("O nome da marca é obrigatório.");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pr.save(pm), HttpStatus.CREATED);
        }
    }

    /**
     * Método para alterar produtos.
     */
    public ResponseEntity<?> alterar(Long id, ProdutoModelo pm) {
        if (!pr.existsById(id)) {
            rm.setMensagem("O produto com o ID " + id + " não foi encontrado para alteração.");
            return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
        } else if (pm.getNome() == null || pm.getNome().isEmpty()) {
            rm.setMensagem("O nome do produto é obrigatório.");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        } else if (pm.getMarca() == null || pm.getMarca().isEmpty()) {
            rm.setMensagem("O nome da marca é obrigatório.");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        } else {
            // Seta o ID do produto que veio na URL no objeto a ser salvo
            pm.setCodigo(id); // Supondo que o nome do seu campo ID seja 'codigo'
            return new ResponseEntity<>(pr.save(pm), HttpStatus.OK);
        }
    }

    /**
     * Método para remover produtos.
     */
    public ResponseEntity<RespostaModelo> remover(Long id) {
        if (!pr.existsById(id)) {
            rm.setMensagem("O produto com o ID " + id + " não foi encontrado para remoção.");
            return new ResponseEntity<>(rm, HttpStatus.NOT_FOUND);
        } else {
            pr.deleteById(id);
            rm.setMensagem("Produto removido com sucesso!");
            return new ResponseEntity<>(rm, HttpStatus.OK);
        }
    }
}