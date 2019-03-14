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
      <el-form-item label="区域">
        <el-select v-model="form.area" placeholder="请选择">
          <el-option v-for="item in areaOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

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
          status: 2,
          payment: '',
          type:'',
          area:'',
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
        areaOption:[
          {
            value :'宁夏',
            label : '宁夏'
          },
          {
            value :'西藏',
            label : '西藏'
          },
          {
            value :'广东',
            label : '广东'
          },
          {
            value :'福建',
            label : '福建'
          },
          {
            value :'吉林',
            label : '吉林'
          },
          {
            value :'内蒙古',
            label : '内蒙古'
          },
          {
            value :'甘肃',
            label : '甘肃'
          },
          {
            value :'湖北',
            label : '湖北'
          },
          {
            value :'江西',
            label : '江西'
          },
          {
            value :'浙江',
            label : '浙江'
          },
          {
            value :'江苏',
            label : '江苏'
          },
          {
            value :'新疆',
            label : '新疆'
          },
          {
            value :'山东',
            label : '山东'
          },
          {
            value :'安徽',
            label : '安徽'
          },
          {
            value :'湖南',
            label : '湖南'
          },
          {
            value :'黑龙江',
            label : '黑龙江'
          },
          {
            value :'辽宁',
            label : '辽宁'
          },
          {
            value :'云南',
            label : '云南'
          },
          {
            value :'河南',
            label : '河南'
          },
          {
            value :'河北',
            label : '河北'
          },
          {
            value :'重庆',
            label : '重庆'
          },
          {
            value :'上海',
            label : '上海'
          },
          {
            value :'北京',
            label : '北京'
          },
          {
            value :'天津',
            label : '天津'
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
          },
          {
            value : 5,
            label : '其他'
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
