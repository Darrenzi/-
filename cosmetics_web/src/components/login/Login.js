import Loading from '../Loading.vue'
import Status from '../../common/status'
import Qs from 'qs'
export default {
    components: {
        Loading
    },
    name: "Login",
    data: function() {
        return {
            loading: false,

            user: {
                un: "",
                pd: ""
            }
        }
    },
    methods: {
        login: function() {
            this.$http.post("/api/user/login", Qs.stringify(this.user))
                .then(res => {
                    console.log(res);
                    if (res.data.code == Status.SUCCESS) {
                        this.$router.push("/");
                    } else {
                        this.$message.error(res.data.desc);
                    }
                })
                .catch(err => {
                    this.$message.error(err.data);
                })
        },
        goHome: function() {
            this.$router.push("/");
        }
    }
};