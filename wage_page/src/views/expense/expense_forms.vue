<template>
  <div>
    <Condition v-on:fromConditon="searchData"></Condition>
    <div id="forms" style="width: 100%; height:500px">
    </div>
    <div><el-table
      :data="tableData"
      border
      style="width:900px">
      <el-table-column
        prop="nowTotal"
        label="费用总计(元)"
        width="148px">
      </el-table-column>
      <el-table-column
        prop="aveDepart"
        label="部门平均(元)"
        width="150px">
      </el-table-column>
      <el-table-column
        prop="aveDay"
        label="日平均(元)"
        width="200px">

      </el-table-column>
      <el-table-column
        prop="lastMonthTotal"
        label="上月总计(元)"
        width="200px">
      </el-table-column>
      <el-table-column
        width="200px"
        prop="createInc"
        label="同比上月">
      </el-table-column>
    </el-table></div>
  </div>
</template>

<script>
  import Condition from '../../common/month_select'
    export default {
        name: "expense_forms",
      components : { Condition },
      data(){
          return {
            monthConditon:{
              sdate : "" ,
              departId :""
            },
            tableData:[
              {
                nowTotal:0,
                aveDepart:0,
                aveDay:0,
                lastMonthTotal:0,
                createInc:'0.00%'
              }
            ],
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
      mounted() {
        this.getData()
      },
      methods :{
        searchData (data){
          this.monthConditon = data
          this.getData()
         },
        setTableData(data,last){
          var nowTotal =0
          console.log(data)
          for (var i =0 ;i<data.length;i++){
             nowTotal = nowTotal+data[i].money
          }
          console.log(nowTotal)
          this.tableData[0].nowTotal = nowTotal
          this.tableData[0].aveDepart = (nowTotal/12).toFixed(2)
          this.tableData[0].aveDay = (nowTotal/30).toFixed(2)
          this.tableData[0].lastMonthTotal = last
          if(last != 0){
            this.tableData[0].createInc =((nowTotal-last)/last*100).toFixed(2)+"%"
          }else{
            this.tableData[0].createInc = "0.00%"
          }

        },
        getData(){
          this.axios({
            method : 'get',
            url : 'http://localhost:8083/getExpenseForms?dayTime='+this.monthConditon.sdate+'&departId='+this.monthConditon.departId
          }).then((res) =>{
            if(res.data.code ==0){
              let myEchars = this.$echarts.init(document.getElementById("forms"))
              this.option.dataset.source = res.data.data.pieList
              this.option.series[0].name =res.data.data.date+"月"
              this.setTableData(res.data.data.pieList,res.data.data.value)
              this.option.series[2].name =res.data.data.date+"月"
              myEchars.setOption(this.option)

            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
