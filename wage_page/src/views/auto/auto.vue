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
              legend: {
                top : 30,
                right:'6%',
                bottom : 20
              },
              title : {
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
                  name :'类目',
                  type : 'category',
                  axisTick: {
                    alignWithLabel: true
                  },
                  gridIndex : 0
                }
              ],
              yAxis : [
                {
                  type : 'value',
                  gridIndex : 0
                }
              ],
              series : [
                {
                  type:'bar',
                  barWidth: '30%',
                  // itemStyle :{
                  //   color : '#2894FF'
                  // }
                },
                {
                  name : '类目占比',
                  type : 'pie',
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
        checkFileType(file) {
          var fileTypes = new Array("xls","xlsx");
          var fileTypeFlag = "0";
          var selectedFile  = file.name
          var filetype = selectedFile .substring(selectedFile .indexOf('.') + 1, selectedFile .length)
          console.log(filetype)
          for (var i = 0; i < fileTypes.length; i++) {
            if (fileTypes[i].indexOf(filetype) !=-1) {
              fileTypeFlag = "1";
            }
          }
          if (fileTypeFlag == "0") {
            return
          }
        },
        beforeUpload(file){
          this.checkFileType(file)
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
              var typeOptin = new Object()
              typeOptin.value = -1;
              typeOptin.label = "默认"
              options[typeList.length] = typeOptin
              this.headTypes = options
              //设置分割线
              this.createLine(typeList.length)

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
              var data = res.data.data
              let myEchars = this.$echarts.init(document.getElementById("autoWage"))
              if(data.type =='map'){
                  this.mapData(data)
              }else{
                  this.listData(data)
              }
              myEchars.setOption(this.option)
            }else{
              alert(res.data.msg)
            }
          })
        },
        //封装map类型数据
        mapData(data){
         // this.option.yAxis.data = Object.keys(data.obj[0])
          this.option.xAxis[0].name = data.xname
          this.option.yAxis[0].name = data.yname
          this.option.series[0].name =data.xname
          console.log(data)
          this.option.xAxis[0].data = Object.keys(data.objects[0])
          this.option.series[0].data = Object.keys(data.objects[0]).map(function (key) {
            return data.objects[0][key];
          })
          this.option.series[1].data = Object.keys(data.objects[0]).map(function (key) {
            return {
              name: key,
              value: data.objects[0][key]
            }
          })
          console.log(this.option.series[1].data)
        },
        //封装list类型数据
        listData(data){
          this.option.xAxis[0].data = data.objects[0]
          this.option.series[0].data = data.objects[1]
          var options = new Array() ;
          for(var i=0;i<data.objects[0].length;i++){
            var pie = new Object()
            pie.value = data.objects[1][i];
            pie.name = data.objects[0][i]
            options[i] = pie
          }
          this.option.series[1].data = options
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
