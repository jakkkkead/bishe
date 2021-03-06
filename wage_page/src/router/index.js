/**
 * 路由器模块，需要再入口js引入使用
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import expense_add from '../views/expense/expense_add'
import expense_view from '../views/expense/expense_view'
import expense_check from '../views/expense/expense_check'
import expense_forms from '../views/expense/expense_forms'
import expense_yearForms from '../views/expense/expense_yearForms'
import holiday_add from '../views/holiday/holiday_add'
import holiday_view from '../views/holiday/holiday_view'
import holiday_check from '../views/holiday/holiday_check'
import holiday_yearForm from '../views/holiday/holiday_yearForm'
import wage_view from '../views/wage_view/wage_view'
import item_add from '../views/item/item_add'
import item_view from '../views/item/item_view'
import item_form from '../views/item/item_form'
import user_add from '../views/user/user_add'
import user_view from '../views/user/user_view'
import user_form from '../views/user/user_form'
import year_map from '../views/year_totalform/year_map'
import year_wage from '../views/year_totalform/year_wage'
import year_categery from '../views/year_totalform/year_categery'
import auto from '../views/auto/auto'

//向外暴露路由器
export default new VueRouter({
  //配置n个路由
   routes : [
     {
       path:'/',
       redirect:'expense_view'
     },
    {
      path : '/expense_add',
      //可以引入组件,无需引号
      component : expense_add
    },
     {
       path : '/expense_view',
       component :expense_view
     },
     {
       path : '/expense_check',
       component :expense_check
     },
     {
       path : '/expense_forms',
       component :expense_forms
     },
     {
       path : '/expense_yearForms',
       component :expense_yearForms
     },
     {
       path : '/holiday_add',
       component :holiday_add
     },
     {
       path : '/holiday_view',
       component :holiday_view
     },
     {
       path : '/holiday_check',
       component :holiday_check
     },
     {
       path : '/holiday_yearForm',
       component :holiday_yearForm
     },
     {
       path : '/wage_view',
       component :wage_view
     },
     {
       path : '/item_add',
       component :item_add
     },
     {
       path : '/item_view',
       component :item_view
     },
     {
       path : '/user_add',
       component :user_add
     },
     {
       path : '/user_view',
       component :user_view
     },
     {
       path : '/user_form',
       component :user_form
     },
     {
       path : '/item_form',
       component :item_form
     },
     {
       path : '/year_map',
       component :year_map
     },
     {
       path : '/year_wage',
       component :year_wage
     },
     {
       path : '/year_categery',
       component :year_categery
     },
     {
       path : '/auto',
       component :auto
     }



  ]
})
