<template>
  <el-dialog
    :visible.sync="modelVisible"
    title="流程图"
    :fullscreen="true"
    append-to-body
  >
    <div class="bpmn-containers" ref="content">
      <div class="bpmn-canvas" ref="canvas"></div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button size="mini" icon="el-icon-download" @click="downloadBpmn">bpmn</el-button>
      <el-button size="mini" icon="el-icon-picture" @click="downloadSvg">svg</el-button>
      <a hidden ref="downloadLink"></a>
    </div>
  </el-dialog>
</template>

<script>
//可编辑的建模器
import BpmnViewer from "bpmn-js/lib/Viewer";
import {addDeploymentByString, getDefinitionXML} from "@/api/activiti/definition";


export default {
  name: "bpmnjs-get",
  components:{},
  data() {
    return {
      modelVisible:false,
      // bpmn建模器
      bpmnViewer: null,
      container: null,
      canvas: null,
      bpmnTemplate: ''
    };
  },
  mounted() {
  },
  methods: {
    async get(data){
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
        if(that.bpmnViewer){
          that.bpmnViewer.destroy();
        }
        // 创建Bpmn对象
        that.bpmnViewer = new BpmnViewer({
          // 设置bpmn的绘图容器为上门获取的画布 element
          container: that.canvas
        });

        that.createNewDiagram(this.bpmnTemplate);
      })

    },
    async createNewDiagram(bpmn) {
      let that = this;
      that.bpmnViewer.clear();
      // 将字符串转换成图显示出来;
      that.bpmnViewer.importXML(bpmn, err => {
        if (err) {
          this.$message.error('打开模型出错,请确认该模型符合Bpmn2.0规范');
        }
      });
    },

    downloadBpmn() {
      let that = this;
      that.bpmnViewer.saveXML({ format: true }, (err, xml) => {
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
      that.bpmnViewer.saveXML({ format: true }, (err, xml) => {
        if (!err) {
          // 获取文件名
          const name = `${this.getFilename(xml)}.svg`;

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
          const svg = `
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
          // 将文件名以及数据交给下载方法
          that.download({ name: name, data: svg });
        }
      });
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
</style>
