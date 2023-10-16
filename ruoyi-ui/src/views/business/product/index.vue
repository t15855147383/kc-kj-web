<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="searchValue">
        <el-input
          v-model="queryParams.searchValue"
          placeholder="请输入"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:product:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList">
      <el-table-column label="菜单" align="center" prop="manuId" width="150"/>
      <el-table-column label="标题" align="center" prop="title" width="200"/>
      <el-table-column label="标签" align="center" prop="lable" width="300"/>
      <el-table-column label="描述" align="center" prop="desc" width="500"/>
      <el-table-column label="操作" align="center" fixed="right" width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:product:remove']"
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

    <!-- 添加或修改产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="菜单" prop="manuId">
              <el-select v-model="form.manuId" filterable placeholder="请选择">
                <el-option
                  v-for="item in menuOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="标签" prop="lable">
              <el-input v-model="form.lable" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述" prop="desc">
              <el-input v-model="form.desc" type="textarea" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="产品图片" prop="images">
              <ImageUpload v-model="form.images" :limit="10"></ImageUpload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-tabs v-model="detailName">
              <el-tab-pane label="功能特点" name="detail1">
                <Editor v-model="form.detail1" :height="800"></Editor>
              </el-tab-pane>
              <el-tab-pane label="功能清单" name="detail2">
                <Editor v-model="form.detail2" :height="800"></Editor>
              </el-tab-pane>
              <el-tab-pane label="在线预览" name="detail3">
                <Editor v-model="form.detail3" :height="800"></Editor>
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/business/product";
import {allMenu} from "@/api/business/menu"
export default {
  name: "Product",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      detailName:'detail1',
      menuOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        manuId: null,
        searchValue: null
      },
      // 表单参数
      form: {images:[]},
      // 表单校验
      rules: {
        manuId: [
          { required: true, message: "菜單不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        lable: [
          { required: true, message: "标签不能为空", trigger: "blur" }
        ]
      },
    };
  },
  created() {
    this.getList();
    allMenu({}).then(response => {
      this.menuOptions = response.data;
    });
  },
  methods: {
    /** 查询产品列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
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
        manuId: null,
        title: null,
        lable: null,
        desc: null,
        detail1: null,
        detail2: null,
        detail3: null,
        images:[]
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.manuId = null;
      this.queryParams.searchValue = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产品编号为"' + ids + '"的数据项？').then(function() {
        return delProduct(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
