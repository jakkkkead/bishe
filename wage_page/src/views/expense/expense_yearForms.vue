<template>
  <div>
  <yearCondition v-on:fromConditon="searchData"></yearCondition>
  <div id="yearChars" style="width: 100%; height:500px">
  </div>
    <div>
      <el-table
        :data="tableData"
        border
        style="width:900px">
        <el-table-column
          prop="nowTotal"
          label="费用总计(元)"
          width="148px">
        </el-table-column>
        <el-table-column
          prop="lastTotal"
          label="去年同期总计(元)"
          width="150px">
        </el-table-column>
        <el-table-column
          prop="aveDepart"
          label="部门平均(元)"
          width="200px">
        </el-table-column>
        <el-table-column
          prop="aveMonth"
          label="月平均(元)"
          width="200px">
        </el-table-column>
        <el-table-column
          width="200px"
          prop="createInc"
          label="同比去年">
        </el-table-column>
      </el-table></div>
  </div>
</template>

<script>
  import yearCondition from '../../common/year_select'
    export default {
        name: "expense_yearForms",
      components :{
        yearCondition
      },
      data(){
          return {
            yearCoditon:{
              sdate : "" ,
              departId :""
            },
            tableData:[
              {
                nowTotal:0,
                aveDepart:0,
                aveMonth:0,
                lastTotal:0,
                createInc:'0.00%'
              }
            ],
            option :{

              dataset:{
                dimensions: ['typeName','money'],
                source :[]
              },
              title: {
                top : '4%',
                text: '年度报销费用图'
              },
              tooltip: {
                 trigger: 'axis'
              },
              legend: {
                top:'5%'
                //data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
              },
              grid: {
                top : '18%',
                left: '3%',
                right: '50%',
                containLabel: true
              },
              xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
              },
              yAxis: {
                type: 'value',
                name :'金额(元)'
              },
              series: [
                {
                  type:'line',
                  data:[],
                  tooltip:{
                    show : true,
                    formatter :'{a} <br> {b} : {c}'
                  }
                  // lable :{
                  //   show : true
                  // },
                  // tooltip: {
                  //    trigger: 'axis',
                  //   axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                  //     type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  //   }
                  // }
                },
                {
                  name :'去年同期',
                  type:'line',
                  data:[]
                },{
                  name :'类目占比',
                  type : 'pie',
                  radius:'50%',
                  center: ['75%', '50%'],
                  encode: {
                    itemName :'typeName',
                    value : 'money'
                  },
                  tooltip:{
                    trigger: 'item',
                    formatter :'{a} <br> {b}:{d}%)'

                  },
                  label: {
                    show : true,
                    positon : 'outside'
                  },
                  labelLine: {
                    show : true
                  },
                  itemStyle: {
                    emphasis: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
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
      methods :{
        searchData (data){
           this.yearCoditon = data
          this.getData()
           },
        setTableData(now,last){
           var nowTotal =0
          var lastTotal = 0
          var  createInc
          for (var i =0 ;i<now.length ; i++){
             nowTotal = nowTotal + now[i]
             lastTotal = lastTotal + last[i]
          }
          console.log(nowTotal)
          console.log(nowTotal)
          this.tableData[0].nowTotal =nowTotal
          this.tableData[0].lastTotal =lastTotal
          this.tableData[0].aveMonth = (nowTotal/12).toFixed(2)
          this.tableData[0].aveDepart = (nowTotal/5).toFixed(2)
          if(lastTotal !=0){
             createInc = ((nowTotal-lastTotal)*100).toFixed(2)+'%'
          }else {
            createInc = '0.00%'
          }

          this.tableData[0].createInc = createInc
          console.log(this.tableData[0])
        },
        getData(){
          this.axios({
            method : 'post',
            url : 'http://localhost:8083/getYearExpenseForms?year='+this.yearCoditon.sdate+'&departId='+this.yearCoditon.departId
          }).then((res) =>{
            if(res.data.code ==0){
              let yearChars = this.$echarts.init(document.getElementById("yearChars"))
              this.option.series[0].data =res.data.data.nowValeList
              this.option.series[0].name = res.data.data.date;
              this.option.series[1].data =res.data.data.lastValeList
              this.option.series[2].name = res.data.data.date
              this.setTableData(res.data.data.nowValeList,res.data.data.lastValeList)
              this.option.dataset.source = res.data.data.pieList
              yearChars.setOption(this.option)

            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
