<template>
  <el-form id="seach_form" v-model="yearCoditon">
    <el-date-picker
      type="year"
      v-model="yearCoditon.sdate"
      value-format="yyyy-MM-dd"
      placeholder="选择年份">
    </el-date-picker>
    <!--&#45;&#45;-->
    <!--<el-date-picker-->
    <!--type="month"-->
    <!--v-model="edate"-->
    <!--value-format="yyyy-MM-dd"-->
    <!--placeholder="结束日期">-->
    <!--</el-date-picker>-->
    <el-select v-model.number="yearCoditon.departId" placeholder="请选择部门">
      <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
    </el-select>
    <el-button icon="el-icon-search" circle @click="searchData"></el-button>
  </el-form>
</template>

<script>
  export default {
    name: "year_select",
    data(){
      return {
        yearCoditon:{
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
        // this.axios({
        //   method : 'get',
        //   url : 'http://localhost:8083/getYearExpenseForms?year='+this.sdate+'&departId='+this.departId
        // }).then((res) =>{
        //   if(res.data.code ==0){
        //     this.$emit("fromConditon",res.data.data)
        //   }
        // })
        this.$emit("fromConditon",this.yearCoditon)
      }
    }
  }
</script>

<style scoped>

</style>
