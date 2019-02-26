/*
入口文件
 */
import Vue from 'vue'
import App from  './App'
import { Button, Select,Radio,DatePicker,Tabs,TabPane,Icon,Dropdown,DropdownMenu,DropdownItem,
  MenuItem,MenuItemGroup,Row,Col,Menu,Submenu,Form,FormItem,Switch,CheckboxGroup,Checkbox,
RadioGroup,Input,Option,TimePicker,Table,TableColumn,Pagination,Dialog} from 'element-ui'
import router from './router/index'
import qs from 'qs'
Vue.prototype.$qs = qs
import axios from 'axios'
import VueAxios from 'vue-axios'
import Echars from 'echarts'
Vue.prototype.$echarts = Echars
// 引入全局过滤器
import filters from './filters/filters'
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
Vue.use(VueAxios,axios);
Vue.prototype.$http.defaults.withCredentials = true
Vue.component(Button.name, Button);
Vue.component(Select.name, Select);
Vue.component(Radio.name, Radio);
Vue.component(DatePicker.name, DatePicker);
Vue.component(Tabs.name, Tabs);
Vue.component(TabPane.name, TabPane);
Vue.component(Dropdown.name, Dropdown);
Vue.component(Icon.name, Icon);
Vue.component(DropdownMenu.name, DropdownMenu);
Vue.component(DropdownItem.name, DropdownItem);
Vue.component(MenuItem.name, MenuItem);
Vue.component(MenuItemGroup.name, MenuItemGroup);
Vue.component(Row.name, Row);
Vue.component(Col.name, Col);
Vue.component(Menu.name, Menu);
Vue.component(Submenu.name, Submenu);
Vue.component(Form.name, Form);
Vue.component(FormItem.name, FormItem);
Vue.component(Switch.name, Switch);
Vue.component(CheckboxGroup.name, CheckboxGroup);
Vue.component(Checkbox.name, Checkbox);
Vue.component(RadioGroup.name, RadioGroup);
Vue.component(Input.name, Input);
Vue.component(Option.name, Option);
Vue.component(TimePicker.name, TimePicker);
Vue.component(Table.name, Table);
Vue.component(TableColumn.name, TableColumn);
Vue.component(Pagination.name, Pagination);
Vue.component(Dialog.name, Dialog);
new Vue({
  el:'#app',
  components:{
    App
  },
  template:'<App/>',
  router : router
})
