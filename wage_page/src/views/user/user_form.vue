<template>
  <div>
    <yearConditon v-on:fromConditon="searchData"></yearConditon>
    <div id ="userChar" style="width: 100%; height:1200px"></div>
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
                  bottom: '55%',
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
                  myChar.setOption(this.option)
                }
            })
          },
        searchData (data){
          this.yearCoditon = data
          this.getData()
        }
      }

    }
</script>

<style scoped>

</style>
