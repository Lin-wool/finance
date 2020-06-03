<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model.trim="filters.searchOrderId" placeholder="请输入订单编号"></el-input>
        </el-form-item>

        <el-form-item>
          <el-input v-model.trim="filters.searchProductName" placeholder="请输入产品名称"></el-input>
        </el-form-item>

        <el-form-item>
          <el-input v-model.trim="filters.searchAsin" placeholder="请输入产品AISN"></el-input>
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="filters.searchOrderDate"
            placeholder="请选择订单日期"
            auto-complete="off"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="filters.searchRebateDate"
            placeholder="请选择返款日期"
            auto-complete="off"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="search" :loading="$store.state.isLoading">查询</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleAdd" :loading="$store.state.isLoading">新增订单</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!-- 列表 -->
    <el-table
      :data="orders"
      highlight-current-row
      @selection-change="handleSelectionChange"
      border
      style="width: 100%"
      :loading="$store.state.isLoading"
    >
      <el-table-column type="selection" prop="id"></el-table-column>
      <el-table-column prop="orderId" label="订单编号"></el-table-column>
      <el-table-column prop="productName" label="产品名称"></el-table-column>
      <el-table-column prop="asin" label="产品ASIN"></el-table-column>
      <el-table-column prop="price" label="产品价格"></el-table-column>
      <el-table-column prop="commission" label="产品佣金"></el-table-column>
      <el-table-column prop="receive" label="卖家返款"></el-table-column>
      <el-table-column prop="rebate" label="返款买家"></el-table-column>
      <el-table-column prop="orderDate" label="订单时间"></el-table-column>
      <el-table-column prop="rebateDate" label="返款时间"></el-table-column>
      <el-table-column prop="description" label="订单详情"></el-table-column>
      <el-table-column label="操作" width="140px;">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="primary" size="small" @click="handleDelete(scope.row)">删除</el-button>
            <el-button type="primary" size="small" @click="handeEdit(scope.row)">修改</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增 -->
    <el-dialog title="新增订单" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model.trim="addForm.orderId" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model.trim="addForm.productName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品AISN" prop="asin">
          <el-input v-model.trim="addForm.asin" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品价格" prop="price">
          <el-input v-model.trim="addForm.price" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品佣金" prop="commission">
          <el-input v-model.trim="addForm.commission" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="卖家返款" prop="receive">
          <el-input v-model.trim="addForm.receive" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="返款买家" prop="rebate">
          <el-input v-model.trim="addForm.rebate" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单日期" prop="orderDate" style="text-align:left">
          <el-date-picker
            v-model.trim="addForm.orderDate"
            placeholder="订单日期"
            auto-complete="off"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="返款日期" prop="rebateDate" style="text-align:left">
          <el-date-picker
            v-model.trim="addForm.rebateDate"
            placeholder="返款日期"
            auto-complete="off"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="订单详情" prop="description">
          <el-input v-model.trim="addForm.description" auto-complete="off"></el-input>
        </el-form-item>
        <div>
          <el-button @click.native="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="addSubmit" :loading="$store.state.isLoading">提交</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="handleCurrentChange"
    ></el-pagination>
  </section>
</template>

<script>
export default {
  data() {
    return {
      filters: {
        searchOrderId: "",
        searchProductName: "",
        searchAsin: "",
        searchOrderDate: "",
        searchRebateDate: ""
      },
      orders: [],
      total: 0,
      pageIndex: 1,
      pageSize: 10,
      multipleSelection: [],
      addFormVisible: false,
      pickerOptions: { 
        disabledDate(time) {
          return time.getTime() > Date.now();//如果没有后面的-8.64e7就是不可以选择今天的  - 8.64e7
        }
      },

      addFormRules: {
        orderId: [
          { required: true, message: "请输入订单编号", trigger: "blur" },
        ],
        asin: [
          { required: true, message: "请输入产品ASIN", trigger: "blur" },
        ],
        price: [
          { required: true, message: "请输入产品价格", trigger: "blur" },
        ],
        orderDate: [
          { required: true, message: "请选择订单日期", trigger: "blur" },
        ]
      },
      addForm: {
        id: null,
        orderId: "",
        asin: "",
        productName: "",
        price: 0.0,
        rebate: "",
        receive: "",
        commission: 0.0,
        orderDate: "",
        rebateDate: "",
        description: "",
      }
    };
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 分页
    handleSizeChange(val) {
      this.pageIndex = 1;
      this.pageSize = val;
      this.search();
    },
    handleCurrentChange(val) {
      this.pageIndex = val;
      this.search();
    },

    // 获取列表
    search() {
      let params = {
        pageIndex: this.pageIndex,
        pageSize: this.pageSize,
        orderId: this.filters.searchOrderId,
        productName: this.filters.searchProductName,
        asin: this.filters.searchAsin,
        orderDate: this.filters.searchOrderDate,
        rebateDate: this.filters.searchRebateDate
      };
      axios
        .get("http://localhost:9999/order/list", { params: params })
        .then(res => {
          if (res.data.success) {
            this.total = res.data.data.totalElements;
            this.orders = res.data.data.content;
          }
        });
    },

    // 显示新增界面
    handleAdd: function() {
      this.addFormVisible = true;
      this.addForm = {
        id: null,
        name: "",
        position: "",
        coordinate: "",
        maintain: "",
        description: ""
      };
    },

    // 显示编辑页面
    handeEdit(row) {
      this.addFormVisible = true;
      this.addForm = Object.assign({}, row);
    },
    // 新增
    addSubmit: function() {
      this.$refs.addForm.validate(valid =>{
        if(valid){
          // 验证addForm表单通过
          let params = this.addForm;
          axios.post("http://localhost:9999/order/save",params).then(res => {
            if (res.data.success) {
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.addFormVisible = false;
            }else{
              this.$message({
                message: res.data.error,
                type: "fail"
              });
            }
          });
        }
      })
    }
  },
  created() {
    // 加载列表
    this.search();
  },
  mounted() {}
};
</script>