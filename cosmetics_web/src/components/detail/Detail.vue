<template>
  <div class="container detail-container bcolor">
    <loading :loading="loading" />
    <div class="navigation bcolor">
      <div class="navigation-icon" @click="goHome">
        <div>Cosmetics<span class="sbgcolor"> Volunteer</span></div>
        美妆护肤产品志愿者测试系统
      </div>
    </div>

    <div class="detail-main">
      <div class="detail-main-left">
        <div class="detial-title">
          {{
            type == "detail"
              ? "产品测试方案"
              : type == "volunteer"
              ? "志愿者列表"
              : "测试报告"
          }}
          <div class="detail-btn">
            <div
              class="detail-update-btn"
              @click="changeDetail"
              v-show="type != 'volunteer'"
              v-if="currentUser.role < 3"
            >
              {{ type == "detail" ? "修改/审核" : "修改报告" }}
            </div>
            <div
              class="detail-update-btn"
              @click="uploadFormVisible = true"
              v-show="type == 'report'"
            >
              上传/更新附件
            </div>
            <!-- <div class="detail-update-btn" v-show="type=='report'" @click="type = 'volunteer'">
              志愿者列表
            </div> -->
            <div
              class="detail-update-btn"
              @click="addVolunteer"
              v-show="type == 'volunteer'"
            >
              新增志愿者
            </div>
            <!-- <div class="detail-update-btn" v-show="type=='volunteer'||type=='report'" @click="type = 'detail'">
              测试方案
            </div> -->
            <!-- <div class="detail-update-btn" v-show="type=='report'" @click="type = 'report'">
              测试报告
            </div> -->
          </div>
        </div>
        <div class="detail-left-title">
          产品编号
          <div class="detail-left-title-us">
            <em>Product Number</em>
          </div>
        </div>
        <div class="detail-left-item">{{ solution.number }}</div>
        <div class="detail-left-title" v-show="type == 'detail'">
          状态
          <div class="detail-left-title-us">
            <em>Solution Status</em>
          </div>
        </div>
        <div class="detail-left-item" v-show="type == 'detail'">
          {{
            solution.status == -1
              ? "未通过"
              : solution.status == 0
              ? "未审核"
              : solution.status == 1
              ? "已通过"
              : "已完成"
          }}
          <div class="fail-reason" v-show="solution.status == -1">
            {{ solution.failReason }}
          </div>
        </div>
        <div class="detail-left-title">
          研发员
          <div class="detail-left-title-us">
            <em>Research Personnel</em>
          </div>
        </div>
        <div class="detail-left-item">{{ solution.researcher }}</div>
        <div class="detail-left-title">
          测试员
          <div class="detail-left-title-us">
            <em>Testing Personnel</em>
          </div>
        </div>
        <div class="detail-left-item">{{ solution.testor }}</div>

        <div class="detail-left-title" v-show="solution.status != 0">
          审核时间
          <div class="detail-left-title-us">
            <em>Review Time</em>
          </div>
        </div>
        <div class="detail-left-item" v-show="solution.status != 0">
          {{ solution.updateAt }}
        </div>

        <div class="detail-left-title" v-show="type == 'report'">
          附件
          <div class="detail-left-title-us">
            <em>Other Attachments</em>
          </div>
        </div>
        <div
          class="detail-left-item"
          v-show="type == 'report'"
          :style="solution.accessory == '' ? '' : 'cursor: pointer'"
          @click="downFile"
        >
          {{ solution.accessory == "" ? "无" : "点击下载" }}
        </div>
      </div>

      <div class="detail-main-right">
        <transition name="el-zoom-in-top">
          <div class="detail-content-container" v-show="type == 'report'">
            <el-image
              v-if="solution.photo != ''"
              :src="STATIC_SERVER + solution.photo"
              fit="cover"
              style="width: 100%"
            ></el-image>
            {{ solution.report }}
            <el-empty
              description="暂无测试报告"
              v-show="solution.report == ''"
            ></el-empty>
          </div>
        </transition>

        <transition name="el-zoom-in-top">
          <div v-show="type == 'detail'">{{ solution.content }}</div>
        </transition>

        <transition name="el-zoom-in-top">
          <div
            class="volunteer-container detail-content-container"
            v-show="type == 'volunteer'"
          >
            <el-empty
              description="暂无志愿者"
              v-show="volunteers.length == 0"
            ></el-empty>
            <el-table
              :data="volunteers"
              style="width: 100%"
              v-show="volunteers.length != 0"
            >
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form
                    label-position="left"
                    inline
                    class="user-table-expand"
                  >
                    <el-form-item label="姓名">
                      <span>{{ props.row.name }}</span>
                    </el-form-item>
                    <el-form-item label="性别">
                      <span>{{ props.row.sex }}</span>
                    </el-form-item>
                    <el-form-item label="年龄">
                      <span>{{ props.row.age }}</span>
                    </el-form-item>
                    <el-form-item label="手机号">
                      <span>{{ props.row.phone }}</span>
                    </el-form-item>
                    <el-form-item label="肤质">
                      <span>{{ props.row.skin }}</span>
                    </el-form-item>
                    <el-form-item label="是否敏感肌">
                      <span>{{ props.row.sensibility }}</span>
                    </el-form-item>
                    <el-form-item label="是否长斑">
                      <span>{{ props.row.spot }}</span>
                    </el-form-item>
                    <el-form-item label="是否痤疮">
                      <span>{{ props.row.acne }}</span>
                    </el-form-item>
                    <el-form-item label="其他说明">
                      <span>{{
                        props.row.other == "" ? "无" : props.row.other
                      }}</span>
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
                        @click="updateVolunteer(props.$index)"
                        >更新</el-button
                      >
                      <el-button
                        style="width: 150px"
                        @click="deleteVolunteer(props.row.id)"
                        >删除</el-button
                      >
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column prop="createAt" label="日期" width="120">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="80">
              </el-table-column>
              <el-table-column prop="sex" label="性别" width="50">
              </el-table-column>
              <el-table-column prop="age" label="年龄" width="50">
              </el-table-column>
              <el-table-column prop="phone" label="电话号" width="120">
              </el-table-column>
              <el-table-column prop="skin" label="肤质"> </el-table-column>
            </el-table>

            <el-pagination
              background
              layout="prev, pager, next"
              :total="volunteerTotal"
              style="margin-top: 30px"
              v-show="volunteers.length != 0"
              @current-change="changePage"
            >
            </el-pagination>
          </div>
        </transition>
      </div>

      <!-- 志愿者修改对话框 -->
      <el-dialog
        title="新增/修改志愿者信息"
        :visible.sync="volunteerFormVisible"
      >
        <el-form :model="volunteerForm">
          <el-form-item label="姓名" :label-width="formLabelWidth">
            <el-input
              v-model="volunteerForm.name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth">
            <el-radio v-model="volunteerForm.sex" label="男">男</el-radio>
            <el-radio v-model="volunteerForm.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input v-model="volunteerForm.age" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input
              v-model="volunteerForm.phone"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="肤质" :label-width="formLabelWidth">
            <el-input
              v-model="volunteerForm.skin"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="敏感肌" :label-width="formLabelWidth">
            <el-radio v-model="volunteerForm.sensibility" label="是"
              >是</el-radio
            >
            <el-radio v-model="volunteerForm.sensibility" label="否"
              >否</el-radio
            >
          </el-form-item>
          <el-form-item label="长斑" :label-width="formLabelWidth">
            <el-radio v-model="volunteerForm.spot" label="是">是</el-radio>
            <el-radio v-model="volunteerForm.spot" label="否">否</el-radio>
          </el-form-item>
          <el-form-item label="痤疮" :label-width="formLabelWidth">
            <el-radio v-model="volunteerForm.acne" label="是">是</el-radio>
            <el-radio v-model="volunteerForm.acne" label="否">否</el-radio>
          </el-form-item>
          <el-form-item label="其他说明" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              :rows="3"
              v-model="volunteerForm.other"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="volunteerFormVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="saveRecord('volunteer', volunteerForm)"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <!-- 修改/审核对话框 -->
      <el-dialog
        :title="type == 'detail' ? '修改/审核测试方案' : '修改测试报告'"
        :visible.sync="solutionFormVisible"
      >
        <el-form :model="solutionForm">
          <el-form-item label="产品编号" :label-width="formLabelWidth">
            <el-input
              v-model="solutionForm.number"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="状态"
            :label-width="formLabelWidth"
            v-if="currentUser.role < 2 && type == 'detail'"
          >
            <el-radio v-model="solutionForm.status" label="-1">未通过</el-radio>
            <el-radio v-model="solutionForm.status" label="0">未审核</el-radio>
            <el-radio v-model="solutionForm.status" label="1">已通过</el-radio>
            <el-radio v-model="solutionForm.status" label="2">已完成</el-radio>
          </el-form-item>
          <el-form-item label="研发员" :label-width="formLabelWidth">
            <el-input
              v-model="solutionForm.researcher"
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
          <el-form-item
            label="未通过原因"
            label-width="100"
            v-if="currentUser.role < 2 && type == 'detail'"
          >
            <el-input
              type="textarea"
              :rows="3"
              v-model="solutionForm.failReason"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="测试方案"
            label-width="150"
            v-show="type == 'detail'"
          >
            <el-input
              type="textarea"
              :rows="15"
              v-model="solutionForm.content"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="测试报告"
            label-width="150"
            v-show="type == 'report'"
          >
            <el-input
              type="textarea"
              :rows="15"
              v-model="solutionForm.report"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="solutionFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveSolution">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="上传/更新附件" :visible.sync="uploadFormVisible">
        <div>
          上传现场图片
          <input
            type="file"
            ref="clearFile"
            @change="getFile($event, 'photo')"
            multiple="multiplt"
            class="add-file-right-input"
            style="margin-left: 70px"
            accept=".jpg,.jpeg,.png"
          />
        </div>
        <div style="margin-top: 20px">
          上传其他附件
          <input
            type="file"
            ref="clearFile"
            @change="getFile($event, 'accessory')"
            multiple="multiplt"
            class="add-file-right-input"
            style="margin-left: 70px"
            accept=".doc,.pdf"
          />
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="uploadFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="uploadFile">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Detail from "./Detail.js";
export default Detail;
</script>

<style>
@import "./Detail.css";
</style>
