<template>
  <div>
    <yearConditon v-on:fromConditon="searchData"></yearConditon>
    <div id ="itemChar" style="width: 100%; height:600px"></div>
  </div>
</template>

<script>
  import yearConditon from '../../common/year_simple'
  require('echarts/map/js/china.js')
  export default {
    name: "year_map",
    components : {
      yearConditon
    },
    data(){
      return {
        yearCoditon:{
          sdate : ""
        },
        year : '',
        option :{

          legend: {
            top:'24%',
            orient: 'vertical',
            left : '0%',
            data :[]
          },
          tooltip:{
            trigger: 'axis',
            formatter :'{a} <br> {b} : {c}元'
          },
          title: [{
            x: '27%'
          }, {
            text: '部门贡献榜',
            top :'5%',
            right:'19%',
            textAlign: 'center'
          }, {
            text: '项目各行业占比',
            x: '73%',
            y: '46%',
            textAlign: 'center'
          }],
          grid: {
            left:'65%',
            bottom:'50%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            // boundaryGap: [0, 0.01],
            show:false
          },
          yAxis: {
           // show:false,
            type: 'category',
            axisLabel: {
              show: true //这行代码控制着坐标轴x轴的文字是否显示
            },
            axisLine: {
              show: false //这行代码控制着坐标轴x轴的文字是否显示
            },
            axisTick: {
              show: false //这行代码控制着坐标轴x轴的文字是否显示
            }
          },
          toolbox: {
            show: true,
            orient : 'vertical',
            left: '60%',
            top: 'center',
            feature : {
              mark : {show: true},
              dataView : {show: true, readOnly: false},
              restore : {show: true},
              saveAsImage : {show: true}
            }
          },
          series : [
            {
              name: '项目收入',
             type: 'map',
             mapType: 'china',
              left:'5%',
              zoom:0.8,
              //    layoutCenter: ['30%', '30%'],
         //      type: 'scatter',
         //      coordinateSystem: 'geo',
              itemStyle:{
                normal:{
                  label:{show:true},
                  areaStyle:{color:'red'}
                },
                emphasis:{
                  label:{show:true},
                  emphasis: {// 也是选中样式
                    borderWidth: 2,
                    borderColor: '#fff',
                    areaColor: 'red',
                    label: {
                      show: true,
                      textStyle: {
                        color: '#fff'
                      }
                    }
                  }
                }
              },
              tooltip:{
                show:true,
                trigger: 'item' ,
                formatter :'{a} <br> {b} : {c}元)'
              }
            },
            {
              type: 'bar',
              xAxisIndex :0,
              yAxisIndex:0,
              barWidth:'40%',
              itemStyle:{
                normal:{
                  color :'#DC143C'
                }
              },
              label: {
                show: true, //开启显示
                position: 'right', //在上方显示
                textStyle: { //数值样式
                  color: 'black',
                  fontSize: 16
                }
              },
              tooltip:{
                show:true,
                trigger: 'axis' ,
                formatter :'{a} <br> {b} : {c}元'
              }
              },
            {
              type: 'pie',
              radius: [60, 100],
              center: ['80%', '75%'],
              tooltip:{
                show:true,
                trigger: 'item' ,
                formatter :'{a} <br> {b} : {c}元({d}%)'
              }
           //   roseType : 'area',
           //    itemStyle: {
           //      emphasis: {
           //        shadowBlur: 10,
           //        shadowOffsetX: 0,
           //        shadowColor: 'rgba(0, 0, 0, 0.5)'
           //      },
           //      normal: {
           //        color: function (params) {
           //          //自定义颜色
           //          var colorList = [
           //            '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
           //            '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
           //            '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
           //          ];
           //          return colorList[params.dataIndex]
           //        }
           //      }}


            }

          ]
        }

      }
    },
    mounted() {
     this.getData()
    },
    methods :{
      getData(){
        this.axios({
          method :'get',
          url : 'http://localhost:8083/getMapForm?year='+this.yearCoditon.sdate
        }).then((res)=>{
          if(res.data.code ===0){
            var data = res.data.data
            var mychar = this.$echarts.init(document.getElementById("itemChar"))
            this.option.series[0].data = data.objList
            this.option.title[0].text = data.date+"年项目收入分布"
            this.option.series[0].name = data.date+"年"
            this.year = data.date
            this.option.legend.data[0]= data.date+"年"
            //填充部门贡献榜和饼状图
            this.option.series[1].name =data.date+"年"
            this.option.series[1].data = data.obj[0].nowValeList
            this.option.yAxis.data = data.obj[0].nowTimeList
            this.option.series[2].data = data.obj[0].objList[0]
            this.option.series[2].name =data.date+"年"
            mychar.setOption(this.option)
            mychar.on('click', (params) =>{
               this.checkMap(params.data.name)
            })
          }
        })
      },
      searchData (data){
        this.yearCoditon = data
        this.getData()
      },
      checkMap(name){
        this.axios({
          method :'get',
          url : 'http://localhost:8083/getDetailMapForm?year='+this.year+'&area='+name
        }).then((res)=>{
          if(res.data.code ===0){
            var data = res.data.data
            this.option.series[1].name =data.area+':'+data.date
            this.option.series[1].data = data.nowValeList
            this.option.yAxis.data = data.nowTimeList
            this.option.series[2].data = data.objList[0]
            this.option.series[2].name =data.area+':'+data.date
            var mychar = this.$echarts.getInstanceByDom(document.getElementById("itemChar"))
            mychar.setOption(this.option)
          }
        })
      }
    }
  }

</script>

<style scoped>

</style>
