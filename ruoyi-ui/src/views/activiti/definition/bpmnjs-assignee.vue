<template>
  <el-dialog
    :visible.sync="open"
    title="流程处理人"
    width="80%"
    append-to-body
  >
    <el-table
      :data="dataList"
      style="width: 100%">
      <el-table-column align="center" width="80">
        <template slot="header" slot-scope="scope">
          <el-button type="text" icon="el-icon-circle-plus" @click="addItem()"></el-button>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-remove"
            style="color: #F56C6C;"
            @click="removeItem(scope.$index)"
          >
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="处理人" width="250" align="center">
        <template slot-scope="scope">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-select v-model="scope.row.assigneeType" style="float: left;width: 120px;" placeholder="请选择" @change="typeSelection(scope.$index,'assignee')">
                <el-option label="用户" value="user"></el-option>
                <el-option label="岗位" value="post"></el-option>
                <el-option label="角色" value="role"></el-option>
              </el-select>
              <el-button icon="el-icon-plus" style="float: right; padding: 3px 0" type="text" @click="openSelection(scope.$index,scope.row.assigneeType,'assignee')"></el-button>
            </div>
            <div class="item">
              <el-tag v-for="(id,i) in scope.row.assigneeValue" effect="plain" closable @close="removeSelection(scope.$index,i,'assignee')">
                {{selectionTest(id,scope.row.assigneeType)}}
              </el-tag>
            </div>
          </el-card>
        </template>
      </el-table-column>
      <el-table-column label="发起人" width="500" align="center">
        <template slot-scope="scope">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-select v-model="scope.row.sponsorType" style="float: left;width: 120px;" placeholder="请选择" @change="typeSelection(scope.$index,'sponsor')">
                <el-option label="用户" value="user"></el-option>
                <el-option label="岗位" value="post"></el-option>
                <el-option label="角色" value="role"></el-option>
                <el-option label="部门" value="dept"></el-option>
              </el-select>
              <el-button icon="el-icon-plus" style="float: right; padding: 3px 0" type="text" @click="openSelection(scope.$index,scope.row.sponsorType,'sponsor')"></el-button>
            </div>
            <div class="item">
              <el-tag v-for="(id,i) in scope.row.sponsorValue" effect="plain" closable @close="removeSelection(scope.$index,i,'sponsor')">
                {{selectionTest(id,scope.row.sponsorType)}}
              </el-tag>
            </div>
          </el-card>
        </template>
      </el-table-column>
      <el-table-column label="移除编内人员" width="300" align="center">
        <template slot-scope="scope">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span style="float: left;">用户</span>
              <el-button icon="el-icon-plus" style="float: right; padding: 3px 0" type="text" @click="openSelection(scope.$index,'user','out')"></el-button>
            </div>
            <div class="item">
              <el-tag v-for="(id,i) in scope.row.sponsorUserOut" effect="plain" closable @close="removeSelection(scope.$index,i,'out')">
                {{selectionTest(id,'user')}}
              </el-tag>
            </div>
          </el-card>
        </template>
      </el-table-column>
      <el-table-column label="包含编外人员" width="300" align="center">
        <template slot-scope="scope">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span style="float: left;">用户</span>
              <el-button icon="el-icon-plus" style="float: right; padding: 3px 0" type="text" @click="openSelection(scope.$index,'user','in')"></el-button>
            </div>
            <div class="item">
              <el-tag v-for="(id,i) in scope.row.sponsorUserIn" effect="plain" closable @close="removeSelection(scope.$index,i,'in')">
                {{selectionTest(id,'user')}}
              </el-tag>
            </div>
          </el-card>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
    <UserChoose ref="UserChoose" @selection="confirmSelection"></UserChoose>
    <PostChoose ref="PostChoose" @selection="confirmSelection"></PostChoose>
    <RoleChoose ref="RoleChoose" @selection="confirmSelection"></RoleChoose>
    <DeptChoose ref="DeptChoose" @selection="confirmSelection"></DeptChoose>
  </el-dialog>
</template>

