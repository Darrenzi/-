<template>
  <div class="loading-container" v-show="loading">
    <transition name="el-fade-in-linear">
      <div class="loading-bg" v-show="loading">
        <el-image
          class="loading"
          :src="require('../assets/loading.gif')"
          fit="cover"
        ></el-image>
        Loading...
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Loading",
  props: ["loading"],
  methods: {},
};
</script>

<style>
.loading-container {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  background: rgba(255, 0, 0, 0);
  color:gray;
}
.loading-bg {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: white;
  flex-direction: column;
}
.loading {
  width: 300px;
}
</style>
