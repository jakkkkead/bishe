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
        <el-select v-model="selectLine" placeholder="请选择X轴" style="width: 10%">
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
        类型
        <el-select v-model="valueType" placeholder="类型"  style="width: 10%">
          <el-option v-for="item in valueTypes" :key="item.value" :value="item.value" :label="item.label"></el-option>
        </el-select>
        <el-button  size="small" type="primary" @click="selectXY">确认</el-button>
    </div>
      <div id ="autoWage" style="width: 100%; height:500px"></div>
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
            valueTypes:[
              {
                value: 0,
                label: '总和'
              },
              {
                value: 1,
                label: '最大值'
              },
              {
                value: 2,
                label: '平均值'
              },
              {
                value: 3,
                label: '最小值'
              },
            ],
            lineOptions:[
              {
                value: -1,
                label:-1
              }
            ],
            x:-1,
            y:-1,
            valueType:0,
            selectLine:-1,
            fileId:'',
            option : {
              // legend: {
              //   top : 30,
              //   right:'6%',
              //   bottom : 20
              // },
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
                  top:'18%',
                  left:'7%',
                  right:'45%'
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
              dataZoom: [
                {
                  type: 'slider',
                  show: true,
                  xAxisIndex: [0],
                  filterMode: 'empty',
                  height: 8,
                  handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                  handleSize: 20
                },
                {
                  type: 'slider',
                  show: true,
                  yAxisIndex: [0],
                  filterMode: 'empty',
                  right:'39%',
                  width:8,
                  handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                  handleSize: 20
                },
                {
                  type: 'inside',
                  xAxisIndex: [0],
                  filterMode: 'empty',
                  height: 8
                }

              ],
              toolbox: {
                show: true,
                // orient : 'vertical',
                left: '4%',
                top: '4%',
                feature : {
                  mark : {show: true},
                  dataView : {show: true, readOnly: false},
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
              },
              series : [
                {
                  type:'bar',
                  barMaxWidth:'15%',
                  label: {
                    show: true, //开启显示
                    position: 'top', //在上方显示
                    textStyle: { //数值样式
                      color: 'black',
                      fontSize: 16
                    }
                  }
                  // itemStyle :{
                  //   color : '#2894FF'
                  // }
                },
                {
                  name : '类目占比',
                  type : 'pie',
                  center: ['80%', '50%'],
                  radius: '28%',
                  tooltip: {
                    trigger :'item',
                    formatter: "{a} <br/>{b} : ({d}%)"},
                  label: {
                    show : true,
                    position : 'inside'
                  }
                  // labelLine: {
                  //   show : false
                  // }

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
            url: 'http://localhost:8082/getExcelForm?fileId='+this.fileId+'&index='+this.selectLine+'&x='+this.x+'&y='+this.y+"&valueType="+this.valueType,
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
