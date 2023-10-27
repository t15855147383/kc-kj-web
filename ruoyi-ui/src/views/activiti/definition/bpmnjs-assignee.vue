<template>
  <el-dialog
    :visible.sync="open"
    title="流程处理人"
    width="70%"
    append-to-body
  >
    <el-table
      :data="dataList"
      style="width: 100%">
      <el-table-column align="center" width="80">
        <template slot="header" slot-scope="scope">
          <el-button type="text" icon="el-icon-circle-plus"></el-button>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-remove"
            style="color: #F56C6C;"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="类型" width="150" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.type" placeholder="请选择" style="width: 100%;">
            <el-option label="用户" value="user"></el-option>
            <el-option label="岗位" value="post"></el-option>
            <el-option label="部门" value="dept"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="处理人" width="300" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.userId" filterable multiple placeholder="请选择" style="width: 100%;">
            <el-option label="用户" value="user"></el-option>
            <el-option label="岗位" value="post"></el-option>
            <el-option label="部门" value="dept"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="移除指定人" width="200" align="center">
        <template slot-scope="scope">

        </template>
      </el-table-column>
      <el-table-column label="包含指定人" width="200" align="center">
        <template slot-scope="scope">

        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary">确 定</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "bpmnjs-assignee",
  data() {
    return {
      open : false,
      dataList: []
    };
  },
  created() {
  },
  methods: {
    set(data){
      this.open = true
    }
  }
}
</script>



<style scoped lang="scss">

</style>
