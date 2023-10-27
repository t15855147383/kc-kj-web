<template>
  <el-dialog
    :visible.sync="modelVisible"
    title="流程图"
    :fullscreen="true"
    append-to-body
  >
    <div class="bpmn-containers" ref="content">
      <div class="bpmn-canvas" ref="canvas"></div>
      <div id="js-properties-panel" class="bpmn-js-properties-panel"></div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-upload action style="display: inline-block;margin-right: 10px;" :before-upload="openBpmn">
        <el-button size="mini" icon="el-icon-folder-opened">导入</el-button>
      </el-upload>
      <el-button size="mini" icon="el-icon-circle-plus" @click="saveBpmn">部署</el-button>
      <el-button size="mini" icon="el-icon-download" @click="downloadBpmn">bpmn</el-button>
      <el-button size="mini" icon="el-icon-picture" @click="downloadSvg">svg</el-button>
      <a hidden ref="downloadLink"></a>
    </div>
  </el-dialog>
</template>

<script>
//可编辑的建模器
import BpmnModeler from "bpmn-js/lib/Modeler";
//左侧工具栏
import "bpmn-js/dist/assets/diagram-js.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";
//右侧工具栏
import "bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css"
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda';
import propertiesPanelModule from 'bpmn-js-properties-panel';
import camundaModdleDescriptor from "camunda-bpmn-moddle/resources/camunda";
//汉化
import customTranslate from '@/api/activiti/customTranslate'
import {
  addDeploymentByString,
  editDeploymentByString,
  getDefinitionXML,
  getProcdefByKey
} from "@/api/activiti/definition";


