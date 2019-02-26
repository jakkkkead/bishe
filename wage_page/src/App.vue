<template>
  <div>
    <Head></Head>

    <div id="navDiv">
      <el-row class="tac">
        <el-col :span="24">

          <el-menu default-active="1"
                   class="el-menu-vertical-demo"
                   background-color="#545c64"
                   text-color="#fff"
                   active-text-color="#ffd04b"

          >
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-document"></i>
                <span>费用报销</span>
              </template>
              <el-menu-item-group>
                <router-link to="/expense_add"> <el-menu-item index="1-1"><i class="el-icon-edit"></i>申请报销单</el-menu-item></router-link>
                <router-link to="/expense_view"><el-menu-item index="1-2"><i class="el-icon-view"></i>查看报销单</el-menu-item></router-link>
                <router-link to="/expense_check"><el-menu-item index="1-3"><i class="el-icon-check"></i>审批报销单</el-menu-item></router-link>
                <el-submenu index="1-4">
                  <template slot="title"><i class="el-icon-view"></i>查看报销报表</template>
                  <router-link to="/expense_forms"><el-menu-item index="1-4-1"><i class="el-icon-picture-outline"></i>月报表</el-menu-item></router-link>
                  <router-link to="/expense_yearForms"> <el-menu-item index="1-4-2"><i class="el-icon-picture-outline"></i>年报表</el-menu-item></router-link>
                </el-submenu>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-tickets"></i>
                <span>请假</span>
              </template>
              <el-menu-item-group>
                <router-link to="holiday_add"><el-menu-item index="2-1"><i class="el-icon-edit"></i>申请请假单</el-menu-item></router-link>
                <router-link to="holiday_view"><el-menu-item index="2-2"><i class="el-icon-view"></i>查看请假单</el-menu-item></router-link>
                <router-link to="holiday_check"><el-menu-item index="2-3"><i class="el-icon-check"></i>审批请假单</el-menu-item></router-link>
                <router-link to="holiday_yearForm"><el-menu-item index="3-4"><i class="el-icon-view"></i>查看请假报表</el-menu-item></router-link>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-info"></i>
                <span>工资</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="3-1"><i class="el-icon-view"></i><router-link to="wage_view">查看工资单</router-link></el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-info"></i>
                <span>项目信息</span>
              </template>
              <el-menu-item-group>
                <router-link to="item_view"><el-menu-item index="4-1"><i class="el-icon-view"></i>查看项目信息</el-menu-item></router-link>
                <router-link to="item_add"><el-menu-item index="4-2"><i class="el-icon-circle-plus"></i>添加项目信息</el-menu-item></router-link>
                <el-menu-item index="4-3"><i class="el-icon-view"></i>项目报表分析</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="5">
              <template slot="title">
                <i class="el-icon-picture-outline"></i>
                <span>人员管理</span>
              </template>
              <el-menu-item-group>
                <router-link to="user_add"><el-menu-item index="5-1"><i class="el-icon-view"></i>入职办理</el-menu-item></router-link>
                <router-link to="user_view"><el-menu-item index="5-2"><i class="el-icon-circle-plus"></i>查看员工信息</el-menu-item></router-link>
                <router-link to="item_view"><el-menu-item index="5-3"><i class="el-icon-circle-plus"></i>员工报表</el-menu-item></router-link>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="6">
              <template slot="title">
                <i class="el-icon-picture-outline"></i>
                <span>年度报表统计</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="6-1"><i class="el-icon-view"></i>年度报表统计</el-menu-item>
                <el-menu-item index="6-2"><i class="el-icon-circle-plus"></i>人员变动分析</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-col>

      </el-row>
    </div>
    <!--    <router-link to ="/expense">去去</router-link>
        <div>
          <router-view></router-view>
        </div>-->
    <div id="routerView">
      <router-view></router-view>
    </div>
    <div id = "clean"></div>
  </div>

</template>

<script>
  import Head from './components/Head'

  export default {
    components: {
      Head
    }
  }
</script>

<style scoped>
  #navDiv{
    /*width: auto;*/
/*    float: left;*/
    width: 201px;
    /*padding-right: 20px;*/
    margin-right: 0px;
    min-height: 800px;
    background-color: #545c64;
    display: inline-block;
  }
  #routerView {
    float: right;
    width: 1000px;
    margin-top: 10px;
    /*margin-right: 250px;*/
    margin-bottom: 100px;

  }
  a {color:#252525; text-decoration:none;}
  a:visited {text-decoration:none;}
  a:hover {color:#ba2636;text-decoration:underline;text-decoration:none;}
  a:active {color:#ba2636;text-decoration:none;}

  /*  #clean {
      clear: both;
    }*/

</style>
