<template>
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
    <div class="app-container">
      <el-row :gutter="20">
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              v-model="roleTypeName"
              placeholder="请输入名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-tree
              :data="roleTypeOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              node-key="id"
              default-expand-all
              highlight-current
              @node-click="handleNodeClick"
            >
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="角色名称" prop="roleName">
              <el-input
                v-model="queryParams.roleName"
                placeholder="请输入角色名称"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="权限字符" prop="roleKey">
              <el-input
                v-model="queryParams.roleKey"
                placeholder="请输入权限字符"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="角色状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="dateRange"
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
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="角色编号" prop="roleId" width="120" />
            <el-table-column label="角色名称" prop="roleName" :show-overflow-tooltip="true" width="150" />
            <el-table-column label="权限字符" prop="roleKey" :show-overflow-tooltip="true" width="150" />
            <el-table-column label="显示顺序" prop="roleSort" width="100" />
            <el-table-column label="状态" align="center" width="100">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.status"
                  active-value="0"
                  inactive-value="1"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
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
        </el-col>
      </el-row>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确认选择</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listRole} from "@/api/system/role";
import {roleTypeTreeSelect} from "@/api/system/roleType";

export default {
  name: "Role-choose",
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
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      roleTypeOptions:null,
      roleTypeName:null,

      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined,
        roleTypeId:undefined
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    roleTypeName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {

  },
  methods: {
    get(){
      this.open = true;
      this.title = '选择角色';
      this.ids = [];
      this.single = true;
      this.multiple = true;
      this.getRoleTypeTree();
      this.getList();
    },
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    getRoleTypeTree() {
      roleTypeTreeSelect().then(response => {
        this.roleTypeOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.roleTypeId = data.id;
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    submitForm(){
      let that = this;
      if(!that.ids || that.ids.length === 0){
        that.$message.warning('请选择角色');
        return;
      }
      that.open = false;
      that.$emit('selection',that.ids);
    }
  }
};
</script>
