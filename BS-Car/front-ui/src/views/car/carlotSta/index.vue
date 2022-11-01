<template>
  <div class="app-container">
<!--  条件查询表单  -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="130">
      <el-form-item label="请选择您想监控的停车场" prop="carparkId">
        <el-select v-model="queryParams.carparkId" @change="changeQuery" placeholder="请选择停车场名">
          <el-option
            v-for="item in carparkList"
            :key="item.carparkId"
            :label="item.carparkName"
            :value="item.carparkId"
          ></el-option>
        </el-select>
      </el-form-item>

    </el-form>

    <div align="center" v-show="showChart">
      <h2>当前停车场内车位状态统计数据</h2>
    </div>

    <!-- 1.定义一片echarts区域用于图表显示 -->
    <div class="chart-container" v-show="showChart">
      <!--饼状图-->
      <span id="chart" class="chart" style="width:700px;height:400px;float: left"/>
      <!--折线图-->
      <span id="lineChart" class="chart" style="width:400px;height:400px;float: right">
      </span>
    </div>


  </div>
</template>

<script>
//导入Api
import { listCarpark } from "@/api/car/carpark"
import { getChart } from '@/api/car/carlotSta'
//2.引入echarts
import echarts from 'echarts'

export default {
  dicts: ['car_status_type'],
  data() {
    return {
      queryParams: {   //条件查询条件
        carparkId: null,
      },
      showChart: false,  //chart图表显示开关
      carparkList: [],  //停车场数据
      chartList: [],   //用于封装后端查到的数据，用于下面的图表展示
    }
  },
  created() {   //在页面加载前执行
    //查询所有停车场数据
    this.getCarparkList();
  },
  mounted() {
    //3.页面渲染之后就调用方法显示ECharts图表(如果是放在created()中，有可能会出现报错(报错信息Cannot read properties of null (reading‘getAttribute‘)),
    // 造成报错的原因是因为echarts的图形容器还未生成就对其进行了初始化)
    // this.getChart()
  },
  methods:{
    /**  查询停车场相关数据 */
    getCarparkList(){
      listCarpark().then(response => {
        this.carparkList = response.rows;
      });
    },
    //下拉列表change事件
    changeQuery(){
      // alert("++++++++++" + this.queryParams.carparkId)
      getChart(this.queryParams.carparkId).then(response => {
          // console.log("-----------" + response.data)   //输出-----------0,0,2
        //将查到的数据赋给this.chartList用于数据展示
        this.chartList = response.data
        //打开Chart开关
        this.showChart = true
        this.getChart()
      })
    },
    //显示Chart图表
    getChart(){
      // 4.基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('chart'));
      var myChart2 = echarts.init(document.getElementById('lineChart'));
      //5.指定图表的配置项和数据
      var option = {
        title: {
          text: '饼状图',
          subtext: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '车位状态',
            type: 'pie',
            radius: '70%',
            data: [
              { name: '未用', value: this.chartList[0] },
              { name: '已用', value: this.chartList[1] },
              { name: '损坏', value: this.chartList[2] },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      var option2 = {
        title: {  //图表标题
          text: '柱状图'
        },
        tooltip: {  //x坐标轴触发提示(鼠标放在图表上有提示)
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: ['未用','已用','损坏']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [
              {
                value: this.chartList[0],
                itemStyle: {
                  color: '#00FF7F'
                }
              },
              {
                value: this.chartList[1],
                itemStyle: {
                  color: '#79CDCD'
                }
              },
              {
                value: this.chartList[2],
                itemStyle: {
                  color: '#CD5555'
                }
              },
            ],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      };

      //6.使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      myChart2.setOption(option2)
    }
  }
}
</script>
