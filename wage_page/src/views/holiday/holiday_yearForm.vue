<template>
  <div>
    <yearCondition v-on:fromConditon="searchData"></yearCondition>
    <div id="forms" style="width: 100%; height:1200px">
    </div>
  </div>
</template>

<script>
   import yearCondition from '../../common/year_select'
    export default {
        name: "holiday_yearForm",
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
              tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                  type : 'cross'        // 默认为直线，可选为：'line' | 'shadow'
                }
              },
              title: {
                top : '4%',
                text: '请假图表'
              },
              legend: {
                top : '7%',
                right: '10%'
              //  data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
              },
              grid: {
                top : '10%',
                left: '3%',
                right: '10%',
                bottom: '55%',
                containLabel: true
              },
              xAxis : [
                {
                  type : 'category',
                  data : []
                }
              ],
              yAxis : [
                {
                  type : 'value',
                  name : '天数'
                }
              ],
              series : [
                {
                  name:'事假',
                  type:'bar',
                  data:[],
                  markLine : {
                    lineStyle: {
                      normal: {
                        type: 'dashed'
                      }
                    },
                    data : [
                      // [{type : 'min'}, {type : 'max'}]
                      {
                        name: '平均线',
                        // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                },
                {
                  name:'婚假',
                  type:'bar',
                  // stack: '广告',
                  data:[],
                  markLine : {
                    lineStyle: {
                      normal: {
                        type: 'dashed'
                      }
                    },
                    data : [
                      // [{type : 'min'}, {type : 'max'}]
                      {
                        name: '平均线',
                        // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                },
                {
                  name:'产假',
                  type:'bar',
                  // stack: '广告',
                  data:[],
                  markLine : {
                    lineStyle: {
                      normal: {
                        type: 'dashed'
                      }
                    },
                    data : [
                      // [{type : 'min'}, {type : 'max'}]
                      {
                        name: '平均线',
                        // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                },
                {
                  name:'年假',
                  type:'bar',
                  // stack: '广告',
                  data:[],
                  markLine : {
                    lineStyle: {
                      normal: {
                        type: 'dashed'
                      }
                    },
                    data : [
                      // [{type : 'min'}, {type : 'max'}]
                      {
                        name: '平均线',
                        // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                },
                {
                  name:'病假',
                  type:'bar',
                  data:[],
                  markLine : {
                        lineStyle: {
                          normal: {
                            type: 'dashed'
                          }
                        },
                        data : [
                          // [{type : 'min'}, {type : 'max'}]
                          {
                            name: '平均线',
                            // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                }
              ]
            }
          }
        },
      methods :{
          getData(){
            this.axios({
              method : 'get',
              url : 'http://localhost:8083/getHolidayForms?year='+this.yearCoditon.sdate+'&departId='+this.yearCoditon.departId
            }).then((res) =>{
              if(res.data.code ==0){
                let myEchars = this.$echarts.init(document.getElementById("forms"))
                var i =0;
                // this.option.series[0].data
                for(i; i< 5 ; i++){
                  this.option.series[i].data = res.data.data.obj[i]
                }
                this.option.xAxis[0].data =res.data.data.nowTimeList
                console.log((this.option.xAxis.data))
                myEchars.setOption(this.option)
              }
            })
          },
        searchData (data){
            this.yearCoditon = data
            this.getData()
        }
      },
      mounted() {
        this.getData()
        // this.axios({
        //   method : 'get',
        //   url : 'http://localhost:8083/getHolidayForms?year='+this.yearCoditon.sdate+'&departId='+this.yearCoditon.departId
        // }).then((res) =>{
        //   if(res.data.code ==0){
        //     let myEchars = this.$echarts.init(document.getElementById("forms"))
        //     var i =0;
        //     // this.option.series[0].data
        //     for(i; i< 5 ; i++){
        //       this.option.series[i].data = res.data.data.obj[i]
        //     }
        //     this.option.xAxis[0].data =res.data.data.nowTimeList
        //     console.log((this.option.xAxis.data))
        //     myEchars.setOption(this.option)
        //   }
        // })
      }
    }
</script>

<style scoped>

</style>
