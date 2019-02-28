<template>
  <div>
    <yearConditon v-on:fromConditon="searchData" ></yearConditon>
    <div id ="itemChar" style="width: 100%; height:700px"></div>
  </div>
</template>

<script>
  import yearConditon from '../../common/year_select'
    export default {
        name: "item_form",
      components : {
        yearConditon
      },
      data(){
          return {
            yearCoditon:{
              sdate : "" ,
              departId :""
            },
            option:{
              title:{
                 top: '5%'
              },
            tooltip: {
              trigger: 'item',
              formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
              orient: 'vertical',
              x: 'left',
              data:['未进行','进行中','已完成','餐饮','制造','纺织','互联网','金融','其他'],
              top:'10%'
            },
              grid:{
                top : '10%'
              },
            series: [
              {
                name:'项目进度(销售额:元)',
                type:'pie',
                selectedMode: 'single',
                radius: [0, '20%'],

                label: {
                  normal: {
                    position: 'inner'
                  }
                },
                labelLine: {
                  normal: {
                    show: false
                  }
                },
                data:[
                ]
              },
              {
                name:'项目收入(元)',
                type:'pie',
                radius: ['30%', '40%'],
                label: {
                  normal: {
                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                    backgroundColor: '#eee',
                    borderColor: '#aaa',
                    borderWidth: 1,
                    borderRadius: 4,
                    // shadowBlur:3,
                    // shadowOffsetX: 2,
                    // shadowOffsetY: 2,
                    // shadowColor: '#999',
                    // padding: [0, 7],
                    rich: {
                      a: {
                        color: '#999',
                        lineHeight: 10,
                        align: 'center'
                      },
                      // abg: {
                      //     backgroundColor: '#333',
                      //     width: '100%',
                      //     align: 'right',
                      //     height: 22,
                      //     borderRadius: [4, 4, 0, 0]
                      // },
                      hr: {
                        borderColor: '#aaa',
                        width: '100%',
                        borderWidth: 0.5,
                        height: 0
                      },
                      b: {
                        fontSize: 16,
                        lineHeight: 15
                      },
                      per: {
                        color: '#eee',
                        backgroundColor: '#334455',
                        padding: [2, 4],
                        borderRadius: 2
                      }
                    }
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
      methods:{
          getData(){
            this.axios({
              method :'get',
              url : 'http://localhost:8083/getItemForm?year='+this.yearCoditon.sdate+"&departId="+this.yearCoditon.departId
            }).then((res)=>{
              if(res.data.code ===0){
                let mychar = this.$echarts.init(document.getElementById("itemChar"))
                  var data = res.data.data
                  this.option.series[0].data=data.obj[0]
                this.option.series[1].data=data.obj[1]
                this.option.title.text = data.date+'项目信息'
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
