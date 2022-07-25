import axios from 'axios'

export const pessoaService = {
  list: () => {
    return axios.get('pessoa/listar');
  },
  insert: (pessoa) => {
    return axios.post('pessoa/inserir', pessoa);
  },
  
  edit: (pessoa) => {
    return axios.post('pessoa/editar', pessoa);
  },
  delete: (pessoa) => {
    return axios.post('pessoa/excluir', pessoa);
  },
}