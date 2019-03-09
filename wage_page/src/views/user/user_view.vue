<template>
  <div>
    <h4>查看员工信息</h4>
    <dateAndDepart v-on:fromConditon="searchData"></dateAndDepart>
    <el-table
      :data="tableData2"
      border
      style="width: 85%">
      <el-table-column
        prop="createTime"
        label="入职时间"
        width="130">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="100">
      </el-table-column>
      <el-table-column
        label="性别"
        width="80">
        <template slot-scope="scope">
          {{scope.row.sex | sexFilter}}
        </template>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机"
        width="148">
      </el-table-column>
      <el-table-column
        width="80"
        label="部门">
        <template slot-scope="scope">
          {{scope.row.departId | departFilter}}
        </template>
      </el-table-column>
      <el-table-column

        label="在职状态"
        width="70">
        <template slot-scope="scope">
          {{scope.row.status | userStatusFilter}}
        </template>
      </el-table-column>
      <el-table-column
        label="职位"
        width="90">
        <template slot-scope="scope">
          {{scope.row.post | userPostFilter}}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button  style="width: 90px" size="small" type="primary" plain @click="editExpense(scope.$index, scope.row)" >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div id="pagination">
      <el-pagination
        background
        @current-change="handleCurrentChange"
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
      <el-form ref="editForm" :model="editForm" label-width="80px" style="width: 600px">
        <el-form-item label="姓名">
          <el-input v-model="editForm.name" placeholder="姓名" ></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" placeholder="密码" type="password" ></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <el-select v-model="editForm.departId" placeholder="请选择部门">
            <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editForm.phone" placeholder="手机号" ></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱">
          <el-input v-model="editForm.email" placeholder="电子邮箱" ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.sex">
            <el-radio v-for="item in sexOption" :key="item.id" :label="item.id">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="在职状态">
          <el-select v-model="editForm.status" placeholder="请选在职状态">
            <el-option v-for="item in statusOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="职位">
          <el-select v-model="editForm.post" placeholder="请选择职位类型">
            <el-option v-for="item in postOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入职时间">
          <el-date-picker
            v-model="editForm.createTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="入职时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出生日期">
        <el-date-picker
          v-model="editForm.birthdate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="出生日期">
        </el-date-picker>
      </el-form-item>
        <el-form-item label="离职时间">
          <el-date-picker
            v-model="editForm.leaveTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="离职时间">
          </el-date-picker>
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
  import dateAndDepart from '../../common/date_depart'
  export default {
    name: "user_view",
    components:{
      dateAndDepart
    },
    data() {
      return {
        Conditons:{
          sdate : "" ,
          edate :"",
          departId:''
        },
        departId:5,
        tableData2: [{
          userId: '',
          name: '',
          departId: '',
          createTime: '',
          birthdate: '',
          sex: '',
          post: '',
          email: '',
          phone: ''
        }],
        currentPage: 1,
        total: 10,
        pageSize: 5,
        editFormVisible: false,
        editForm: {
          userId: '',
          name: '',
          departId: '',
          createTime: '',
          birthdate: '',
          sex: 1,
          post: '',
          email: '',
          phone: '',
          leaveTime: '',
          password: '',
          status : ''
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
          }
        ],
        postOption: [
          {
            value: 0,
            label: '一般职员'
          },
          {
            value: 1,
            label: '部门主管'
          },
          {
            value: 2,
            label: '副总经理'
          },
          {
            value: 3,
            label: '总经理'
          }
        ],
        sexOption: [
          {
            id: 0,
            name: '女'
          },
          {
            id: 1,
            name: '男'
          }
        ],
        statusOption:[{
           value : 0,
           label :'离职'
        },
        {
          value : 1,
          label :'在职'
         }]

      }
    },
    methods: {
      getData() {
        var baseUrl = 'http://localhost:8083'
        this.axios({
          method: 'get',
          url: baseUrl + '/getUsers?currentPage=' + this.currentPage + '&pageSize=' + this.pageSize + '&beginDate=' + this.Conditons.sdate + '&endDate=' + this.Conditons.edate+'&departId='+this.Conditons.departId
        }).then((res) => {
          this.tableData2 = res.data.data.list
          this.total = res.data.data.total
        }).catch(reason => {
         // window.location.href="http://localhost:8083/tologin"
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
          url: baseUrl + '/updateUser',
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
         // window.location.href="http://localhost:8083/tologin"
        })
      },
      handleCurrentChange(val){
        this.currentPage = val
        this.getData()
      },
      searchData (data){
        this.Conditons = data
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
