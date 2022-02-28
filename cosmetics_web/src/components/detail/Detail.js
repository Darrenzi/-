import Loading from '../Loading.vue'
import Status from '../../common/status'
import Qs from 'qs'
import util from '../../common/util'
export default {
    name: "Detail",
    components: {
        Loading
    },
    data: function() {
        return {
            currentPage: 1,
            updateFlag: false,
            loading: false,
            id: 0,
            type: 'detail',
            solutionFormVisible: false,
            volunteerFormVisible: false,
            testFormVisible: false,
            formLabelWidth: '80px',
            volunteerTotal: 10,
            volunteerForm: {
                id: 0,
                solutionId: "",
                name: "",
                sex: "",
                age: "",
                skin: "",
                sensibility: "",
                spot: "", //是否长斑
                acne: "", //是否痤疮
                phone: "",
                other: ""
            },
            volunteers: [],
            solutionForm: {
                id: "",
                testId: "",
                number: "",
                status: "",
                failReason: "",
                researcher: "",
                testor: "",
                updateAt: "",
                content: ""
            },
            solution: {}
        }
    },
    methods: {
        goHome: function() {
            this.$router.push("/");
        },
        initData: function(page) {
            this.loading = true;
            //获取方案数据
            this.$http.get("/api/solution/get", { params: { id: this.id } }).then(res => {
                    if (res.data.code == Status.SUCCESS) {

                        this.solution = res.data.data;

                    } else {
                        this.$message.error(res.data.desc);
                    }
                }).catch(err => {
                    this.$message.error(err);
                })
                .finally(() => {
                    this.loading = false;
                })
            if (this.type == "volunteer") {
                this.$http.get("/api/volunteer/page", { params: { num: page, size: 10 } })
                    .then(res => {
                        this['volunteers'] = res.data.data.records;
                        this['volunteerTotal'] = res.data.data.total;
                    })
                    .catch(err => {
                        this.$message.error("获取志愿者信息失败" + err);
                    })
                    .finally(() => {
                        this.loading = false;
                    })
            }
        },
        changeDetail: function() {
            this.solutionFormVisible = true;
            this.solutionForm = {
                testId: this.solution.testId,
                number: this.solution.number,
                status: this.solution.status,
                failReason: this.solution.failReason,
                researcher: this.solution.researcher,
                testor: this.solution.testor,
                content: this.solution.content,
                report: this.solution.report
            }
        },
        addVolunteer: function() {
            this.volunteerForm = {
                name: "",
                sex: "",
                age: "",
                skin: "",
                sensibility: "",
                spot: "", //是否长斑
                acne: "", //是否痤疮
                phone: "",
                other: "",
            };
            this.volunteerFormVisible = true;
        },
        deleteVolunteer: function(id) {
            this.loading = true;
            this.$http.post("/api/volunteer/delete", Qs.stringify({ id: id }))
                .then(res => {
                    if (res.data.code == Status.SUCCESS) {
                        this.initData(this.currentPage);
                    } else {
                        this.$message.error(res.data.desc);
                    }
                }).catch(err => {
                    this.$message.error(err);
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        updateVolunteer: function(idx) {
            let volunteer = this.volunteers[idx];
            this.updateFlag = true;
            this.volunteerForm = {
                id: volunteer.id,
                name: volunteer.name,
                sex: volunteer.sex,
                age: volunteer.age,
                skin: volunteer.skin,
                sensibility: volunteer.sensibility,
                spot: volunteer.spot, //是否长斑
                acne: volunteer.acne, //是否痤疮
                phone: volunteer.phone,
                other: volunteer.other,
            }

            this.volunteerFormVisible = true;
        },
        saveSolution: function() {
            this.loading = true;
            this.solutionFormVisible = false;
            this.solutionForm.id = this.solution.id;
            this.$http.post("/api/solution/update", Qs.stringify(this.solutionForm))
                .then(res => {
                    if (res.data.code == Status.SUCCESS) {
                        this.initData(this.currentPage);
                    } else {
                        this.$message.error(res.data.desc);
                    }
                }).catch(err => {
                    this.$message.error(err);
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        saveRecord: function(path, params) {
            params.solutionId = this.solution.id;
            this.loading = true;
            this[path + 'FormVisible'] = false;
            let prefix = "/api/" + path;
            let api;
            let updateFlag = this.updateFlag;
            if (!updateFlag) {
                api = prefix + "/add";
            } else {
                this.updateFlag = false;
                api = prefix + "/update";
            }
            this.$http.post(api, Qs.stringify(params)).then(res => {
                if (res.data.code == Status.SUCCESS) {
                    this.initData(this.currentPage);
                    this.$message({
                        message: "操作成功",
                        type: 'success'
                    });
                } else {
                    this.$message.error(res.data.desc);
                }
            }).catch(err => {
                this.$message.error(err);
            }).finally(() => {
                this.loading = false;
            })

        },
        changePage: function(page) {
            this.currentPage = page;
            this.initData(page);
        }
    },
    created: function() {
        var query = this.$route.query;
        this.type = query.type;
        this.id = query.id;

        util.checkLogin(this);
    },
    mounted: function() {
        this.initData(1);
    }
};