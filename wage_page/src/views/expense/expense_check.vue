<template>
  <div>
    <h4>审核报销单</h4>
    <el-form id="seach_form">
      <el-date-picker
        type="date"
        v-model="sdate"
        value-format="yyyy-MM-dd"
        placeholder="起始日期">
      </el-date-picker>--
      <el-date-picker
        type="date"
        v-model="edate"
        value-format="yyyy-MM-dd"
        placeholder="结束日期">
      </el-date-picker>
      <el-button icon="el-icon-search" circle @click="getData"></el-button>
    </el-form>
  <el-table
    :data="tableData2"
    border
    style="width: 100%">
    <el-table-column
      prop="createTime"
      label="申请日期"
      width="100">
    </el-table-column>
    <el-table-column
      prop="createName"
      label="申请人"
      width="80">
    </el-table-column>
    <el-table-column
      prop="receiverName"
      label="收款人"
      width="80">

    </el-table-column>
    <el-table-column
      prop="money"
      label="报销金额（元）"
      width="90">
    </el-table-column>
    <el-table-column
      prop="accountNumber"
      label="银行账号">
    </el-table-column>
    <el-table-column

      label="申请状态"
      width="70">
      <template slot-scope="scope">
        {{scope.row.status | statusFilter}}
      </template>
    </el-table-column>
    <el-table-column
      label="费用类型"
      width="50">
      <template slot-scope="scope">
        {{scope.row.type | payTypeFilter}}
      </template>
    </el-table-column>
    <el-table-column
      label="支付方式"
      width="50">
      <template slot-scope="scope">
        {{scope.row.payway | paywayFilter}}
      </template>
    </el-table-column>
    <el-table-column
      prop="remark"
      label="备注">
    </el-table-column>
    <el-table-column
      label="操作"
      width="200">
      <template slot-scope="scope">
        <el-button type="primary" plain style="width: 75px" size="small" @click="passExp(scope.$index, scope.row,1)">同意</el-button>
        <el-button type="primary" plain style="width: 75px" size="small" @click="passExp(scope.$index, scope.row,2)">拒绝</el-button>
      </template>
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
  var baseUrl = 'http://localhost:8083'
  export default {
    name: "expense_view",
    data() {
      return {
        sdate : '' ,
        edate :'',
        tableData2: [{
          expenseId:'',
          createName: '',
          receiverName: '',
          accountNumber: '',
          money: 0,
          payway: '',
          createTime: '',
          endTime: '',
          remark: '',
          type : '',
          status : ''
        }],
        currentPage:1,
        total : 10,
        pageSize : 5
      }
    },
    watch : {
      currentPage(oldv,newv){
        this.getData()
      }

    },methods:{
      getData(){
        this.axios({
          method : 'get',
          url : baseUrl+'/getExpense?currentPage='+this.currentPage+'&pageSize='+this.pageSize+'&beginDate='+this.sdate+'&endDate='+this.edate
        }).then((res) =>{
          this.tableData2 = res.data.data.list
          this.total = res.data.data.total
        }).catch(reason => {
          window.location.href="http://localhost:8083/tologin"
        })
      },
      passExp(index,row,flag){
        this.axios({
          method : 'get',
          url : baseUrl+'/checkExpense?expenseId='+row.expenseId+'&flag='+flag
        }).then((res) =>{
            if(res.data.code ==0){
              alert("审核成功！")
              this.getData()
            }else{
              alert("审核失败！")
            }
        })
      }
    },
    mounted() {
      this.getData()
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
  #pagination{
    margin-top: 30px;
    margin-left: 200px;
  }
  #seach_form{
    margin-bottom: 50px;
  }
</style>
