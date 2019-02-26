<template>
  <div>
  <yearCondition v-on:fromConditon="searchData"></yearCondition>
  <div id="yearChars" style="width: 100%; height:1200px">


  </div>
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
                top : '10%',
                left: '3%',
                right: '50%',
                bottom: '60%',
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
                  center: ['75%', '20%'],
                  encode: {
                    itemName :'typeName',
                    value : 'money'
                  },
                  radius: '20%',
                  tooltip: {
                    trigger :'item',
                    // formatter: "{a} <br/>{b} :{@[1]} {d}%"
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
