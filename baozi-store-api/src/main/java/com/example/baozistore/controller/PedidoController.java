package com.example.baozistore.controller;

import com.example.baozistore.model.Pedido;
import com.example.baozistore.repository.ClienteRepository;
import com.example.baozistore.repository.PedidoRepository;
import com.example.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Pedido pedido) {
        ResponseEntity<String> erro = validarClienteEProduto(pedido);
        if (erro != null) {
            return erro;
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedidoAtualizado) {
        ResponseEntity<String> erro = validarClienteEProduto(pedidoAtualizado);
        if (erro != null) {
            return erro;
        }

        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setClienteId(pedidoAtualizado.getClienteId());
                    pedido.setProdutoId(pedidoAtualizado.getProdutoId());
                    pedido.setQuantidade(pedidoAtualizado.getQuantidade());
                    Pedido salvo = pedidoRepository.save(pedido);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<String> validarClienteEProduto(Pedido pedido) {
        if (!clienteRepository.existsById(pedido.getClienteId())) {
            return ResponseEntity.badRequest().body("Cliente não encontrado para o clienteId informado.");
        }

        if (!produtoRepository.existsById(pedido.getProdutoId())) {
            return ResponseEntity.badRequest().body("Produto não encontrado para o produtoId informado.");
        }

        return null;
    }
}
