import Loading from '../Loading.vue'
import util from '../../common/util'
import Status from '../../common/status'
import Qs from 'qs'
export default {
    components: {
        Loading
    },
    data: function() {
        return {
            currentPage: 1,
            updateFlag: false,
            currentUser: null,
            loading: false,
            currentShow: 2,
            userTotal: 0,
            testTotal: 0,
            solutionTotal: 0,
            reportTotal: 0,
            articleMouseIdx: -1,
            mouseOverType: '',
            personFormVisible: false,
            userFormVisible: false,
            solutionFormVisible: false,
            testFormVisible: false,
            formLabelWidth: '80px',
            personForm: {
                username: "",
                password: "",
                phone: "",
                email: "",
            },
            solutionForm: {
                testId: 1,
                number: "",
                status: "",
                desc: "",
                researcherId: "",
                researcher: "",
                testorId: "",
                testor: "",
                updateAt: "",
                content: ""
            },
            userForm: {
                username: "",
                password: "",
                role: "",
                phone: "",
                email: ""
            },
            testForm: {
                number: "",
                name: "",
                age: "",
                effect: ``,
                researcherId: "",
                researcher: "",
                testId: "",
                testor: ""
            },
            tests: [],
            reports: [],
            solutions: [],
            users: [],
            researchers: [],
            testors: [],
            researcherChoose: null,
            testorChoose: null,
        };
    },
    methods: {
        getPage: function(api, page, prop, total) {
            this.loading = true;
            this.$http.get(api, { params: { num: page, size: 10 } })
                .then(res => {
                    this[prop] = res.data.data.records;
                    this[total] = res.data.data.total;
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        changePage: function(page) {
            this.currentPage = page;
            //换页
            switch (this.currentShow) {
                case 0:
                    {
                        this.getPage("/api/user/page", page, 'users', 'userTotal')
                        break;
                    }
                case 1:
                    {
                        this.getPage("/api/test/page", 1, 'tests', 'testTotal');

                        break;
                    }
                case 2:
                    {
                        this.getPage("/api/solution/page", 1, 'solutions', 'solutionTotal');
                        break;
                    }
                case 3:
                    {
                        this.getPage("/api/solution/report/page", 1, 'reports', 'reportTotal');
                        break;
                    }
            }
        },
        goLogin: function() {
            this.$router.push("/login");
        },
        goDetail: function(id, type) {
            this.$router.push("/detail?id=" + id + "&type=" + type);
        },
        changeData: function(idx) {
            this.currentShow = idx;
        },
        articleMouseOver: function(idx, type) {
            this.articleMouseIdx = idx;
            this.mouseOverType = type;
        },
        deleteRecord: function(path, id) {
            let prefix = "/api/" + path;
            this.$http.post(prefix + "/delete", Qs.stringify({ id: id })).then(res => {
                if (res.data.code == Status.SUCCESS) {
                    this.getPage(prefix + "/page", this.currentPage, path + "s", path + "total")
                    this.$message({
                        message: "操作成功",
                        type: 'success'
                    });
                } else {
                    this.$message.error(res.data.desc);
                }
            }).catch(err => {
                this.$message.error(err);
            })
        },
        saveRecord: function(path, params) {
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
                    this.getPage(prefix + "/page", this.currentPage, path + "s", path + "total")
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
        addRecord: function() {
            if (this.currentShow == 0) {
                this.userForm = {
                    username: "",
                    role: "",
                    phone: "",
                    email: ""
                }
                this.userFormVisible = true;
            }

            if (this.currentShow == 1) {
                this.testForm = {
                    number: "",
                    name: "",
                    age: "",
                    effect: ``,
                    researcher: "",
                    testor: ""
                };
                this.testFormVisible = true;

                // this.$http.get("/api/user/role", { params: { role: 1 } }).then(res => {
                //     if (res.data.code == Status.SUCCESS) {
                //         this.researchers = res.data.data;
                //     } else {
                //         this.$message.error("获取研发员失败," + res.data.desc);
                //     }
                // }).catch(err => {
                //     this.$message.error("获取研发员失败," + err);
                // })
            }
        },
        commitSolution: function(item) {
            this.solutionForm = {
                testId: item.id,
                number: item.number,
                researcherId: item.researcherId,
                researcher: item.researcher,
                testorId: item.testorId,
                testor: item.testor,
                content: ""
            }
            this.solutionFormVisible = true;
        },
        updatePerson: function() {
            this.personForm = {
                id: this.currentUser.id,
                username: this.currentUser.username,
                role: this.currentUser.role,
                email: this.currentUser.email,
                phone: this.currentUser.phone,
                password: this.currentUser.password
            };
            this.updateFlag = true;
            this.personFormVisible = true;
        },
        changePerson: function() {
            this.loading = true;
            this.personFormVisible = false;
            this.$http.post("/api/user/update", Qs.stringify(this.personForm))
                .then(res => {
                    if (res.data.code == Status.SUCCESS) {
                        this.$message({
                            type: "success",
                            message: "修改个人信息成功"
                        })
                        util.checkLogin(this);
                    } else {
                        this.$message.error(res.data.desc);
                    }
                })
                .catch(err => {
                    this.$message.error(err);
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        updateUser: function(idx) {
            let user = this.users[idx];
            this.userForm = {
                id: user.id,
                username: user.username,
                role: user.role,
                phone: user.phone,
                email: user.email
            };
            this.updateFlag = true;
            this.userFormVisible = true;
        },
        updateTest: function(idx) {
            let test = this.tests[idx]
            this.testForm = {
                id: test.id,
                number: test.number,
                name: test.name,
                age: test.age,
                effect: test.effect,
                researcherId: test.researcherId,
                researcher: test.researcher,
                testorId: test.testorId,
                testor: test.testor
            };
            this.updateFlag = true;
            this.testFormVisible = true;
        },
        changeResearcher: function(id) {
            for (let i = 0; i < this.researchers.length; i++) {
                if (this.researchers[i].id == id) this.researcherChoose = this.researchers[i];
            }
        },
        changeTestor: function(id) {
            for (let i = 0; i < this.testors.length; i++) {
                if (this.testors[i].id == id) this.testorChoose = this.testors[i];
            }
        },
        closeTestDialog: function() {
            this.testorChoose = null;
            this.researcherChoose = null;
        }
    },
    created: function() {
        util.checkLogin(this);
    },
    mounted: function() {
        this.getPage("/api/user/page", 1, 'users', 'userTotal');
        this.getPage("/api/test/page", 1, 'tests', 'testTotal');
        this.getPage("/api/solution/page", 1, 'solutions', 'solutionTotal');
        this.getPage("/api/solution/report/page", 1, 'reports', 'reportTotal');

        this.$http.get("/api/user/role", { params: { role: 1 } })
            .then(res => {
                this.researchers = res.data.data;
            })

        this.$http.get("/api/user/role", { params: { role: 2 } })
            .then(res => {
                this.testors = res.data.data;
            })
    }
}