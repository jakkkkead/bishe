<template>
  <div>
    <yearConditon v-on:fromConditon="searchData"></yearConditon>
    <div id ="userChar" style="width: 100%; height:400px"></div>
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
          label="净增率"
          width="150px">
        </el-table-column>
        <el-table-column
          width="150px"
          prop="enterInc"
          label="入职率">
        </el-table-column>
        <el-table-column
          width="150px"
          prop="leaveInc"
          label="离职率">
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
                  data:['入职', '离职'],
                  right :'10%'
                },
                grid: {
                  top :'5%',
                  left: '3%',
                  right: '10%',
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
                  this.getTableData(res.data.data.nowValeList,res.data.data.lastValeList)
                  myChar.setOption(this.option)
                }
            })
          },
        searchData (data){
          this.yearCoditon = data
          this.getData()
        },
        getTableData(enterData,leaveData){
          var enter=0
          var leave = 0
            for(var i =0 ; i<enterData.length;i++){
              enter=enterData[i]+enter
              leave = leaveData[i]+leave
            }
          this.tableData[0].enterTotal =enter
          this. tableData[0].leaveTotal =leave
          this. tableData[0].total = enter+leave
          this.tableData[0].enterInc = (( enter/12)*100).toFixed(2)+"%"
          this.tableData[0].leaveInc = ((leave/-12)*100).toFixed(2)+"%"
          this.tableData[0].totalInc =((this. tableData[0].total/12)*100).toFixed(2)+"%"
          console.log(this.tableData[0])

        }
      }

    }
</script>

<style scoped>
  #table{
    margin-top: 20px;
  }

</style>
