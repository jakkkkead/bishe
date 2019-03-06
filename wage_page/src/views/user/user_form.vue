<template>
  <div>
    <yearConditon v-on:fromConditon="searchData"></yearConditon>
    <div id ="userChar" style="width: 100%; height:500px"></div>
    <div id="table">
      <el-table
        :data="tableData"
        border
        style="width:900px">
        <el-table-column
          prop="enterTotal"
          label="入职总数"
          width="148px">
        </el-table-column>
        <el-table-column
          prop="leaveTotal"
          label="离职总数"
          width="150px">
        </el-table-column>
        <el-table-column
          prop="total"
          label="净增人数"
          width="150px">

        </el-table-column>
        <el-table-column
          prop="totalInc"
          label="月平均净增数"
          width="150px">
        </el-table-column>
        <el-table-column
          width="150px"
          prop="enterInc"
          label="月平均入职数">
        </el-table-column>
        <el-table-column
          width="150px"
          prop="leaveInc"
          label="月平均离职数">
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>

<script>
  import yearConditon from '../../common/year_select'
    export default {
        name: "user_form",
        components : {
          yearConditon
        },
        data(){
          return {
            yearCoditon:{
              sdate : "" ,
              departId :""
            },
            tableData:[{
              leaveTotal:0,
              enterTotal:0,
              total:0,
              totalInc:'0.00%',
              enterInc:'0.00%',
              leaveInc:'0.00%'
            }],
            option :{

                tooltip : {
                  trigger: 'axis',
                  axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'cross'        // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  left:'50%'
                },
                grid: {
                  top :'10%',
                  left: '3%',
                  right: '40%',
                  bottom: '1%',
                  containLabel: true
                },
                xAxis : [
                  {
                    type : 'value',
                    name :'人数（人）'
                  }
                ],
                yAxis : [
                  {
                    type : 'category',
                    axisTick : {show: true},
                    data : []
                  }
                ],
                series : [
                  {
                    name:'入职',
                    type:'bar',
                    label: {
                      normal: {
                        show: true,
                        position: 'inside'
                      }
                    },
                    data:[]
                  },
                  {
                    name:'离职',
                    type:'bar',
                    stack: '',
                    label: {
                      normal: {
                        show: true
                      }
                    },
                    data:[]
                  },
                  {
                    name: '在职人数',
                    type: 'pie',
                    radius : ['21%','26%'],
                    center: ['80%', '20%'],
                    hoverAnimation:false,
                    data:[
                      {value:0, name:'在职人数'}
                    ],
                    label: {
                      normal: {
                        show:true,
                        position:'center',
                        formatter:'{b} : {c}',
                        textStyle: {
                          fontSize: 15,
                          color: '#235894'
                        }
                      }
                    }
                  },
                  {
                    name: '净增人数',
                    type: 'pie',
                    radius : ['21%','26%'],
                    center: ['80%', '50%'],
                    hoverAnimation:false,
                    data:[
                      {value:0, name:'净增人数'}
                    ],
                    label: {
                      normal: {
                        show:true,
                        position:'center',
                        formatter:'{b} : {c}',
                        textStyle: {
                          fontSize: 15,
                          color: '#235894'
                        }
                      }
                    }
                  },
                  {
                    name: '离职率',
                    type: 'pie',
                    radius : ['21%','26%'],
                    center: ['80%', '80%'],
                    hoverAnimation:false,
                    data:[
                      {value:21, name:'离职率'}
                    ],
                    label: {
                      normal: {
                        show:true,
                        position:'center',
                        formatter:'{b} : {c}%',
                        textStyle: {
                          fontSize: 14,
                          color: '#235894'
                        }
                      }
                    }
                  }
                ]
              }
            }
          },
      mounted() {
          this.getData()

      },
      methods : {
          getData(){
            this.axios({
              method : 'get',
              url : 'http://localhost:8083/getUserForm?year='+this.yearCoditon.sdate+'&departId='+this.yearCoditon.departId
            }).then((res) =>{
                if(res.data.code ==0){
                  let myChar = this.$echarts.init(document.getElementById("userChar"))
                    this.option.series[0].data = res.data.data.nowValeList
                  this.option.series[1].data = res.data.data.lastValeList
                  this.option.yAxis[0].data = res.data.data.nowTimeList
                  this.getTableData(res.data.data.nowValeList,res.data.data.lastValeList,res.data.data.value)
                  myChar.setOption(this.option)
                }
            })
          },
        searchData (data){
          this.yearCoditon = data
          this.getData()
        },
        getTableData(enterData,leaveData,total){
        //    console.log(total)
          var enter=0
          var leave = 0
            for(var i =0 ; i<enterData.length;i++){
              enter=enterData[i]+enter
              leave = leaveData[i]+leave//负数
            }
          this.tableData[0].enterTotal =enter
          this. tableData[0].leaveTotal =leave
          this. tableData[0].total = enter+leave
          this.tableData[0].enterInc = ( enter/12).toFixed(2)
          this.tableData[0].leaveInc = (-leave/12).toFixed(2)
          this.tableData[0].totalInc =((this. tableData[0].total/12)).toFixed(2)
          this.option.series[2].data[0].value = total
          this.option.series[3].data[0].value = this. tableData[0].total
          this.option.series[4].data[0].value = ((leave/-total)*100).toFixed(2)

        }
      }

    }
</script>

<style scoped>
  #table{
    margin-top: 20px;
  }

</style>
