<template>
  <el-form id="seach_form" v-model="dateAndDepart">
    <el-date-picker
      type="month"
      v-model="dateAndDepart.sdate"
      value-format="yyyy-MM-dd"
      placeholder="起始日期">
    </el-date-picker>
    --
    <el-date-picker
    type="month"
    v-model="dateAndDepart.edate"
    value-format="yyyy-MM-dd"
    placeholder="结束日期">
    </el-date-picker>
    <el-select v-model.number="dateAndDepart.departId" placeholder="请选择部门">
      <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
    </el-select>
    <el-button icon="el-icon-search" circle @click="searchData"></el-button>
  </el-form>
</template>

<script>
  export default {
    name: "date_depart",
    data(){
      return {
        dateAndDepart:{
          sdate : "" ,
          edate:"",
          departId :5
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
        this.$emit("fromConditon",this.dateAndDepart)
      }
    }
  }
</script>

<style scoped>

</style>
