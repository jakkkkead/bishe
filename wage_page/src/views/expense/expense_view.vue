<template>
  <div>
    <h4>查看报销单</h4>
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
      width="50">
      <template slot-scope="scope">
        <el-button type="text" size="small" @click="editExpense(scope.$index, scope.row)" >编辑</el-button>
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
    <el-dialog title="编辑"
               :visible.sync="editFormVisible"
               :close-on-click-modal="false"
               class="edit-form"
               >
      <el-form :model="editForm" label-width="80px"  ref="editForm">
        <el-form-item label="申请人">
          <el-input v-model="editForm.createName"></el-input>
        </el-form-item>
        <el-form-item label="收款人">
          <el-input v-model="editForm.receiverName"></el-input>
        </el-form-item>
        <el-form-item label="银行账号">
          <el-input v-model="editForm.accountNumber"></el-input>
        </el-form-item>
        <el-form-item label="报销金额">
          <el-input v-model="editForm.money"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="editForm.payway" placeholder="请选择收付方式">
             <el-option v-for="item in payWayOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费用类型">
          <el-select v-model="editForm.type" placeholder="请选择类型">
            <el-option v-for="item in typeOption" :value="item.value" :label="item.label" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起止时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="起始日期" v-model="editForm.beginTime" style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">
            -   ->
          </el-col>

          <el-col :span="11">
            <el-date-picker type="date" placeholder="结束日期" v-model="editForm.endTime" style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="editForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="handleCancel">取消</el-button>
        <el-button type="primary" @click.native="handleUpdate">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "expense_view",
      data() {
        return {
          sdate : "" ,
          edate :"",
          tableData2: [{
            expenseId:'',
            createName: '',
            receiverName: '',
            accountNumber: '',
            money: 0,
            payway: 0,
            createTime: '',
            remark: '',
            type :0,
            status : '',
            beginTime:'',
            endTime:''
          }],
          currentPage:1,
          total : 10,
          pageSize : 5,
          editFormVisible : false,
          editForm :{
            expenseId :'',
            createName: '',
            receiverName: '',
            accountNumber: '',
            money: 0,
            payway: 1,
            beginTime: '',
            endTime: '',
            remark: '',
            type : 0,
            beginTime:'',
            endTime:''
          },
          payWayOption:[{
            value : 0,
            label : '网银'
          },{
            value : 1,
            label : '现金'
          }],
          typeOption:[
            {
              value : 0,
              label:'差旅'
            },
            {
              value : 1,
              label:'交通费'
            },
            {
              value : 2,
              label:'日常缴费'
            },
            {
              value : 3,
              label:'其他'
            }
          ]
        }
      },
      watch : {
        currentPage(oldv,newv){
          this.getData()
        }

      },
      methods: {
        getData() {
          var baseUrl = 'http://localhost:8083'
          this.axios({
            method: 'get',
            url: baseUrl + '/getExpense?currentPage=' + this.currentPage + '&pageSize=' + this.pageSize + '&beginDate=' + this.sdate + '&endDate=' + this.edate
          }).then((res) => {
            this.tableData2 = res.data.data.list
            this.total = res.data.data.total
          }).catch(reason => {
            window.location.href="http://localhost:8083/tologin"
          })
        },
        handleCancel() {
          this.editFormVisible = false
        },
        editExpense(index, row) {
          this.editFormVisible = true;
          this.editForm = Object.assign({}, row);
        },
        handleUpdate() {
          var baseUrl = 'http://localhost:8083'
          var datas = this.$qs.stringify(this.editForm)
          this.axios({
            method: 'post',
            data: datas,
            url: baseUrl + '/updateExpense',
            headers: {
              'Content-type': 'application/x-www-form-urlencoded'
            }
          }).then((res) => {
            if (res.data.code == 0) {
              alert("更新成功！")
              this.editFormVisible = false
              this.getData()
            } else {
              alert("更新失败！")
            }
          }).catch(reason => {
            window.location.href="http://localhost:8083/tologin"
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
  #seach_form{
    margin-bottom: 50px;
  }
  #pagination{
    margin-top: 30px;
    margin-left: 200px;
  }

</style>
