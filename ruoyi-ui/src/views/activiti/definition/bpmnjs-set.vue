<template>
    <el-dialog
            :visible.sync="modelVisible"
            title="流程图"
            :fullscreen="true"
            append-to-body
    >
        <div class="bpmn-containers" ref="content">
            <div class="bpmn-canvas" ref="canvas"></div>
            <div id="bpmn-panel-process" class="bpmn-js-properties-panel" v-loading="">
                <el-card class="box-card bpmnjsset-card" v-if="processVisible">
                  <div slot="header" class="clearfix">
                      <div style="width: 100%;text-align: center">{{data.key}}</div>
                  </div>
                  <el-form ref="form" label-width="80px" v-if="processForm.id">
                      <el-form-item label="PASS">
                          <el-input v-model="processForm.pass"></el-input>
                      </el-form-item>
                      <el-form-item label="REJECT">
                          <el-input v-model="processForm.reject"></el-input>
                      </el-form-item>
                      <el-form-item label="详情页">
                          <el-input v-model="processForm.page"></el-input>
                      </el-form-item>
                      <el-form-item>
                          <el-button type="primary" size="small" @click="editWorkflowProcess">保存</el-button>
                      </el-form-item>
                  </el-form>
                  <el-empty description="暂无流程配置信息" v-else>
                    <el-button type="primary" size="mini" @click="addWorkflowProcess">立即生成</el-button>
                  </el-empty>
                </el-card>
              <el-card class="box-card bpmnjsset-card" v-if="taskVisible">
                <div slot="header" class="clearfix">
                  <div style="width: 100%;text-align: center">{{taskForm.elementId}}</div>
                </div>
                <el-form ref="form" label-width="80px">
                  <el-form-item label="名称">
                    {{taskForm.elementName}}
                  </el-form-item>
                  <el-form-item label="序号">
                    <el-input-number v-model="taskForm.sort" :min="0" :max="100"></el-input-number>
                  </el-form-item>
                  <el-form-item label="处理人">
                    <el-link icon="el-icon-edit" @click="editWorkflowAssignee">编辑处理人</el-link>
                    <el-tag size="mini" type="info" style="margin-left: 10px;" v-if="taskForm.assigneeCount == 0">未配置</el-tag>
                    <el-tag size="mini" type="success" style="margin-left: 10px;" v-if="taskForm.assigneeCount > 0">已配置</el-tag>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" size="small" @click="editWorkflowTask">保存</el-button>
                  </el-form-item>
                </el-form>
              </el-card>
            </div>
        </div>
      <BpmnAssignee ref="BpmnAssignee" @selection="getWorkflowTask"></BpmnAssignee>
    </el-dialog>

</template>

<script>
//可编辑的建模器
import BpmnViewer from "bpmn-js/lib/Viewer";
import {
  addWorkflowProcess, editWorkflowProcess, editWorkflowTask,
  getDefinitionXML, getWorkflowProcess, getWorkflowTask
} from "@/api/activiti/definition";
import BpmnAssignee from "@/views/activiti/definition/bpmnjs-assignee.vue";
import {Loading} from "element-ui";


export default {
    name: "bpmnjs-set",
    components:{BpmnAssignee},
    data() {
        return {
            modelVisible:false,
            processVisible:false,
            taskVisible:false,
            // bpmn建模器
            BpmnViewer: null,
            container: null,
            canvas: null,
            element: null,
            bpmnTemplate: '',
            data:null,
            processForm: {},
            taskForm: {}
        };
    },
    mounted() {
    },
    methods: {
        async set(data){
            let that = this;
            that.data = data;
            getDefinitionXML({
              deploymentId: data.deploymentId,
              resourceName: encodeURI(data.resourceName)
            }).then(response => {
                that.bpmnTemplate = response;
                that.init();
            });
        },
        async init() {
            let that = this;
            that.modelVisible = true;
            that.$nextTick(() => {
                // 获取画布 element
                that.canvas = that.$refs.canvas;
                if(that.BpmnViewer){
                    that.BpmnViewer.destroy();
                }
                // 创建Bpmn对象
                that.BpmnViewer = new BpmnViewer({
                    // 设置bpmn的绘图容器为上门获取的画布 element
                    container: that.canvas
                });
                that.createNewDiagram(this.bpmnTemplate);
                that.selectionChangedEvent();
                that.getWorkflowProcess();
            })

        },
        async createNewDiagram(bpmn) {
            let that = this;
            that.BpmnViewer.clear();
            // 将字符串转换成图显示出来;
            that.BpmnViewer.importXML(bpmn, err => {
                if (err) {
                    this.$message.error('打开模型出错,请确认该模型符合Bpmn2.0规范');
                }
            });
        },
        async selectionChangedEvent(){
            let that = this;
            that.BpmnViewer.on('selection.changed',  e => {
                if(that.processForm.id){
                    if(e.newSelection[0] && e.newSelection[0].type == 'bpmn:Task'){
                      that.element =  e.newSelection[0];
                      that.getWorkflowTask();
                    }else{
                      that.getWorkflowProcess();
                    }
                }
            });
        },
        async getWorkflowProcess(){
            let that = this;
            that.processVisible = true;
            that.taskVisible = false;

            let loading = Loading.service({ target: '.bpmnjsset-card' });
            getWorkflowProcess({procDefId:that.data.id}).then(response => {
              loading.close();
              if(response.data){
                that.processForm = response.data;
              }
            })
        },
      async getWorkflowTask(){
          let that = this;
          that.processVisible = false;
          that.taskVisible = true;
          let loading = Loading.service({ target: '.bpmnjsset-card' });
          getWorkflowTask({
            processId: that.processForm.id,
            elementId: that.element.id
          }).then(response => {
            loading.close();
            if(response.data){
              that.taskForm = response.data;
            }
          })
      },
      addWorkflowProcess(){
        let that = this;
        that.processForm.procDefId = that.data.id;
        let loading = Loading.service({ target: '.bpmnjsset-card' });
        addWorkflowProcess(that.processForm).then(response => {
          loading.close();
          this.$message.success('创建成功');
          that.getWorkflowProcess();
        })
      },
      editWorkflowProcess(){
        let that = this;
        let loading = Loading.service({ target: '.bpmnjsset-card' });
        editWorkflowProcess(that.processForm).then(response => {
          loading.close();
          this.$message.success('修改成功');
          that.getWorkflowProcess();
        })
      },
      editWorkflowTask(){
        let that = this;
        let loading = Loading.service({ target: '.bpmnjsset-card' });
        editWorkflowTask(that.taskForm).then(response => {
          loading.close();
          this.$message.success('修改成功');
          that.getWorkflowTask();
        })
      },
      editWorkflowAssignee(){
        let taht = this;
        taht.$refs.BpmnAssignee.set({
          processId: taht.taskForm.processId,
          taskId: taht.taskForm.id
        });
      }
    },
}
</script>

<style scoped lang="scss">
.bpmn-containers {
  position: relative;
  /* 使用网格作为背景 */
  background: white;
  overflow: auto;
  background-image: linear-gradient(
                  90deg,
                  rgba(220, 220, 220, 0.5) 6%,
                  transparent 0
  ),
  linear-gradient(rgba(192, 192, 192, 0.5) 6%, transparent 0);
  background-size: 12px 12px;
  width: 100%;
  height: calc(100vh - 180px);
  -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
}
.bpmn-canvas {
  width: 100%;
  height: 95%;
}
.bpmn-js-properties-panel{
  position: absolute;
  right: 20px;
  top: 20px;
  width: 350px;
}
</style>
