<template>
  <div>
    <yearSimple v-on:fromConditon="searchData"></yearSimple>
    <div id ="itemChar" style="width: 100%; height:800px">
      <div style="border: black;width: 324px"></div>
    </div>
    <div >
      <el-table
      :data="tableData"
      border
      style="width:900px">
      <el-table-column
        prop="name"
        width="148px">
      </el-table-column>
      <el-table-column
        prop="item"
        label="项目"
        width="150px">
      </el-table-column>
      <el-table-column
        prop="wage"
        label="工资"
        width="200px">

      </el-table-column>
      <el-table-column
        prop="expense"
        label="报销"
        width="200px">
      </el-table-column>
      <el-table-column
        width="200px"
        prop="total"
        label="年利润">
      </el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script>
  import yearSimple from '../../common/year_simple'
    export default {
        name: "year_categery",
      components :{
        yearSimple
      },
      data(){
          return {
            yearCoditon:{
              sdate : ""
            },
            tableData:[
              {
                name :'',
                item :0,
                wage :0,
                expense :0,
                total :0
              }
              ],
            option :{
              title: {
                top:'3%'
              },
              tooltip: {
                // trigger:'item'
                // trigger:'axis'
              },
              legend: {
                top:'3%',
                right: '20%'
              },
              grid: [{
                top: '10%',
                width: '40%',
                bottom: '65%',
                left: '50%',
                containLabel: true
              },
                {
                top: '40%',
                width: '40%',
                left: '50%',
                bottom: '35%',
                containLabel: true
              },
                {
                  top: '70%',
                  width: '40%',
                  left: '50%',
                  containLabel: true
                }
                ],
              xAxis: [{
                gridIndex: 0,
                splitLine: {
                  show: false
                },
                data:[]
              }, {
                gridIndex: 1,
                splitLine: {
                  show: false
                },
               data:[]
              },
                {
                  gridIndex: 2,
                  splitLine: {
                    show: false
                  },
                  data:[]
                }],
              yAxis: [{
                gridIndex: 0,
                axisLabel: {
                  interval: 0,
                  rotate: 30
                },
                splitLine: {
                  show: false
                }
              }, {
                gridIndex: 1,
                axisLabel: {
                  interval: 0,
                  rotate: 30
                },
                splitLine: {
                  show: false
                }
              },
                {
                  gridIndex: 2,
                  axisLabel: {
                    interval: 0,
                    rotate: 30
                  },
                  splitLine: {
                    show: false
                  }
                }],
              radar: {
                radius :'35%',
                center: ['22%', '35%'],
              //  shape: 'circle',
                name: {
                  textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                  }
                },
                indicator: [
                  { name: '工资费用'},
                  { name: '报销费用'},
                  { name: '项目收入'},
                  { name: '年利润'}

                ]
              },
              series: [
                {
                name: '2019',
                type: 'radar',
                areaStyle: {normal: {}},
                data : [
                  {
                    value : []

                  },
                  {
                    value : [],
                    name : '去年同期(元)'
                  }
                ],
                  // tooltip: {
                  //   trigger:'item',
                  //   formatter:'{a} <br> {b}:{c}元'
                  // }
              },
                {
                  name:'费用报销',
                  type:'line',
                  xAxisIndex:0,
                  yAxisIndex:0,
                  data:[],
                  itemStyle : { normal: {label : {show: true}}}
                  // tooltip: {
                  //   trigger:'axis',
                  //   formatter:'{a} <br> {b}:{c}元'
                  // }
                },
                {
                  name:'工资',
                  type:'line',
                  xAxisIndex:1,
                  yAxisIndex:1,
                  data:[],
                  itemStyle : { normal: {label : {show: true}}}
                  // tooltip: {
                  //   trigger:'axis',
                  //    formatter:'{a} <br> {b}:{c}元'
                  //  }
                },
                {
                  name:'项目',
                  type:'line',
                  xAxisIndex:2,
                  yAxisIndex:2,
                  data:[],
                  itemStyle : { normal: {label : {show: true}}}
                },
                {
                  name: '业务指标',
                  radius:'39%',
                  center : ['22%', '80%'],
                  max:300000,
                  min:-300000,
                  type: 'gauge',
                  detail: {formatter:'{value}%'},
                  data: [{value: 300002, name: '完成率'},{value: -20, name: '成交率'}]
                }


              ]
            }
          }
      },
      methods :{
        searchData (data){
          this.yearCoditon = data
          this.getData()
        },
        getData(){
          this.axios({
            method :'get',
            url : 'http://localhost:8083/getYearDetailForm?year='+this.yearCoditon.sdate
          }).then((res)=>{
            if(res.data.code ===0){
              var data = res.data.data
              let mychar = this.$echarts.init(document.getElementById("itemChar"))
              this.option.series[0].data[0].value=data.nowValeList
              this.option.series[0].data[0].name=data.date+"年(元）"
              this.option.title.text = data.date + "年财务明细对比"
              this.getTableData(data.objList)
              this.option.series[0].data[1].value =data.lastValeList
              this.setLineData(data.obj)
              mychar.setOption(this.option)
            }
          })
        },
        getTableData(data){
          this.tableData[0].name = data[0]
          this.tableData[0].wage = data[1]
          this.tableData[0].expense = data[2]
          this.tableData[0].item = data[3]
          this.tableData[0].total = data[4]
        },
          setLineData(data){
          console.log(data)
           for(var i =0 ; i< data.length;i++){
             if(i == 1 ){
               this.option.series[2].data = Object.keys(data[i]).map(function (key) {
                 return data[i][key];
               })
               this.option.xAxis[i].data = Object.keys(data[i])
             }else{
               console.log(i)
               for(var y =0 ;y< data[i].length;y++){
                 //console.log("name",data[i][y].name)
             //    debugger
                 this.option.series[i+1].data[y] = data[i][y].value
                 this.option.xAxis[i].data[y] = data[i][y].name
               }
              console.log(this.option.series[i+1].data)
                console.log(this.option.xAxis[i].data)
             }


           }

          }
      },
      mounted() {
        this.getData()
      }
    }
</script>

<style scoped>

</style>
