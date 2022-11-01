<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
      <el-form-item label="车位编号" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入车位编号"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 150px"
        />
      </el-form-item>

      <el-form-item label="所属停车场" prop="carCarpark.carparkName">
        <el-select v-model="queryParams['carCarpark.carparkName']" placeholder="请选择所属停车场" clearable style="width: 170px">
<!--          <el-option-->
<!--            v-for="item in parkinglotList"-->
<!--            :key="item.carCarpark.carparkId"-->
<!--            :label="item.carCarpark.carparkName"-->
<!--            :value="item.carCarpark.carparkName"-->
<!--          />-->
          <el-option
            v-for="item in carparkList"
            :key="item.carparkId"
            :label="item.carparkName"
            :value="item.carparkName"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择车位状态" clearable style="width: 150px">
          <el-option
            v-for="dict in dict.type.car_status_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['car:parkinglot:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['car:parkinglot:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['car:parkinglot:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['car:parkinglot:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="parkinglotList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车位ID" align="center" prop="parkinglotId" width="80px"/>
      <el-table-column label="车位编号" align="center" prop="sort" width="80px"/>
      <el-table-column label="所属停车场" align="center" prop="carCarpark.carparkName" width="250px"/>
      <el-table-column label="停车场所在地址" align="center" prop="carCarpark.address" width="250px"/>
      <el-table-column label="车位状态" align="center" prop="status" width="130px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.car_status_type" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="示例图" align="center" prop="photo" width="150px">
        <template slot-scope="scope">
          <image-preview :src="scope.row.photo" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['car:parkinglot:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['car:parkinglot:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改车位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="车位编号" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入车位编号" />
        </el-form-item>

        <el-form-item label="所属停车场" prop="carparkId">
<!--          <el-input v-model="form.carparkId" placeholder="请输入所属停车场" />-->
          <el-select v-model="form.carparkId" placeholder="请输入所属停车场">
            <el-option
              v-for="item in carparkList"
              :key="item.carparkId"
              :label="item.carparkName"
              :value="item.carparkId"
            ></el-option>
          </el-select>
<!--         -->
        </el-form-item>

        <el-form-item label="示例图">
          <image-upload v-model="form.photo"/>
        </el-form-item>
        <el-form-item label="车位状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择车位状态">
            <el-option
              v-for="dict in dict.type.car_status_type"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listParkinglot, getParkinglot, delParkinglot, addParkinglot, updateParkinglot } from "@/api/car/parkinglot";
import { listCarpark } from "@/api/car/carpark"

export default {
  name: "Parkinglot",
  dicts: ['car_status_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车位表格数据
      parkinglotList: [],
      //停车场数据
      carparkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 车位状态 (0 未用、1 已用、2 损坏)时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        sort: null,
        carparkId: null,
        status: null,
        createTime: null,
        'carCarpark.carparkName': null  //封装了车位所属的停车场的信息
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sort: [
          { required: true, message: "车位编号不能为空", trigger: "blur" }
        ],
        carparkId: [
          { required: true, message: "所属停车场不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    //查询车位列表
    this.getList();
    //查询停车场列表
    this.getCarparkList();
  },
  methods: {
    /** 查询车位列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listParkinglot(this.queryParams).then(response => {
        this.parkinglotList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**  查询停车场相关数据 */
    getCarparkList(){
      listCarpark().then(response => {
        this.carparkList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        parkinglotId: null,
        sort: null,
        carparkId: null,
        photo: null,
        status: null,
        createTime: null,
        isDeleted: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // debugger
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // debugger
      this.daterangeCreateTime = [];
      // this.resetForm("queryForm");
      // this.queryParams.carparkId = null;
      this.queryParams["carCarpark.carparkName"] = null;
      this.queryParams.status = null;
      this.queryParams.sort = null;
      // debugger
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.parkinglotId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车位";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const parkinglotId = row.parkinglotId || this.ids
      getParkinglot(parkinglotId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改车位";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.parkinglotId != null) {
            updateParkinglot(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParkinglot(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const parkinglotIds = row.parkinglotId || this.ids;
      this.$modal.confirm('是否确认删除车位编号为"' + parkinglotIds + '"的数据项？').then(function() {
        return delParkinglot(parkinglotIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('car/parkinglot/export', {
        ...this.queryParams
      }, `parkinglot_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
