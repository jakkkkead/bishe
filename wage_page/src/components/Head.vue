<template>
    <div id="header">
      <span id = "system-name">Wage_System</span>
      <div id="head-menu">
        <span>{{userName}}</span>
      <el-dropdown>
        <span class="el-dropdown-link">
        <font color="white">设置</font><i class="el-icon-setting"></i>
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="sigin">注册新用户</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      </div>
    </div>
</template>

<script>
    export default {
        name: "Head",
      data(){
          return {
            userName:''
          }
      },
      methods:{
          getUserName(){
            this.axios({
              method:'get',
              url:'http://localhost:8083/getUserName'
            }).then((res)=>{
                if(res.data.code == 0 ){
                  console.log(res.data.data)
                  this.userName = res.data.data.name
                }else{
                    alert(res.data.msg)
                   window.location.href="http://locahost:8083/tologin"
                }
            })
          },
          sigin(){
            window.location.href = "http://localhost:8083/tosigin"
          },
          logout(){
            window.location.href = "http://localhost:8083/logout"
          }
      },
      mounted() {
          //this.getUserName
      }
    }
</script>

<style scoped>
  #header {
    width: 100%;
    height: 60px;
    background-color: black;
    padding-top: 20px;
    padding-left: 30px;
    padding-bottom: 20px;
    color: white;
  }
  #system-name{
    font-family: "Poppins", sans-serif;
    font-size: 2em;
    font-weight: 400;
    line-height: 1.5;
  }

  #head-menu{
    display: inline-block;
    margin-right: 30px;
    margin-left: 1200px;
    margin-bottom: 160px;
  }

</style>