<script>
import UserChoose from "@/views/system/user/choose.vue";
import PostChoose from "@/views/system/post/choose.vue";
import RoleChoose from "@/views/system/role/choose.vue";
import DeptChoose from "@/views/system/dept/choose.vue";
import {allUser} from "@/api/system/user";
import {allPost} from "@/api/system/post";
import {allRole} from "@/api/system/role";
import {listDept} from "@/api/system/dept";
import {addWorkflowAssignee, getWorkflowAssignee} from "@/api/activiti/definition";
export default {
  name: "bpmnjs-assignee",
  components:{UserChoose,PostChoose,RoleChoose,DeptChoose},
  data() {
    return {
      open : false,
      itemIndex:null,
      itemColumn:null,
      columnMap:{assignee:'assigneeValue',sponsor:'sponsorValue',out:'sponsorUserOut',in:'sponsorUserIn'},
      data:{},
      dataList: [],
      userOptions:[],
      postOptions:[],
      roleOptions:[],
      deptOptions:[],
    };
  },
  async created() {
    let res1 = await allUser();
    let res2 = await allPost();
    let res3 = await allRole();
    let res4 = await listDept();
    this.userOptions = res1.data;
    this.postOptions = res2.data;
    this.roleOptions = res3.data;
    this.deptOptions = res4.data;
  },
  methods: {
    async set(data){
      this.data = data;
      this.dataList = [];
      this.open = true;
      getWorkflowAssignee(data).then(response => {
        if(response.data){
          for(let i=0;i<response.data.length;i++){
            response.data[i].assigneeValue = response.data[i].assigneeValue ? response.data[i].assigneeValue.split(',') : [];
            response.data[i].sponsorValue = response.data[i].sponsorValue ? response.data[i].sponsorValue.split(',') : [];
            response.data[i].sponsorUserOut = response.data[i].sponsorUserOut ? response.data[i].sponsorUserOut.split(',') : [];
            response.data[i].sponsorUserIn = response.data[i].sponsorUserIn ? response.data[i].sponsorUserIn.split(',') : [];
          }
          this.dataList = response.data;
        }
      });
    },
    addItem(){
      let that = this;
      that.dataList.push({
        taskId: that.data.taskId,
        assigneeType: 'user',
        assigneeValue: [],
        sponsorType: 'user',
        sponsorValue: [],
        sponsorUserOut: [],
        sponsorUserIn: []
      });
    },
    removeItem(index){
      let that = this;
      that.dataList.splice(index,1);
    },
    typeSelection(index,column){
      let that = this;
      that.dataList[index][that.columnMap[column]] = [];
    },
    removeSelection(index0,index1,column){
      let that = this;
      that.dataList[index0][that.columnMap[column]].splice(index1,1);
    },
    openSelection(index,type,column){
      let that = this;
      that.itemIndex = index;
      this.itemColumn = column;
      if(type === 'user'){
        that.$refs.UserChoose.get();
      }else if(type === 'post'){
        that.$refs.PostChoose.get();
      }else if(type === 'role'){
        that.$refs.RoleChoose.get();
      }else if(type === 'dept'){
        that.$refs.DeptChoose.get();
      }
    },
    confirmSelection(ids){
      let that = this;
      for(let i=0;i < ids.length;i++){
        if(!that.dataList[that.itemIndex][that.columnMap[that.itemColumn]].includes(ids[i])){
          that.dataList[that.itemIndex][that.columnMap[that.itemColumn]].push(ids[i]);
        }
      }
      that.itemIndex = null;
      that.itemColumn = null;
    },
    submit(){
      let that = this;
      let dataList = JSON.parse(JSON.stringify(that.dataList));
      for(let i = 0;i < dataList.length;i++){
        dataList[i].assigneeValue = dataList[i].assigneeValue.join(',');
        dataList[i].sponsorValue = dataList[i].sponsorValue.join(',');
        dataList[i].sponsorUserOut = dataList[i].sponsorUserOut.join(',');
        dataList[i].sponsorUserIn = dataList[i].sponsorUserIn.join(',');
      }
      addWorkflowAssignee({list : dataList}).then(response => {
        that.open = false;
        that.$message.success('修改成功');
        that.$emit('selection',{})
      });
    },
    selectionTest(id,type){
      let that = this;
      if(type === 'user'){
        return that.userFilter(id);
      }else if(type === 'post'){
        return that.postFilter(id);
      }else if(type === 'role'){
        return that.roleFilter(id);
      }else if(type === 'dept'){
        return that.deptFilter(id);
      }
    },
    userFilter(id){
      let ids = this.userOptions.map(item => String(item.userId));
      if(ids.indexOf(String(id)) > -1){
        return this.userOptions[ids.indexOf(String(id))].nickName;
      }
      return '';
    },
    postFilter(id){
      let ids = this.postOptions.map(item => String(item.postId));
      if(ids.indexOf(String(id)) > -1){
        return this.postOptions[ids.indexOf(String(id))].postName;
      }
      return '';
    },
    roleFilter(id){
      let ids = this.roleOptions.map(item => String(item.roleId));
      if(ids.indexOf(String(id)) > -1){
        return this.roleOptions[ids.indexOf(String(id))].roleName;
      }
      return '';
    },
    deptFilter(id){
      let ids = this.deptOptions.map(item => String(item.deptId));
      if(ids.indexOf(String(id)) > -1){
        return this.deptOptions[ids.indexOf(String(id))].deptName;
      }
      return '';
    },
  }
}
</script>



<style scoped lang="scss">
  .item{
    text-align: left;
  }
  .item span{
    margin-right: 5px;
    margin-bottom: 5px;
  }
</style>
