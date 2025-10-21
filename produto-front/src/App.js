import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

function App() {
  const [produtos, setProdutos] = useState([]);
  const [produto, setProduto] = useState({ nome: '', marca: '' });
  const [mensagem, setMensagem] = useState('');

  // Função para buscar os produtos
  const fetchProdutos = () => {
    axios.get('http://localhost:8080/produtos') // <-- ROTA CORRIGIDA
      .then(response => {
        setProdutos(response.data);
      })
      .catch(error => {
        console.error("Houve um erro ao buscar os produtos", error);
        setMensagem('Não foi possível carregar os produtos do servidor.');
      });
  };

  // Carregar produtos ao iniciar
  useEffect(() => {
    fetchProdutos();
  }, []);

  // Função para cadastrar produto
  const cadastrarProduto = () => {
    // Validando se nome e marca estão preenchidos
    if (!produto.nome || !produto.marca) {
      setMensagem('Por favor, preencha o nome e a marca do produto.');
      return;
    }

    axios.post('http://localhost:8080/produtos', produto) // <-- ROTA CORRIGIDA
      .then(response => {
        setMensagem('Produto cadastrado com sucesso!');
        setProduto({ nome: '', marca: '' }); // Limpa o formulário
        fetchProdutos(); // Atualiza a lista de produtos
      })
      .catch(error => {
        console.error("Erro ao cadastrar produto:", error);
        
        // Tenta pegar a mensagem de erro do back-end, se houver
        if (error.response && error.response.data && error.response.data.mensagem) {
          setMensagem(error.response.data.mensagem);
        } else {
          setMensagem('Erro ao cadastrar produto. Verifique o console para mais detalhes.');
        }
      });
  };

  return (
    <div className="App">
      <h1>Cadastro de Produtos</h1>
      
      {/* Mensagem de feedback */}
      {mensagem && <p className="mensagem">{mensagem}</p>}

      {/* Formulário de cadastro */}
      <div>
        <input 
          type="text" 
          placeholder="Nome do Produto" 
          value={produto.nome}
          onChange={e => setProduto({ ...produto, nome: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="Marca do Produto" 
          value={produto.marca}
          onChange={e => setProduto({ ...produto, marca: e.target.value })}
        />
        <button onClick={cadastrarProduto}>Cadastrar Produto</button>
      </div>

      <h2>Produtos Cadastrados</h2>
      <ul>
        {produtos.map(prod => (
          <li key={prod.codigo}> {/* Use o ID do produto como chave, se disponível */}
            {prod.nome} - {prod.marca}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;