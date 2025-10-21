package com.api.produtos.controller;

import com.api.produtos.model.ProdutoModelo;
import com.api.produtos.model.RespostaModelo;
import com.api.produtos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // Permite que requisições de qualquer origem acessem a API
@RequestMapping("/produtos") // Define a rota base para todos os endpoints de produtos
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Endpoint para listar todos os produtos.
     * Rota: GET /produtos
     */
    @GetMapping("")
    public ResponseEntity<Iterable<ProdutoModelo>> listarTodos() {
        return produtoService.listar();
    }
    
    /**
     * Endpoint para buscar um produto específico pelo ID.
     * Rota: GET /produtos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id); // Supondo que você crie este método no serviço
    }

    /**
     * Endpoint para cadastrar um novo produto.
     * Rota: POST /produtos
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoModelo produtoModelo) {
        return produtoService.cadastrar(produtoModelo);
    }

    /**
     * Endpoint para alterar um produto existente.
     * Rota: PUT /produtos/{id}
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody ProdutoModelo produtoModelo) {
        return produtoService.alterar(id, produtoModelo);
    }

    /**
     * Endpoint para remover um produto pelo ID.
     * Rota: DELETE /produtos/{id}
     */
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable Long id) {
        return produtoService.remover(id);
    }
}