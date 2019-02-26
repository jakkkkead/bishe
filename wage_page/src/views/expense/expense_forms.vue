<template>
  <div>
    <Condition v-on:fromConditon="searchData"></Condition>
    <div id="forms" style="width: 100%; height:1200px">


    </div>
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
            option : {

              dataset:{
                  source :[]
              },
              legend: {
                top : 30,
                bottom : 20
              },
              title : {
                  text : '费用报销',
                  top : 30,
                 height: 100

              },
              tooltip : {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross'
                }
              },
              grid: [  {x: '7%', y: '8%', width: '40%', height: '30%'},
                       ],
              xAxis : [
                {
                  name : '费用类型',
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
                  center: ['65%', '20%'],
                  radius: '20%',
                  tooltip: {
                    trigger :'item',
                    formatter: "{a} <br/>{b} : ({d}%)"},
                  // tooltip :{
                  //   trigger :'item',
                  //   formatter: "{a} <br/>{b} : {c} ({d}%)"
                  // },
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

        // this.axios({
        //       method : 'get',
        //       url : 'http://localhost:8083/getExpenseForms'
        // }).then((res) =>{
        //     if(res.data.code ==0){
        //       let myEchars = this.$echarts.init(document.getElementById("forms"))
        //       // alert("klklkl")
        //        this.option.dataset.source = res.data.data.pieList
        //       this.option.series[0].name =res.data.data.date+"月"
        //       myEchars.setOption(this.option)
        //
        //     }
        // })
        this.getData()
      },
      methods :{
        searchData (data){
          // this.option.dataset.source = data.pieList
          // this.option.series[0].name =data.date+"月"
          // let myEchars = this.$echarts.init(document.getElementById("forms"))
          // myEchars.setOption(this.option)
          this.monthConditon = data
          this.getData()
         },
        getData(){
          this.axios({
            method : 'get',
            url : 'http://localhost:8083/getExpenseForms?dayTime='+this.monthConditon.sdate+'&departId='+this.monthConditon.departId
          }).then((res) =>{
            if(res.data.code ==0){
              let myEchars = this.$echarts.init(document.getElementById("forms"))
              // alert("klklkl")
              this.option.dataset.source = res.data.data.pieList
              this.option.series[0].name =res.data.data.date+"月"
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
