<template>
  <div>
    <h4>员工入职</h4>
    <el-form ref="form" :model="form" label-width="80px" style="width: 600px">
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="姓名" ></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" placeholder="密码" type="password" ></el-input>
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="form.departId" placeholder="请选择部门">
          <el-option v-for="item in departOption" :key="item.value" :value="item.value" :label="item.label"></el-option>

        </el-select>
      </el-form-item>
      <el-form-item label="手机号">
      <el-input v-model="form.phone" placeholder="手机号" ></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱">
        <el-input v-model="form.email" placeholder="电子邮箱" ></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio v-for="item in sexOption" :key="item.id" :label="item.id">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>


      <el-form-item label="职位">
        <el-select v-model="form.post" placeholder="请选择职位类型">
          <el-option v-for="item in postOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker
          v-model="form.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="入职时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="出生日期">
        <el-date-picker
          v-model="form.birthdate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="出生日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createUser">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "user_add",
    data() {
      return {
        form: {
          name: '',
          departId: '',
          createTime: '',
          birthdate: '',
          sex: 1,
          password: '',
          post :'',
          email:'',
          phone : ''
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
        postOption:[
          {
            value : 0,
            label : '一般职员'
          },
          {
            value : 1,
            label : '部门主管'
          },
          {
            value : 2,
            label : '副总经理'
          },
          {
            value : 3,
            label : '总经理'
          }
        ],
        sexOption:[
          {
            id : 0,
            name : '女'
          },
       {
         id : 1,
           name : '男'
       }
        ]
      }
    },
    methods: {
      createUser(){
        var datas = this.$qs.stringify(this.form)
        this.axios({
          method : 'post',
          url : 'http://localhost:8083/sigin',
          data : datas,
          headers:{
            'Content-type': 'application/x-www-form-urlencoded'
          }
        }).then(function (res) {
          if(res.data.code == 0){
            alert("创建成功！")
          }else{
            alert(res.data.data.msg+"已存在！")
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
