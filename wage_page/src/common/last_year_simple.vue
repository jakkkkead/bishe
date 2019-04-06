<template>
  <el-form id="seach_form" v-model="yearCoditon">
    <el-date-picker
      type="year"
      v-model="yearCoditon.sdate"
      value-format="yyyy-MM-dd"
      placeholder="选择同期">
    </el-date-picker>
    <el-button icon="el-icon-search" circle @click="searchData"></el-button>
  </el-form>
</template>

<script>
    export default {
        name: "last_year_simple",
      data(){
          return {
            yearCoditon:{
              sdate : ""
            }
          }
      },
      methods : {
        searchData (){
          this.$emit("fromConditon",this.yearCoditon)
        }
      }
    }
</script>

<style scoped>

</style>
