<template>
  <div>
    <monthConditon v-on:fromConditon="searchData"></monthConditon>
    <div id ="wageChar" style="width: 100%; height:500px"></div>
  </div>
</template>

<script>
  import monthConditon from '../../common/month_select'

  var waterMarkText = 'ECHARTS';

  var canvas = document.createElement('canvas');
  var ctx = canvas.getContext('2d');
  canvas.width = canvas.height = 100;
  ctx.textAlign = 'center';
  ctx.textBaseline = 'middle';
  ctx.globalAlpha = 0.08;
  ctx.font = '20px Microsoft Yahei';
  ctx.translate(50, 50);
  ctx.rotate(-Math.PI / 4);
  ctx.fillText(waterMarkText, 0, 0);

  export default {
      name: "expense_view",
      components: {
        monthConditon
      },
      data() {
        return {
          monthConditon: {
            sdate: "",
            departId: ""
          },
          option: {
            backgroundColor: {
              type: 'pattern',
              image: canvas,
              repeat: 'repeat'
            },
            tooltip: {},
            title: [{
              text: '工资组成',
              top :'10%'
            }, {
              text: '类目占比',
              x: '75%',
              top :'9%',
              textAlign: 'center'
            }
            ],
            grid: [{
              top: '20%',
              width: '50%',
              bottom: '45%',
              left: 10,
              containLabel: true,
            }],
            xAxis: {
              type: 'value',
            //  max: builderJson.all,
              splitLine: {
                show: false
              }
            }
            ,
            yAxis: {
              type: 'category',
              axisLabel: {
                interval: 0,
                rotate: 20
              },
              splitLine: {
                show: false
              }
            },
            series: [{
              type: 'bar',
              stack: 'chart',
              tooltip:{
                formatter :'{a} <br> {b} : {c} 元'
              },
              z: 3,
              label: {
                normal: {
                  position: 'right',
                  show: true
                }
              }
            },
              {
              name :'2019-01',
              type: 'pie',
              radius: [0, '30%'],
              center: ['75%', '35%'],
                tooltip:{
                    formatter :'{a} <br> {b} : {c} ({d}%)'
                }
              }
            ]
          }
        }
      },
          methods:{
            searchData (data){
              this.monthConditon = data
              this.getData()
            },
          getData()
          {
            this.axios({
              method :'get',
              url : 'http://localhost:8083/getWageForm?month='+this.monthConditon.sdate+"&departId="+this.monthConditon.departId
            }).then((res)=>{
              if(res.data.code ===0){
                var data = res.data.data
                var departName = this.getDepartName(this.monthConditon.departId)
                let mychar = this.$echarts.init(document.getElementById("wageChar"))
                this.option.series[0].name =departName+":"+ data.date
                this.option.series[1].name = departName+":"+data.date
                this.option.yAxis.data = Object.keys(data.obj[0])
                this.option.series[0].data = Object.keys(data.obj[0]).map(function (key) {
                  return data.obj[0][key];
                })
                this.option.series[1].data = Object.keys(data.obj[1]).map(function (key) {
                  return {
                    name: key,
                    value: data.obj[1][key]
                  }
                })

                mychar.setOption(this.option)
              }
            })
          },
            getDepartName(val){
              switch (val) {
                case 0 :
                  return "研发部";
                case 1 :
                  return "财务部";
                case 2 :
                  return "人力资源部";
                case 3 :
                  return "采购部";
                case 4 :
                  return "事业部";
                case 5:
                  return "所有部门"
                default :
                  return "所有部门";
              }
            }
        },
      mounted() {
        this.getData()
      }
    }

</script>

<style scoped>

</style>
