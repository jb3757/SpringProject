import { createRouter, createWebHistory } from "vue-router";
import Main from "../views/Main.vue";
import Login from "../views/Login.vue";
import NaverLogin from "../views/NaverLogin.vue";
import Join from "../views/Join.vue";
import Survey from "../views/survey.vue";

import RecommendList from "../views/recommendList.vue";
import SearchList from "../views/searchList.vue";
import BestList from "../views/bestList.vue";
import NewList from "../views/newList.vue";
import bookDetail from "../views/bookDetail.vue";
import startest from "@/views/test.vue";
import Goods from "../views/goods.vue";
import Categories from "../views/categories.vue";

import booktestPage from "../views/bookTest.vue";
import cart from "../views/cart.vue";
import resultpage from "../views/paymentResult.vue";
import pay from "../views/pay.vue";

import qnaList from "../views/qnaList.vue";
import qnaWrite from "../views/qnaWrite.vue";
import qnaEdit from "../views/qnaEdit.vue";
import faq from "../views/faq.vue";

import eventPage from "../views/event.vue";
import attendance from "../views/attendance.vue";
import mypage from "../views/mypage.vue";
import axios from "axios";

import Admin from "../views/Admin.vue";
import AdminBookManage from "../components/adminBookManage.vue";
import bookInsert from "../components/bookInsert.vue";
import AdminUserManage from "../components/adminUserManage.vue";
import AdminQnaManage from "../components/adminQnaManage.vue";
import AdminOrderManage from "../components/adminOrderManage.vue";
import AdminUserAnalyze from "../components/adminUserAnalyze.vue";
import AdminBuyAnalyze from "../components/adminBuyAnalyze.vue";

import mypageOrderInfo from "../views/mypageOrderInfo.vue";
import mypageUserInfo from "../views/mypageUserInfo.vue";
import mypageReview from "../views/mypageReview.vue";
import mypageGrade from "@/views/mypageGrade.vue";

const requireLogin = () => (to, from, next) => {
    //로그인안하고 접근하려했을때 실행할 함수?
    if (localStorage.getItem("userID") !== null) {
        //localStorage에 데이터 있으면
        return next(); //접근가능
    }
    next("/login"); //localStorage에 데이터 없으면 로그인창으로 리다이렉트
};
const requireSurvey = () => (to, from, next) => {
    if (!localStorage.getItem("userID")) {
        return next();
    }
    axios({
        url: "http://localhost:3000/sur/check",
        method: "GET",
        params: { userEmail: localStorage.getItem("userID") },
    })
        .then((res) => {
            if (res.data == "설문조사안함") {
                next("/survey");
            } else {
                return next();
            }
        })
        .catch((err) => {
            console.log(err);
        });
};
const getQna = () => (to, from, next) => {
    axios({
        url: "http://localhost:3000/qna/getCountQna",
        method: "post",
    }).then((res) => {
        // const qnaCount = res.data[0].count;
        console.log(res.data.allC);
        sessionStorage.setItem("qnaCount", res.data.allC);
        sessionStorage.setItem("qnaW", res.data.WC);
        sessionStorage.setItem("qnaF", res.data.FC);
        return next();
    });
};
// beforeEnter: requireLogin(),  <<로그인한 사람만 들어갈 수 있는 페이지 component아래에 붙여넣으면됨
const routes = [
    {
        path: "/",
        name: "Main",
        component: Main,
        beforeEnter: requireSurvey(),
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    },
    {
        path: "/naverlogin",
        name: "naverlogin",
        component: NaverLogin,
    },
    {
        path: "/join",
        name: "Join",
        component: Join,
    },
    {
        path: "/survey",
        name: "Survey",
        component: Survey,
        beforeEnter: requireLogin(),
    },
    {
        path: "/test",
        name: "testAPIPage",
        component: booktestPage,
    },
    //---책리스트-------------------------------------------------
    {
        path: "/recommendlist",
        name: "RecommendList",
        component: RecommendList,
    },
    {
        path: "/search",
        name: "SearchList",
        component: SearchList,
    },
    {
        path: "/best",
        name: "BestList",
        component: BestList,
    },
    {
        path: "/new",
        name: "NewList",
        component: NewList,
    },
    {
        path: "/detail/:bookNum",
        name: "bookDetail",
        component: bookDetail,
        props: true, // 라우트 파라미터를 컴포넌트의 프롭스로 전달
    },
    {
        path: "/startest",
        name: "writeStar",
        component: startest,
    },
    {
        path: "/goods",
        name: "Goods",
        component: Goods,
    },
    {
        path: "/categories/:category",
        name: "Categories",
        component: Categories,
        props: true, // 전달된 파라미터를 컴포넌트의 props로 전달
    },
    //-------------------------------------------------------------
    //장바구니 - 결제 - 결제완료
    {
        path: "/cart",
        name: "cartPage",
        component: cart,
    },
    {
        path: "/pay",
        name: "paymentPage",
        component: pay,
    },
    {
        path: "/result",
        name: "resultpage",
        component: resultpage,
    },
    //-------------------------------------------------------------
    //QNA, faq
    {
        path: "/qna",
        name: "qnaList",
        component: qnaList,
        beforeEnter: getQna(),
    },
    {
        path: "/qnaWrite",
        name: "qnaWrite",
        component: qnaWrite,
    },
    {
        path: "/qnaEdit/:id",
        name: "qnaEdit",
        component: qnaEdit,
        props: true,
    },
    {
        path: "/faq",
        name: "faq",
        component: faq,
    },
    //-------------------------------------------------------------
    //룰렛, 쿠폰주는 화면
    {
        path: "/event",
        name: "eventPage",
        component: eventPage,
    },
    // {
    //     path: "/event1",
    //     name: "eventPage1",
    //     component: eventPage1,
    // },
    // {
    //     path: "/roulette",
    //     name: "roulette",
    //     component: roulette,
    // },
    //-------------------------------------------------------------
    //관리자페이지
    {
        path: "/admin/bookinsert",
        name: "bookInsert",
        component: bookInsert,
    },
    {
        path: "/admin",
        name: "Admin",
        component: Admin,
    },
    {
        path: "/admin/bookmanage",
        name: "AdminBookManage",
        component: AdminBookManage,
    },
    {
        path: "/admin/usermanage",
        name: "AdminUserManage",
        component: AdminUserManage,
    },
    {
        path: "/admin/qnamanage",
        name: "AdminQnaManage",
        component: AdminQnaManage,
    },
    {
        path: "/admin/ordermanage",
        name: "AdminOrderManage",
        component: AdminOrderManage,
    },
    {
        path: "/admin/useranalyze",
        name: "AdminUserAnalyze",
        component: AdminUserAnalyze,
    },
    {
        path: "/admin/buyanalyze",
        name: "AdminBuyAnalyze",
        component: AdminBuyAnalyze,
    },
    //-------------------------------------------------------------
    //출석체크페이지
    {
        path: "/attendance",
        name: "attendanceEventPage",
        component: attendance,
    },

    //마이페이지
    {
        path: "/mypage",
        name: "mypage",
        component: mypage,
    },
    {
        path: "/mypagereview",
        name: "mypageReview",
        component: mypageReview,
    },
    {
        path: "/mypageorderinfo",
        name: "mypageOrderInfo",
        component: mypageOrderInfo,
    },
    {
        path: "/mypageuserinfo",
        name: "mypageUserInfo",
        component: mypageUserInfo,
    },
    {
        path: "/mygrade",
        name: "mypageGradePage",
        component: mypageGrade,
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    scrollBehavior() {
        return { top: 0 };
    },
});
export default router;
