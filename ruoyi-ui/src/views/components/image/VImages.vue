<template>
  <div class="app-container">
    <el-upload
      action="https://jsonplaceholder.typicode.com/posts/"
      list-type="picture-card"
      :auto-upload="true"
      accept="image/*"
      :file-list="fileList"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :on-error="handleError"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" width="900px" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "VImages",
  props: {
    fileList: {
      type: Array
    },
    images:{
      type: Array
    }
  },
  data() {
    return {
      files:[],
      dialogImageUrl: '',
      dialogVisible: false
    }
  },
  created() {
  },
  methods: {
    wrap(fileList){
      console.log(fileList)
      let that = this;
      that.files = [];
      for(let i=0;i<fileList.length;i++){
        if(fileList[i].raw){
          this.asyncImgChecked(fileList[i]).then(data => {
            let fileSize = fileList[i].raw.size / 1024; //KB
            fileSize = Math.floor(fileSize * 1000) / 1000; // 小数取整后三位
            let suffix = fileList[i].raw.name.split('.').pop().toLocaleLowerCase();
            that.files[that.files.length] = {
              url: fileList[i].url,
              name: fileList[i].raw.name,
              size: fileSize,
              mime: fileList[i].raw.type,
              width: data.width,
              height: data.height,
              suffix: suffix
            };
            console.log(this.files)
            this.$emit('update:images',that.files)
          });
        }else{
          that.files[that.files.length] = fileList[i];
          console.log(this.files)
          this.$emit('update:images',that.files)
        }


      }
    },
    asyncImgChecked(file) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader();
        reader.readAsDataURL(file.raw); // 必须用file.raw
        reader.onload = () => { // 让页面中的img标签的src指向读取的路径
          let img = new Image();
          img.src = reader.result;
          img.onload = () => {
            resolve({width:img.width,height:img.height});
          }
        };
      })
    },
    handleSuccess(response, file, fileList){
      this.wrap(fileList);
    },
    handleError(err, file, fileList){
      this.$message.error('文件['+file.name+']上传失败。');
    },
    handleRemove(file, fileList) {
      this.wrap(fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
}
</script>

<style scoped lang="scss">

</style>
