<template>
    <div>
      <div>
      <el-upload id="uploaddiv"
        class="upload-demo"
        ref="upload"
        name ="file"
        :before-upload="beforeUpload"
        action="test"
        :auto-upload="true"
        :on-sucess="subSucess"
      >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
        数据分割线
        <el-select v-model="selectLine" placeholder="请选择X轴">
          <el-option v-for="item in lineOptions" :key="item.value" :value="item.value" :label="item.value"></el-option>
        </el-select>
        X轴:
      <el-select v-model="x" placeholder="请选择X轴">
        <el-option v-for="item in headTypes" :key="item.value" :value="item.value" :label="item.label"></el-option>
      </el-select>
        y轴
      <el-select v-model="y" placeholder="请选择y轴">
        <el-option v-for="item in headTypes" :key="item.value" :value="item.value" :label="item.label"></el-option>
      </el-select>
        <el-button  size="small" type="primary" @click="selectXY">确认</el-button>
    </div>
      <div id ="autoWage" style="width: 100%; height:600px"></div>
    </div>
</template>

<script>
    export default {
        name: "auto",
        data(){
          return {
            headTypes:[
              {
                value: -1,
                label:'默认'
              }
              ],
            lineOptions:[
              {
                value: -1,
                label:-1
              }
            ],
            x:-1,
            y:-1,
            selectLine:-1,
            fileId:'',
            option : {

              dataset:{
                source :[]
              },
              legend: {
                top : 30,
                right:'6%',
                bottom : 20
              },
              title : {
                text : '费用报销',
                top : '2%',
                height: 100

              },
              tooltip : {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: [
                {
                  top:'15%',
                  left:'7%',
                  right:'50%'
                }
              ],
              xAxis : [
                {
                  name : '类型',
                  type : 'category',
                  axisTick: {
                    alignWithLabel: true
                  },
                  gridIndex : 0
                }
              ],
              yAxis : [
                {
                  name :'金额(元)',
                  type : 'value',
                  gridIndex : 0
                }
              ],
              series : [
                {
                  name:'报销金额',
                  type:'bar',
                  barWidth: '30%',
                  encode: {
                    // 将 "amount" 列映射到 X 轴。
                    x: 'typeName',
                    // 将 "product" 列映射到 Y 轴。
                    y: 'money'
                  },
                  itemStyle :{
                    color : '#2894FF'
                  }
                },{

                  type:'line',
                  tooltip :{
                    show : false
                  },
                  encode: {
                    // 将 "amount" 列映射到 X 轴。
                    x: 'typeName',
                    // 将 "product" 列映射到 Y 轴。
                    y: 'money'
                  }
                },
                {
                  name : '类目占比',
                  type : 'pie',
                  encode: {
                    itemName :'typeName',
                    value : 'money'
                  },
                  center: ['75%', '50%'],
                  radius: '35%',
                  tooltip: {
                    trigger :'item',
                    formatter: "{a} <br/>{b} : ({d}%)"},
                  label: {
                    show : true,
                    positon : 'outside'
                  },
                  labelLine: {
                    show : true
                  }

                }
              ]
            }
          }
        },
      methods:{
        subSucess(response){
             console.log(response)
        },
        createLine(size){
          var options = new Array() ;
          for(var i =0 ;i<size; i++ ){
            var typeOptin = new Object()
            typeOptin.value = i
            typeOptin.label = i
            options[i] = typeOptin
          }
          console.log(options)
          this.lineOptions = options
        },
        beforeUpload(file){
          let fd = new FormData();
          fd.append('file',file);//传文件
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/uploadExcel',
            data : fd
          }).then((res) => {
            if(res.data.code === 0){
              alert("文件上传成功，请选择坐标")
              var typeList = res.data.data.objects[0]
              this.fileId = res.data.data.id
              var options = new Array() ;
              for(var i =0 ;i<typeList.length; i++ ){
                var typeOptin = new Object()
                typeOptin.value = i;
                typeOptin.label = typeList[i]
                options[i] = typeOptin
              }
              this.createLine(typeList.length)
              this.headTypes = options
            }else{
              alert(res.data.msg)
            }
          })

        },
        selectXY(){
          this.axios({
            method: 'get',
            url: 'http://localhost:8082/getExcelForm?fileId='+this.fileId+'&index='+this.selectLine+'&x='+this.x+'&y='+this.y,
          }).then((res) => {
            if(res.data.code === 0){
              let myEchars = this.$echarts.init(document.getElementById("autoWage"))

            }else{
              alert(res.data.msg)
            }
          })
        }

      }
    }
</script>

<style scoped>
  #uploaddiv {
    margin-left: 5px;
    margin-bottom: 30px;
  }
</style>
