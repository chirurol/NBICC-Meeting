import { createRouter, createWebHistory } from 'vue-router'
import request from '@/request/request'
const routes = [
  {
    path: '/login',
    name: '登陆',
    component: ()=>import('@/views/login/index'),
  },
  {
    path: '/',
    name: '菜单栏',
    component: ()=>import('@/views/index'),
    redirect: '/home',
    children:[
      {
        path: '/home',
        name: '首页',
        component: ()=>import('@/views/homeData/index'),
      }
    ]
  },
  {
    path: '/basic',
    name: '订单管理',
    component: ()=>import('@/views/index'),
    children:[
      {
        path: '/itemShow',
        name: '订单录入',
        component: ()=>import('@/views/basic/itemShow/index'),
      },
      {
        path: '/itemType',
        name: '订单查询',
        component: ()=>import('@/views/basic/itemType/index'),
      },
      {
        path: '/recordShow',
        name: '订单文档',
        component: ()=>import('@/views/basic/recordShow/index'),
      },
      {
        path: '/lookData',
        name: '订单审批流',
        component: ()=>import('@/views/basic/lookData/index'),
      }
    ],
  },
  {
    path: '/sys',
    name: '权限设置',
    component: ()=>import('@/views/index'),
    children:[
      {
        path: '/auth',
        name: '权限管理',
        component: ()=>import('@/views/sys/auth/index'),
      },
      {
        path: '/role',
        name: '角色管理',
        component: ()=>import('@/views/sys/role/index'),
      },
      {
        path: '/mans',
        name: '人员管理',
        component: ()=>import('@/views/sys/mans/index'),
      }
    ],
  },
  {
    path: '/sett',
    name: '系统设置',
    component: ()=>import('@/views/index'),
    children:[
      {
        path: '/pwd',
        name: '修改密码',
        component: ()=>import('@/views/sys/updatePwd/index'),
      },
      {
        path: '/perPwd',
        name: '个人密码',
        component: ()=>import('@/views/sys/perPwd/index'),
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.path!='/login'){ //需要登录
    let token = localStorage.getItem('token')
    let logindto = {}
    logindto.token=token
    logindto.urlpath=to.path
    if(token!=null){ //登录过
      //验证token
      request.post("/user/checkToken",token).then(res=>{
        //成功
        if(res.statusCode == '200'){
          // next()
          if(to.path=='/' || to.path=='/home'){
            next()
          }else {
            request.post("/user/checkURL",logindto).then(res=>{
              if(res.statusCode == '200'){
                next()
              }else{
                next({path:'/login'})
              }
            })
          }

        }else{
          next({path:'/login'})
        }

      }).catch(()=>{
        next({path:'/login'})
      })

    }else{ //没有登录
      next({path:'/login'})
    }

  }else{ //不需要登录
    next()
  }
})

export default router
