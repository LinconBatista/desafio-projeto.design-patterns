package dio.digitalinnovation.desafioprojeto.designpatterns.service.impl;

import dio.digitalinnovation.desafioprojeto.designpatterns.model.Cliente;
import dio.digitalinnovation.desafioprojeto.designpatterns.model.ClienteRepository;
import dio.digitalinnovation.desafioprojeto.designpatterns.model.Endereco;
import dio.digitalinnovation.desafioprojeto.designpatterns.model.EnderecoRepository;
import dio.digitalinnovation.desafioprojeto.designpatterns.service.ClienteService;
import dio.digitalinnovation.desafioprojeto.designpatterns.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired(required = false)
    private ClienteRepository clienteRepository;
    @Autowired(required = false)
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;
    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
