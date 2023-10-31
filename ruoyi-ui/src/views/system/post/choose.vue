<template>
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="岗位编码" prop="postCode">
          <el-input
            v-model="queryParams.postCode"
            placeholder="请输入岗位编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="岗位名称" prop="postName">
          <el-input
            v-model="queryParams.postName"
            placeholder="请输入岗位名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="岗位状态" clearable>
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="岗位编号" align="center" prop="postId" />
        <el-table-column label="岗位编码" align="center" prop="postCode" />
        <el-table-column label="岗位名称" align="center" prop="postName" />
        <el-table-column label="岗位排序" align="center" prop="postSort" />
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
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
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确认选择</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listPost} from "@/api/system/post";

export default {
  name: "Post-choose",
  dicts: ['sys_normal_disable'],
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
      // 岗位表格数据
      postList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {

  },
  methods: {
    get(){
      this.open = true;
      this.title = '选择岗位';
      this.ids = [];
      this.single = true;
      this.multiple = true;
      this.getList();
    },
    /** 查询岗位列表 */
    getList() {
      this.loading = true;
      listPost(this.queryParams).then(response => {
        this.postList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 提交按钮 */
    submitForm: function() {
      let that = this;
      if(!that.ids || that.ids.length === 0){
        that.$message.warning('请选择岗位');
        return;
      }
      that.open = false;
      that.$emit('selection',that.ids);
    }
  }
};
</script>
