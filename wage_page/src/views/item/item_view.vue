<template>
  <div>
    <el-form id="seach_form">
      <el-date-picker
        type="date"
        v-model="sdate"
        value-format="yyyy-MM-dd"
        placeholder="起始日期">
      </el-date-picker>
      --
      <el-date-picker
        type="date"
        v-model="edate"
        value-format="yyyy-MM-dd"
        placeholder="结束日期">
      </el-date-picker>
      <el-select v-model.number="departId" placeholder="请选择部门">
        <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
      </el-select>
      <el-button icon="el-icon-search" circle @click="searchData"></el-button>
    </el-form>
    <el-table
      :data="form"
      style="width: 80%"
    border>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="项目名称:">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="开始时间:">
              <span>{{ props.row.beginTime }}</span>
            </el-form-item>
            <el-form-item label="结束时间:">
              <span>{{ props.row.endTime }}</span>
            </el-form-item>
            <el-form-item label="负责部门:">
              <span>{{ props.row.departId |departFilter}}</span>
            </el-form-item>
            <el-form-item label="项目负责人:">
              <span>{{ props.row.principal }}</span>
            </el-form-item>
            <el-form-item label="成交金额(元):">
              <span>{{ props.row.payment }}</span>
            </el-form-item>
            <el-form-item label="状态:">
              <span>{{ props.row.status |itemStatusFilter }}</span>
            </el-form-item>
            <el-form-item label="所属行业:">
              <span>{{ props.row.type |itemTypeFilter}}</span>
            </el-form-item>
            <el-form-item label="收支类型:">
              <span>{{ props.row.payType |itemPaytypeFilter }}</span>
            </el-form-item>
            <el-form-item label="描述:">
              <span>{{ props.row.description }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="项目名称"
        prop="name"
        width="120">
      </el-table-column>
      <el-table-column
        label="开始时间"
        prop="beginTime"
        width="120">
      </el-table-column>
      <el-table-column
        label="结束时间"
        prop="endTime"
        width="120">
      </el-table-column>
      <el-table-column
        label="项目负责部门"
        width="120">
        <template slot-scope="scope">
          {{scope.row.departId | departFilter}}
        </template>
      </el-table-column>
      <el-table-column
        label="项目负责人"
        prop="principal"
        width="120">
      </el-table-column>
      <el-table-column
        label="操作"
        >
        <template slot-scope="scope">
          <el-button type="primary" plain style="width: 75px" size="small" @click="editExpense(scope.$index, scope.row)">编辑</el-button>
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
      <el-form ref="editForm" :model="editForm" label-width="80px">
        <el-form-item label="项目名">
          <el-input v-model="editForm.name" placeholder="项目名"></el-input>
        </el-form-item>
        <el-form-item label="负责部门">
          <el-select v-model="editForm.departId" placeholder="请选择部门">
            <el-option v-for="item in departOption" :key="item.value" :value="item.value"
                       :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="editForm.principal" placeholder="项目负责人"></el-input>
        </el-form-item>
        <el-form-item label="收支类型">
          <el-select v-model="editForm.payType" placeholder="请选择">
            <el-option v-for="item in payTypeOption" :key="item.value" :value="item.value"
                       :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="项目状态">
          <el-select v-model="editForm.status" placeholder="请选择">
            <el-option v-for="item in itemStatusOption" :key="item.value" :value="item.value"
                       :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="所属行业">
          <el-select v-model="editForm.type" placeholder="请选择行业">
            <el-option v-for="item in typeOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="起止时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="起始日期" v-model="editForm.beginTime" value-format="yyyy-MM-dd"
                            style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">
            - ->
          </el-col>

          <el-col :span="11">
            <el-date-picker type="date" placeholder="结束日期" v-model="editForm.endTime" value-format="yyyy-MM-dd"
                            style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="成交金额">
          <el-input v-model="editForm.payment" placeholder="单位(元)"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="editForm.description"></el-input>
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
        form: [
          {
            id: '',
            name: '',
            beginTime: '',
            endTime: '',
            departId: '',
            principal: '',
            description: '',
            status: '',
            payment: '',
            type: '',
            payType: ''
          }
        ],
        editForm: {
          id: '',
          name: '',
          beginTime: '',
          endTime: '',
          departId: '',
          principal: '',
          description: '',
          status: '',
          payment: '',
          type: '',
          payType: ''
        },
        editFormVisible: false,
        sdate: '',
        edate: '',
        total: '',
        currentPage: 1,
        total: 0,
        pageSize: 5,
        departId: 5,
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
        ],
        typeOption: [
          {
            value: 0,
            label: '餐饮'
          },
          {
            value: 1,
            label: '制造'
          },
          {
            value: 2,
            label: '纺织'
          },
          {
            value: 3,
            label: '互联网'
          },
          {
            value: 4,
            label: '金融'
          }
        ],
        payTypeOption: [
          {
            value: 0,
            label: '收入'
          },
          {
            value: 1,
            label: '支出'
          }
        ],
        itemStatusOption: [
          {
            value: 0,
            label: '未进行'
          },
          {
            value: 1,
            label: '进行中'
          },
          {
            value: 3,
            label: '已完成'
          }
        ]
      }
    },
    methods: {
      getData() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8083/getItemList?currentPage=' + this.currentPage + '&pageSize=' + this.pageSize + '&beginDate=' + this.sdate + '&endDate=' + this.edate + "&departId=" + this.departId
        }).then((res) => {
          if (res.data.code == 0) {
            this.form = res.data.data.list
            this.total = res.data.data.total
          } else {
            alert("获取请假数据失败！")
          }
        }).catch(reason => {
          window.location.href="http://localhost:8083/tologin"
        })
      },
      searchData() {
        this.getData()
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
          url: baseUrl + '/updateItem',
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
        })
      },
      handleCancel() {
        this.editFormVisible = false
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

  #seach_form {
    margin-bottom: 50px;
  }

  #pagination {
    margin-top: 30px;
    margin-left: 200px;
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

</style>
