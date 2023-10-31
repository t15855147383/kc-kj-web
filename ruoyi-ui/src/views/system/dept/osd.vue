<template>
  <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
    <div class="app-container" style="overflow: auto;" id="deptChooseContainer">
      <vue2-org-tree
        :data="data"
        :horizontal="false"
        collapsable
        @on-expand="onExpand"
        @on-node-click="NodeClick"
        @on-node-mouseover="onMouseover"
        @on-node-mouseout="onMouseout"
        :renderContent="renderContent"
      />
      <!-- 创建浮窗盒子 -->
      <div v-show="basicSwitch" class="floating">
        <p>ID:{{basicInfo.id}}</p>
        <p>Name:{{basicInfo.label}}</p>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "choose",
  data(){
    return{
      open:false,
      title:'',
      basicInfo: { id: null, label: null },
      basicSwitch: false,
      data: {
        id: 0,
        label: "XXX科技有限公司",
        children: [
          {
            id: 2,
            label: "产品研发部",
            children: [
              {
                id: 5,
                label: "研发-前端",
                children: [
                  {
                    id: 55,
                    label: "前端1"
                  },
                  {
                    id: 56,
                    label: "前端2"
                  },
                  {
                    id: 57,
                    label: "前端3"
                  },
                  {
                    id: 58,
                    label: "前端4"
                  }
                ]
              },
              {
                id: 6,
                label: "研发-后端"
              },
              {
                id: 9,
                label: "UI设计"
              },
              {
                id: 10,
                label: "产品经理"
              }
            ]
          },
          {
            id: 3,
            label: "销售部",
            children: [
              {
                id: 7,
                label: "销售一部"
              },
              {
                id: 8,
                label: "销售二部"
              }
            ]
          },
          {
            id: 4,
            label: "财务部"
          },
          {
            id: 9,
            label: "HR人事"
          }
        ]
      },
    }
  },
  methods:{
    get(){
      this.open = true;
      this.title = '选择部门';
      this.toggleExpand(this.data,true);
    },
    //渲染节点
    renderContent(h, data) {
      return (
        <div style="white-space:nowrap;">
          <div>
            <i class="el-icon-user-solid"></i>
            <span>{data.label}</span>
            <span>男</span>
          </div>
          <div style="font-size:12px;line-height:20px;">测试人员</div>
        </div>
      );
    },
    //鼠标移出
    onMouseout(e, data) {
      this.basicSwitch = false;
    },
    //鼠标移入
    onMouseover(e, data) {
      this.basicInfo = data;
      this.basicSwitch = true;
      let floating = document.getElementsByClassName("floating")[0];
      floating.style.left = (e.clientX + 20) + "px";
      floating.style.top = (e.clientY + 20) + "px";
    },
    //点击节点
    NodeClick(e, data) {
      console.log(e, data);
    },
    //默认展开
    toggleExpand(data, val) {
      if (Array.isArray(data)) {
        data.forEach(item => {
          this.$set(item, "expand", val);
          if (item.children) {
            this.toggleExpand(item.children, val);
          }
        });
      } else {
        this.$set(data, "expand", val);
        if (data.children) {
          this.toggleExpand(data.children, val);
        }
      }
    },
    collapse(list) {
      list.forEach(child => {
        if (child.expand) {
          child.expand = false;
        }
        child.children && this.collapse(child.children);
      });
    },
    //展开
    onExpand(e, data) {
      if ("expand" in data) {
        data.expand = !data.expand;
        if (!data.expand && data.children) {
          this.collapse(data.children);
        }
      } else {
        this.$set(data, "expand", true);
      }
    },
  }
}
</script>

<style lang="scss">
#deptChooseContainer .org-tree-node-label-inner{
  cursor: pointer;
}
.floating{
  position: fixed;
  z-index: 11;
  padding: 18px 20px;
  background-color: #fff;
  border: 1px solid #ebeef5;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
</style>
