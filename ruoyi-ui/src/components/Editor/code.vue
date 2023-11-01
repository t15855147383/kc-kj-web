<template>
  <el-dialog
    :visible.sync="open"
    title="代码编辑器"
    width="1200px"
    append-to-body
  >
    <div style="width: 100%;height: 100%;" id="editor-code-id">
      <div style="width: 100%;display: flex;align-items: center;justify-content: flex-end;">
        <el-tooltip class="item" effect="dark" content="白板" placement="top-end">
          <el-button type="text" icon="el-icon-monitor" @click="setTheme('default')"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="黑板" placement="top-end">
          <el-button type="text" icon="el-icon-s-platform" @click="setTheme('monokai')"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="格式化" placement="top-end">
          <el-button type="text" icon="el-icon-c-scale-to-original" @click="formatCode"></el-button>
        </el-tooltip>
      </div>

      <codemirror
        ref="codeEditor"
        v-model="codeView"
        :options="options"
        style="height: 450px;"
      />

    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitCode">保 存</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>


</template>
<script>
// require component
import {codemirror} from 'vue-codemirror'

// require styles
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/monokai.css';
import 'codemirror/mode/xml/xml';
import 'codemirror/mode/css/css';
import 'codemirror/mode/sass/sass';
import 'codemirror/mode/haml/haml';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/go/go';
import 'codemirror/mode/php/php';
import 'codemirror/mode/vue/vue';

import jsBeautify from 'js-beautify';

export default {
  components:{codemirror},
  data() {
    return {
      open: false,
      options:{
        tabSize:4,
        styleActiveLine:true,
        lineNumbers:true,
        line:true,
        mode:'json',
        lineWrapping:true,
        theme:'default'
      },
      codeView: ''
    }
  },
  computed: {
    codemirror () {
      return this.$refs.codeEditor.codemirror
    }
  },
  created() {
  },
  methods: {
    set(data){
      this.open = true;
      this.options.mode = data.mode;
      this.codeView = data.code;
      this.getTheme();
      this.formatCode();

      this.$nextTick(() => {
        let editor = this.$refs.codeEditor.codemirror;
        let clientHeight = document
          .getElementById('editor-code-id')
          .querySelector('.vue-codemirror')
          .offsetHeight;
        editor.setSize('auto',parseFloat(clientHeight));
      });
    },    getTheme(){
      let code_theme = window.localStorage.getItem('code_theme');

      if(code_theme){
        this.options.theme = code_theme;//monokai
      }
    },
    setTheme(theme){
      window.localStorage.setItem('code_theme',theme);
      this.getTheme();
    },
    formatCode() {
      // 使用缩进来格式化XML代码
      // 更新编辑器的内容以显示格式化后的XML
      if(this.options.mode === 'htmlmixed' || this.options.mode === 'xml'){
        this.codeView = jsBeautify.html(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'css' || this.options.mode === 'sass'){
        this.codeView = jsBeautify.css(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'json'){
        this.codeView = jsBeautify.json(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'php'){
        this.codeView = jsBeautify.php(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'go'){
        this.codeView = jsBeautify.go(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'vue'){
        this.codeView = jsBeautify.vue(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }else if(this.options.mode === 'javascript'){
        this.codeView = jsBeautify.javascript(this.codeView, {
          indent_size: 2, // 自定义缩进
          wrap_line_length: 80, // 可选：换行
        });
      }

    },
    submitCode(){
      this.open = false;
      this.$emit('selection',this.codeView)
    }
  }
};
</script>

<style scoped lang="scss">

</style>
