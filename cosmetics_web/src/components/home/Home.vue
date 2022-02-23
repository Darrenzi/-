<template>
  <div class="container">
    <loading :loading="loading" />
    <div class="home-top">
      <el-image
        class="home-bg"
        style="position: absolute"
        :src="require('../../assets/home_bg.gif')"
        fit="cover"
      ></el-image>
      <div class="navigation bcolor">
        <div class="navigation-icon">
          <div>Cosmetics<span class="sbgcolor"> Volunteer</span></div>
          美妆护肤产品志愿者测试系统
        </div>
        <div class="navigation-options">
          <!-- <div class="navigation-option">作品</div> -->
          <div
            class="navigation-option"
            @click="goLogin"
            v-if="currentUser == null"
          >
            登录/注册
          </div>
          <div
            class="navigation-option"
            v-if="currentUser != null"
            @click="updatePerson"
          >
            欢迎，{{
              currentUser.role == 0
                ? "管理员"
                : currentUser.role == 1
                ? "研发员"
                : currentUser.role == 2
                ? "测试员"
                : "招募员"
            }}
          </div>
        </div>
      </div>
      <div class="home-title">Cosmetics Volunteer</div>
    </div>

    <div class="home-main">
      <div class="home-main-left hidden-md-and-down">
        <!-- <div class="main-left-title fcolor">
          关键词搜索
          <div class="main-left-title-us">
            <em>Search Something By keywords</em>
          </div>
        </div>
        <div class="search">
          <el-input></el-input>
          <el-button type="primary" style="margin-left: 10px">搜索</el-button>
        </div> -->
        <div class="main-left-title fcolor">
          数据类别
          <div class="main-left-title-us">
            <em>Data Category </em>
          </div>
        </div>
        <div class="main-left-options">
          <div
            class="main-left-option"
            :class="currentShow == 1 ? 'main-left-option-choose' : 'fcolor'"
            @click="changeData(1)"
            v-if="currentUser.role < 3"
          >
            测试需求
          </div>
          <div
            class="main-left-option"
            :class="currentShow == 2 ? 'main-left-option-choose' : 'fcolor'"
            @click="changeData(2)"
          >
            测试方案
          </div>
          <div
            class="main-left-option"
            :class="currentShow == 3 ? 'main-left-option-choose' : 'fcolor'"
            @click="changeData(3)"
            v-if="currentUser.role < 3"
          >
            测试报告
          </div>
          <div
            class="main-left-option"
            :class="currentShow == 0 ? 'main-left-option-choose' : 'fcolor'"
            @click="changeData(0)"
            v-if="currentUser.role == 0"
          >
            用户
          </div>
        </div>
        <div class="main-left-title fcolor" v-if="currentUser.role < 2">
          可选操作
          <div class="main-left-title-us">
            <em>Optional Operation</em>
          </div>
        </div>
        <div class="main-left-options" v-if="currentUser.role < 2">
          <div class="main-left-option fcolor" @click="addRecord">新增</div>
          <!-- <div class="main-left-option fcolor">删除</div>  -->
        </div>
      </div>

      <div class="home-main-right">
        <transition name="el-zoom-in-top">
          <div class="main-right-container" v-show="currentShow == 1">
            <el-empty
              description="暂无数据"
              v-show="tests.length == 0"
            ></el-empty>
            <div
              class="test-card"
              v-for="(item, index) in tests"
              :key="item.id"
            >
              <el-descriptions
                class="margin-top"
                :title="'产品' + item.number + '的测试需求'"
                :column="4"
                border
              >
                <template slot="extra">
                  <el-button size="small" @click="commitSolution(item)"
                    >提交测试方案</el-button
                  >
                  <el-button
                    size="small"
                    @click="updateTest(index)"
                    v-if="currentUser.role < 2"
                    >更新</el-button
                  >
                  <!-- <el-button size="small" @click="deleteRecord('test',item.id)">删除</el-button> -->
                </template>
                <el-descriptions-item>
                  <template slot="label"> 编号 </template>
                  {{ item.number }}
                </el-descriptions-item>
                <el-descriptions-item span="2">
                  <template slot="label"> 产品名 </template>
                  {{ item.name }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> 适合年龄 </template>
                  {{ item.age }} 岁
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label" span="1"> 研发员工号 </template>
                  {{ item.researcherId }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> 研发员 </template>
                  {{ item.researcher }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> 测试员工号 </template>
                  {{ item.testorId }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> 测试员 </template>
                  {{ item.testor }}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label"> 测试功效 </template>
                  {{ item.effect }}
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </transition>

        <!-- 测试方案 -->
        <transition name="el-zoom-in-top">
          <div class="main-right-container" v-show="currentShow == 2">
            <el-empty
              description="暂无数据"
              v-show="solutions.length == 0"
            ></el-empty>
            <div
              class="article"
              v-for="item in solutions"
              :key="item.id"
              v-show="currentShow != 0"
            >
              <div class="article-right">
                <div class="article-title">
                  产品编号 "{{ item.number }}" 的测试方案
                  <div
                    class="article-status"
                    :class="
                      item.status == -1
                        ? 'article-status-fail'
                        : item.status == 0
                        ? 'article-status-wait'
                        : item.status == 1
                        ? 'article-status-pass'
                        : 'article-status-finish'
                    "
                  >
                    {{
                      item.status == -1
                        ? "未通过"
                        : item.status == 0
                        ? "未审核"
                        : item.status == 1
                        ? "已通过"
                        : "已完成"
                    }}
                  </div>
                </div>
                <div class="article-msg">
                  研发 {{ item.testor }} · 测试 {{ item.researcher }} ·
                  {{ item.createAt }}
                </div>
                <div class="article-content">{{ item.content }}</div>
                <div class="article-label"># 美妆产品</div>
                <div class="article-btn">
                  <div
                    class="article-detail fcolor"
                    :class="
                      articleMouseIdx == item.id && mouseOverType == 'detail'
                        ? 'scolor'
                        : ''
                    "
                    @click="goDetail(item.id, 'detail')"
                    @mouseover="articleMouseOver(item.id, 'detail')"
                    @mouseleave="articleMouseOver(-1)"
                  >
                    查看/审核{{
                      articleMouseIdx == item.id && mouseOverType == "detail"
                        ? " ▶"
                        : ""
                    }}
                  </div>
                  <div
                    class="article-detail fcolor"
                    :class="
                      articleMouseIdx == item.id && mouseOverType == 'volunteer'
                        ? 'scolor'
                        : ''
                    "
                    @click="goDetail(item.id, 'volunteer')"
                    @mouseover="articleMouseOver(item.id, 'volunteer')"
                    @mouseleave="articleMouseOver(-1)"
                    v-show="item.status == 1"
                  >
                    添加志愿者{{
                      articleMouseIdx == item.id && mouseOverType == "volunteer"
                        ? " ▶"
                        : ""
                    }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </transition>

        <!-- 测试报告 -->
        <transition name="el-zoom-in-top">
          <div class="main-right-container" v-show="currentShow == 3">
            <el-empty
              description="暂无数据"
              v-show="reportTotal == 0"
            ></el-empty>
            <div
              class="article"
              v-for="item in reports"
              :key="item.id"
              v-show="currentShow != 0"
            >
              <div class="article-right">
                <div class="article-title">
                  产品编号 "{{ item.number }}" 的测试报告
                </div>
                <div class="article-msg">
                  研发 {{ item.testor }} · 测试 {{ item.researcher }} ·
                  {{ item.createAt }}
                </div>
                <div class="article-content">{{ item.report }}</div>
                <div class="article-label"># 测试报告</div>
                <div class="article-btn">
                  <div
                    class="article-detail fcolor"
                    :class="
                      articleMouseIdx == item.id && mouseOverType == 'report'
                        ? 'scolor'
                        : ''
                    "
                    @click="goDetail(item.id, 'report')"
                    @mouseover="articleMouseOver(item.id, 'report')"
                    @mouseleave="articleMouseOver(-1)"
                  >
                    报告详情{{
                      articleMouseIdx == item.id && mouseOverType == "report"
                        ? " ▶"
                        : ""
                    }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </transition>

        <!-- 用户信息 -->
        <transition name="el-zoom-in-top">
          <div class="main-right-container" v-show="currentShow == 0">
            <el-table :data="users" style="width: 100%">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form
                    label-position="left"
                    inline
                    class="user-table-expand"
                  >
                    <el-form-item label="工号">
                      <span>{{ props.row.id }}</span>
                    </el-form-item>
                    <el-form-item label="姓名">
                      <span>{{ props.row.username }}</span>
                    </el-form-item>
                    <el-form-item label="性别">
                      <span>{{ props.row.gender == 0 ? "女" : "男" }}</span>
                    </el-form-item>
                    <el-form-item label="岗位">
                      <span>{{
                        props.row.role == 0
                          ? "管理员"
                          : props.row.role == 1
                          ? "研发员"
                          : props.row.role == 2
                          ? "测试员"
                          : "招募员"
                      }}</span>
                    </el-form-item>
                    <el-form-item label="手机号">
                      <span>{{ props.row.phone }}</span>
                    </el-form-item>
                    <el-form-item label="邮箱">
                      <span>{{ props.row.email }}</span>
                    </el-form-item>
                    <el-form-item label="更新日期">
                      <span>{{ props.row.updateAt }}</span>
                    </el-form-item>
                    <el-form-item label="创建日期">
                      <span>{{ props.row.createAt }}</span>
                    </el-form-item>
                    <el-form-item>
                      <el-button
                        style="width: 150px"
                        @click="updateUser(props.$index)"
                        >更新</el-button
                      >
                      <!-- <el-button
                        style="width: 150px"
                        @click="deleteRecord('user', props.row.id)"
                        >删除</el-button
                      > -->
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column prop="createAt" label="创建日期" width="180">
              </el-table-column>
              <el-table-column prop="id" label="工号" width="180">
              </el-table-column>
              <el-table-column prop="username" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="phone" label="电话号"> </el-table-column>
            </el-table>
          </div>
        </transition>

        <el-pagination
          background
          layout="prev, pager, next"
          :total="
            currentShow == 0
              ? userTotal
              : currentShow == 1
              ? testTotal
              : currentShow == 2
              ? solutionTotal
              : reportTotal
          "
          v-show="
            (currentShow == 0 && userTotal != 0) ||
            (currentShow == 1 && testTotal != 0) ||
            (currentShow == 2 && solutionTotal != 0) ||
            (currentShow == 3 && reportTotal != 0)
          "
          style="margin-top: 30px"
          @current-change="changePage"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 更新/新增用户对话框 -->
    <el-dialog title="更新/新增用户" :visible.sync="userFormVisible">
      <el-form :model="userForm">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input
            v-model="userForm.password"
            autocomplete="off"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="岗位" :label-width="formLabelWidth">
          <el-radio v-model="userForm.role" label="0">管理员</el-radio>
          <el-radio v-model="userForm.role" label="1">研发员</el-radio>
          <el-radio v-model="userForm.role" label="2">测试员</el-radio>
          <el-radio v-model="userForm.role" label="3">招募员</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRecord('user', userForm)"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 新增/更新测试需求 -->
    <el-dialog title="新增/更新测试需求" :visible.sync="testFormVisible">
      <el-form :model="testForm">
        <el-form-item label="产品编号" :label-width="formLabelWidth">
          <el-input v-model="testForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品名" :label-width="formLabelWidth">
          <el-input v-model="testForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="适合年龄" :label-width="formLabelWidth">
          <el-input v-model="testForm.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="研发工号" :label-width="formLabelWidth">
          <el-input
            v-model="testForm.researcherId"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="研发员" :label-width="formLabelWidth">
          <el-input
            v-model="testForm.researcher"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="测试工号" :label-width="formLabelWidth">
          <el-input v-model="testForm.testorId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="测试员" :label-width="formLabelWidth">
          <el-input
            v-model="testForm.testor"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="测试功效" :label-width="formLabelWidth">
          <el-input
            type="textarea"
            :rows="3"
            v-model="testForm.effect"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="testFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRecord('test', testForm)"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 提交测试方案对话框 -->
    <el-dialog title="提交测试方案" :visible.sync="solutionFormVisible">
      <el-form :model="solutionForm">
        <el-form-item label="产品编号" :label-width="formLabelWidth">
          <el-input
            v-model="solutionForm.number"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="研发工号" :label-width="formLabelWidth">
          <el-input
            v-model="solutionForm.researcherId"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="研发员" :label-width="formLabelWidth">
          <el-input
            v-model="solutionForm.researcher"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="测试工号" :label-width="formLabelWidth">
          <el-input
            v-model="solutionForm.testorId"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="测试员" :label-width="formLabelWidth">
          <el-input
            v-model="solutionForm.testor"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="测试方案" :label-width="formLabelWidth">
          <el-input
            type="textarea"
            :rows="10"
            v-model="solutionForm.content"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="solutionFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRecord('solution', solutionForm)"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 个人信息 -->
    <el-dialog
      title="个人信息"
      :visible.sync="personFormVisible"
      v-if="currentUser != null"
    >
      <el-form :model="personForm">
        <el-form-item label="权限" :label-width="formLabelWidth">
          {{
            currentUser.role == 0
              ? "管理员"
              : currentUser.role == 1
              ? "研发员"
              : currentUser.role == 2
              ? "测试员"
              : "招募员"
          }}
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="personForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input
            v-model="personForm.password"
            autocomplete="off"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="personForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="personForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="personFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="changePerson">确 定</el-button>
      </div>
    </el-dialog>

    <div class="main-bottom bcolor">
      <em
        >©2021-2022 Cosmetics <span class="scolor">粤ICP备202110000号</span></em
      >
      <div class="line"></div>
      <em>
        Power By
        <span class="scolor">Spring</span> & <span class="scolor">Vue</span> &
        <span class="scolor">ElementUI</span></em
      >
    </div>
  </div>
</template>

<script>
import Home from "./Home.js";
export default Home;
</script>

<style scoped>
@import "./Home.css";
@import "element-ui/lib/theme-chalk/display.css";
</style>