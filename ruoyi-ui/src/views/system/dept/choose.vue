<template>
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="部门名称" prop="deptName">
          <el-input
            v-model="queryParams.deptName"
            placeholder="请输入部门名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <el-tree
        ref="deptTree"
        :data="deptList"
        show-checkbox
        node-key="deptId"
        :check-strictly="true"
        default-expand-all
        :expand-on-click-node="false">
        <div class="custom-tree-node" slot-scope="{ node, data }" style="display: flex;align-items: center;justify-content: flex-start;">
          <div style="width: 280px;">{{ data.deptName }}</div>
          <div style="width: 300px;">{{ data.createTime }}</div>
          <div style="width: 150px;"><dict-tag :options="dict.type.sys_normal_disable" :value="data.status"/></div>
        </div>
      </el-tree>

    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确认选择</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>

</template>

<script>
import { listDept} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept-choose",
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        deptName: undefined,
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
      this.title = '选择部门';
      this.getList();
    },
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      listDept(this.queryParams).then(response => {
        this.deptList = this.handleTree(response.data, "deptId");
        this.loading = false;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 提交按钮 */
    submitForm: function() {
      let that = this;
      let childsIds = this.$refs.deptTree.getCheckedKeys();
      if(!childsIds || childsIds.length === 0){
        that.$message.warning('请选择部门');
        return;
      }
      that.open = false;
      that.$emit('selection',childsIds);
    }
  }
};
</script>
