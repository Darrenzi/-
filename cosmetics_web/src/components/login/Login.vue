<template>
  <div class="container login-container">
    <loading :loading="loading"/>
    <el-image
      class="login-bg"
      style="position: absolute"
      :src="require('../../assets/home_bg.gif')"
      fit="cover"
    ></el-image>
    <div class="login-box">
      <el-image
        class="login-icon"
        :src="require('../../assets/login_bg.jpg')"
        fit="cover"
      ></el-image>
      <div class="login-item">
        <div>用户名或电子邮箱</div>
        <el-input class="login-input" v-model="user.un"> </el-input>
      </div>
      <div class="login-item">
        <div>密码</div>
        <el-input class="login-input" v-model="user.pd" show-password>
        </el-input>
      </div>
      <el-button class="login-button" @click="login">登录</el-button>

      <!-- <div class="handler-box">
        <div class="handler-item">注册</div>
        |
        <div class="handler-item">忘记密码</div>
      </div> -->

      <div class="handler-item" @click="goHome">返回首页</div>
    </div>
  </div>
</template>

<script>
import Login from "./Login.js";
export default Login;
</script>

<style>
@import "./Login.css";
</style>
