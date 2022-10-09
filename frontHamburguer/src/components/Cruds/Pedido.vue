<script>
import { defineComponent } from "vue";
import { pedidoStore } from "../../stores/pedido";
import { pessoaStore } from "../../stores/pessoa";
import { hamburguerStore } from "../../stores/hamburguer";
import { paoStore } from "../../stores/pao";
import { carneStore } from "../../stores/carne";
import { queijoStore } from "../../stores/queijo";
import { saladaStore } from "../../stores/salada";
import { opcionaisStore } from "../../stores/opcionais";
import { mapState, mapActions } from "pinia";
import CrudPessoaVue from "../Cruds/Pessoa.vue"

export default defineComponent({
  components: {
    CrudPessoaVue
  },
    data() {
        return {
            displayEditar: false,
            displayCriarHamburguer: false,
            displayExcluir: false,
            pedidoEditar: {
              id: null,
              pessoa: null,
              hamburguer: null
            },
            displayDetalhePedido: false,
            saladasPedido: [],
            stringSaladas:'',
            opcionaisPedido: [],
            stringOpcionais: '',
            displayCriarPessoa: false,
        };
    },
    computed: {
        ...mapState(carneStore, [
          "listaCarne",
          "carne",
          "statusServer",
          "carneRetorno",
        ]),
        ...mapState(opcionaisStore, [
          "listaOpcionais",
          "opcionais",
          "statusServer",
          "opcionaisRetorno",
        ]),
        ...mapState(paoStore, [
          "listaPao",
          "pao",
          "statusServer",
          "paoRetorno",
        ]),
        ...mapState(queijoStore, [
          "listaQueijo",
          "queijo",
          "statusServer",
          "queijoRetorno",
        ]),
        ...mapState(saladaStore, [
          "listaSalada",
          "salada",
          "statusServer",
          "saladaRetorno",
        ]),
        ...mapState(hamburguerStore, [
            "listaHamburguer",
            "hamburguer",
            "statusServer",
            "hamburguerRetorno",
        ]),
        ...mapState(pessoaStore, [
            "listaPessoa",
            "pessoa",
            "statusServer",
            "pessoaRetorno",
        ]),
        ...mapState(pedidoStore, [
            "listaPedido",
            "pedido",
            "statusServer",
            "pedidoRetorno",
        ]),
    },
    methods: {
        ...mapActions(carneStore, [
          "listarCarne",
          "salvarCarne",
          "deletarCarne",
          "resetCarne",
          "editarCarne",
        ]),
        ...mapActions(opcionaisStore, [
          "listarOpcionais",
          "salvarOpcionais",
          "deletarOpcionais",
          "resetOpcionais",
          "editarOpcionais",
        ]),
        ...mapActions(paoStore, [
          "listarPao",
          "salvarPao",
          "deletarPao",
          "resetPao",
          "editarPao",
        ]),
        ...mapActions(queijoStore, [
          "listarQueijo",
          "salvarQueijo",
          "deletarQueijo",
          "resetQueijo",
          "editarQueijo",
        ]),
        ...mapActions(saladaStore, [
          "listarSalada",
          "salvarSalada",
          "deletarSalada",
          "resetSalada",
          "editarSalada",
        ]),
        ...mapActions(hamburguerStore, [
            "listarHamburguer",
            "salvarHamburguer",
            "deletarHamburguer",
            "resetHamburguer",
            "editarHamburguer",
        ]),
        ...mapActions(pessoaStore, [
            "listarPessoa",
            "salvarPessoa",
            "deletarPessoa",
            "resetPessoa",
            "editarPessoa",
        ]),
        ...mapActions(pedidoStore, [
            "listarPedido",
            "salvarPedido",
            "deletarPedido",
            "resetPedido",
            "editarPedido",
        ]),

        async cadastrarHamburguer() {
          await this.salvarHamburguer();

          if (this.statusServer.status == "OK") {
            this.$toast.add({
              severity: "success",
              summary: "Salvo com Sucesso",
              detail: "Hamburguer inserida no banco de dados",
              life: 3000,
            });

            this.$emit('savedPedido');
          }

          if (this.statusServer.status == "Alerta") {
            this.$toast.add({
              severity: "warn",
              summary: "Aviso!",
              detail: this.statusServer.mensagem,
              life: 8000,
            });
          }

          if (this.statusServer.status == "Erro") {
            this.$toast.add({
              severity: "error",
              summary: "Erro ao salvar",
              detail: this.statusServer.mensagem,
              life: 8000,
            });
          }
        this.pedido.hamburguer = this.hamburguerRetorno
        this.resetHamburguer();
        },

        async cadastrarPedido() {
          this.pedido.hamburguer = this.hamburguer;
            await this.salvarPedido();
            if (this.statusServer.status == "OK") {
                this.$toast.add({
                    severity: "success",
                    summary: "Salvo com Sucesso",
                    detail: "Pedido inserida no banco de dados",
                    life: 3000,
                });
                this.$emit("savedPedido");
            }
            if (this.statusServer.status == "Alerta") {
                this.$toast.add({
                    severity: "warn",
                    summary: "Aviso!",
                    detail: this.statusServer.mensagem,
                    life: 8000,
                });
            }
            if (this.statusServer.status == "Erro") {
                this.$toast.add({
                    severity: "error",
                    summary: "Erro ao salvar",
                    detail: this.statusServer.mensagem,
                    life: 8000,
                });
            }
            this.listarPedido();
            this.resetPedido();
        },
        
        criarPessoa() {
            this.displayCriarPessoa = !this.displayCriarPessoa;
        },

        
        sairPessoa(pessoaRtn) {
            this.pedido.pessoa = pessoaRtn;
            this.displayCriarPessoa = !this.displayCriarPessoa;
        },


        criarHamburguer() {
            this.listarPessoa();
            this.listarPao();
            this.listarCarne();
            this.listarQueijo();
            this.listarOpcionais();
            this.listarSalada();
            this.displayCriarHamburguer = !this.displayCriarHamburguer;
        },

        detalhesPedido(obj) {
            this.stringOpcionais='';
            this.stringSaladas='';
            this.pedido.id = obj.id;
            this.pedido.hamburguer = obj.hamburguer;
            this.pedido.pessoa = obj.pessoa;


            this.saladasPedido = this.pedido.hamburguer.saladas;
            this.saladasPedido.forEach((salada) => {
                this.stringSaladas += salada.tipo + ', ' 
            })
            this.stringSaladas = this.stringSaladas.substring(0, this.stringSaladas.length-2)


            this.opcionaisPedido = this.pedido.hamburguer.opcionais;
            this.opcionaisPedido.forEach((opcionais) => {
                this.stringOpcionais += opcionais.tipo + ', ' 
            })
            this.stringOpcionais = this.stringOpcionais.substring(0, this.stringOpcionais.length-2)

            this.displayDetalhePedido = !this.displayDetalhePedido;
        },
        

        confirmarEditar() {
            this.pessoa.id = this.pedido.pessoa.id;
            this.pessoa.nome = this.pedido.pessoa.nome;
            this.hamburguer.id = this.pedido.hamburguer.id;
            this.hamburguer.pao = this.pedido.hamburguer.pao;
            this.hamburguer.carne = this.pedido.hamburguer.carne;
            this.hamburguer.queijo = this.pedido.hamburguer.queijo;
            this.hamburguer.saladas = this.pedido.hamburguer.saladas;
            this.hamburguer.opcionais = this.pedido.hamburguer.opcionais;

            this.listarPao();
            this.listarCarne();
            this.listarQueijo();
            this.listarOpcionais();
            this.listarSalada();
          

            this.displayEditar = !this.displayEditar;
        },
            
        async mudarHamburguer() {
          await this.editarHamburguer(this.hamburguer);

          if (this.statusServer.status == "OK") {
            this.$toast.add({
              severity: "success",
              summary: "Editado com Sucesso",
              detail: "Hamburguer Editado no banco de dados",
              life: 3000,
            });

            this.$emit('editedHamburguer');
          }

          if (this.statusServer.status == "Alerta") {
            this.$toast.add({
              severity: "warn",
              summary: "Aviso!",
              detail: this.statusServer.mensagem,
              life: 8000,
            });
          }

          if (this.statusServer.status == "Erro") {
            this.$toast.add({
              severity: "error",
              summary: "Erro ao salvar",
              detail: this.statusServer.mensagem,
              life: 8000,
            });
          }

          this.pedidoEditar.id = this.pedido.id;          
          this.pedidoEditar.pessoa = this.pessoa;
          this.pedidoEditar.hamburguer = this.hamburguerRetorno
          
          this.displayDetalhePedido = !this.displayDetalhePedido;

          this.detalhesPedido(this.pedidoEditar)


        this.listarPedido();
        this.hamburguerPropsData=null;
        this.resetHamburguer()
        
        },  


        confirmarExcluir(obj) {
          this.displayExcluir = !this.displayExcluir
        },

      
        async excluirPedido(obj) {
            this.pedido.id = obj.id;
            this.pedido.hamburguer = obj.hamburguer;
            this.pedido.pessoa = obj.pessoa;
            await this.deletarPedido(this.pedido);
            if (this.statusServer.status == "OK") {
                this.$toast.add({
                    severity: "success",
                    summary: "Excluído com Sucesso",
                    detail: "Pedido excluída do banco de dados",
                    life: 3000,
                });
                this.$emit("deletedPedido");
            }
            if (this.statusServer.status == "Alerta") {
                this.$toast.add({
                    severity: "warn",
                    summary: "Aviso!",
                    detail: this.statusServer.mensagem,
                    life: 8000,
                });
            }
            if (this.statusServer.status == "Erro") {
                this.$toast.add({
                    severity: "error",
                    summary: "Erro ao salvar",
                    detail: this.statusServer.mensagem,
                    life: 8000,
                });
            }
            this.listarPedido();
            this.resetPedido();
            this.listarPessoa();
        },
        
    },
    mounted() {
        this.listarPessoa();
        this.listarPedido();
    },
});
</script>
<template>
    <div class="card mt-0">
      <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
        <div class="p-inputgroup w-8">
          <Button label="+" class="w-2"  @click="criarPessoa()"/>
          <Dropdown v-model="pedido.pessoa" :options="listaPessoa" optionLabel="nome" placeholder="Selecione a pessoa" :filter="true" filterPlaceholder="Buscar" class="w-8" />
        </div>
        <Button label="Montar Hamburguer" class="p-button-rounded p-button-warning m-2" @click="criarHamburguer()"/>
      </div>
    </div>
    <div class="card mt-0">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <DataTable
          :style="{width: '350px'}"
          :value="listaPedido"
          responsiveLayout="scroll"
        >
          <Column field="pessoa.nome" header="Nome"></Column>
          <Column field="hamburguer.id" header="ID.BURGUER"></Column>
          <Column>
            <template #body="slotProps">
              <Button icon="pi pi-eye" class="p-button-rounded p-button-success mr-2" @click="detalhesPedido(slotProps.data)" />
            </template>
          </Column>
        </DataTable>
      </div>
    </div>

    
    <Dialog v-model:visible="displayCriarPessoa" :style="{width: '450px'}" :modal="true" :closable="false">
      <h2 class="flex justify-content-center m-0">Adicionar Pessoa</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6"><div class="card mt-0">
            <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
              <CrudPessoaVue/>
            </div>
          </div>
        </div>
      </div>
      <template #footer>
          <Button label="Sair" icon="pi pi-check" class="p-button-text" @click="sairPessoa(pessoaRetorno), displayEditar=false" />
      </template>
    </Dialog>

    <Dialog v-model:visible="displayCriarHamburguer" class="w-10" :modal="true" :closable="false">
      <h2 class="flex justify-content-center m-0">Montrar Hamburguer</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
          <div class="card mt-0">
            <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
              <h4 class="mb-0 mt-0">Pao</h4>
              <Listbox v-model="hamburguer.pao" :options="listaPao" optionLabel="tipo" placeholder="tipo" class="m-3 w-17rem"/>
              <h4 class="mb-0">Carne</h4>
              <Listbox v-model="hamburguer.carne" :options="listaCarne" optionLabel="pesoGramas" class="m-3 w-17rem"/>
              <h4 class="mb-0">Queijo</h4>
              <Listbox v-model="hamburguer.queijo" :options="listaQueijo" optionLabel="tipo" class="m-3 w-17rem"/>
              <h4 class="mb-0">Saladas</h4>
              <Listbox v-model="hamburguer.saladas" :options="listaSalada" optionLabel="tipo" :multiple="true" class="m-3 w-17rem"/>
              <h4 class="mb-0">Opcionais</h4>
              <Listbox v-model="hamburguer.opcionais" :options="listaOpcionais" optionLabel="tipo" :multiple="true" class="m-3 w-17rem"/>
            </div>
          </div>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayCriarHamburguer=false"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="cadastrarPedido(), displayCriarHamburguer=false" />
      </template>
    </Dialog>

        <Dialog v-model:visible="displayDetalhePedido" :style="{width: '450px'}" :modal="true" :closable="false">
      <h2 class="flex justify-content-center m-0">Detalhes Pedido</h2>
      <div class="card justify-content-center">
          <div class="flex flex-wrap justify-content-center card-container" style="max-width: 500px">
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Pessoa:</b>  &ensp;</div>
                <div>{{pedido.pessoa.nome}}</div>
              </div>
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Pão:</b>  &ensp;</div>
                <div>{{pedido.hamburguer.pao.tipo}}</div>
              </div>
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Carne:</b>  &ensp;</div>
                <div>{{pedido.hamburguer.carne.pesoGramas}} g</div>
              </div>
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Queijo:</b>  &ensp;</div>
                <div>{{pedido.hamburguer.queijo.tipo}}</div>
              </div>
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Saladas:</b>  &ensp;</div>
                <div>{{stringSaladas}}</div>
              </div>
              <div class="flex align-items-center justify-content-center bg-blue-100 text-gray-900 m-2 border-round" style="width: 200px; min-height: 40px">
                <div><b>Opcionais:</b>  &ensp;</div>
                <div>{{stringOpcionais}}</div>
              </div>
          </div>
      </div>
      <template #footer>
          <Button label="Excluir" class="p-button-text" @click="confirmarExcluir()"/>
          <Button label="Editar" class="p-button-text" @click="confirmarEditar(), displayEditar=true" />
          <Button label="Sair" class="p-button-text" @click="displayDetalhePedido=false"/>
      </template>
    </Dialog>

    <Dialog v-model:visible="displayEditar" :style="{width: '450px'}" :modal="true" :closable="false">
      <h2 class="flex justify-content-center m-0">Editar Hamburguer</h2>
      <div class="confirmation-content">
        <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6"><div class="card mt-0">
            <div class="flex flex-wrap flex-column align-items-center justify-content-center mt-6">
              <h4 class="mb-0 mt-0">Pao</h4>
              <Listbox v-model="hamburguer.pao" :options="listaPao" optionLabel="tipo" placeholder="tipo" class="m-3 w-10"/>
              <h4 class="mb-0">Carne</h4>
              <Listbox v-model="hamburguer.carne" :options="listaCarne" optionLabel="pesoGramas" class="m-3 w-10"/>
              <h4 class="mb-0">Queijo</h4>
              <Listbox v-model="hamburguer.queijo" :options="listaQueijo" optionLabel="tipo" class="m-3 w-10"/>
              <h4 class="mb-0">Saladas</h4>
              <Listbox v-model="hamburguer.saladas" :options="listaSalada" optionLabel="tipo" :multiple="true" class="m-3 w-10"/>
              <h4 class="mb-0">Opcionais</h4>
              <Listbox v-model="hamburguer.opcionais" :options="listaOpcionais" optionLabel="tipo" :multiple="true" class="m-3 w-10"/>
            </div>
          </div>
        </div>
      </div>
      <template #footer>
          <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayEditar=false"/>
          <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="mudarHamburguer(), displayEditar=false" />
      </template>
    </Dialog>

  <Dialog v-model:visible="displayExcluir" :style="{width: '450px'}" :modal="true" :closable="false">
    <h2 class="flex justify-content-center m-0">Excluir Pedido</h2>
    <div class="confirmation-content">
      <div class="flex flex-wrap align-items-center justify-content-center mt-6">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <div >Excluir pedido de : '<b>{{pedido.pessoa.nome}}</b>' ?</div>
      </div>
    </div>
    <template #footer>
        <Button label="Não" icon="pi pi-times" class="p-button-text" @click="displayExcluir=false"/>
        <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="excluirPedido(pedido), displayExcluir=false, displayDetalhePedido=false" />
    </template>
  </Dialog>

</template>
