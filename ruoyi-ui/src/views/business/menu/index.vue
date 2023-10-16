<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd(null)"
          v-hasPermi="['system:menu:add']"
        >新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading"
              :data="menuList"
              row-key="id"
              border
              default-expand-all
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="ID" align="left" prop="id" width="80"/>
      <el-table-column label="名称" align="left" prop="name" width="300"/>
      <el-table-column label="页面" align="left" prop="page">
        <template slot-scope="scope">
          {{scope.row.page.name}}（{{scope.row.page.url}}）
        </template>
      </el-table-column>
      <el-table-column label="序号" align="center" prop="sort" width="50"/>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
            v-if="scope.row.level < 3"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:menu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改导航菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input-number v-model="form.sort" :min="0" :max="99"></el-input-number>
        </el-form-item>
        <el-form-item label="页面" prop="pageId">
          <el-radio-group v-model="form.pageId">
            <el-radio :label="p.id" v-for="p in pageList">{{p.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="参数" prop="param">
          <el-input v-model="form.param" placeholder="请输入" />
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
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/business/menu";
import {allPage} from "@/api/business/page";

export default {
  name: "Menu",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 导航菜单表格数据
      menuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
      },
      pageList:[],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        pageId: [
          { required: true, message: "页面不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    allPage({}).then(response => {
      this.pageList = response.data;
    });
  },
  methods: {
    /** 查询导航菜单列表 */
    getList() {
      this.loading = true;
      listMenu(this.queryParams).then(response => {
        this.menuList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        parentId: null,
        name: null,
        pageId: null,
        param:null,
        sort:null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.open = true;
      this.title = "添加导航菜单";
      this.form.sort = 0;
      this.form.parentId = (row != null ? row.id : 0);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMenu(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改导航菜单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMenu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMenu(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除导航菜单编号为"' + ids + '"的数据项？').then(function() {
        return delMenu(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