export default {
  name: "bpmnjs-edit",
  components:{},
  data() {
    return {
      modelVisible:false,
      // bpmn建模器
      bpmnModeler: null,
      container: null,
      canvas: null,
      bpmnTemplate: '<?xml version="1.0" encoding="UTF-8"?>' + '<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' + 'xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" ' + 'xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" ' + 'xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" ' + 'targetNamespace="http://bpmn.io/schema/bpmn" ' + 'id="Definitions_1">' + '<bpmn:process id="Process_1" isExecutable="true">' + '<bpmn:startEvent id="StartEvent_1"/>' + '</bpmn:process>' + '<bpmndi:BPMNDiagram id="BPMNDiagram_1">' + '<bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">' + '<bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">' + '<dc:Bounds height="36.0" width="36.0" x="173.0" y="102.0"/>' + '</bpmndi:BPMNShape>' + '</bpmndi:BPMNPlane>' + '</bpmndi:BPMNDiagram>' + '</bpmn:definitions>'
    };
  },
  mounted() {
  },
  methods: {
    async add(){
      let that = this;
      await that.init();
    },
    async edit(data){
      let that = this;
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
        //将汉化包装成一个模块
        let customTranslateModule = {
          translate: [ 'value', customTranslate ]
        }
        if(that.bpmnModeler){
          that.bpmnModeler.destroy();
        }
        // 创建Bpmn对象
        that.bpmnModeler = new BpmnModeler({
          // 设置bpmn的绘图容器为上门获取的画布 element
          container: that.canvas,

          // 加入工具栏支持
          propertiesPanel: {
            parent: "#js-properties-panel"
          },
          additionalModules: [
            propertiesProviderModule,
            propertiesPanelModule,
            //汉化模块
            customTranslateModule

          ],
          moddleExtensions: {
            camunda: camundaModdleDescriptor
          }
        });

        that.createNewDiagram(this.bpmnTemplate);
      })

    },
    async createNewDiagram(bpmn) {
      let that = this;
      that.bpmnModeler.clear();
      // 将字符串转换成图显示出来;
      that.bpmnModeler.importXML(bpmn, err => {
        if (err) {
          this.$message.error('打开模型出错,请确认该模型符合Bpmn2.0规范');
        }
      });
    },
    saveBpmn() {
      let that = this;
      that.$confirm('确认是否部署该流程?', '部署流程', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        that.bpmnModeler.saveXML({
          format: true
        }, function (err, xml) {
          if (err) {
            return console.error('保存失败，请重试', err);
          }
          let svg = that.getContextSVG();
          let param = {
            "key" : that.bpmnModeler._definitions.rootElements[0].id,
            "name" : that.bpmnModeler._definitions.rootElements[0].name,
            "bpmn" : xml,
            "svg" : svg
          };
          getProcdefByKey(that.bpmnModeler._definitions.rootElements[0].id).then(response => {
            if(response.data){
              param.id = response.data.id;
              param.category = response.data.category;
              param.deploymentId = response.data.deploymentId;
              param.resourceName = response.data.resourceName;
              let newDatas = []
              let h = that.$createElement;
              newDatas.push(h('p', null, '流程定义['+that.bpmnModeler._definitions.rootElements[0].id+']已存在'));
              newDatas.push(h('p', null, '流程ID:['+response.data.id+']'));
              newDatas.push(h('p', null, '是否更新部署？'));
              that.$confirm('部署流程', {
                title: '部署流程',
                message: h('div', null, newDatas),
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                that.editDeployment(param)
              }).catch(() => {
                that.addDeployment(param);
              });
            }else{
              that.addDeployment(param);
            }
          });
        });
      }).catch(() => {});

    },
    addDeployment(param){
      let that = this;
      addDeploymentByString(param).then(response => {
        that.$message.success('部署成功');
        that.modelVisible = false;
        that.$emit('selection',{})
      });
    },
    editDeployment(param){
      let that = this;
      editDeploymentByString(param).then(response => {
        that.$message.success('部署成功');
        that.modelVisible = false;
        that.$emit('selection',{})
      });
    },
    downloadBpmn() {
      let that = this;
      that.bpmnModeler.saveXML({ format: true }, (err, xml) => {
        if (!err) {
          // 获取文件名
          const name = `${that.getFilename(xml)}.bpmn`;
          // 将文件名以及数据交给下载方法
          this.download({ name: name, data: xml });
        }
      });
    },
    downloadSvg() {
      let that = this;
      that.bpmnModeler.saveXML({ format: true }, (err, xml) => {
        if (!err) {
          // 获取文件名
          const name = `${this.getFilename(xml)}.svg`;
          // 将标签和数据拼接成一个完整正常的svg图形
          const svg = that.getContextSVG();
          // 将文件名以及数据交给下载方法
          that.download({ name: name, data: svg });
        }
      });
    },
    getContextSVG(){
      let that = this;
      // 从建模器画布中提取svg图形标签
      let context = "";
      const djsGroupAll = that.$refs.canvas.querySelectorAll(".djs-group");
      for (let item of djsGroupAll) {
        context += item.innerHTML;
      }
      // 获取svg的基本数据，长宽高
      const viewport = that.$refs.canvas
        .querySelector(".viewport")
        .getBBox();

      // 将标签和数据拼接成一个完整正常的svg图形
      return `
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              width="${viewport.width}"
              height="${viewport.height}"
              viewBox="${viewport.x} ${viewport.y} ${viewport.width} ${viewport.height}"
              version="1.1"
              >
              ${context}
            </svg>
          `;
    },

    openBpmn(file) {
      let that = this;
      const reader = new FileReader();
      // 读取File对象中的文本信息，编码格式为UTF-8
      reader.readAsText(file, "utf-8");
      reader.onload = () => {
        //读取完毕后将文本信息导入到Bpmn建模器
        that.createNewDiagram(reader.result);
      };
      return false;
    },

    getFilename(xml) {
      let start = xml.indexOf("process");
      let filename = xml.substr(start, xml.indexOf(">"));
      filename = filename.substr(filename.indexOf("id") + 4);
      filename = filename.substr(0, filename.indexOf('"'));
      return filename;
    },

    download({ name = "diagram.bpmn", data }) {
      let that = this;
      // 这里就获取到了之前设置的隐藏链接
      const downloadLink = that.$refs.downloadLink;
      // 把输就转换为URI，下载要用到的
      const encodedData = encodeURIComponent(data);

      if (data) {
        // 将数据给到链接
        downloadLink.href =
          "data:application/bpmn20-xml;charset=UTF-8," + encodedData;
        // 设置文件名
        downloadLink.download = name;
        // 触发点击事件开始下载
        downloadLink.click();
      }
    },


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
    width: 300px;
    border: solid 1px #cccccc;
  }
</style>
