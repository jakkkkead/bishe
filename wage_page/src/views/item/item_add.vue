<template>
  <div>
    <h4>添加项目</h4>
    <el-form ref="form" :model="form" label-width="80px" style="width: 600px">
      <el-form-item label="项目名" >
        <el-input v-model="form.name" placeholder="项目名"></el-input>
      </el-form-item>
      <el-form-item label="负责部门">
        <el-select v-model="form.departId" placeholder="请选择部门">
          <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="负责人">
        <el-input v-model="form.principal" placeholder="项目负责人"></el-input>
      </el-form-item>
      <el-form-item label="收支类型">
        <el-select v-model="form.payType" placeholder="请选择">
          <el-option v-for="item in payTypeOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="所属行业">
        <el-select v-model="form.type" placeholder="请选择行业">
          <el-option v-for="item in typeOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="起止时间">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="起始日期" v-model="form.beginTime"  value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">
          -   ->
        </el-col>

        <el-col :span="11">
          <el-date-picker type="date" placeholder="结束日期" v-model="form.endTime"  value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="成交金额">
        <el-input v-model="form.payment" placeholder="单位(元)"></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createItem">立即创建</el-button>
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
          beginTime: '',
          endTime: '',
          departId: '',
          principal: '',
          description: '',
          status: 0,
          payment: '',
          type:'',
          payType: ''
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
        typeOption:[
          {
            value : 0,
            label : '餐饮'
          },
          {
            value : 1,
            label : '制造'
          },
          {
            value : 2,
            label : '纺织'
          },
          {
            value : 3,
            label : '互联网'
          },
          {
            value : 4,
            label : '金融'
          }
        ],
        payTypeOption:[
          {
            value : 0,
            label : '收入'
          },
          {
            value : 1,
            label : '支出'
          }
        ]
      }
    },
    methods: {
      createItem() {
        var datas = this.$qs.stringify(this.form)
        this.axios({
          method : 'post',
          url : 'http://localhost:8083/createItem',
          data : datas,
          headers:{
            'Content-type': 'application/x-www-form-urlencoded'
          }
        }).then(function (res) {
          if(res.data.code == 0){
            alert("创建成功！")
          }else{
            alert("创建失败！")
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
