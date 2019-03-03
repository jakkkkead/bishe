<template>
  <div>
    <yearConditon v-on:fromConditon="searchData"></yearConditon>
    <div id ="itemChar" style="width: 100%; height:600px"></div>
  </div>
</template>

<script>
  import yearConditon from '../../common/year_select'
  require('echarts/map/js/china.js')
  export default {
    name: "year_map",
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
          title : {
            top : '4%',
            left: 'center'
          },
          tooltip : {
            formatter : '{a} <br> {b} : {c}'
          },
          legend: {
            top:'24%',
            orient: 'vertical',
            left : '0%',
            data :[]
          },
          visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            bottom : '10%',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
          },
          toolbox: {
            show: true,
            orient : 'vertical',
            left: 'right',
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
              }
              // tooltip:{
              //     formatter:
              // }
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
            let mychar = this.$echarts.init(document.getElementById("itemChar"))
            this.option.series[0].data = data.objList
            this.option.title.text = data.date+"年项目收入分布"
            this.option.series[0].name = data.date+"年"
            this.option.legend.data[0]= data.date+"年"
            mychar.setOption(this.option)
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
