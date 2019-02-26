<template>
  <div>
    <h4>申请假条</h4>
    <el-form ref="form" :model="form" label-width="80px" style="width: 600px">
      <el-form-item label="申请人">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="form.departId" placeholder="请选择部门">
          <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="假期类型">
        <el-select v-model="form.type" placeholder="请选择假期类型">
          <el-option v-for="item in holidayTypeOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="起止时间">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="起始日期" v-model="form.beginTime" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">
          -   ->
        </el-col>

        <el-col :span="11">
          <el-date-picker type="date" placeholder="结束日期" v-model="form.endTime" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createHoliday">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "expense_add",
    data() {
      return {
        form: {
          name: '',
          departId: '',
          beginTime: '',
          endTime: '',
          type: '',
          remark: ''
        },
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
        ],
        holidayTypeOption:[
          {
            value : 0,
            label : '事假'
          },
          {
            value : 1,
            label : '婚假'
          },
          {
            value : 2,
            label : '产假'
          },
          {
            value : 3,
            label : '年假'
          },
          {
            value : 4,
            label : '病假'
          }
        ]
      }
    },
    methods: {
      createHoliday(){
        var datas = this.$qs.stringify(this.form)
          this.axios({
            method : 'post',
            url : 'http://localhost:8083/createHoliday',
            data : datas,
            headers:{
              'Content-type': 'application/x-www-form-urlencoded'
            }
          }).then(function (res) {
              if(res.data.code == 0){
                alert("申请成功！")
              }else{
                alert("申请失败！")
              }
          })
      }
    }
  }
</script>

<style scoped>

</style>
