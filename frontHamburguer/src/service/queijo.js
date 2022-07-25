import axios from 'axios'

export const queijoService = {
  list: () => {
    return axios.get('queijo/listar');
  },
  insert: (queijo) => {
    return axios.post('queijo/inserir', queijo);
  },
  
  edit: (queijo) => {
    return axios.post('queijo/editar', queijo);
  },
  delete: (queijo) => {
    return axios.post('queijo/excluir', queijo);
  },
}