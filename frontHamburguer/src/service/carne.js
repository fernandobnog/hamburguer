import axios from 'axios'

export const carneService = {
  list: () => {
    return axios.get('carne/listar');
  },
  insert: (carne) => {
    return axios.post('carne/inserir', carne);
  },
  
  edit: (carne) => {
    return axios.post('carne/editar', carne);
  },
  delete: (carne) => {
    return axios.post('carne/excluir', carne);
  },
}