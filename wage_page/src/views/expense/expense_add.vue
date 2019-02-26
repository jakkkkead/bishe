<template>
    <div>

      <h4>申请报销单</h4>
      <el-form ref="form" :model="form" label-width="80px" style="width: 600px">
        <el-form-item label="申请人">
          <el-input v-model="form.createName"></el-input>
        </el-form-item>
        <el-form-item label="部门">
            <el-select v-model="form.departId" placeholder="申请人所在部门">
              <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
            </el-select>

        </el-form-item>
        <el-form-item label="收款人">
          <el-input v-model="form.receiverName"></el-input>
        </el-form-item>
        <el-form-item label="银行账号">
        <el-input v-model="form.accountNumber"></el-input>
        </el-form-item>
        <el-form-item label="报销金额">
          <el-input v-model="form.money"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="form.payway" placeholder="请选择收付方式">
            <el-option v-for="item in payWayOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费用类型">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option v-for="item in typeOption" :value="item.value" :label="item.label" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起止时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="起始日期" v-model="form.beginTime" style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">
             -   ->
          </el-col>

          <el-col :span="11">
            <el-date-picker type="date" placeholder="结束日期" v-model="form.endTime" style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.remark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
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
            createName: '',
            receiverName: '',
            accountNumber: '',
            money: 0,
            departId:'',
            payway:0,
            beginTime: '',
            endTime: '',
            remark: '',
            type :0
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
          ],
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
      methods: {
        onSubmit() {
          var datas = this.$qs.stringify(this.form)
          this.axios({
            method : 'post',
            data : datas,
            url : 'http://localhost:8083/addExpense',
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
          console.log('submit!');
        }
      }
    }
</script>

<style scoped>

</style>
