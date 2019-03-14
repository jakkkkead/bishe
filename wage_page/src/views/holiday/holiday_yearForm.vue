<template>
  <div>
    <yearCondition v-on:fromConditon="searchData"></yearCondition>
    <div id="forms" style="width: 100%; height:500px">
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
                top : '4%'
              },
              legend: {
                top : '7%',
                right: '10%'
              //  data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
              },
              grid: {
                top : '20%',
                left: '1%',
                right: '30%',
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
              dataZoom: [
                {
                  type: 'slider',
                  show: true,
                  xAxisIndex: [0],
                  filterMode: 'empty',
                  height: 8,
                  handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                  handleSize: 20
                },
                {
                  type: 'slider',
                  show: true,
                  yAxisIndex: [0],
                  filterMode: 'empty',
                  right:'25%',
                  width:8,
                  handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                  handleSize: 20
                },
                {
                  type: 'inside',
                  xAxisIndex: [0],
                  filterMode: 'empty',
                  height: 8
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
                        name: '事假',
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
                        name: '婚假',
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
                        name: '产假',
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
                        name: '年假',
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
                            name: '病假',
                            // 支持 'average', 'min', 'max'
                        type: 'average'

                      }
                    ]
                  }
                },
                {
                  name: '总请假数',
                  type: 'pie',
                  radius : ['23%','28%'],
                  center: ['87%', '35%'],
                  hoverAnimation:false,
                  data:[
                    {value:0, name:'总请假数'}
                  ],
                  label: {
                    normal: {
                      show:true,
                      position:'center',
                      formatter:'{b}: {c}天',
                      textStyle: {
                        fontSize: 14,
                        color: '#235894'
                      }
                    }
                  }
                },
                {
                  name: '月平均数',
                  type: 'pie',
                  radius : ['23%','28%'],
                  center: ['87%', '70%'],
                  hoverAnimation:false,
                  data:[
                    {value:0, name:'月平均数'}
                  ],
                  label: {
                    normal: {
                      show:true,
                      position:'center',
                      formatter:'{b}: {c}天',
                      textStyle: {
                        fontSize: 14,
                        color: '#235894'
                      }
                    }
                  }
                },
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
                this.option.title.text = res.data.data.date+"年请假图表"
                var i =0;
                var total=0
                var avg =0
                for(i; i< 5 ; i++){
                  this.option.series[i].data = res.data.data.obj[i]
                  console.log(this.option.series[i].data)
                  //计算总请假天数
                   for(var y=0 ;y<res.data.data.obj[i].length;y++){
                     total = total+res.data.data.obj[i][y].value
                   }
                }
                avg = (total/12).toFixed(1)
                this.option.series[5].data[0].value = total
                this.option.series[6].data[0].value = avg
                this.option.xAxis[0].data =res.data.data.nowTimeList
           //     console.log((this.option.xAxis.data))
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
      }
    }
</script>

<style scoped>

</style>
