<template>
  <div>
    <h4>审批请假单</h4>
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
    <el-button icon="el-icon-search" circle @click="searchData"></el-button>
  </el-form>
    <el-table
      :data="tableData2"
      border
      style="width: 89%">
      <el-table-column
        prop="beginTime"
        label="起始日期"
        width="120">
      </el-table-column>
      <el-table-column
        prop="endTime"
        label="结束日期"
        width="120">
      </el-table-column>
      <el-table-column
        prop="name"
        label="申请人"
        width="80">
      </el-table-column>
      <el-table-column
        label="申请状态"
        width="80">
        <template slot-scope="scope">
          {{scope.row.status | statusFilter}}
        </template>
      </el-table-column>
      <el-table-column
        label="假期类型"
        width="80">
        <template slot-scope="scope">
          {{scope.row.type | holidayTypeFilter}}
        </template>
      </el-table-column>
      <el-table-column
        label="部门"
        width="80">
        <template slot-scope="scope">
          {{scope.row.departId | departFilter}}
        </template>
      </el-table-column>
      <el-table-column
        prop="remark"
        label="备注"
        width="120">
      </el-table-column>
      <el-table-column
        label="操作"
        width="208">
        <template slot-scope="scope">
          <el-button type="primary" plain style="width: 75px" size="small" @click="checkHoliday(scope.$index, scope.row,1)">同意</el-button>
          <el-button type="primary" plain style="width: 75px" size="small" @click="checkHoliday(scope.$index, scope.row,2)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div id="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
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
            holidayId:'',
            name: '',
            departId: '',
            beginTime: '',
            endTime: '',
            type: '',
            remark: '',
            status:''
          }],
          sdate :'',
          edate :'',
          currentPage:1,
          total : 10,
          pageSize : 5
        }
      },
          methods:{
            getData(){
              this.axios({
                method : 'get',
                url : 'http://localhost:8083/getHolidayList?currentPage='+ this.currentPage + '&pageSize=' + this.pageSize + '&beginDate=' + this.sdate + '&endDate=' + this.edate
              }).then((res)=>{
                if(res.data.code == 0){
                  this.tableData2 = res.data.data.list
                  this.total = res.data.data.total
                }else{
                  alert("获取请假数据失败！")
                }
              }).catch(reason => {
                window.location.href="http://localhost:8083/tologin"
              })
            },
            searchData(){
              this.getData()
            },
            checkHoliday(index,row,flag){
              this.axios({
                method : 'get',
                url :' http://localhost:8083'+'/checkHoliday?holidayId='+row.holidayId+'&flag='+flag
              }).then((res) =>{
                if(res.data.code ==0){
                  alert("审核成功！")
                  this.getData()
                }else{
                  alert("审核失败！")
                }
              })
            },
            handleCurrentChange(val){
              this.currentPage = val
              this.getData()
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
  #seach_form{
    margin-bottom: 50px;
  }
  #pagination{
    margin-top: 30px;
    margin-left: 200px;
  }

</style>
