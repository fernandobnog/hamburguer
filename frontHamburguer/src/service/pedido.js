import axios from 'axios'

export const pedidoService = {
  list: () => {
    return axios.get('pedido/listar');
  },
  insert: (pedido) => {
    return axios.post('pedido/inserir', pedido);
  },
  
  edit: (pedido) => {
    return axios.post('pedido/editar', pedido);
  },
  delete: (pedido) => {
    return axios.post('pedido/excluir', pedido);
  },
}