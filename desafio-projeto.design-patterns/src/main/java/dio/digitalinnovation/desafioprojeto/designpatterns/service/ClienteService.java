package dio.digitalinnovation.desafioprojeto.designpatterns.service;
import dio.digitalinnovation.desafioprojeto.designpatterns.model.Cliente;
import feign.Client;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);
    void inserir(Cliente cliente);
    void atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}
