<template>
  <div>
    <h4>查看请假条</h4>
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
    style="width: 80%">
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
      width="118">
      <template slot-scope="scope">
        <el-button type="primary" plain style="width: 90px" size="small" @click="editHoliday(scope.$index, scope.row)">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
    <div id="pagination">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
        :total="total"
        :current-page="currentPage"
        :page-size="pageSize">
      </el-pagination>
    </div>
    <el-dialog title="编辑"
               :visible.sync="editFormVisible"
               :close-on-click-modal="false"
               class="edit-form"
    >
      <el-form ref="editForm" :model="editForm" label-width="80px">
        <el-form-item label="申请人">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <el-select v-model="editForm.departId" placeholder="请选择部门">
            <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="假期类型">
          <el-select v-model="editForm.type" placeholder="请选择假期类型">
            <el-option v-for="item in holidayTypeOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请状态">
          <el-select v-model="editForm.status" placeholder="请选择状态">
            <el-option v-for="item in statusOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起止时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="起始日期" v-model="editForm.beginTime" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">
            -   ->
          </el-col>

          <el-col :span="11">
            <el-date-picker type="date" placeholder="结束日期" v-model="editForm.endTime" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
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
          editFormVisible: false,
          tableData2: [{
            holidayId: '',
            name: '',
            departId: '',
            beginTime: '',
            endTime: '',
            type: '',
            remark: '',
            status: ''
          }],
          sdate: '',
          edate: '',
          total: 10,
          currentPage: 1,
          pageSize: 5,
          editForm: {
            holidayId: '',
            name: '',
            departId: '',
            beginTime: '',
            endTime: '',
            type: '',
            remark: '',
            status: ''
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
          holidayTypeOption: [
            {
              value: 0,
              label: '事假'
            },
            {
              value: 1,
              label: '婚假'
            },
            {
              value: 2,
              label: '产假'
            },
            {
              value: 3,
              label: '年假'
            },
            {
              value: 4,
              label: '病假'
            }
          ],
          statusOption: [
            {
              value: 0,
              label: '申请中'
            },
            {
              value: 1,
              label: '同意'
            },
            {
              value: 2,
              label: '拒绝'
            }
          ]
        }
      },
      methods: {
        getData() {
          this.axios({
            method: 'get',
            url: 'http://localhost:8083/getHolidayList?currentPage=' + this.currentPage + '&pageSize=' + this.pageSize + '&beginDate=' + this.sdate + '&endDate=' + this.edate
          }).then((res) => {
            if (res.data.code == 0) {
              this.tableData2 = res.data.data.list
              this.total = res.data.data.total
            } else {
              alert("获取请假数据失败！")
            }
          })
        },
        editHoliday(index, row) {
          this.editFormVisible = true;
          this.editForm = Object.assign({}, row);
        },
        searchData() {
          this.getData()
        },
        handleUpdate() {
          var baseUrl = 'http://localhost:8083'
          var datas = this.$qs.stringify(this.editForm)
          this.axios({
            method: 'post',
            data: datas,
            url: baseUrl + '/updateHoliday',
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
  #seach_form{
    margin-bottom: 50px;
  }
  #pagination{
    margin-top: 30px;
    margin-left: 200px;
  }

</style>
