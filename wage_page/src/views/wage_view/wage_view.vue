<template>
  <div>
    <h4>查看工资</h4>
  <el-form id="seach_form">
    <el-date-picker
      type="month"
      v-model="sdate"
      value-format="yyyy-MM-dd"
      placeholder="选择日期">
    </el-date-picker>
    <el-select v-model="departId" placeholder="请选择部门">
      <el-option
        v-for="item in departOption"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <el-button icon="el-icon-search" circle></el-button>
  </el-form>
  <el-table
    :data="tableData2"
    border
    style="width: 100%">
    <el-table-column
      prop="workTime"
      label="时间"
      width="100">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="70">
    </el-table-column>
    <el-table-column
      label="部门"
      width="65">
      <template slot-scope="scope">
        {{scope.row.departId | departFilter}}
      </template>
    </el-table-column>
    <el-table-column
      prop="baseWage"
      label="基本工资"
      width="80">
    </el-table-column>
    <el-table-column
      prop="name"
      label="事假扣除"
      width="80">
    </el-table-column>
    <el-table-column
      prop="socialPay"
      label="扣除社保"
      width="80">
    </el-table-column>


    <el-table-column
      prop="workDay"
      label="工作天数"
      width="40">
    </el-table-column>
    <el-table-column
      prop="holidayDay"
      label="请假天数"
      width="40">
    </el-table-column>
    <el-table-column
      label="发放状态"
      width="40">
      <template slot-scope="scope">
        {{scope.row.status | wageStatusFilter}}
      </template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="绩效工资"
      width="80">
    </el-table-column>
    <el-table-column
      prop="takePay"
      label="实发工资"
      width="90">
    </el-table-column>
  </el-table>
    <div id="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :current-page.sync="currentPage"
        :page-size="pageSize">
      </el-pagination>
    </div>
  </div>
</template>

<script>
    export default {
        name: "expense_view",
      data() {
        return {
          tableData2: [{
            userId:'',
            name : '',
            baseWage : '',
            workDay : '',
            holidayDay : '',
            socialPay : '',
            accumulationFund : '',
            status : '',
            workTime:'',
            departId:''
          }],
          sdate :'',
          departId :'',
          total : '',
          currentPage:1,
          total : 10,
          pageSize : 5,
          departOption:[
            {
              value : 0,
              label : '研发部'
            },
            {
              value : 1,
              label : '财务部'
            },
            {
              value : 2,
              label : '人力资源部'
            },
            {
              value : 3,
              label : '采购部'
            },
            {
              value : 4,
              label : '事业部'
            }
          ]
        }
      },
      methods :{
          getData(){
            this.axios({
              method : 'get',
              url : 'http://localhost:8083/getWageList?currentPage='+this.currentPage+'&pageSize='+this.pageSize+'&sdate='+this.sdate+'&departId='+this.departId
            }).then((res=>{
               if(res.data.code == 0){
                   this.tableData2 = res.data.data.list
                   this.total =res.data.data.total
               }else{

               }
            })).catch(reason => {
              window.location.href="http://localhost:8083/tologin"
            })
          }
      }
    }
</script>

<style scoped>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
  #seach_form{
    margin-bottom: 50px;
  }
  #pagination{
    margin-top: 30px;
    margin-left: 200px;
  }

</style>
