<template>
  <div>
    <div id="nowConditionDiv">
    <yearSimple v-on:fromConditon="searchData"></yearSimple></div>
    <div id="lastConditonDiv">
    <lastYear v-on:fromConditon="searchLastData"></lastYear>
    </div>

    <div id="itemChar" style="width: 100%; height:700px"></div>
  </div>
</template>

<script>
  import yearSimple from '../../common/year_simple'
  import lastYear from '../../common/last_year_simple'
  export default {
    name: "year_wage",
    components: {
      yearSimple,lastYear
    },
    data() {
      return {
        yearCoditon: {
          sdate: ""
        },
        lastYearConditon:{
          sdate: ""
        },
        option: {
          title: {
            x: 'center',
            top:'1%'
          },
          legend: {
            // data:[],
            x: 'left',
            top: '5%'
          },
          tooltip: {
            trigger: 'axis'
          },
          toolbox: {
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              restore: {},
              saveAsImage: {}
            }
          },
          axisPointer: {
            link: {xAxisIndex: 'all'}
          },
          grid: [{
            left: '5%',
            top: '15%',
            right: '55%',
            height: '35%'
          }, {
            left: '5%',
            right: '55%',
            top: '57%',
            height: '35%'
          }],
          xAxis: [
            {
              type: 'category',
              data:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
              // boundaryGap : false,
              // axisLine: {onZero: true}

            },
            {
              gridIndex: 1,
              type: 'category',
              data:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
              // boundaryGap : false,
              // axisLine: {onZero: true},
              position: 'top'
            }
          ],
          yAxis: [
            {
              name: '收入(百万元)'

            },
            {
              gridIndex: 1,
              name: '支出(百万元)',
              inverse: true

            }
          ],
          series: [
            {
              name: '收入',
              type: 'line',
              symbolSize: 8,
              data: [],
              tooltip: {
                show: true,
                formatter: '{a} <br> {b} : {c}'
              },
              smooth: true

            },
            {
              name: '支出',
              type: 'line',
              xAxisIndex: 1,
              yAxisIndex: 1,
              symbolSize: 8,
              data: [],
              smooth: true,
              tooltip: {
                show: true,
                formatter: '{a} <br> {b} : {c}'
              }
            },
            {
              name: '支出部门占比',
              type: 'pie',
              radius: '20%',
              center: ['80%', '32%'],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br> {b} : {c}元({d}%)'

              }
            },
            {
              name: '收入部门占比',
              type: 'pie',
              radius: '20%',
              center: ['80%', '78%'],
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br> {b} : {c}元({d}%)'
              }
            },
            {
              name: '同期收入',
              type: 'line',
              symbolSize: 8,
              data: [],
              tooltip: {
                show: true,
                formatter: '{a} <br> {b} : {c}'
              },
              smooth: true

            },
            {
              name: '同期支出',
              type: 'line',
              xAxisIndex: 1,
              yAxisIndex: 1,
              symbolSize: 8,
              data: [],
              smooth: true,
              tooltip: {
                show: true,
                formatter: '{a} <br> {b} : {c}'
              }
            }
          ]
        }

      }
    },
    mounted() {
      this.getData()

    },
    methods: {
      getData() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8083/getYearWageForm?year=' + this.yearCoditon.sdate
        }).then((res) => {
          if (res.data.code === 0) {
            var data = res.data.data
            let mychar = this.$echarts.init(document.getElementById("itemChar"))
            // console.log(data[0])
            this.formaterData(data[0])
            this.formaterData(data[2])
            this.option.series[0].data = data[0]
            this.option.series[1].data = data[2]
            this.option.series[2].data = data[1]
            this.option.series[3].data = data[3]
            this.option.series[0].name = data[5]+"年收入"
            this.option.series[1].name = data[5]+"年支出"
            // this.option.xAxis[0].data = data[4]
            // this.option.xAxis[1].data = data[4]
            this.option.title.text = data[5] + '年收入支出统计'
            mychar.setOption(this.option)
          }
        })
      },
      getLastData() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8083/getYearWageForm?year=' + this.lastYearConditon.sdate
        }).then((res) => {
          if (res.data.code === 0) {
            var data = res.data.data
            let mychar = this.$echarts.init(document.getElementById("itemChar"))
            // console.log(data[0])
            this.formaterData(data[0])
            this.formaterData(data[2])
            this.option.series[4].data = data[0]
            this.option.series[5].data = data[2]
            this.option.series[4].name = data[5]+"年收入"
            this.option.series[5].name = data[5]+"年支出"
            mychar.setOption(this.option)
          }
        })
      },
      searchData(data) {
        this.yearCoditon = data
        this.getData()
      },
      //搜索同期数据时间
      searchLastData(data) {
        this.lastYearConditon = data
        this.getLastData()
      },
      formaterData(data) {
        for (var i = 0; i < data.length; i++) {
          data[i] = data[i] / 1000000
        }
      }
    }
  }

</script>

<style scoped>
  #nowConditionDiv{
    display: inline-block;
  }
  #lastConditonDiv{
    margin-right: 40%;
    float: right;
  }

</style>
