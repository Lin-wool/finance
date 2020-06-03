<template>
  <div class="container">
    <el-container style=" border: 1px solid #eee" class="container">
      <el-aside width="200px"  style="background-color: rgb(238, 241, 246) ; height: 100%">
        <el-header style="text-align: center; font-size: 20px">
          <span>刷单管理</span>
        </el-header>
        <el-menu router :default-active="$route.path">
          <!-- <el-menu-item v-for="item in $router.options.routes" :key="item.path" :index="item.path" >{{item.name}}</el-menu-item> -->
          <el-menu-item index="/index" >首页</el-menu-item>
          <el-menu-item index="/order" >订单管理</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <span>账号信息</span>
        </el-header>
        <el-main style="height:100%">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style>
  .container{
    position: absolute;
    top: 0px;
    bottom: 0px;
    width: 100%;
    height: 100%;
  }
  .el-header {
    background-color: #b3c0d1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>


<script>
export default {
  data() {
    const item = {
      date: "2016-05-02",
      name: "王小虎",
      address: "上海市普陀区金沙江路 1518 弄"
    };
    return {
      tableData: Array(20).fill(item)
    };
  }
};
</script>