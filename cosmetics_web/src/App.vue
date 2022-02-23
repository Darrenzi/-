<template>
  <div id="app">
    <transition name="el-zoom-in-center">
      <router-view />
    </transition>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
::-webkit-scrollbar {
  display: none; /*Chrome Safari*/
}
html,
body {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  /* 隐藏滚动条 */
  scrollbar-width: none; /* firefox */
  -ms-overflow-style: none; /*8IE 10+*/
  overflow-x: hidden;
  overflow-y: visible;
}
#app {
  width: 100%;
  height: 100%;
}
.container {
  width: 100%;
  height: 100%;
  position: relative;
}
/* 背景主色 */
.bcolor {
  background: rgb(42, 20, 48);
}
/* 文字主色 */
.fcolor {
  color: rgb(140, 68, 160);
}
/* 强调颜色 */
.scolor {
  color: rgb(255, 74, 90);
}
.sbgcolor {
  background: rgb(255, 74, 90);
}
.line {
  width: 90%;
  height: 1px;
  margin-top: 10px;
  margin-bottom: 10px;
  background: ghostwhite;
}
.el-form-item__label {
  font-weight: bold;
}
.navigation {
  position: fixed;
  top: 0;
  width: 100%;
  display: flex;
  align-items: center;
  color: white;
  padding-top: 20px;
  padding-bottom: 10px;
  z-index: 10;
}

.navigation-icon {
  margin-right: 100px;
  margin-left: 20px;
  font-size: 12px;
  padding: 5px;
  cursor: pointer;
}

.navigation-icon div {
  font-weight: bolder;
  font-size: 20px;
}

.navigation-options {
  display: flex;
  position: absolute;
  right: 30px;
}

.navigation-option {
  margin-left: 10px;
  margin-right: 10px;
  cursor: pointer;
}

.navigation-option:hover {
  border-bottom: solid 2px;
  color: rgb(255, 74, 90);
}
</style>
