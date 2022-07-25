import axios from 'axios'

export const paoService = {
  list: () => {
    return axios.get('pao/listar');
  },
  insert: (pao) => {
    return axios.post('pao/inserir', pao);
  },
  
  edit: (pao) => {
    return axios.post('pao/editar', pao);
  },
  delete: (pao) => {
    return axios.post('pao/excluir', pao);
  },
}