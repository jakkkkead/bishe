<template>
  <div>
     <p>{{msg}}</p>
    <el-button style="width: 100px" @click="test">点我</el-button>
    <el-button type="danger" icon="el-icon-delete" circle @click="titleMsg('danger')"></el-button>
    <el-radio v-model="radio" label="1">男</el-radio>
    <el-radio v-model="radio" label="0">女</el-radio>
    <br>
    <el-date-picker
      v-model="value1"
      type="date"
      placeholder="选择日期">
    </el-date-picker>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="用户管理" name="first">用户管理</el-tab-pane>
      <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
    </el-tabs>
  </div>
  </template>

<script>

  export default {
    name: 'Hello',
    data (){
      return {
        msg : '你好呀dfssa！',
        radio :'1',
        value1 : '',
        activeName :'second'
      }
    },
    methods :{
      test (){
        alert("test按钮")
      },
      titleMsg(val){
        alert(val)
      },
      handleClick(tab, event) {
        console.log(tab.name);
      }
    }
  }
</script>

<style scoped>

</style>
