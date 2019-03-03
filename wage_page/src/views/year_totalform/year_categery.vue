<template>
  <div>
    <yearSimple v-on:fromConditon="searchData"></yearSimple>
    <div id ="itemChar" style="width: 100%; height:600px"></div>
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
              tooltip: {},
              legend: {
                top:'3%',
                right: '5%'
              },
              radar: {
                radius :'70%',
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
              series: [{
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
                ]
              }]
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
        }
      },
      mounted() {
        this.getData()
      }
    }
</script>

<style scoped>

</style>
