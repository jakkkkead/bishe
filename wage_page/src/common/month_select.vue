<template>
  <el-form id="seach_form" v-model="monthConditon">
    <el-date-picker
      type="month"
      v-model="monthConditon.sdate"
      value-format="yyyy-MM-dd"
      placeholder="选择月份">
    </el-date-picker>
    <!--&#45;&#45;-->
    <!--<el-date-picker-->
      <!--type="month"-->
      <!--v-model="edate"-->
      <!--value-format="yyyy-MM-dd"-->
      <!--placeholder="结束日期">-->
    <!--</el-date-picker>-->
    <el-select v-model.number="monthConditon.departId" placeholder="请选择部门">
      <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
    </el-select>
    <el-button icon="el-icon-search" circle @click="searchData"></el-button>
  </el-form>
</template>

<script>
    export default {
        name: "Condition",
        data(){
          return {
            monthConditon:{
              sdate : "" ,
              departId :""
            },
            departOption: [
              {
                value: 0,
                label: '研发部'
              },
              {
                value: 1,
                label: '财务部'
              },
              {
                value: 2,
                label: '人力资源部'
              },
              {
                value: 3,
                label: '采购部'
              },
              {
                value: 4,
                label: '事业部'
              },
              {
                value: 5,
                label: '所有'
              }
            ]
          }
        },
      methods : {
        searchData (){
          this.$emit("fromConditon",this.monthConditon)
          // this.axios({
          //   method : 'get',
          //   url : 'http://localhost:8083/getExpenseForms?dayTime='+this.sdate+'&departId='+this.departId
          // }).then((res) =>{
          //   if(res.data.code ==0){
          //     this.$emit("fromConditon",res.data.data)
          //   }
          // })
        }
      }
    }
</script>

<style scoped>

</style>
