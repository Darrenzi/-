import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [{
            path: '/login',
            name: 'Login',
            component: () =>
                import ("../components/login/Login.vue")
        },
        {
            path: '/',
            name: 'Home',
            component: () =>
                import ("../components/home/Home.vue")
        },
        {
            path: '/detail',
            name: 'Detail',
            component: () =>
                import ("../components/detail/Detail.vue")
        }
    ]
})