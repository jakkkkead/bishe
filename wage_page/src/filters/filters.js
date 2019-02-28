//申请状态过滤器
const statusFilter = function (val) {
  switch (val) {
    case 0 : return "申请中";
    case 2 : return "拒绝";
    case 1 : return "同意";
    default :
      return "申请中";
  }
}
//费用类型
const payTypeFilter = function (val) {
  switch (val) {
    case 0 : return "差旅";
    case 1 : return "交通";
    case 2 : return "日常";
    default :
      return "其他";
  }
}
//支付方式
const paywayFilter = function (val) {
  switch (val) {
    case 0 : return "网银";
    case 1 : return "现金";
    default: return "网银";
  }
}
//假期类型
const holidayTypeFilter = function(val){
  switch (val) {
    case 0 : return "事假";
    case 1 : return "婚假";
    case 2 : return "产假";
    case 3 : return "年假";
    case 4 : return "病假";
    default :
      return "事假";
  }
}
//工资发放状态
const wageStatusFilter = function(val){
  switch (val) {
    case 0 : return "未发";
    case 1 : return "已发";
    default :
      return "未发";
  }
}
//项目进度
const itemStatusFilter = function(val){
  switch (val) {
    case 0 : return "未进行";
    case 1 : return "进行中";
    case 2 : return "已完成";
    default :
      return "未进行";
  }
}
//项目收支类型
const itemPaytypeFilter = function(val){
  switch (val) {
    case 0 : return "收入";
    case 1 : return "支出";
    default :
      return "收入";
  }
}
//项目所属行业
const itemTypeFilter = function (val) {
  switch (val) {
    case 0 :
      return "餐饮";
    case 1 :
      return "制造";
    case 2 :
      return "纺织";
    case 3 :
      return "互联网";
    case 4 :
      return "金融";
    case 5 :
      return "其他";
    default :
      return "餐饮";
  }
}
//部门类型
const departFilter = function (val) {
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
    default :
      return "研发部";
  }
}
//员工在职状态
const userStatusFilter = function(val){
  switch (val) {
    case 0 :
      return "离职";
    case 1 :
      return "在职";
  }
}
//员工职位
const userPostFilter = function (val) {
  switch (val) {
    case 0 :
      return "一般职员";
    case 1 :
      return "部门主管";
    case 2 :
      return "副总经理";
    case 3 :
      return "总经理";
  }
}
const sexFilter = function (val) {
  switch (val) {
    case 0 :
      return "女";
    case 1 :
      return "男";
  }
}

export default {
  statusFilter,payTypeFilter,paywayFilter,holidayTypeFilter,departFilter,wageStatusFilter,itemStatusFilter,itemPaytypeFilter,itemTypeFilter,
  userStatusFilter,userPostFilter,sexFilter
}
