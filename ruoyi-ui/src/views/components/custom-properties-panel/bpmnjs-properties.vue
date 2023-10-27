<template>
  <div class="custom-properties-panel">
    <div class="empty" v-if="selectedElement.length <= 0">
      请选择一个元素
    </div>
    <div class="empty" v-else-if="selectedElement.length > 1">
      只能选择一个元素
    </div>
    <div v-else>
      <fieldset class="element-item">
        <label>id</label>
        <span>{{element.id}}</span>
      </fieldset>
      <fieldset class="element-item">
        <label>name</label>
        <input :value="element.name" @change="(event) => changeField(event,'name')"/>
      </fieldset>
      <fieldset class="element-item">
        <label>customProps</label>
        <input :value="element.name" @change="(event) => changeField(event,'customProps')"/>
      </fieldset>
    </div>
  </div>
</template>

<script>
export default {
  name: "bpmnjs-properties",
  props:{
    modeler:{
      type: Object,
      default:() => ({})
    }
  },
  data(){
    return {
      selectedElement: [],
      element:null
    }
  },
  created() {
    this.init();
  },
  methods:{
    init(){
      const { modeler } = this;
      modeler.on('selection.changed',  e => {
        this.selectedElement = e.newSelection;
        this.element =  e.newSelection[0];
      });
      modeler.on('element.changed',e => {
        const { element } = e;
        console.log(element.name)
        const { element:currentElement } = this;
        if(!currentElement){
          return;
        }
        if(element.id == currentElement.id){
          this.element = element;
        }
      });
    },
    updateProperties(properties){
      const { modeler,element } = this;
      const modeling = modeler.get('modeling');
      modeling.updateProperties(element,properties)
    },
    /**
     * 改变控件触发的事件
     * @param event input的Event
     * @param type 要修改的属性的名称
     */
    changeField(event,type){
      const value = event.target.value;
      let properties = {};
      properties[type] = value;
      this.element[type] = value;
      this.updateProperties(properties)
    }
  }
}
</script>

<style scoped lang="scss">
  .custom-properties-panel{
    position: absolute;
    right: 0;
    top: 0;
    width: 300px;
    background-color: #fff9f9;
    border-color: rgba(0,0,0,0.09);
    padding: 20px;
  }
</style>
