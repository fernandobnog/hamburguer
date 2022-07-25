import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import ToastService from 'primevue/toastservice';
import PrimeVue from 'primevue/config'
import axios from 'axios';

import TabMenu from 'primevue/tabmenu';
import TabView from 'primevue/tabview';
import TabPanel from 'primevue/tabpanel';
import InputText from 'primevue/inputtext';
import InputMask from 'primevue/inputmask';
import Dialog from 'primevue/dialog';
import RadioButton from 'primevue/radiobutton';
import Button from 'primevue/button';
import TriStateCheckbox from 'primevue/tristatecheckbox';
import ToggleButton from 'primevue/togglebutton';
import Dropdown from 'primevue/dropdown';
import AutoComplete from 'primevue/autocomplete';
import Menubar from 'primevue/menubar';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';
import Toast from 'primevue/toast';
import Calendar from 'primevue/calendar';
import ProgressBar from 'primevue/progressbar';
import Steps from 'primevue/steps';
import InputNumber from 'primevue/inputnumber';
import Textarea from 'primevue/textarea';
import Chart from 'primevue/chart';
import InputSwitch from 'primevue/inputswitch';
import Checkbox from 'primevue/checkbox';
import Badge from 'primevue/badge';
import Card from 'primevue/card';
import Toolbar from 'primevue/toolbar';
import Listbox from 'primevue/listbox';
import DataView from 'primevue/dataview';
import Image from 'primevue/image';


import 'primevue/resources/themes/vela-green/theme.css'
import 'primevue/resources/primevue.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'
import './assets/customizado.css'

axios.defaults.baseURL = import.meta.env.VITE_VUE_APP_IP_ADDRESS;

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(PrimeVue, {
  locale: {
    dayNames: ["Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"],
    dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
    dayNamesMin: ["D","S","T","Q","Q","S","S"],
    monthNames: ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"],
    monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun","Jul", "Ago", "Set", "Out", "Nov", "Dez"],
    today: 'Hoje',
    firstDayOfWeek: 0,
    dateFormat: 'dd/mm/yy',
    weak: 'Semana',
    dateIs: 'Dia',
    dateIsNot: 'Não é dia',
    dateBefore: 'Ontem',
    dateAfter: 'Amanhã',
  }
});

app.use(ToastService);

app.component('TabMenu', TabMenu);
app.component('Badge', Badge);
app.component('TabView', TabView);
app.component('TabPanel', TabPanel);
app.component('InputText', InputText);
app.component('InputMask', InputMask);
app.component('RadioButton', RadioButton);
app.component('Button', Button);
app.component('TriStateCheckbox', TriStateCheckbox);
app.component('ToggleButton', ToggleButton);
app.component('Dropdown', Dropdown);
app.component('AutoComplete', AutoComplete);
app.component('Menubar', Menubar);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('ColumnGroup', ColumnGroup);
app.component('Toast', Toast);
app.component('Dialog', Dialog);
app.component('Calendar', Calendar);
app.component('ProgressBar', ProgressBar);
app.component('Steps', Steps);
app.component('InputNumber', InputNumber);
app.component('Textarea', Textarea);
app.component('Chart', Chart);
app.component('InputSwitch', InputSwitch);
app.component('Checkbox', Checkbox);
app.component('Card', Card);
app.component('Toolbar', Toolbar);
app.component('Listbox', Listbox);
app.component('DataView', DataView);
app.component('Image', Image);



app.mount('#app')