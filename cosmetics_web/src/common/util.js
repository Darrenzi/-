import Status from './status'
/**
 * instance： 组件实例
 * 检查用户是否登录
 */
var checkLogin = function(instance) {
    if (typeof(instance.loading) != "undefined") {
        instance.loading = true;
    }
    instance.$http.post("/api/user/check")
        .then(res => {
            if (res.data.code == Status.SUCCESS) {
                instance.currentUser = res.data.data;
            } else {
                instance.$router.push('/login');
            }
        })
        .catch(err => {
            this.$message.error(err.data);
            instance.$router.push('/login');
        })
        .finally(() => {
            if (typeof(instance.loading) != "undefined") {
                instance.loading = false;
            }
        })
}

export default {
    checkLogin
}